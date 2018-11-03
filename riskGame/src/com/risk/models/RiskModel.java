/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * Represents the model of the game
 *
 * @author Nellybett
 */
public final class RiskModel extends Observable {

    /**
     * map a reference to the map of the game
     */
    private MapModel map;
    /**
     * players the list of players of the game
     */
    private LinkedList<PlayerModel> players;
    /**
     * turn reference with the player current turn
     */
    private int turn;
    /**
     * winningPlayer a reference to the player who won
     */
    private PlayerModel winningPlayer;
    /**
     * phase the current phase of the game
     */
    private GamePhase phase;
    /**
     * maxNbOfPlayers max number of players
     */
    static Integer maxNbOfPlayers = 6;
    /**
     * currentPlayer the player with the turn
     */
    private PlayerModel currentPlayer;
    /**
     * deck the deck of cards of the game
     */
    private LinkedList<CardModel> deck;

    /**
     * Constructor of the model It includes son random players
     */
    public RiskModel() {
        this.players = new LinkedList<>();
        this.turn = 0;
        this.phase = GamePhase.STARTUP;
        addPlayerToPlayerList("Player 1", Color.red, true);
        addPlayerToPlayerList("Player 2", Color.green, true);
        addPlayerToPlayerList("Player 3", Color.blue, true);
        this.currentPlayer = this.players.getFirst();

    }

    /**
     * It adds a human or AI player to the player list
     *
     * @param name the name of the player
     * @param color color of the player
     * @param isHuman true if it is human
     */
    public void addPlayerToPlayerList(String name, Color color, boolean isHuman) {
        if (isHuman) {
            players.add(new HumanPlayerModel(name, color, this));
        } else {
            players.add(new AIPlayerModel(name, color, this));
        }

        setChanged();
        notifyObservers();
    }

    /**
     * It removes a player from the list
     *
     * @param index the position in the list
     */
    public void removePlayer(int index) {
        players.remove(index);

        setChanged();
        notifyObservers();
    }

    /**
     * It removes a player from the list
     *
     * @param player the player to remove
     */
    public void removePlayer(PlayerModel player) {
        players.remove(player);
        if (players.size() == 1) {
            this.winningPlayer = this.players.getFirst();
            addNewEvent(this.getWinningPlayer().getName() + " win the game");
            addNewLogEvent(this.getWinningPlayer().getName() + " win the game");
            return;
        }

        setChanged();
        notifyObservers();
    }

