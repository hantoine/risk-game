/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.CardModel;
import com.risk.models.HandModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View of the cards owned by the player
 *
 * @author liyixuan
 */
public final class HandPanel extends JPanel implements Observer {

    /**
     * The view of the cards of the current player
     */
    private HashMap<String, JButton> cardButtons = new HashMap<>();

    /**
     * Constructor
     *
     */
    public HandPanel() {
        this.setLayout(new FlowLayout());
        //BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Update the information displayed by the PlayerGameHandPanl according to
     * the information of the current player
     *
     * @param hand The model of the current game hand
     */
    public void updateView(HandModel hand) {

        // Remove cards that have been removed
        Iterator<JButton> it = this.getCardButtons().values().iterator();
        while (it.hasNext()) {
            JButton cb = it.next();
            boolean cardDeleted = hand.getCards().stream()
                    .anyMatch(c -> c.getCountryName().equals(cb.getName()));
            if (!cardDeleted) {
                it.remove();
                this.remove(cb);
            }
        }

        // Add cards that have been added
        hand.getCards().stream()
                .filter((c) -> !this.cardButtons.containsKey(c.getCountryName()))
                .forEach((c) -> {
                    this.addCard(c, Color.BLACK);
                });

        // Change color of buttons depending on which cards have been selected
        this.cardButtons.values().stream().forEach(cb -> {
            if (hand.isCardSelected(cb.getName())) {
                cb.setBackground(Color.BLACK);
            } else {
                cb.setBackground(hand.getOwner().getColor());
            }
        });

        this.repaint();
        this.revalidate();
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
     * @param card the card model corresponding to the card to add
     * @param bgColor The background color of the card
     */
    public void addCard(CardModel card, Color bgColor) {
        ImageIcon cardIcon = new ImageIcon("." + File.separator + "images"
                + File.separator + card.getTypeOfArmie() + ".png");
        Image image = cardIcon.getImage();
        Image newImage = image.getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        cardIcon = new ImageIcon(newImage);
        JButton aux = new JButton();
        aux.setIcon(cardIcon);
        aux.setName(card.getCountryName());
        aux.setText("");
        aux.setBackground(bgColor);
        getCardButtons().put(card.getCountryName(), aux);
        this.add(aux);
    }

    /**
     * @return the handCards
     */
    public HashMap<String, JButton> getCardButtons() {
        return cardButtons;
    }

}
