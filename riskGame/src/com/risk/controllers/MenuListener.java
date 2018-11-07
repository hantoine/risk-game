/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.HumanPlayerModel;
import com.risk.models.MapFileManagement;
import com.risk.models.MapModel;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.menu.DeletableButton;
import com.risk.views.menu.NewGamePanel;
import com.risk.views.menu.PlayerListPanel;
import com.risk.views.menu.PlayerPanel;
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

    /**
     * riskModel it represents the model of the game
     */
    private RiskModel riskModel;
    /**
     * riskView it is a reference to the main view in the game
     */
    private RiskView riskView;
    /**
     * playerList it is a reference to the view that has a list of player panels
     * in the menu
     */
    private PlayerListPanel playerList;
    /**
     * riskController it is a reference to the controller of the game
     */
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
                minusButton(addPlayer);
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
                playButton(addPlayer);
                break;
            default:
                break;
        }
    }

    /**
     * This method is for minus the player
     * @param addPlayer the player which is gonna be delete
     */
    public void minusButton(JButton addPlayer) {

        if ((this.getPlayerList().getPlayersArray().size()) <= 3) {
            JOptionPane.showMessageDialog(null, "You need at least three players to play the game.");
        } else {
            DeletableButton buttonToDelete = (DeletableButton) addPlayer;
            int IDtoDelete = buttonToDelete.getID();

            for (Iterator<PlayerPanel> it = this.getPlayerList().getPlayersArray().iterator(); it.hasNext();) {
                PlayerPanel panelToTest = it.next();

                if (panelToTest.getDelButton().getID() == IDtoDelete) {
                    Color colorToRemove = panelToTest.getColorButton().getBackground();
                    this.getPlayerList().getColorUsed().remove(colorToRemove);
                    this.getPlayerList().remove(panelToTest);
                    this.getPlayerList().revalidate();
                    this.getPlayerList().repaint();
                    it.remove();
                }
            }

        }
    }

    /**
     * This method is for add player
     * @param addPlayer the new player which is added
     * @param addPlayer
     */
    public void playButton(JButton addPlayer) {
        NewGamePanel newGamePanel = this.getRiskView().getNewGamePanel();
        String selectedPath = newGamePanel.getSelectFileTextField().getText();

        if (selectedPath.equals("")) {
            JOptionPane.showMessageDialog(null, "You have not selected a map");
            return;
        }
        MapModel map = new MapModel();
        int resultReadingValidation = MapFileManagement.createBoard(selectedPath, map);
        if (resultReadingValidation != 0) {
            JOptionPane.showMessageDialog(null, MapFileManagement.readingError(resultReadingValidation));
            return;
        }
        getRiskModel().setMap(map);

        if (!getRiskModel().getMap().isValid()) {
            JOptionPane.showMessageDialog(null, MapFileManagement.readingError(-7));
            return;
        }

        LinkedList<PlayerPanel> listPlayerPanels = newGamePanel.getPlayersPanel().getPlayersArray();
        LinkedList<PlayerModel> listPlayers = new LinkedList<>();
        for (int i = 0; i < listPlayerPanels.size(); i++) {
            PlayerPanel player = listPlayerPanels.get(i);
            PlayerModel playerGame = new HumanPlayerModel(
                    player.getPlayerNameTextField().getText(),
                    player.getColorButton().getBackground(),
                    this.getRiskModel()
            );
            listPlayers.add(playerGame);

        }
        this.getRiskModel().setPlayerList(listPlayers);

        if (!this.getRiskModel().validateTerritories()) {
            JOptionPane.showMessageDialog(null, "No enough territories in this map for the number of players. Select another map.");
            return;
        }
        this.getRiskView().closeMenu();
        this.getRiskController().playGame();

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
