/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.CardModel;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import java.util.LinkedList;
import java.util.List;
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
     *
     * @param riskModel
     */
    public SaverLoader(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    /**
     * Save current game
     *
     * @param nameOfGame
     */
    public void saveGame(String nameOfGame) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            this.doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(nameOfGame);
            doc.appendChild(rootElement);

            saveGeneralInfos(rootElement);

            LinkedList<Element> players;
            players = this.getPlayersInfo();
        } catch  {

        }
    }

    /**
     * Add general information about the game to save
     *
     * @param rootNode
     */
    public void addGeneralInfos(Element rootNode) {
        //name of mapfile
        Attr map_file_name = doc.createAttribute("map_file_name");
        map_file_name.setValue(this.riskModel.getMap().getConfigurationInfo().getImagePath());
        rootNode.setAttributeNode(map_file_name);

        //get winning player
        Attr winning_player = doc.createAttribute("winning_player");
        winning_player.setValue(this.riskModel.getWinningPlayer().getName());
        rootNode.setAttributeNode(winning_player);

        //get current player
        Attr current_player = doc.createAttribute("current_player");
        current_player.setValue(this.riskModel.getCurrentPlayer().getName());
        rootNode.setAttributeNode(current_player);
    }

    /**
     * Add nodes that store information about each player of the game
     *
     * @return
     */
    private LinkedList<Element> getPlayersInfo() {
        LinkedList<Element> playersInfos = new LinkedList<>();

        for (PlayerModel player : this.riskModel.getPlayerList()) {
            Element playerInfos = this.doc.createElement("player");
            
            //list of countries owned by player 
            LinkedList<String> countriesNames = player.getCountriesOwned();
            Element countriesOwned = doc.createElement("country");
            
            //for each country owned
            for (String countryName : countriesNames) {
                
                //create country element
                Element countryOwned = doc.createElement("country");
                
                //set name of country
                Attr nameAttr = doc.createAttribute("name");
		nameAttr.setValue(countryName);
                countryOwned.setAttributeNode(nameAttr);
                
                //add country element to list of countries owned
                countriesOwned.appendChild(countryOwned);
            }

            List<CardModel> cards = player.getHand().getCards();
            Element cardsOwned = doc.createElement("country");
            
            //for each card owned
            for(CardModel card: cards){
                //create card element
                Element cardOwned = doc.createElement("country");
                
                //set name of card
                Attr nameAttr = doc.createAttribute("name");
		nameAttr.setValue(card.getTerritoryName());
                cardOwned.setAttributeNode(nameAttr);
                
                //set type of army
                Attr armyAttr = doc.createAttribute("army");
		armyAttr.setValue(card.getTypeOfArmie());
                cardOwned.setAttributeNode(armyAttr);
                
                //add card element to list of cards owned
                cardsOwned.appendChild(cardOwned);
            }
            
            //add informations to main element : 
            playerInfos.appendChild(countriesOwned);
            playerInfos.appendChild(cardsOwned);
            playersInfos.add(playerInfos);
        }

        return playersInfos;
    }

    public void loadGame() {

    }
}
