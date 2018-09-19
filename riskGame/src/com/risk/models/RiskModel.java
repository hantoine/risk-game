/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;
import static com.risk.models.FileManagement.createBoard;
import static com.risk.models.FileManagement.generateBoardFile;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.risk.models.FileManagement;
import java.util.LinkedList;

/**
 *
 * @author Nellybett
 */
public class RiskModel {

    
    private Board board;
    private LinkedList<Player> playerList;
    
    static Integer maxNbOfPlayers=6; //tim 
    private Player currentPlayer;

    
    public RiskModel() {
        this.playerList=new LinkedList<>();
        addPlayerToPlayerList("Player 1", Color.red, true);
        addPlayerToPlayerList("Player 2", Color.green, true);
        addPlayerToPlayerList("Bot 1", Color.blue, false);
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

    //tim
    public int getMaxNumberOfPlayers(){
        return maxNbOfPlayers;
    }
    
    public LinkedList<Player> getPlayerList(){
        return this.playerList;
    }
}
