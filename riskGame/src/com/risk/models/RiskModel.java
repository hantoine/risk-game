/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;
import com.risk.models.FileManagement;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
/**
 *
 * @author Nellybett
 */
public class RiskModel {

    private Board board;
    private LinkedList<Player> playerList;
    private Player currentPlayer;

    public RiskModel() {

    }

    //public void setPlayerList(String[] playersInfo) {
    //    int i;
    //    LinkedList<Player> playerListAux = new LinkedList<>();
    //    for (i = 0; i < playersInfo.length; i++) {
    //        String[] separator = playersInfo[i].split(",");
    //        Player auxiliar = new Player(separator[0], separator[1]);
    //        playerListAux.add(auxiliar);
    //    }

    //    playerList = playerListAux;
    //}

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
        * 
        * @param playerlist
        * Assigns random parameters to each player
        * contriesOwned
        */
    public void assignRandomParams(LinkedList<Player> playerlist){
        //System.out.println("[assinging random countires.....]");
        HashMap<String, Country>temp_contries = board.getGraphTerritories();
        Collection<Country> countries = new ArrayList<>();
        int contr_size = temp_contries.size();
        int player_size = 1;
        
        
        
        if(playerlist!=null){
        player_size = playerlist.size();
        //System.out.println("[contry_size = "+contr_size);
        }
        
        
        countries = temp_contries.values();
        //System.out.println("Original List : \n" + countries);
        
        Collections.shuffle(countries);
        //System.out.println("shuffles List : \n" + countries);
        
        int countriesPerPlayer = (contr_size/player_size);
        int remainingCountries = (contr_size%player_size);
        

        for (int i=0;i<player_size;i++){
            LinkedList<Country> ownedCountries = new LinkedList<>();
            for(int j=0;j<countriesPerPlayer;j++){
                ownedCountries.add(countries.get(0));
                countries.remove(0);
            }
                        
            if(playerlist!=null){
                playerlist.get(i).setContriesOwned(ownedCountries);
                System.out.println(playerlist.get(i).getContriesOwned());
            }
        }
        
        if(!countries.isEmpty()){
            for (int j=0;j<remainingCountries;j++){
                Random rnd = new Random();

                int playerIndex = rnd.nextInt(player_size);
                //System.out.println(playerIndex);
                if(playerlist!=null){
                LinkedList<Country> temp = playerlist.get(playerIndex).getContriesOwned();
                temp.add(countries.get(0));
                countries.remove(0);
                playerlist.get(playerIndex).setContriesOwned(temp);
                }
            }
        }
        
        //debug check
        for (int i=0;i<player_size;i++){
            System.out.println(playerlist.get(i).getContriesOwned());
        }
    }
}
