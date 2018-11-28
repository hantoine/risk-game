/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.controllers.TournamentSaverInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import javax.swing.Timer;

/**
 * Represents the model of the game
 *
 * @author Nellybett
 */
public final class RiskModel extends Observable implements Serializable {

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
     * It is attack or defense of the attack phase
     */
    private boolean attackPhase;
    /**
     * Contains the log message for events that happened in this event
     */
    private LogModel log;

    /**
     * Waiting time between computer played phases
     */
    private int interPhaseTime;

    /**
     * Number of turn left before draw;
     */
    int nbTurnBeforeDraw;

    /**
     * To restore logs when loading a game from file
     */
    private LinkedList<String> logsBackup;

    /**
     * In charge of writing all logs from this game to a log file
     */
    transient private LogWriter logWriter;

    /**
     * Object in charge of saving the tournament progress
     */
    transient TournamentSaverInterface tournamentSaver;

    /**
     * Constructor of the model It includes son random players
     */
    public RiskModel() {
        logsBackup = new LinkedList<>();
        this.players = new LinkedList<>();
        this.turn = 0;
        this.phase = GamePhase.STARTUP;
        this.attackPhase = true;
        addPlayerToPlayerList("Player 1", Color.red);
        addPlayerToPlayerList("Player 2", Color.green);
        addPlayerToPlayerList("Player 3", Color.blue);
        this.currentPlayer = this.players.getFirst();
        this.log = new LogModel();
        this.interPhaseTime = 600;
        this.nbTurnBeforeDraw = Integer.MAX_VALUE;

    }

    /**
     * Before new game changes
     */
    public void reset() {
        this.players.clear();
        addPlayerToPlayerList("Player 1", Color.red);
        addPlayerToPlayerList("Player 2", Color.green);
        addPlayerToPlayerList("Player 3", Color.blue);
        this.currentPlayer = this.players.getFirst();
        this.turn = 0;
        this.phase = GamePhase.STARTUP;
    }

    public void setNbTurnBeforeDraw(int nbTurnBeforeDraw) {
        this.nbTurnBeforeDraw = nbTurnBeforeDraw;
    }

    /**
     * Set logs to be saved into the model before serializing the model
     *
     * @param logsBackup list of backup logs
     */
    public void setSavedLogs(LinkedList<String> logsBackup) {
        this.logsBackup = logsBackup;
    }

    /**
     * Get saved logs of a game being loaded
     *
     * @return list of logs being currently displayed
     */
    public LinkedList<String> getLogs() {
        return this.logsBackup;
    }

    /**
     * It adds a human or AI player to the player list
     *
     * @param name the name of the player
     * @param color color of the player
     */
    public void addPlayerToPlayerList(String name, Color color) {

        PlayerModel pl = PlayerFactory.getPlayer("HUMAN", name, color);
        pl.setGame(this);
        players.add(pl);

        setChanged();
        notifyObservers();
    }

