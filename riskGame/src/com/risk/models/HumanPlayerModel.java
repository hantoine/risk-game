/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.interfaces.PlayerModel;
import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;

/**
 * It represents an human player (child of PlayerModel)
 * @author Nellybett
 */
public class HumanPlayerModel extends PlayerModel{
    
    /**
     * Constructor from Player Model
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
    public void reinforcement() {
    //this.setNumArmies(armiesAssignation());
        while(this.getNumArmies()!=0){
            
        }
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
    

    
}
