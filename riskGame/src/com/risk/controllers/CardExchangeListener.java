/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;


import com.risk.models.HumanPlayerModel;
import com.risk.views.game.PlayerGameHandPanel;
import com.risk.views.reinforcement.CardExchangeView;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;


/**
 * The listener of the exchange card view
 * @author rebecca
 */
public class CardExchangeListener extends MouseAdapter{
    /**
     * The controller of the views in the game
     */
    RiskController riskController;
    /**
     * Panel of cards
     */
    PlayerGameHandPanel playerGameHandPanel;
    /**
     * Cards to be handed
     */
    LinkedList<String> selectedCards=new LinkedList<>();
    /**
     * Boolean to validate that the player hand cards at least 1 time
     */
    boolean handed;
    
    /**
     * Constructor  
     * @param rc controller of the game
     */
    public CardExchangeListener(RiskController rc){
        riskController=rc;
        handed=false;
    }
    
    /**
     * The event manager of the exchange card view
     * @param e the event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        System.out.println(c.getClass());
        if (c instanceof JButton) {
           
           JButton buttonPressed=(JButton) c;
           if(buttonPressed.isEnabled()){
                if(buttonPressed.getText().equals("Exit")){             
                    riskController.getViewRisk().closeExchangeMenu();
                    selectedCards.clear();
                }else if(buttonPressed.getText().equals("Hand")){
                    if(selectedCards.size()<3)
                        this.showMessage("You have to select 3 cards first.");
                    else{
                        if(riskController.getPlayGame().clickHand(selectedCards)){
                            this.showMessage("You can only hand 3 equal or 3 different cards");
                        }else{
                            selectedCards.clear();
                            ((HumanPlayerModel)riskController.getModelRisk().getCurrentPlayer()).setHanded(true);
                        }
                        
                    }
                    
                }else{
                    if(buttonPressed.getBackground()==Color.black){
                        buttonPressed.setBackground(riskController.getModelRisk().getCurrentPlayer().getColor());
                        selectedCards.remove(buttonPressed.getName());
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
        }
    }
    
    /**
     * Assignation of observers
     * @param cardExchangeView the observer
     */
    public void setPanel(CardExchangeView cardExchangeView){
        this.playerGameHandPanel=cardExchangeView.getPlayerGameHandPanel();
        riskController.getModelRisk().getCurrentPlayer().addObserver(cardExchangeView.getPlayerGameHandPanel());
        riskController.getModelRisk().getCurrentPlayer().addObserver(cardExchangeView);
    }
    
    /**
     * Error message
     * @param message the error 
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
