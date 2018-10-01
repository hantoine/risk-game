/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.controllers.GameController;
import com.risk.models.interfaces.PlayerModel;
import java.awt.Color;

/**
 * It represents an human player (child of PlayerModel)
 *
 * @author Nellybett
 */
public class HumanPlayerModel extends PlayerModel {

    /**
     * Constructor from Player Model
     *
     * @param name name of the player
     * @param color color of the player
     * @param isHuman true if it is human
     */
    public HumanPlayerModel(String name, Color color, boolean isHuman) {
        super(name, color, isHuman);
    }

    /**
     * Reinforcement phase for Human Player
     */
    @Override
    public void reinforcement(GameController playGame) {
        int armies = armiesAssignation();
        this.setArmiesDeploy(armies);
        this.setNumArmies(this.getNumArmies() + armies);
    }

    /**
     * Fortification phase for Human Player
     */
    @Override
    public void fortification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attack phase for Human Player
     */
    @Override
    public void attack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Assign the armies for reinforcement phase
     *
     * @return number of armies to deploy
     */
    private int armiesAssignation() {
        int extraCountries = (int) Math.floor(this.getContriesOwned().size() / 3);
        int extraContinent = 0;
        for (ContinentModel continent : this.getContinentsOwned()) {
            extraContinent = extraContinent + continent.getBonusScore();
        }
        System.out.println(extraContinent + extraCountries);
        if (extraContinent + extraCountries < 3) {
            return 3;
        } else {
            return extraContinent + extraCountries;
        }
    }

    /**
     * Assign extra armies depending on handed cards
     *
     * @return number of extra armies according to handed cards
     */
    private int armiesAssignationCards() {
        this.setReturnedCards(this.getReturnedCards() + 3);

        switch (this.getReturnedCards()) {
            case 3:
                return 4;
            case 6:
                return 6;
            case 9:
                return 8;
            case 12:
                return 10;
            case 15:
                return 12;
            case 18:
                return 15;
            default:
                return 15 + (((this.getReturnedCards() - 18) / 3) * 5); //after 18 you get 5 more for every 3 cards returned
        }
    }

}
