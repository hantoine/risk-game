/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.util.LinkedList;


/**
 *
 * @author Nellybett
 */
public class RiskModel {
   
    private Board board;
    private LinkedList<Player> playerList;
    
    static Integer maxNbOfPlayers = 6;
    private Player currentPlayer;

    
    public RiskModel() {
        this.playerList=new LinkedList<>();
        
        addPlayerToPlayerList("Player 1", Color.red, true);
        addPlayerToPlayerList("Player 2", Color.green, true);
        addPlayerToPlayerList("Player 3", Color.blue, false);
        
        
        this.currentPlayer=this.playerList.getFirst();
        
    }

    public void addPlayerToPlayerList(String name, Color color, boolean isHuman){
        playerList.add(new Player(name, color, isHuman));
    }
    
    public void removePlayer(int index){
        playerList.remove(index);
    }
    
    public void setPlayerList (LinkedList<Player> playerList){
        this.playerList=playerList;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    
    public void setBoard(String path) {
        board = FileManagement.createBoard(path);
    }

    public void createFile(String fileContent) {
        FileManagement.generateBoardFile(fileContent);
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Assigns random countries to players
     */
    public void assignCoutriesToPlayers() {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        List<Country> countriesLeft = new ArrayList<>(board.getGraphTerritories().values());
        Collections.shuffle(countriesLeft);

        int countriesPerPlayer = (countriesLeft.size() / players.size());

        players.stream().forEach((player) -> {
            List<Country> ownedCountries = countriesLeft.subList(0, countriesPerPlayer);
            player.setContriesOwned(ownedCountries);
            countriesLeft.removeAll(ownedCountries);
        });

        Random rnd = new Random();
        while (!countriesLeft.isEmpty()) {
            int playerIndex = rnd.nextInt(players.size());
            players.get(playerIndex).addCountryOwned(countriesLeft.remove(0));
        }
    }

    public int getMaxNumberOfPlayers(){
        return maxNbOfPlayers;
    }
    
    public LinkedList<Player> getPlayerList(){
        return this.playerList;
    }
}
