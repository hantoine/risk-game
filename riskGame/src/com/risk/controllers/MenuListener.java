/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.views.menu.DeletableButton;
import com.risk.views.menu.PlayerListPanel;
import com.risk.views.RiskView;
import com.risk.views.menu.NewGamePanel;
import com.risk.views.menu.PlayerPanel;
import com.risk.views.menu.StartMenuView;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Nellybett
 */
public class MenuListener extends MouseAdapter {

    RiskModel riskModel;
    RiskView riskView;
    PlayerListPanel playerList;
    RiskController riskController;
   
    

    public MenuListener(RiskModel riskModel, RiskView riskView, RiskController riskController) {
        this.riskModel = riskModel;
        this.riskView = riskView;
        this.riskController=riskController;
    }

    public void setPanel(PlayerListPanel playerList) {
        this.playerList = playerList;

    }

    @Override
    public void mousePressed(MouseEvent e) {
            JComponent c = (JComponent) e.getSource();
            JButton addPlayer = (JButton) c;
        switch (addPlayer.getText()) {
            case "+":
                this.playerList.addElement(this.playerList.getNewPlayerName(), this.playerList.getNewColor());
                break;
            case "-":
                System.out.println("number of players" + this.playerList.getPlayersArray().size());
                if ((this.playerList.getPlayersArray().size()) <= 3) {
                    JOptionPane.showMessageDialog(null, "You need at least three players to play the game.");
                } else {
                    DeletableButton buttonToDelete = (DeletableButton) addPlayer;
                    int IDtoDelete = buttonToDelete.getID();

                    for (Iterator<PlayerPanel> it = this.playerList.getPlayersArray().iterator(); it.hasNext();) {
                        PlayerPanel panelToTest = it.next();

                        if (panelToTest.getDelButton().getID() == IDtoDelete) {
                            System.out.println(panelToTest.getDelButton().getID());
                            Color colorToRemove = panelToTest.getColorButton().getBackground();
                            this.playerList.getColorUsed().remove(colorToRemove);
                            this.playerList.remove(panelToTest);
                            this.playerList.revalidate();
                            this.playerList.repaint();
                            it.remove();
                        }
                    }

                }
                break;
            case "    ":
                Color selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                if (this.playerList.getColorUsed().contains(selectedColor)) {
                    JOptionPane.showMessageDialog(null, "This color is already used");
                } else {
                    this.playerList.getColorUsed().remove(addPlayer.getBackground());
                    this.playerList.getColorUsed().add(selectedColor);
                    addPlayer.setBackground(selectedColor);

                }
                break;
            case "PLAY":
                StartMenuView aux1=(StartMenuView)this.riskView.getMenuPanel().getStartMenu();
                NewGamePanel aux=(NewGamePanel) aux1.getTabbedPane().getComponent(0);
                String selectedPath=aux.getSelectFileTextField().getText();
                if(!selectedPath.equals("")){
                    int resultReadingValidation=riskModel.setBoard(selectedPath);
                    if(resultReadingValidation==0){         
                        if((riskModel.getBoard()).connectedGraph()){
                            this.riskView.getMenuPanel().setVisible(false);
                            this.riskView.remove(this.riskView.getMenuPanel());
                            this.riskView.setMenuPanel(null);
                            this.riskView.getPhase().setVisible(true);
                            LinkedList<PlayerPanel> aux2=aux.getPlayersPanel().getPlayersArray();
                            LinkedList<PlayerModel> listPlayers=new LinkedList<>();
                            for(int i=0; i<aux2.size();i++){
                                PlayerPanel player=aux2.get(i);
                                PlayerModel playerGame=new PlayerModel(player.getPlayerNameTextField().getText(),player.getColorButton().getBackground(),true);
                                listPlayers.add(playerGame);
                                i++;
                            }
                            
                            this.riskModel.setPlayerList(listPlayers);
                            this.riskController.playGame();
                            
                        }else{
                            JOptionPane.showMessageDialog(null, "Countries are not connected. please selected a new file");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,readingError(resultReadingValidation));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You have not selected a map");
                }   break;
            default:
                break;
        }
    }

    private String readingError(int resultReadingValidation) {
        switch(resultReadingValidation){
            case -1:
                return "Error reading the file";
            case -2:
                return "Error in parameters to configurate the map.";
            case -3:
                return "Error in continent information.";
            case -4:
                return "Error in country information.";
            case -5:
                return "No territories separator in file.";
            case -6:
                return "No continents separator in file.";
        }
        return "Error in file format";
    }

    
    
}
