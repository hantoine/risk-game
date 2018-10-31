/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;

/**
 * It represents an AI player (child of PlayerModel)
 *
 * @author Nellybett
 */
public class AIPlayerModel extends PlayerModel {

    /**
     * Constructor from Player Model
     *
     * @param name name of the player
     * @param color color of the player
     * @param game Game in which this players belongs
     */
    AIPlayerModel(String name, Color color, RiskModel game) {
        super(name, color, false, game);
    }

    /**
     * Reinforcement phase for AI Player
     */
    @Override
    public void reinforcement(RiskModel playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Fortification phase for AI Player
     */
    @Override
    public void fortification(RiskModel playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attack phase for AI Player
     */
    @Override
    public void attack(RiskModel playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
