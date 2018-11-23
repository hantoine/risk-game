/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class to be used by the game controller to save a game and load a new one.
 *
 * @author user
 */
public class SaverLoader {

    /**
     * Model of the game to save
     */
    RiskModel riskModel;

    /**
     * Document that is being built
     */
    Document doc;
    
    /**
     * constructor
     */
    public SaverLoader(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    /**
     * Save current game
     */
    public void saveGame(String nameOfGame) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(nameOfGame);
            doc.appendChild(rootElement);

            //name of mapfile
            Attr map_file_name = doc.createAttribute("map_file_name");
            map_file_name.setValue(this.riskModel.getMap().getConfigurationInfo().getImagePath());
            rootElement.setAttributeNode(map_file_name);
            
            //get winning player
            Attr winning_player = doc.createAttribute("winning_player");
            winning_player.setValue(this.riskModel.getWinningPlayer().getName());
            rootElement.setAttributeNode(winning_player);

            //get current player
            Attr current_player = doc.createAttribute("current_player");
            current_player.setValue(this.riskModel.getCurrentPlayer().getName());
            rootElement.setAttributeNode(current_player);

            LinkedList<Element> players;
            players = this.getPlayersInfo();
        }
        catch{
            
        }
    }

    private LinkedList<Element> getPlayersInfo(){
        for(PlayerModel player : this.riskModel.getPlayerList()){
            Element newPlayer = new doc.createElement("player");
            
            
        }
    }
    
    public void loadGame() {

    }
}
