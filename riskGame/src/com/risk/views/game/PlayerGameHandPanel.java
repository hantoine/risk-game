/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.RiskModel;
import com.risk.models.PlayerModel;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View of the cards owned by the player
 *
 * @author liyixuan
 */
public final class PlayerGameHandPanel extends JPanel implements Observer {

    HashMap<String, JButton> handCards = new HashMap<>();

    /**
     * Constructor
     *
     */
    public PlayerGameHandPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Update the information displayed by the PlayerGameHandPanl according to
     * the information of the current player
     *
     * @param rm The model of the game containing all information about the
     * current player
     */
    public void updateView(RiskModel rm) {
        PlayerModel currentPlayer = rm.getCurrentPlayer();
        this.removeAll();
        currentPlayer.getCardsOwned().getCards().stream()
                .forEach((card) -> {
                    addCard(card.getTypeOfArmie(), card.getCountryName(), currentPlayer.getColor());
                });
        this.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof RiskModel) {
            this.updateView((RiskModel) arg);
        } else {
            LinkedList<String> receivedObj = (LinkedList<String>) arg;
            addCard(receivedObj.get(2), receivedObj.get(1), ((PlayerModel) o).getColor());
        }
    }

    /**
     * Add a card to be displayed in this panel
     *
     * @param typeofArmies The name of the type of armies associated with the
     * card
     * @param territoryName The name of the territory associated with the card
     * @param backgroundColor The background color of the card
     */
    public void addCard(String typeofArmies, String territoryName, Color backgroundColor) {
        ImageIcon cardIcon = new ImageIcon("." + File.separator + "images"
                + File.separator + typeofArmies + ".png");
        Image image = cardIcon.getImage();
        Image newImage = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        cardIcon = new ImageIcon(newImage);
        JButton aux = new JButton();
        aux.setIcon(cardIcon);
        aux.setText("");
        aux.setBackground(backgroundColor);
        handCards.put(territoryName, aux);
        this.add(aux);
    }
}
