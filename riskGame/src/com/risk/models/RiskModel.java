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
import java.util.LinkedList;
/**
 *
 * @author Nellybett
 */
public class RiskModel {

 
    private Board board;
    private LinkedList<Player> playerList;
    
    static Integer maxNbOfPlayers=6; //tim 
    

    public RiskModel() {
        playerList= new LinkedList<>();
        setPlayerList();
    }

    public void addPlayerToPlayerList(String name, Color color, boolean isHuman){
        playerList.add(new Player(name, color, isHuman));
    }
    
    public void removePlayer(int index){
        playerList.remove(index);
    }
    
    public void setPlayerList (){
        addPlayerToPlayerList("Player 1", Color.red, true);
        addPlayerToPlayerList("Player 2", Color.green, true);
        addPlayerToPlayerList("Bot 1", Color.blue, false);
    }
    
    public void setBoard(String path){
        board=createBoard(path);
    }
    
    public void createFile(String fileContent){
        generateBoardFile(fileContent);
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
