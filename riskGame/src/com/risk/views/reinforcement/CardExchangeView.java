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
 *
 * @author rebecca
 */
public class CardExchangeView extends JDialog implements Observer{
    PlayerGameHandPanel playerGameHandPanel;
    JButton handCards;
    JButton exit;
    JPanel buttonPanel;
    JLabel exchangeMessage;
    
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
        
        setEnableHand(rm);
      
        
        this.add(exchangeMessage, BorderLayout.NORTH);
        this.add(playerGameHandPanel, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        
    }
    
    public void setListener(CardExchangeListener cardExchangeListener){
        exit.addMouseListener(cardExchangeListener);
        handCards.addMouseListener(cardExchangeListener);
        getPlayerGameHandPanel().getHandCards().values().stream()
                .forEach(b -> b.addMouseListener(cardExchangeListener));
    }

    /**
     * @return the playerGameHandPanel
     */
    public PlayerGameHandPanel getPlayerGameHandPanel() {
        return playerGameHandPanel;
    }

    public void setEnableHand(RiskModel rm){
        if(rm.getCurrentPlayer().getCardsOwned().threeDifferentCardsOrThreeEqualCards())
            handCards.setEnabled(true);
        else{
            handCards.setEnabled(false);
            exit.setEnabled(true);
        }
        if(rm.getCurrentPlayer().isHanded() || rm.getCurrentPlayer().getCardsOwned().getCards().size()<3)
               exit.setEnabled(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof RiskModel) {
           
            setEnableHand((RiskModel) arg);
            
        }

    }
     
    
    
    
}
