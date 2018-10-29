/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.CardModel;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
public final class PlayerGameHandPanel extends JPanel implements Observer {

    /**
     * The view of the cards of the current player
     */
    private HashMap<String, JButton> handCards = new HashMap<>();

    /**
     * Constructor
     *
     */
    public PlayerGameHandPanel() {
        this.setLayout(new FlowLayout());
        //BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Update the information displayed by the PlayerGameHandPanl according to
     * the information of the current player
     *
     * @param rm The model of the game containing all information about the
     * current player
     */
    public void updateView(RiskModel rm) {
        this.setSize(rm.getMap().getMapWidth()+200, rm.getCurrentPlayer().getCardsOwned().getCards().size()/10*100);
        this.removeAll();
        PlayerModel currentPlayer = rm.getCurrentPlayer();
        
         
        currentPlayer.getCardsOwned().getCards().stream()
                .forEach((card) -> {
                    addCard(card.getTypeOfArmie(), card.getCountryName(), currentPlayer.getColor());
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
        if (arg instanceof RiskModel) {
            removeCard((RiskModel) arg);
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
        aux.setName(territoryName);
        aux.setText("");
        aux.setBackground(backgroundColor);
        getHandCards().put(territoryName, aux);
        this.add(aux);
    }

    public void removeCard(RiskModel rm){
        LinkedList<String> removeList=new LinkedList<>();
        Iterator it=this.handCards.keySet().iterator();
        while(it.hasNext()){
            String select=(String) it.next();
            CardModel card;
            card=rm.getCurrentPlayer().getCardsOwned().getCards().stream()
                            .filter(c -> c.getCountryName().equals(select))
                            .findFirst()
                            .orElse(null);
            
            if(card==null)
                removeList.add(select);
            
        }
        
        if(removeList.size()>0){
            removeList.stream()
                .forEach(c -> {
                    this.remove(this.handCards.get(c));
                    this.handCards.remove(c);
                        });
        }
        
        
        this.repaint();
        this.revalidate();
    }
    /**
     * @return the handCards
     */
    public HashMap<String, JButton> getHandCards() {
        return handCards;
    }
    
    
}