    /**
     * It set the playerList attribute and the current player
     *
     * @param playerList the list of the players in this game
     */
    public void setPlayerList(LinkedList<PlayerModel> playerList) {
        this.players = playerList;
        this.currentPlayer = playerList.getFirst();

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the currentPlayer attribute
     *
     * @return currentPlayer
     */
    public PlayerModel getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter of the map attribute
     *
     * @param newMap new map to set
     */
    public void setMap(MapModel newMap) {
        this.map = newMap;
        this.initializeDeck();

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the board attribute
     *
     * @return board
     */
    public MapModel getMap() {
        return map;
    }

    /**
     * Assigns random countries to players
     */
    public void assignTerritoriesToPlayers() {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        List<TerritoryModel> terrLeft = new LinkedList<>(map.getTerritories());
        Collections.shuffle(terrLeft);

        int terrPerPlayer = (terrLeft.size() / players.size());

        players.stream().forEach((player) -> {
            List<TerritoryModel> ownerTerr = terrLeft.subList(0, terrPerPlayer);
            ownerTerr.stream().forEach((t) -> {
                t.setNumArmies(1);
            });
            player.setContriesOwned(ownerTerr);

            terrLeft.removeAll(new ArrayList<>(ownerTerr));
        });

        Random rnd = new Random();
        while (!terrLeft.isEmpty()) {
            int playerIndex = rnd.nextInt(players.size());
            TerritoryModel territoryAdded = terrLeft.remove(0);
            territoryAdded.setNumArmies(1);
            players.get(playerIndex).addCountryOwned(territoryAdded);
        }

        // update continents owned accordingly
        map.getContinents().forEach((c) -> {
            PlayerModel owner = c.getMembers().getFirst().getOwner();
            if (c.getMembers().stream()
                    .allMatch((t) -> (t.getOwner() == owner))) {
                owner.addContinentOwned(c);
            }
        });

        addNewLogEvent("Territories are assigned randomly to players");
    }

    public void attackMove(TerritoryModel src, TerritoryModel dest) {
        this.getCurrentPlayer().startAttackMove(src, dest);

        addNewLogEvent(String.format(
                "%s attacks the territory %s from the territory %s",
                currentPlayer.getName(),
                src.getName(),
                dest.getName()
        ));
    }

    public void tryFortificationMove(TerritoryModel src, TerritoryModel dest)
            throws FortificationMoveImpossible {

        checkFortificationMove(src, dest);

        src.decrementNumArmies();
        dest.incrementNumArmies();
        currentPlayer.setCurrentFortificationMove(src, dest);

        addNewLogEvent(String.format(
                "%s moves one army from territory %s to territory %s",
                currentPlayer.getName(),
                src.getName(),
                dest.getName()
        ));
    }

    private void checkFortificationMove(TerritoryModel src, TerritoryModel dest)
            throws FortificationMoveImpossible {

        if (!src.getAdj().contains(dest)) {
            throw new FortificationMoveImpossible(null);
        }

        if (!(currentPlayer.checkOwnTerritory(src)
                && currentPlayer.checkOwnTerritory(dest))) {
            throw new FortificationMoveImpossible(
                    "You don't own this country !");
        }

        FortificationMove attempted = new FortificationMove(src, dest);
        FortificationMove current = currentPlayer.getCurrentFortificationMove();
        if (attempted.compatible(current)) {
            throw new FortificationMoveImpossible(
                    "You can only make one move !");
        }

        if (src.getNumArmies() == 1) {
            throw new FortificationMoveImpossible(
                    "There is only one army in the source country !");
        }
    }

    /**
     * Getter of the maxNbOfPlayers attribute
     *
     * @return maxNbOfPlayers
     */
    public int getMaxNumberOfPlayers() {
        return maxNbOfPlayers;
    }

    /**
     * Getter of the players attribute
     *
     * @return players list
     */
    public List<PlayerModel> getPlayerList() {
        return Collections.unmodifiableList(this.players);
    }

    /**
     * Assigns turn to a player from the list
     */
    public void nextTurn() {
        this.setTurn((this.getTurn() + 1) % this.getPlayerList().size());
        this.setCurrentPlayer(this.getPlayerList().get(this.getTurn()));
    }

    /**
     * Changes the phase of the game
     */
    public void nextPhase() {
        this.setStage(this.getPhase().next());
    }

    /**
     * Getter of the turn attribute
     *
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Setter of the turn attribute
     *
     * @param turn the turn to set
     */
    public void setTurn(int turn) {
        this.turn = turn;

        setChanged();
        notifyObservers();
    }

    /**
     * Setter of the currentPlayer attribute
     *
     * @param currentPlayer the currentPlayer to set
     */
    private void setCurrentPlayer(PlayerModel currentPlayer) {
        this.currentPlayer.setCurrentPlayer(false);
        this.currentPlayer = currentPlayer;
        this.currentPlayer.setCurrentPlayer(true);

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the phase attribute
     *
     * @return the current phase of the game
     */
    public GamePhase getPhase() {
        return phase;
    }

    /**
     * Setter of the stage attribute
     *
     * @param stage the stage to set
     */
    private void setStage(GamePhase stage) {
        this.phase = stage;

        setChanged();
        notifyObservers();
    }

    /**
     * Initialize the initial number of armies for each player
     */
    private void initializePlayersArmies() {
        int nbArmies = PlayerModel.getNbInitialArmies(this.players.size());
        this.players.stream().forEach((player) -> {
            player.setNumArmiesAvailable(nbArmies);
        });

        addNewLogEvent(String.format(
                "Players receive %d armies each",
                nbArmies
        ));
    }

    /**
     * Getter of the deck attribute
     *
     * @return the deck
     */
    public LinkedList<CardModel> getDeck() {
        return deck;
    }

    /**
     * Fill up the deck with cards generated from the territories in the map
     */
    public void initializeDeck() {
        this.deck = new LinkedList();
        int i = 0;
        for (String country : this.getMap().getTerritoryList()) {

            switch (i) {
                case 0:
                    this.deck.add(new CardModel(country, "infantry"));
                    break;
                case 1:
                    this.deck.add(new CardModel(country, "cavalry"));
                    break;
                case 2:
                    this.deck.add(new CardModel(country, "artillery"));
                    break;
            }
            i = (i + 1) % 3;

        }
        shuffleDeck();

        setChanged();
        notifyObservers();
    }

    /**
     * Change the order of the cards
     */
    private void shuffleDeck() {
        Collections.shuffle(this.getDeck());

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the winningPlayer attribute
     *
     * @return the player who wins
     */
    public PlayerModel getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * Setter of the winningPlayer attribute
     *
     * @param winningPlayer the player who wins
     */
    public void setWinningPlayer(PlayerModel winningPlayer) {
        this.winningPlayer = winningPlayer;

        setChanged();
        notifyObservers();
    }

    /**
     * It validates that the number of territories is bigger than the number of
     * players
     *
     * @return true if there is as many territories as players; false if it is
     * not true
     */
    public boolean validateCountries() {
        return (map.getTerritories().size() >= players.size());
    }

    /**
     * Finish the current stage of the game and initialize for the next stage of
     * the game
     *
     * @return True if the game is not over
     */
    public boolean finishPhase() {
        if (this.getWinningPlayer() != null) {
            return false;
        }

        executeEndOfPhaseSteps();
        this.nextPhase();
        executeBeginningOfPhaseSteps();

        setChanged();
        notifyObservers();

        return true;
    }

    /**
     * Final steps after finishing a phase
     */
    private void executeEndOfPhaseSteps() {
        switch (this.getPhase()) {
            case STARTUP:
                Random rand = new Random();
                players.stream().forEach((pl) -> {
                    while (pl.getNumArmiesAvailable() != 0) {
                        int randTerr = rand.nextInt(pl.getNbCountriesOwned());
                        try {
                            placeArmy(pl, pl.getContriesOwned().get(randTerr));
                        } catch (ArmyPlacementImpossible ex) {
                        }
                    }
                });
                break;
            case REINFORCEMENT:
                break;
            case ATTACK:
                this.getCurrentPlayer().addCardToPlayerHand();
                this.getCurrentPlayer().setCurrentAttack(null);
                checkForDeadPlayers();
                attackEndValidations();
                break;
            case FORTIFICATION:
                this.getCurrentPlayer().resetCurrentFortificationMove();
                this.nextTurn();
                break;
        }

        setChanged();
        notifyObservers();
    }

    public void attackEndValidations() {
        if ((this.getCurrentPlayer().getContriesOwned().stream()
                .filter(c -> c.getNumArmies() < 2)).count() == this.getCurrentPlayer().getContinentsOwned().size()) {
            finishPhase();
        }

        if (this.getCurrentPlayer().getContriesOwned().size() == this.getMap().getTerritories().size()) {
            this.setWinningPlayer(this.getCurrentPlayer());
            this.finishPhase();
        }

    }

    /**
     * Steps at the beginning of a phase
     */
    private void executeBeginningOfPhaseSteps() {
        switch (this.getPhase()) {
            case STARTUP:
                break;
            case REINFORCEMENT:
                this.getCurrentPlayer().reinforcement(this);
                //riskView.cardExchangeMenu(modelRisk, riskController);
                break;
            case ATTACK:
                this.getCurrentPlayer().attack(this);
                break;
            case FORTIFICATION:
                this.getCurrentPlayer().fortification(this);
                break;
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Check if any player has no more territories owned and remove these player
     * from the game
     */
    private void checkForDeadPlayers() {
        List<PlayerModel> previousPlayerList = new ArrayList<>(players);
        previousPlayerList.stream()
                .filter(p -> p.getNbCountriesOwned() == 0)
                .forEach((p) -> {
                    this.removePlayer(p);
                    addNewLogEvent(String.format(
                            "%s has no more territories, "
                            + "it is eliminated from the game",
                            p.getName()
                    ));
                });
    }

    /**
     * Place an army from the given player on the given territory.
     *
     * @param player Player whose army is going to be taken
     * @param territory Territory on which the army will be added
     * @throws com.risk.models.RiskModel.ArmyPlacementImpossible
     */
    public void placeArmy(PlayerModel player, TerritoryModel territory)
            throws ArmyPlacementImpossible {
        if (player.getNumArmiesAvailable() <= 0) {
            throw new ArmyPlacementImpossible(
                    "You have no armies left to deploy !");
        }
        if (player.checkOwnTerritory(territory) == false) {
            throw new ArmyPlacementImpossible("You don't own this country !");
        }

        territory.incrementNumArmies();
        player.decrementNumArmiesAvailable();

        addNewLogEvent(String.format(
                "%s place one army on territory %s",
                player.getName(),
                territory.getName()
        ));
    }

    public static class FortificationMoveImpossible extends Exception {

        private final String reason;

        /**
         * Constructor
         *
         * @param reason The reason why the fortification move is not possible
         */
        public FortificationMoveImpossible(String reason) {
            this.reason = reason;
        }

        /**
         * Getter for reason attribute
         *
         * @return the reason attribute
         */
        public String getReason() {
            return reason;
        }
    }

    public static class ArmyPlacementImpossible extends Exception {

        private final String reason;

        /**
         * Constructor
         *
         * @param reason The reason why the army placement is not possible
         */
        public ArmyPlacementImpossible(String reason) {
            this.reason = reason;
        }

        /**
         * Getter for reason attribute
         *
         * @return the reason attribute
         */
        public String getReason() {
            return reason;
        }
    }

    /**
     *
     * @return
     */
    public boolean exchangeCardsWithArmiesForCurrentPlayer() {

        boolean res = this.getCurrentPlayer().exchangeCardsToArmies();

        setChanged();
        notifyObservers();

        return !res;
    }

    /**
     * If the destination country have 0 armies it is conquered
     *
     * @param armies the number of armies to move
     */
    public void moveArmiesToConqueredTerritory(int armies) {
        this.getCurrentPlayer().conquerCountry(armies);

        addNewLogEvent(String.format(
                "%s move %d armies to the newly conquered territory",
                currentPlayer.getName(),
                armies
        ));
    }

    public void startGame() {
        this.setWinningPlayer(null);
        this.assignTerritoriesToPlayers();
        this.initializePlayersArmies();

        addNewLogEvent("The game starts", true);
        this.currentPlayer.setCurrentPlayer(true);
    }

    /**
     * Battle between countries in an attack move
     *
     * @param attacker attacking player
     * @param dice number of dice
     */
    public void performAttack(PlayerModel attacker, int dice) {
        attacker.performCurrentAttack(dice);

        setChanged();
        notifyObservers();
    }

    /**
     * Inform observers that an event occurred in the RiskGame
     *
     * @param eventMessage message to describe the event (Used in the view to
     * display it)
     */
    public void addNewEvent(String eventMessage) {
        setChanged();
        notifyObservers(eventMessage);
    }

    /**
     * Notify observer of a new event that can be displayed in the logs
     *
     * @param logMessage Message describing this event
     */
    public void addNewLogEvent(String logMessage) {
        addNewLogEvent(logMessage, false);
    }

    /**
     * Notify observer of a new event that can be displayed in the logs
     *
     * @param logMessage Message describing this event
     * @param clear true if this event should clear previous log messages
     */
    private void addNewLogEvent(String logMessage, boolean clear) {
        setChanged();
        notifyObservers(new LogEvent(logMessage, clear));
    }
}
