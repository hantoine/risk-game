/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.HandModel;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
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

    /**
     * The view of the cards of the current player
     */
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
     * @param currentHand The model of the current game hand
     */
    public void updateView(HandModel currentHand) {
        this.removeAll();
        currentHand.getCards().forEach((card) -> {
            addCard(card.getTypeOfArmie(),
                    card.getCountryName(),
                    currentHand.getOwner().getColor()
            );
        });
        this.repaint();
    }

    /**
     * Observer patter implementation
     *
     * @param o the observable
     * @param arg the object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof HandModel) {
            HandModel updatedHand = (HandModel) o;
            if (updatedHand.isCurrent()) {
                updateView(updatedHand);
            }
        }
    }

    /**
     * Add a card to be displayed in this panel
     *
     * @param armyType The name of the type of armies associated with the card
     * @param territoryName The name of the territory associated with the card
     * @param bgColor The background color of the card
     */
    public void addCard(String armyType, String territoryName, Color bgColor) {
        ImageIcon cardIcon = new ImageIcon("." + File.separator + "images"
                + File.separator + armyType + ".png");
        Image image = cardIcon.getImage();
        Image newImage = image.getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        cardIcon = new ImageIcon(newImage);
        JButton aux = new JButton();
        aux.setIcon(cardIcon);
        aux.setText("");
        aux.setBackground(bgColor);
        handCards.put(territoryName, aux);
        this.add(aux);
    }
}
