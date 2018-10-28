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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author rebecca
 */
public class CardExchangeView extends JDialog {
    private PlayerGameHandPanel playerGameHandPanel;
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
        
        buttonPanel.add(handCards);
        buttonPanel.add(exit);
        
        playerGameHandPanel=new PlayerGameHandPanel();
        playerGameHandPanel.updateView(rm);
        
        this.add(exchangeMessage, BorderLayout.NORTH);
        this.add(playerGameHandPanel, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.setSize(400, 400);
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
    
    
}
