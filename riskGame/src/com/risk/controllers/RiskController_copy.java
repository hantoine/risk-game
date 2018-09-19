/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;


import com.risk.models.Player;
import com.risk.models.RiskModel;
import com.risk.views.NewGamePanel;
import com.risk.views.PlayerListPanel;
import com.risk.views.RiskView;
import com.risk.views.StartMenuView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Nellybett
 */
public class RiskController_copy {
    RiskView viewRisk;
    StartMenuView menuView;
    RiskModel modelRisk;
    PlayerListPanel playerListPanelView;

    public RiskController_copy(RiskView viewRisk, StartMenuView menuView, RiskModel modelRisk) {
        this.modelRisk = modelRisk;
        this.viewRisk = viewRisk;
        this.menuView = menuView;
        
        this.playerListPanelView = (PlayerListPanel)((NewGamePanel)this.menuView.getComponent(0)).getComponent(1);
        
        this.playerListPanelView.addAddPlayerListener(new addPlayerListener());
        this.playerListPanelView.addRemovePlayerListener(new removePlayerListener());
    }
    
    //tim
    public void setBoard(String mapPath){
        modelRisk.setBoard(mapPath);
    }
    
    public void initializeMap(){
        viewRisk.initialMap(modelRisk);
    }
    
    //listener to add player to players list from view
    class addPlayerListener implements ActionListener{
        String newPlayerName;
        Color newPlayerColor;
        boolean newPlayerHuman;
        
        public addPlayerListener(){
            this.newPlayerName=newPlayerName;
            this.newPlayerColor=newPlayerColor;
            this.newPlayerHuman=newPlayerHuman;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                //TODO
                modelRisk.addPlayerToPlayerList(this.newPlayerName, this.newPlayerColor, this.newPlayerHuman);
            }
            catch(NumberFormatException ex){
                System.out.println(ex);
            }
        }
    }
    
    //listener to remove player to players list from view
    class removePlayerListener implements ActionListener{
        
        int deletedIndex;
        
        public removePlayerListener(){
            this.deletedIndex=deletedIndex;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                //TODO
                modelRisk.removePlayer(this.deletedIndex);
            }
            catch(NumberFormatException ex){
                System.out.println(ex);
            }
        }
    }
    
}
