/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;

/**
 * It represents the game process. It is execute in a thread in the
 * RiskController
 *
 * @author Nellybett
 */
public class GameController {

    RiskModel modelRisk;
    RiskView riskView;
    MapListener countryListener;
    RiskController riskController;

    /**
     * Constructor
     *
     * @param riskModel model of the game
     * @param riskView view of the game
     * @param countryListener listener to mouse events
     * @param riskController principal controller
     */
    public GameController(RiskModel riskModel, RiskView riskView, MapListener countryListener, RiskController riskController) {
        this.countryListener = countryListener;
        this.modelRisk = riskModel;
        this.riskView = riskView;
        this.riskController = riskController;
    }

    /**
     * The function that contains all the phases in the game
     */
    public void runStage() {
        if (modelRisk.getCurrentPlayer().getContriesOwned().containsAll(modelRisk.getMap().getGraphTerritories().values())) {
            modelRisk.setWinningPlayer(modelRisk.getCurrentPlayer());
            return;
        }

        switch (modelRisk.getStage()) {
            case START:
                modelRisk.nextTurn();
                modelRisk.initializePlayers();
                break;
            case REINFORCEMENT:
                modelRisk.getCurrentPlayer().reinforcement(this);
                riskView.updateView(modelRisk);
                break;

            case ATTACK:
                break;
            case FORTIFICATION:
                modelRisk.nextTurn();
                riskView.updateView(modelRisk);
                break;
        }
    }
    
    public void finishStage()
    {
        modelRisk.nextStage();
    }
}