    /**
     * It removes a player from the list
     *
     * @param index the position in the list
     */
    void removePlayer(int index) {
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
            this.setWinningPlayer(this.players.getFirst());
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

        this.players.forEach(p -> {
            p.setGame(this);
        });

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
     * Return true if the game finished with a draw
     *
     * @return boolean
     */
    public boolean isDraw() {
        return this.nbTurnBeforeDraw <= 0;
    }

    /**
     * Assigns random territories to players
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
            players.get(playerIndex).addTerritoryOwned(territoryAdded);
        }

        addNewLogEvent("Territories are assigned randomly to players");
    }

    /**
     * This method is to do implement the attack move
     *
     * @param src the previous territory model
     * @param dest the next territory model
     */
    public void attackMove(TerritoryModel src, TerritoryModel dest) {
        this.getCurrentPlayer().startAttackMove(src, dest);
        addNewLogEvent(String.format(
                "%s attacks the territory %s from the territory %s",
                currentPlayer.getName(),
                dest.getName(),
                src.getName()
        ));
    }

    /**
     * This method is to do implement the fortification move
     *
     * @param src the territory model which is gonna be checked
     * @param dest the territory model which is gonna be checked
     * @throws FortificationMoveImpossible
     * com.risk.models.RiskModel.FortificationMoveImpossible
     */
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

    /**
     * The method is for check the fortification move
     *
     * @param src the territory model which is gonna be checked
     * @param dest the territory model which is gonna be checked
     * @throws FortificationMoveImpossible
     * com.risk.models.RiskModel.FortificationMoveImpossible
     */
    private void checkFortificationMove(TerritoryModel src, TerritoryModel dest)
            throws FortificationMoveImpossible {

        if (!src.getAdj().contains(dest)) {
            throw new FortificationMoveImpossible(null);
        }

        if (!(currentPlayer.checkOwnTerritory(src)
                && currentPlayer.checkOwnTerritory(dest))) {
            throw new FortificationMoveImpossible(
                    "You don't own this territory !");
        }

        FortificationMove attempted = new FortificationMove(src, dest);
        FortificationMove current = currentPlayer.getCurrentFortificationMove();
        if (attempted.compatible(current)) {
            throw new FortificationMoveImpossible(
                    "You can only make one move !");
        }

        if (src.getNumArmies() == 1) {
            throw new FortificationMoveImpossible(
                    "There is only one army in the source territory !");
        }
    }

    /**
     * @return the isAttackPhase
     */
    public boolean isAttackPhase() {
        return attackPhase;
    }

    /**
     * @param attackPhase attack phase initialize indicator
     */
    public void setAttackPhase(boolean attackPhase) {
        this.attackPhase = attackPhase;

        if (attackPhase == false && this.currentPlayer.getCurrentAttack() != null) {
            this.currentPlayer.getCurrentAttack().getDefensePlayer().defense();
        }

        setChanged();
        notifyObservers(this);
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
     * Reinforcement for computer players
     */
    public void aIReinforcement() {
        getCurrentPlayer().setHanded(false);
        getCurrentPlayer().assignNewArmies();

        while (getCurrentPlayer().getHand().cardHandingPossible()) {
            getCurrentPlayer().exchangeCardsToArmies();
        }
    }

    /**
     * Intent of reinforcement
     *
     * @param selectedTerritory the territory to place an army
     */
    public void reinforcementIntent(TerritoryModel selectedTerritory) {
        try {
            placeArmy(currentPlayer, selectedTerritory);
            if (currentPlayer.getNbArmiesAvailable() == 0) {
                finishPhase();
            }
        } catch (RiskModel.ArmyPlacementImpossible ex) {
            addNewEvent(ex.getReason());
        }
    }

    /**
     * Intent of startup move
     *
     * @param territoryClicked the territory to place an army
     */
    public void startupMove(TerritoryModel territoryClicked) {
        try {
            placeArmy(this.currentPlayer, territoryClicked);
            nextTurn();
            executeBeginningOfPhaseSteps();
            if (this.currentPlayer.getNbArmiesAvailable() == 0 && getTurn() == 0) {
                finishPhase();
            }
        } catch (RiskModel.ArmyPlacementImpossible ex) {
            addNewEvent(ex.getReason());
        }
    }

    /**
     * Intent of fortification
     *
     * @param source source territory
     * @param dest destination territory
     */
    public void fortificationIntent(TerritoryModel source, TerritoryModel dest) {

        try {
            tryFortificationMove(source, dest);

        } catch (RiskModel.FortificationMoveImpossible ex) {
            if (ex.getReason() != null) {
                addNewEvent(ex.getReason());
            }
        }
    }

    /**
     * Intent of attack
     *
     * @param sourceTerritory source of the attack
     * @param destTerritory destiny of the attack
     */
    public void attackIntent(TerritoryModel sourceTerritory, TerritoryModel destTerritory) {
        int result = getCurrentPlayer().validateAttack(sourceTerritory, destTerritory);

        if (result == 0) {
            attackMove(sourceTerritory, destTerritory);
        } else {
            switch (result) {
                case -1:
                    this.addNewEvent("The territory is not adjacent.");
                    break;
                case -2:
                    this.addNewEvent("You are already attacking.");
                    break;
                case -3:
                    this.addNewEvent("Invalid movement");
                    break;
                case -4:
                    this.addNewEvent("You can't attack with only one armie");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Assigns turn to a player from the list
     */
    public void nextTurn() {
        if (this.getWinningPlayer() != null || this.nbTurnBeforeDraw <= 1) {
            this.nbTurnBeforeDraw--;
            return;
        }

        this.setTurn((this.getTurn() + 1) % this.getPlayerList().size());
        this.setCurrentPlayer(this.getPlayerList().get(this.getTurn()));

        // Integer.MAX_VALUE means no limit for the number of turn
        if (this.nbTurnBeforeDraw != Integer.MAX_VALUE
                // Startup phase does not count
                && this.getPhase() != GamePhase.STARTUP) {
            this.nbTurnBeforeDraw--;
        }
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
    public void initializePlayersArmies() {
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
        for (String territory : this.getMap().getTerritoryList()) {

            switch (i) {
                case 0:
                    this.deck.add(new CardModel(territory, "infantry"));
                    break;
                case 1:
                    this.deck.add(new CardModel(territory, "cavalry"));
                    break;
                case 2:
                    this.deck.add(new CardModel(territory, "artillery"));
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
        /* first make sure the map is updated to correctly display all
            territory are owned */
        setChanged();
        notifyObservers();

        this.winningPlayer = winningPlayer;
        if (winningPlayer != null) {
            addNewEvent(this.getWinningPlayer().getName() + " win the game");
            addNewLogEvent(this.getWinningPlayer().getName() + " win the game");
            if (this.logWriter != null) {
                this.logWriter.close();
            }
        } else {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * It validates that the number of territories is bigger than the number of
     * players
     *
     * @return true if there is as many territories as players; false if it is
     * not true
     */
    public boolean validateTerritories() {
        return (map.getTerritories().size() >= players.size());
    }

    /**
     * Finish the current stage of the game and initialize for the next stage of
     * the game
     *
     */
    public void finishPhase() {
        if (this.getWinningPlayer() != null) {
            return;
        }

        executeEndOfPhaseSteps();
        this.nextPhase();

        if (this.tournamentSaver != null) {
            this.tournamentSaver.saveTournament();
        }

        continueGame();
    }

    /**
     * Continue the execution of the game
     */
    public void continueGame() {
        if (this.getWinningPlayer() == null && this.nbTurnBeforeDraw > 0) {
            executeBeginningOfPhaseSteps();
        } else {
            this.addNewLogEvent("No player has won before the maximum number of"
                    + " turn, this game is a draw");
            if (this.logWriter != null) {
                this.logWriter.close();
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Execute an attack for IA
     */
    public void executeAttack() {
        this.currentPlayer.attack(this);
    }

    /**
     * Select a random territory from a list
     *
     * @param listTerritories the list of territories
     * @return a territory from the list
     */
    public TerritoryModel randomTerritory(List<TerritoryModel> listTerritories) {
        if (listTerritories.isEmpty()) {
            return null;
        }

        int range = listTerritories.size();
        return listTerritories.get((int) (Math.random() * range));
    }

    /**
     * Final steps after finishing a phase
     */
    private void executeEndOfPhaseSteps() {
        switch (this.getPhase()) {
            case STARTUP:
                Random rand = new Random();
                players.stream().forEach((pl) -> {
                    while (pl.getNbArmiesAvailable() != 0) {
                        int randTerr = rand.nextInt(pl.getNbTerritoriesOwned());
                        try {
                            placeArmy(pl, pl.getTerritoryOwned().get(randTerr));
                        } catch (ArmyPlacementImpossible ex) {
                        }
                    }
                });
                break;
            case REINFORCEMENT:
                break;
            case ATTACK:
                if (this.currentPlayer.isConquered()) {
                    this.getCurrentPlayer().addCardToPlayerHand();
                }

                this.getCurrentPlayer().setConquered(false);
                this.getCurrentPlayer().setCurrentAttack(null);

                break;
            case FORTIFICATION:
                this.getCurrentPlayer().resetCurrentFortificationMove();
                this.nextTurn();
                break;
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Steps at the beginning of a phase
     */
    void executeBeginningOfPhaseSteps() {
        if (this.getCurrentPlayer().getStrategy() instanceof HumanStrategy) {
            this.executeBeginningOfPhaseStepsNow();
        } else {
            this.executeBeginningOfPhaseStepsLater();
        }
    }

    /**
     * Beginning of phase with delay
     */
    void executeBeginningOfPhaseStepsLater() {
        Timer timer = new Timer(
                (this.phase != GamePhase.STARTUP)
                        ? this.interPhaseTime
                        : (int) (this.interPhaseTime * 0.1),
                (ActionEvent ae) -> {
                    this.executeBeginningOfPhaseStepsNow();
                });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Beginning of game without delay
     */
    public void executeBeginningOfPhaseStepsNow() {
        addNewLogEvent("", true);
        switch (this.getPhase()) {
            case STARTUP:
                this.getCurrentPlayer().startup(this);
                break;
            case REINFORCEMENT:
                this.getCurrentPlayer().reinforcement(this);
                break;
            case ATTACK:
                addNewLogEvent(String.format(
                        "%s starts its attack phase",
                        this.getCurrentPlayer().getName()
                ));
                this.getCurrentPlayer().attack(this);
                break;
            case FORTIFICATION:
                this.getCurrentPlayer().fortification(this);
                break;
        }

        setChanged();
        notifyObservers();
    }

    public void setInterPhaseTime(int interPhaseTime) {
        this.interPhaseTime = interPhaseTime;
    }

    /**
     * Check if any player has no more territories owned and remove these player
     * from the game
     */
    public void checkForDeadPlayers() {
        List<PlayerModel> previousPlayerList = new ArrayList<>(players);
        previousPlayerList.stream()
                .filter(p -> p.getNbTerritoriesOwned() == 0)
                .forEach((p) -> {
                    this.currentPlayer.stealCardsFrom(p);
                    addNewLogEvent(String.format(
                            "%s has no more territories, "
                            + "it is eliminated from the game",
                            p.getName()
                    ));
                    this.removePlayer(p);
                });

        /*
         The position of the current player in the list might have changed.
         The currentPlayer cannot be the deleted one since this method is
         called after the current Player conquers a territory, so newPos != -1
         */
        int newPos = this.getPlayerList().indexOf(this.currentPlayer);
        this.setTurn(newPos);
    }

    /**
     * Place an army from the given player on the given territory.
     *
     * @param player Player whose army is going to be taken
     * @param territory Territory on which the army will be added
     * @throws com.risk.models.RiskModel.ArmyPlacementImpossible exception for
     * bad assignation of armies
     */
    public void placeArmy(PlayerModel player, TerritoryModel territory)
            throws ArmyPlacementImpossible {
        if (player.getNbArmiesAvailable() <= 0) {
            throw new ArmyPlacementImpossible(
                    "You have no armies left to deploy !");
        }
        if (player.checkOwnTerritory(territory) == false) {
            throw new ArmyPlacementImpossible("You don't own this territory !");
        }

        territory.incrementNumArmies();
        player.decrementNumArmiesAvailable();

        addNewLogEvent(String.format(
                "%s place one army on territory %s",
                player.getName(),
                territory.getName()
        ));
    }

    /**
     * Exception for fortification movement
     */
    public static class FortificationMoveImpossible extends Exception {

        /**
         * Reason why the fortification is not possible
         */
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

    /**
     * Exception for army placement
     */
    public static class ArmyPlacementImpossible extends Exception {

        /**
         * Reason why an army placement is not possible
         */
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
     * Exchange cards to armies
     *
     * @return true if correct; false if error
     */
    public boolean exchangeCardsWithArmiesForCurrentPlayer() {

        boolean res = this.getCurrentPlayer().exchangeCardsToArmies();

        setChanged();
        notifyObservers();

        return !res;
    }

    /**
     * If the destination territory have 0 armies it is conquered
     *
     * @param armies the number of armies to move
     */
    public void moveArmiesToConqueredTerritory(int armies) {

        this.getCurrentPlayer().conquerTerritory(armies);

        addNewLogEvent(String.format(
                "%s move %d armies to the newly conquered territory",
                currentPlayer.getName(),
                armies
        ));

        if (!checkAttackpossible()) {
            this.finishPhase();
        }
    }

    /**
     * Check if an attack move is possible for the current Player
     *
     * @return True if an attack move is possible for the current Player
     */
    private boolean checkAttackpossible() {
        return this.getCurrentPlayer().getTerritoryOwned().stream()
                .filter((c) -> c.getNumArmies() > 1)
                .anyMatch(
                        (c) -> c.getAdj().stream().anyMatch(
                                (oc) -> oc.getOwner() != this.currentPlayer
                        )
                );
    }

    /**
     * Beginning of the game
     */
    public void startGame() {
        this.setWinningPlayer(null);
        this.setStage(GamePhase.STARTUP);
        this.assignTerritoriesToPlayers();
        this.initializePlayersArmies();

        addNewLogEvent("The game starts", true);
        this.currentPlayer.setCurrentPlayer(true);
        this.executeBeginningOfPhaseStepsNow();
    }

    /**
     * Battle between territories in an attack move
     *
     * @param attacker attacking player
     */
    public void performAttack(PlayerModel attacker) {
        attacker.performCurrentAttack(
                this.getCurrentPlayer().getCurrentAttack().getNbDiceAttack(),
                this.getCurrentPlayer().getCurrentAttack().getNbDiceDefense()
        );

        setChanged();
        notifyObservers();

        if (this.currentPlayer.getCurrentAttack() == null // attack finished
                && !checkAttackpossible()) {
            this.finishPhase();
        }
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
     * Setter for logWriter attribute
     *
     * @param logWriter log writer to set
     */
    public void setLogWriter(LogWriter logWriter) {
        this.logWriter = logWriter;
    }

    /**
     * Notify observer of a new event that can be displayed in the logs
     *
     * @param logMessage Message describing this event
     * @param clear true if this event should clear previous log messages
     */
    void addNewLogEvent(String logMessage, boolean clear) {
        if (clear) {
            log.clear();
        }
        log.addLogEntry(logMessage);
        if (logWriter != null) {
            logWriter.log(logMessage);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Continue the attack with the given number of dices
     *
     * @param nbDice the number of dices to use for the attack
     */
    public void continueAttack(int nbDice) {
        AttackMove attackMove = this.getCurrentPlayer().getCurrentAttack();

        if (nbDice == -1 //battleAll
                || attackMove.getDest().getNumArmies() == 1) {

            this.getCurrentPlayer().setAttackValues(nbDice);
            this.getCurrentPlayer().setDefenseValues(1);

            this.performAttack(this.getCurrentPlayer());

            this.getCurrentPlayer().moveArmies();
            this.setAttackPhase(true);
            if (this.getPhase() == GamePhase.ATTACK) {
                this.executeAttack();
            }
        } else {
            this.getCurrentPlayer().setAttackValues(nbDice);
            this.setAttackPhase(false);
        }
    }

    /**
     * Getter of the log
     *
     * @return log
     */
    public String getLogContent() {
        return this.log.getContent();
    }

    /**
     * Setter for the tournament saver attribute
     *
     * @param tournamentSaver The tournament saver to be set
     */
    public void setTournamentSaver(TournamentSaverInterface tournamentSaver) {
        this.tournamentSaver = tournamentSaver;
    }
}
