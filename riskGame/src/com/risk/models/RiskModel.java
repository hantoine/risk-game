/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.FileManagement;
import java.util.LinkedList;

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

}
