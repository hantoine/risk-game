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
     */
    public HumanPlayerModel(String name, Color color) {
        super(name, color, true);
    }

    /**
     * Reinforcement phase for Human Player
     */
    @Override
    public void reinforcement(GameController playGame) {
        this.assignNewArmies();
    }

    /**
     * Fortification phase for Human Player
     */
    @Override
    public void fortification(GameController playGame) {
        // nothing to do
    }

    /**
     * Attack phase for Human Player
     */
    @Override
    public void attack(GameController playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
