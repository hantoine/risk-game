/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

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
     * @param game Game in which this players belongs
     */
    public HumanPlayerModel(String name, Color color, RiskModel game) {
        super(name, color, true, game);
    }

    /**
     * Reinforcement phase for Human Player
     */
    @Override
    public void reinforcement(RiskModel playGame) {
        this.assignNewArmies();
    }

    /**
     * Fortification phase for Human Player
     */
    @Override
    public void fortification(RiskModel playGame) {
        // nothing to do
    }

    /**
     * Attack phase for Human Player
     */
    @Override
    public void attack(RiskModel playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
