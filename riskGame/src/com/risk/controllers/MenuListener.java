/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.map.CountryButton;
import com.risk.views.menu.DeletableButton;
import com.risk.views.map.MapPanel;
import com.risk.views.menu.PlayerListPanel;
import com.risk.views.RiskView;
import com.risk.views.menu.NewGamePanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nellybett
 */
public class MenuListener extends MouseAdapter{
    
        RiskModel riskModel;
        RiskView riskView;
        PlayerListPanel playerList;
        MapListener countryListener;
        
        public MenuListener(RiskModel riskModel, RiskView riskView, MapListener countryListener){
            this.riskModel=riskModel;
            this.riskView=riskView;
            this.countryListener=countryListener;
            
        }
              
        public void setPanel(PlayerListPanel playerList){
            this.playerList=playerList;
            
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            try{
            
            JComponent c = (JComponent) e.getSource();
            JButton addPlayer= (JButton)c;
            if (addPlayer.getText().equals("+")) {
               this.playerList.addElement(this.playerList.getNewPlayerName(), this.playerList.getNewColor());
            
            }else if (addPlayer.getText().equals("-")){
                if(this.playerList.getPlayersListPanel().getComponentCount()-1<=3)
                JOptionPane.showMessageDialog(null, "You need at least three players to play the game.");
                else{
                DeletableButton buttonToDelete=(DeletableButton) addPlayer;
                int IDtoDelete = buttonToDelete.getID();
                int nbPlayers = this.playerList.getPlayersListPanel().getComponentCount()-1;
                for(int i =0; i< nbPlayers; i++){
                    JPanel panelToTest = (JPanel)this.playerList.getPlayersListPanel().getComponent(i);
                    DeletableButton delButtonToTest = (DeletableButton)panelToTest.getComponent(2);
                    if(delButtonToTest.getID() == IDtoDelete){
                        Color colorToRemove = ((JButton)panelToTest.getComponent(1)).getBackground();
                        this.playerList.getColorUsed().remove(colorToRemove);
                        this.playerList.getPlayersListPanel().remove(i);
                        this.playerList.revalidate();
                        this.playerList.repaint();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Oops... Something went wrong.");
            }
            
            }else if(addPlayer.getText().equals("    ")){
                 Color selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                if( this.playerList.getColorUsed().contains(selectedColor)){
                    JOptionPane.showMessageDialog(null, "This color is already used");
                }else{
                    addPlayer.setBackground(selectedColor);
                    
                }
            }else if(addPlayer.getText().equals("PLAY")){
                
                NewGamePanel aux=(NewGamePanel)this.riskView.getMenuPanel().getTabbedPane().getComponent(0);
                String selectedPath=aux.getSelectFileTextField().getText();
                
                if(!selectedPath.equals(" No file selected  ")){
                    riskModel.setBoard(selectedPath);
                
                
                    this.riskView.remove(this.riskView.getMenuPanel());
                    this.riskView.setMenuPanel(null);
                    this.riskView.getBattlePanel().setVisible(true);
                    this.riskView.getOptionPanel().setVisible(true);
                    this.riskView.initialMap(riskModel, countryListener);
                }else{
                    JOptionPane.showMessageDialog(null, "You have not selected a map");
                }
            }
            }catch(NumberFormatException ex){
                System.out.println(ex);
            }
        }
    
}
