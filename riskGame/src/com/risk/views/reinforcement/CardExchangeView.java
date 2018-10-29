/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.reinforcement;

import com.risk.controllers.CardExchangeListener;
import com.risk.models.RiskModel;
import com.risk.views.game.PlayerGameHandPanel;
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
 * @author rebecca
 */
public class CardExchangeView extends JDialog implements Observer{
    /**
     * Panel of cards
     */
    PlayerGameHandPanel playerGameHandPanel;
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
     * @param rm model of the game
     */
    public  CardExchangeView(RiskModel rm){
        this.setLayout(new BorderLayout());
        
        exchangeMessage= new JLabel("Select 3 cards to exchange:");
        buttonPanel=new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        handCards=new JButton("Hand");
        exit=new JButton("Exit");
        exit.setEnabled(false);
        
        buttonPanel.add(handCards);
        buttonPanel.add(exit);
        
        playerGameHandPanel=new PlayerGameHandPanel();
        playerGameHandPanel.updateView(rm);
        this.setSize(((playerGameHandPanel.getHandCards().size()+1)*50)+150, 400);
        
        this.setEnableHand(rm);
      
        
        this.add(exchangeMessage, BorderLayout.NORTH);
        this.add(playerGameHandPanel, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        
    }
    
    /**
     * Setter of the listener for the different buttons in the view
     * @param cardExchangeListener the mouse listener
     */
    public void setListener(CardExchangeListener cardExchangeListener){
        exit.addMouseListener(cardExchangeListener);
        handCards.addMouseListener(cardExchangeListener);
        getPlayerGameHandPanel().getHandCards().values().stream()
                .forEach(b -> b.addMouseListener(cardExchangeListener));
    }

    /**
     * Getter of the playerHandPanel attribute
     * @return the playerGameHandPanel
     */
    public PlayerGameHandPanel getPlayerGameHandPanel() {
        return playerGameHandPanel;
    }
    
    /**
     * It enables the hand button of the view
     * @param rm the model of the game
     */
    public void setEnableHand(RiskModel rm){
        if(rm.getCurrentPlayer().getCardsOwned().threeDifferentCardsOrThreeEqualCards())
            handCards.setEnabled(true);
        else{
            handCards.setEnabled(false);
            exit.setEnabled(true);
        }
       
        if(rm.getCurrentPlayer().isHanded() || rm.getCurrentPlayer().getCardsOwned().getCards().size()<5)
               exit.setEnabled(true);
    }
    
    /**
     * Observer patter implementation
     * @param o the player or observable
     * @param arg the model receive as parameter
     */
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof RiskModel) {
           
            setEnableHand((RiskModel) arg);
            
        }

    }
     
    
    
    
}
