/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.exceptions.FormatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Nellybett
 */
public class RiskModel {

    private MapModel board;
    private LinkedList<PlayerModel> players;
    private int turn;
    private int stage;
    static Integer maxNbOfPlayers = 6;
    private PlayerModel currentPlayer;

    public RiskModel() {
        this.players = new LinkedList<>();
        this.turn=-1;
        this.stage=-1;
        addPlayerToPlayerList("Player 1", Color.red, true);
        addPlayerToPlayerList("Player 2", Color.green, true);
        addPlayerToPlayerList("Player 3", Color.blue, false);

        this.currentPlayer = this.players.getFirst();

    }

    public void addPlayerToPlayerList(String name, Color color, boolean isHuman) {
        players.add(new PlayerModel(name, color, isHuman));
    }

    public void removePlayer(int index) {
        players.remove(index);
    }

    public void setPlayerList(LinkedList<PlayerModel> playerList) {
        this.players = playerList;
    }

    public PlayerModel getCurrentPlayer() {
        return currentPlayer;
    }

    public void setBoard(String path) throws FormatException, IOException {
        try {
            MapFileManagement aux=new MapFileManagement();
            board = aux.createBoard(path);
        } catch (FormatException ex) {
            throw new FormatException(ex.getMessage());
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void createFile(String fileContent) {
        MapFileManagement.generateBoardFile(fileContent);
    }

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

    public int getMaxNumberOfPlayers() {
        return maxNbOfPlayers;
    }

    public LinkedList<PlayerModel> getPlayerList() {
        return this.players;
    }
    
    public void nextTurn(){
        
        if(this.getTurn()+1<this.maxNbOfPlayers){
            this.setTurn(this.getTurn() + 1);
            this.setCurrentPlayer(this.getPlayerList().get(this.getTurn()));
        }else{
            nextStage();
            this.setTurn(-1);
        }
        
    }

    public void nextStage(){
        this.setStage(this.getStage()+ 1);
    }
    
    
    /**
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @param turn the turn to set
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * @param currentPlayer the currentPlayer to set
     */
    public void setCurrentPlayer(PlayerModel currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(int stage) {
        this.stage = stage;
    }
}
