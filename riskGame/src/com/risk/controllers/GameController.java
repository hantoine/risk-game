/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;


/**
 * It represents the game process.
 * It is execute in a thread in the RiskController
 * @author Nellybett
 */
public class GameController implements Runnable{
    
    RiskModel modelRisk;
    RiskView riskView;
    MapListener countryListener;
    RiskController riskController;
    
    /**
     * Constructor
     * @param riskModel model of the game
     * @param riskView view of the game
     * @param countryListener listener to mouse events
     * @param riskController principal controller
     */
    public GameController(RiskModel riskModel, RiskView riskView, MapListener countryListener,RiskController riskController) {
        this.countryListener=countryListener;
        this.modelRisk=riskModel;
        this.riskView=riskView;
        this.riskController=riskController;
    }

    /**
     * The function that contains all the phases in the game
     */
    @Override
    public void run() {
        while(!modelRisk.getCurrentPlayer().getContriesOwned().containsAll(modelRisk.getBoard().getGraphTerritories().values())){
           
            switch (modelRisk.getStage()) {
                case -1:
                 //Start phase assign territories
                    modelRisk.nextTurn();
                    modelRisk.initializePlayers();
                    break;
                case 0:
                //Reinforcement phase    
                    modelRisk.getCurrentPlayer().reinforcement(this);
                    riskView.initStagePanel(0,modelRisk.getCurrentPlayer().getArmiesDeploy());
                    synchronized(riskController.getSyncObj()) {
                        try {
                            riskController.getSyncObj().wait();
                        } catch (InterruptedException e) {
                            System.out.println("Execution error");
                        }
                    }
                    break;
        
                case 1:
                //Attack phase
                    break;
                case 2:
                //Fortification phase
                    modelRisk.nextTurn();
                    riskView.initialPlayerHandPanel(modelRisk);
                    riskView.initialPlayer(modelRisk);
                    break;
                default:
                    break;
            }
            
            
            modelRisk.nextStage();
         }
    }
    
}
