/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.HandModel;
import com.risk.models.RiskModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * The listener of the exchange card view
 *
 * @author rebecca
 */
public class CardExchangeListener extends MouseAdapter {

    RiskModel rm;
    GameController gc;

    /**
     * Constructor
     *
     */
    public CardExchangeListener(RiskModel rm, GameController gc) {
        this.gc = gc;
        this.rm = rm;
    }

    /**
     * The event manager of the exchange card view
     *
     * @param e the event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        HandModel hand = rm.getCurrentPlayer().getHand();

        if (!(c instanceof JButton)) {
            return;
        }

        JButton buttonPressed = (JButton) c;
        if (!buttonPressed.isEnabled()) {
            return;
        }

        switch (buttonPressed.getText()) {
            case "Exit":
                gc.closeCardExchangeView();
                hand.unselectAllCards();
                rm.getCurrentPlayer().setHanded(false);
                break;
            case "Hand":
                if (hand.getNbSelectedCards() < 3) {
                    this.showMessage("You have to select 3 cards first.");
                    break;
                }
                if (rm.exchangeCardsWithArmiesForCurrentPlayer()) {
                    this.showMessage(
                            "You can only hand 3 equal or 3 different cards");
                    break;
                }
                hand.unselectAllCards();
                break;
            default:
                String buttonName = buttonPressed.getName();
                if (hand.isCardSelected(buttonName)) {
                    hand.unselectCard(buttonName);
                    break;
                }
                if (hand.getNbSelectedCards() == 3) {
                    this.showMessage("You cannot select more than 3 cards.");
                    break;
                }
                hand.selectCard(buttonName);

                break;
        }

    }

    /**
     * Error message
     *
     * @param message the error
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
