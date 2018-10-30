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
import java.util.Random;

/**
 * Represents the model of the game
 *
 * @author Nellybett
 */
public final class RiskModel {

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

    }

    /**
     * It removes a player from the list
     *
     * @param index the position in the list
     */
    public void removePlayer(int index) {
        players.remove(index);
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
        }
    }

    /**
     * It set the playerList attribute and the current player
     *
     * @param playerList the list of the players in this game
     */
    public void setPlayerList(LinkedList<PlayerModel> playerList) {
        this.players = playerList;
        this.currentPlayer = playerList.getFirst();
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
    public void assignCoutriesToPlayers() {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        List<TerritoryModel> countriesLeft = new ArrayList<>(map.getGraphTerritories().values());
        Collections.shuffle(countriesLeft);

        int countriesPerPlayer = (countriesLeft.size() / players.size());

        players.stream().forEach((player) -> {
            List<TerritoryModel> ownedCountries = countriesLeft.subList(0, countriesPerPlayer);
            ownedCountries.stream().forEach((t) -> {
                t.setNumArmies(1);
            });
            player.setContriesOwned(ownedCountries);
            countriesLeft.removeAll(ownedCountries);
        });

        Random rnd = new Random();
        while (!countriesLeft.isEmpty()) {
            int playerIndex = rnd.nextInt(players.size());
            TerritoryModel territoryAdded = countriesLeft.remove(0);
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

    }

    public void tryFortificationMove(TerritoryModel src, TerritoryModel dest)
            throws FortificationMoveImpossible {

        checkFortificationMove(src, dest);

        src.decrementNumArmies();
        dest.incrementNumArmies();
        currentPlayer.setCurrentFortificationMove(src, dest);
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
    public LinkedList<PlayerModel> getPlayerList() {
        return this.players;
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
    }

    /**
     * Setter of the currentPlayer attribute
     *
     * @param currentPlayer the currentPlayer to set
     */
    public void setCurrentPlayer(PlayerModel currentPlayer) {
        this.currentPlayer = currentPlayer;
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
    }

    /**
     * Initialize the initial number of armies for each player
     */
    public void initializePlayersArmies() {
        this.players.stream().forEach((player) -> {
            player.initializeArmies(this.players.size());
        });
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
        for (String country : this.getMap().getGraphTerritories().keySet()) {

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
    }

    /**
     * Change the order of the cards
     */
    public void shuffleDeck() {
        Collections.shuffle(this.getDeck());
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
    }

    /**
     * It validates that the number of territories is bigger than the number of
     * players
     *
     * @return true if there is as many territories as players; false if it is
     * not true
     */
    public boolean validateCountries() {
        return (map.getGraphTerritories().values().size() >= players.size());
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
            // this should be triggered by the view itself when RiskView is updated and a winning player is set
            //            riskView.showMessage("The player " + this.modelRisk.getWinningPlayer().getName() + " has won the game");

        }

        executeEndOfPhaseSteps();
        this.nextPhase();
        executeBeginningOfPhaseSteps();

        return true;
    }

    /**
     * Final steps after finishing a phase
     */
    private void executeEndOfPhaseSteps() {
        switch (this.getPhase()) {
            case STARTUP:
                break;
            case REINFORCEMENT:
                break;
            case ATTACK:
                checkForDeadPlayers();
                break;
            case FORTIFICATION:
                this.getCurrentPlayer().resetCurrentFortificationMove();
                this.nextTurn();
                break;
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
                break;
            case ATTACK:
                try {
                    this.getCurrentPlayer().attack(this);
                } catch (UnsupportedOperationException e) {
                    //since attack is not implemented yet, we skip it
                    this.finishPhase();
                }

                this.getCurrentPlayer().addCardToPlayerHand();
                break;
            case FORTIFICATION:
                this.getCurrentPlayer().fortification(this);
                break;
        }
    }

    /**
     * Check if any player has no more territories owned and remove these player
     * from the game
     */
    private void checkForDeadPlayers() {
        players.stream()
                .filter(p -> p.getNbCountriesOwned() == 0)
                .forEach((p) -> {
                    /*
                    this.riskView.showMessage(String.format(
                            "The player %s has no more territories, it is eliminated from the game !",
                            p.getName())
                    );//*///setchanged and notifyObserver with the dead player as a parameter
                    this.removePlayer(p);
                });
    }

    /**
     * Place an army from the given player on the given territory.
     *
     * @param player Player whose army is going to be taken
     * @param territory Territory on which the army will be added
     * @throws com.risk.models.RiskModel.ArmyPlacementImpossible
     */
    public void placeArmy(PlayerModel player, TerritoryModel territory) throws ArmyPlacementImpossible {
        if (player.getNumArmiesAvailable() <= 0) {
            throw new ArmyPlacementImpossible("You have no armies left to deploy !");
        }
        if (player.checkOwnTerritory(territory) == false) {
            throw new ArmyPlacementImpossible("You don't own this country !");
        }

        territory.incrementNumArmies();
        player.decrementNumArmiesAvailable();
    }

    public static class FortificationMoveImpossible extends Exception {

        private final String reason;

        public FortificationMoveImpossible(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }

    public static class ArmyPlacementImpossible extends Exception {

        private final String reason;

        public ArmyPlacementImpossible(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }
}
