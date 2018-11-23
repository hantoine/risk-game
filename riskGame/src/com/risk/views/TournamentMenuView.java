/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.TournamentController;
import com.risk.views.menu.DeletableButton;
import com.risk.views.menu.PlayerListPanel;
import com.risk.views.menu.PlayerPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author hantoine
 */
public class TournamentMenuView extends JFrame {

    PlayerListPanel playerListPanel;

    public TournamentMenuView() throws HeadlessException {
        this.setSize(200, 150);
        //this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.centerWindow();
        this.setVisible(true);

        playerListPanel = new PlayerListPanel();
        this.add(playerListPanel);
        playerListPanel.setMouseListener(new MouseAdapter() {
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
                if (c instanceof JButton) {
                    JButton addPlayer = (JButton) c;
                    switch (addPlayer.getText()) {
                        case "+":
                            playerListPanel.addElement(playerListPanel.getNewPlayerName(), playerListPanel.getNewColor());
                            break;
                        case "-":
                            minusButton(addPlayer);
                            break;
                        case "    ":
                            Color selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                            if (playerListPanel.getColorUsed().contains(selectedColor)) {
                                JOptionPane.showMessageDialog(null, "This color is already used");
                            } else {
                                playerListPanel.getColorUsed().remove(addPlayer.getBackground());
                                playerListPanel.getColorUsed().add(selectedColor);
                                addPlayer.setBackground(selectedColor);

                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    JComboBox typeOfPlayer = (JComboBox) c;
                    String playerType = (String) typeOfPlayer.getSelectedItem();
                    typeOfPlayer.setSelectedItem(playerType);

                }
            }

            /**
             * This method is for minus the player
             *
             * @param addPlayer the player which is gonna be delete
             */
            public void minusButton(JButton addPlayer) {

                if ((playerListPanel.getPlayersArray().size()) <= 3) {
                    JOptionPane.showMessageDialog(null, "You need at least three players to play the game.");
                } else {
                    DeletableButton buttonToDelete = (DeletableButton) addPlayer;
                    int IDtoDelete = buttonToDelete.getID();

                    for (Iterator<PlayerPanel> it = playerListPanel.getPlayersArray().iterator(); it.hasNext();) {
                        PlayerPanel panelToTest = it.next();

                        if (panelToTest.getDelButton().getID() == IDtoDelete) {
                            Color colorToRemove = panelToTest.getColorButton().getBackground();
                            playerListPanel.getColorUsed().remove(colorToRemove);
                            playerListPanel.remove(panelToTest);
                            playerListPanel.revalidate();
                            playerListPanel.repaint();
                            it.remove();
                        }
                    }

                }
            }
        });
        playerListPanel.setMaxNbPlayers(4);
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void setController(TournamentController ctrl) {

    }

}
