/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.interfaces.PlayerModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.util.LinkedList;

/**
 * Represents the model of the game
 *
 * @author Nellybett
 */
public final class RiskModel {

    private MapModel board;
    private LinkedList<PlayerModel> players;
    private int turn;
    private GameStage stage;
    static Integer maxNbOfPlayers = 6;
    private PlayerModel currentPlayer;
    private LinkedList<CardModel> deck;

    /**
     * Constructor of the model It includes son random players
     */
    public RiskModel() {
        this.players = new LinkedList<>();
        this.turn = -1;
        this.stage = GameStage.START;
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
            players.add(new HumanPlayerModel(name, color, isHuman));

        } else {
            players.add(new AIPlayerModel(name, color, isHuman));
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
     * It set the playerList attribute and the current player
     *
     * @param playerList
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
    public int setBoard(String path) {
        this.board = new MapModel();
        MapFileManagement aux = new MapFileManagement();
        int result = aux.createBoard(path, this.board);
        if (result == 0) {
            this.setDeck();
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
        int result = fileManagement.generateBoardFile(fileContent, this.board);

    }

    /**
     * Getter of the board attribute
     *
     * @return board
     */
    public MapModel getBoard() {
        return board;
    }

    /**
     * Assigns random countries to players
     */
    public void assignCoutriesToPlayers() {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        List<TerritoryModel> countriesLeft = new ArrayList<>(board.getGraphTerritories().values());
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

        if (this.getTurn() + 1 < this.getPlayerList().size()) {
            this.setTurn(this.getTurn() + 1);
            this.setCurrentPlayer(this.getPlayerList().get(this.getTurn()));
            System.out.println("En el modelo--" + this.getCurrentPlayer().getName());
        } else {
            this.setTurn(0);
            this.setCurrentPlayer(this.getPlayerList().get(this.getTurn()));
        }

    }

    /**
     * Changes the stage/phase of the game
     */
    public void nextStage() {
        this.setStage(this.getStage().next());
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
     * Getter of the stage attribute
     *
     * @return the stage
     */
    public GameStage getStage() {
        return stage;
    }

    /**
     * Setter of the stage attribute
     *
     * @param stage the stage to set
     */
    public void setStage(GameStage stage) {
        this.stage = stage;
    }

    /**
     * Initialize the initial number of armies for each player
     */
    public void initializePlayers() {
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
     * Setter of the deck attribute
     */
    public void setDeck() {
        this.deck = new LinkedList();
        int i = 0;
        for (String country : this.getBoard().getGraphTerritories().keySet()) {
            if (i <= 14) {
                this.deck.add(new CardModel(country, "Infantry"));
                this.deck.add(new CardModel(country, "Cavalry"));
                this.deck.add(new CardModel(country, "Artillery"));
            } else {
                break;
            }
            i++;
        }
        shuffleDeck();
    }

    /**
     * Change the order of the cards
     */
    public void shuffleDeck() {
        Collections.shuffle(this.getDeck());
    }
}
