/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.reinforcement;

import com.risk.controllers.CardExchangeListener;
import com.risk.models.HandModel;
import com.risk.models.RiskModel;
import com.risk.views.game.HandPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Implementation of the exchange view for reinforcement phase
 *
 * @author rebecca
 */
public class CardExchangeView extends JDialog implements Observer {

    /**
     * Panel of cards
     */
    HandPanel playerGameHandPanel;
    /**
     * Button to hand cards
     */
    JButton handCards;
    /**
     * Button to exit
     */
    JButton exit;
    /**
     * Panel of buttons
     */
    JPanel buttonPanel;
    /**
     * Message of the view
     */
    JLabel exchangeMessage;

    /**
     * Constructor of the view
     *
     */
    public CardExchangeView() {
        this.setLayout(new BorderLayout());

        exchangeMessage = new JLabel("Select 3 cards to exchange:");
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        handCards = new JButton("Hand");
        exit = new JButton("Exit");
        exit.setEnabled(false);

        buttonPanel.add(handCards);
        buttonPanel.add(exit);

        playerGameHandPanel = new HandPanel();

        this.add(exchangeMessage, BorderLayout.NORTH);
        this.add(playerGameHandPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Setter of the listener for the different buttons in the view
     *
     * @param cardExchangeListener the mouse listener
     */
    public void setListener(CardExchangeListener cardExchangeListener) {
        exit.addMouseListener(cardExchangeListener);
        handCards.addMouseListener(cardExchangeListener);
        getPlayerGameHandPanel().getCardButtons().values().stream()
                .forEach(b -> b.addMouseListener(cardExchangeListener));
    }

    /**
     * Getter of the playerHandPanel attribute
     *
     * @return the playerGameHandPanel
     */
    public HandPanel getPlayerGameHandPanel() {
        return playerGameHandPanel;
    }

    /**
     * It enables the hand button of the view
     *
     * @param hand
     */
    private void setEnableHand(HandModel hand) {
        if (hand.cardHandingPossible()) {
            handCards.setEnabled(true);
        } else {
            handCards.setEnabled(false);
            exit.setEnabled(true);
        }

        if (hand.isHanded() || hand.getCards().size() < 5) {
            exit.setEnabled(true);
        }
    }

    /**
     *
     * @param hand
     */
    public void updateView(HandModel hand) {
        this.setEnableHand(hand);
        playerGameHandPanel.updateView(hand);
        this.setSize(((hand.getNbCards() + 1) * 50) + 150, 400);
    }

    /**
     * Observer patter implementation
     *
     * @param o the player or observable
     * @param arg the model receive as parameter
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
     *
     * @param rm
     */
    public void observe(RiskModel rm) {
        rm.getPlayerList().stream().forEach((pl) -> {
            pl.getHand().addObserver(this.getPlayerGameHandPanel());
            pl.getHand().addObserver(this);
        });
    }

}
