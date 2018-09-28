/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.interfaces.PlayerModel;
import java.awt.Color;

/**
 * It represents an AI player
 * @author Nellybett
 */
public class AIPlayerModel extends PlayerModel{

    /**
     * Constructor from Player Model
     * @param name name of the player
     * @param color color of the player
     * @param isHuman true if it is human
     */
    public AIPlayerModel(String name, Color color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void reinforcement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fortification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
