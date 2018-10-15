/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.interfaces.PlayerModel;
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
     * turn  reference with the player current turn
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
     * Setter of the board attribute from a file
     *
     * @param path path of the file
     * @return 0 success, -1--6 error
     */
    public int loadMap(String path) {
        this.map = new MapModel();
        int result = new MapFileManagement().createBoard(path, this.map);
        if (result == 0) {
            this.initializeDeck();
        }
        return result;
    }

    /**
     * Creation of a map file from a board
     *
     * @param fileContent path where the file content is going to be
     */
    public void createFile(String fileContent) {
        MapFileManagement fileManagement = new MapFileManagement();
        int result = fileManagement.generateBoardFile(fileContent, this.map);

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
            player.setContriesOwned(ownedCountries);
            countriesLeft.removeAll(ownedCountries);
        });

        Random rnd = new Random();
        while (!countriesLeft.isEmpty()) {
            int playerIndex = rnd.nextInt(players.size());
            players.get(playerIndex).addCountryOwned(countriesLeft.remove(0));
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
    public void setStage(GamePhase stage) {
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
     * @return 
     */
    public PlayerModel getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * Setter of the winningPlayer attribute
     * @param winningPlayer 
     */
    public void setWinningPlayer(PlayerModel winningPlayer) {
        this.winningPlayer = winningPlayer;
    }
}
