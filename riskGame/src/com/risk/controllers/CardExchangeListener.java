/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;


import com.risk.views.game.PlayerGameHandPanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;


/**
 *
 * @author rebecca
 */
public class CardExchangeListener extends MouseAdapter{
    RiskController riskController;
    PlayerGameHandPanel playerGameHandPanel;
    LinkedList<String> selectedCards=new LinkedList<>();
    boolean handed;
    public CardExchangeListener(RiskController rc){
        riskController=rc;
        handed=false;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        System.out.println(c.getClass());
        if (c instanceof JButton) {
            JButton buttonPressed=(JButton) c;
           if(buttonPressed.getText().equals("Exit")){
               if(riskController.getModelRisk().getCurrentPlayer().getCardsOwned().getCards().size()>=5 && handed==false)
                   this.showMessage("You have to hand some cards");
               else{
                   riskController.getViewRisk().closeExchangeMenu();
                   handed=true;
               }
           }else if(buttonPressed.getText().equals("Hand")){
               riskController.getPlayGame().clickHand();
           }else{
               if(selectedCards.size()<3){
                   buttonPressed.setOpaque(true);
                   buttonPressed.setBackground(Color.black);
                   selectedCards.add(buttonPressed.getName());
               }else{
                   this.showMessage("You selected 3 cards. Please press hand.");
               }
           }
               
        }
    }
    
    public void setPanel(PlayerGameHandPanel playerGameHandPanel){
        this.playerGameHandPanel=playerGameHandPanel;
        riskController.getModelRisk().getCurrentPlayer().addObserver(playerGameHandPanel);
        
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
