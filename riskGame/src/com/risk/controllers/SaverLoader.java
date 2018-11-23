/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;
import com.risk.models.RiskModel;

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
     * constructor
     *
     * @param riskModel
     */
    public SaverLoader(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    /**
     * Save current game
     */
    public void saveGame() {
        
    }


    public void loadGame() {

    }
}
