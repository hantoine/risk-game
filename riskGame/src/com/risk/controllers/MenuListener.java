/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.HumanPlayerModel;
import com.risk.models.interfaces.PlayerModel;
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
 * It listens to Mouse events in the initial menu
 *
 * @author Nellybett
 */
public class MenuListener extends MouseAdapter {

    private RiskModel riskModel;
    private RiskView riskView;
    private PlayerListPanel playerList;
    private RiskController riskController;

    /**
     * Constructor
     *
     * @param riskModel the model to modify it
     * @param riskView the view to show elements
     * @param riskController the principal controller to start the game
     */
    public MenuListener(RiskModel riskModel, RiskView riskView, RiskController riskController) {
        this.riskModel = riskModel;
        this.riskView = riskView;
        this.riskController = riskController;
    }

    /**
     * It manages mouse pressed event in the menu panel.
     *
     * It includes:
     * <ul>
     * <li>Changing the color</li>
     * <li>Selection of a path for the file</li>
     * <li>Elimination of players</li>
     * <li>Addition of players </li>
     * <li>Start game event</li>
     * </ul>
     *
     * @param e the event to be managed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        JButton addPlayer = (JButton) c;
        switch (addPlayer.getText()) {
            case "+":
                this.getPlayerList().addElement(this.getPlayerList().getNewPlayerName(), this.getPlayerList().getNewColor());
                break;
            case "-":

                if ((this.getPlayerList().getPlayersArray().size()) <= 3) {
                    JOptionPane.showMessageDialog(null, "You need at least three players to play the game.");
                } else {
                    DeletableButton buttonToDelete = (DeletableButton) addPlayer;
                    int IDtoDelete = buttonToDelete.getID();

                    for (Iterator<PlayerPanel> it = this.getPlayerList().getPlayersArray().iterator(); it.hasNext();) {
                        PlayerPanel panelToTest = it.next();

                        if (panelToTest.getDelButton().getID() == IDtoDelete) {
                            System.out.println(panelToTest.getDelButton().getID());
                            Color colorToRemove = panelToTest.getColorButton().getBackground();
                            this.getPlayerList().getColorUsed().remove(colorToRemove);
                            this.getPlayerList().remove(panelToTest);
                            this.getPlayerList().revalidate();
                            this.getPlayerList().repaint();
                            it.remove();
                        }
                    }

                }
                break;
            case "    ":
                Color selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                if (this.getPlayerList().getColorUsed().contains(selectedColor)) {
                    JOptionPane.showMessageDialog(null, "This color is already used");
                } else {
                    this.getPlayerList().getColorUsed().remove(addPlayer.getBackground());
                    this.getPlayerList().getColorUsed().add(selectedColor);
                    addPlayer.setBackground(selectedColor);

                }
                break;
            case "PLAY":
                StartMenuView aux1 = (StartMenuView) this.getRiskView().getMenuPanel().getStartMenu();
                NewGamePanel aux = (NewGamePanel) aux1.getTabbedPane().getComponent(0);
                String selectedPath = aux.getSelectFileTextField().getText();
                if (!selectedPath.equals("")) {
                    int resultReadingValidation = getRiskModel().setBoard(selectedPath);
                    if (resultReadingValidation == 0) {
                        if ((getRiskModel().getBoard()).check()) {
                            this.getRiskView().getMenuPanel().setVisible(false);
                            this.getRiskView().remove(this.getRiskView().getMenuPanel());
                            this.getRiskView().setMenuPanel(null);
                            this.getRiskView().getStagePanel().setVisible(true);

                            LinkedList<PlayerPanel> aux2 = aux.getPlayersPanel().getPlayersArray();
                            LinkedList<PlayerModel> listPlayers = new LinkedList<>();
                            for (int i = 0; i < aux2.size(); i++) {
                                PlayerPanel player = aux2.get(i);
                                PlayerModel playerGame = new HumanPlayerModel(player.getPlayerNameTextField().getText(), player.getColorButton().getBackground(), true);
                                listPlayers.add(playerGame);
                            }

                            this.getRiskModel().setPlayerList(listPlayers);
                            this.getRiskController().playGame();

                        } else {
                            JOptionPane.showMessageDialog(null, "The map is not valid.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, readingError(resultReadingValidation));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You have not selected a map");
                }
                break;
            default:
                break;
        }
    }

    /**
     * It provides the message in case of error in reading the map from a file
     *
     * @param resultReadingValidation it has a value between -1 and -6, each
     * value represent a different error
     * @return A string with the error message
     */
    private String readingError(int resultReadingValidation) {
        switch (resultReadingValidation) {
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

    /**
     * Getter for the riskModel attribute
     *
     * @return the riskModel
     */
    public RiskModel getRiskModel() {
        return riskModel;
    }

    /**
     * Setter for the riskModel attribute
     *
     * @param riskModel the riskModel to set
     */
    public void setRiskModel(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    /**
     * Getter for the riskView attribute
     *
     * @return the riskView
     */
    public RiskView getRiskView() {
        return riskView;
    }

    /**
     * Setter for the riskView attribute
     *
     * @param riskView the riskView to set
     */
    public void setRiskView(RiskView riskView) {
        this.riskView = riskView;
    }

    /**
     * Getter for the playerList attribute
     *
     * @return the playerList
     */
    public PlayerListPanel getPlayerList() {
        return playerList;
    }

    /**
     * Setter for the playerList attribute
     *
     * @param playerList the playerList to set
     */
    public void setPlayerList(PlayerListPanel playerList) {
        this.playerList = playerList;
    }

    /**
     * Getter for the riskController attribute
     *
     * @return the riskController
     */
    public RiskController getRiskController() {
        return riskController;
    }

    /**
     * Setter for the riskController attribute
     *
     * @param riskController the riskController to set
     */
    public void setRiskController(RiskController riskController) {
        this.riskController = riskController;
    }

}
