/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

/**
 * It is the Game-driver
 * @author Nellybett
 */
public class RiskController implements ActionListener{

    private RiskView viewRisk;
    private RiskModel modelRisk;
    private MenuListener menuListener;
    private MapListener countryListener;
    
    /**
     * Constructor
     * @param riskModel the model of the game
     * @param riskView  the view of the game
     */
    public RiskController(RiskModel riskModel,RiskView riskView) {
        this.modelRisk=riskModel;
        this.viewRisk = riskView;
        this.viewRisk.setRiskController(this);
        this.countryListener = new MapListener(getModelRisk());
        this.menuListener = new MenuListener(getModelRisk(), getViewRisk(), this); 
        viewRisk.initialMenu(modelRisk, menuListener);
        viewRisk.addMenuBar();
        viewRisk.initStagePanel();
        viewRisk.setVisible(true);
        //viewRisk.initialMap(modelRisk, countryListener);
        

    }
    
    /**
     * It listens to de menu bar events that are part of the main view
     * @param e the event to manage
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent c = (JComponent) e.getSource();
        
        if (c != null && c instanceof JMenuItem) {
            JMenuItem source = (JMenuItem) c;
            System.out.println(source.getText());
            if(source.getText().equals("New Game")){
                getViewRisk().initialMenu(getModelRisk(), getMenuListener());
            }
        }
    }

    /**
     * It represent the different phases of the game.
     * It is called after setting the players and board information
     */
    void playGame() {
      
        // while(!this.modelRisk.getCurrentPlayer().getContriesOwned().containsAll(this.modelRisk.getBoard().getGraphTerritories().values())){
            switch (this.getModelRisk().getStage()) {
                case -1:
                 //Start phase assign territories
                    this.getModelRisk().nextTurn(); 
                    this.getViewRisk().initialPlayer(getModelRisk());
                    this.getViewRisk().initialMap(getModelRisk(), getCountryListener());
                    break;
                case 0:
                //Reinforcement phase create function
                    getModelRisk().getCurrentPlayer().reinforcement();
                    break;
        
                case 1:
                //Attack phase
                    break;
                case 2:
                //Fortification phase
                    this.getModelRisk().nextTurn();
                    break;
                default:
                    break;
            }
            
            this.getModelRisk().nextStage();
        //}
    }
    
    /**
     * Getter of the viewRisk attribute
     * @return the viewRisk
     */
    public RiskView getViewRisk() {
        return viewRisk;
    }

    /**
     * Setter of the viewRisk attribute
     * @param viewRisk the viewRisk to set
     */
    public void setViewRisk(RiskView viewRisk) {
        this.viewRisk = viewRisk;
    }

    /**
     * Getter of the modelRisk attribute
     * @return the modelRisk
     */
    public RiskModel getModelRisk() {
        return modelRisk;
    }

    /**
     * Setter of the modelRisk attribute
     * @param modelRisk the modelRisk to set
     */
    public void setModelRisk(RiskModel modelRisk) {
        this.modelRisk = modelRisk;
    }

    /**
     * Getter of the menuListener attribute
     * @return the menuListener
     */
    public MenuListener getMenuListener() {
        return menuListener;
    }

    /**
     * Setter of the menuListener attribute
     * @param menuListener the menuListener to set
     */
    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    /**
     * Getter of the countryListener attribute
     * @return the countryListener
     */
    public MapListener getCountryListener() {
        return countryListener;
    }

    /**
     * Setter of the countryListener attribute
     * @param countryListener the countryListener to set
     */
    public void setCountryListener(MapListener countryListener) {
        this.countryListener = countryListener;
    }


}