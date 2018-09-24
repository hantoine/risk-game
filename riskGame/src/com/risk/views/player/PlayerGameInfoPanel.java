/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.CardModel;
import com.risk.models.HandModel;
import com.risk.models.PlayerModel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class PlayerGameInfoPanel extends JPanel {

    private PlayerModel currentPlayer;
    private JButton playerName;
    JLabel numArmies;
    JLabel numCountries;
    JLabel numContinents;
    JLabel[] cards;
    
    public PlayerGameInfoPanel(Player currentPlayer) {
	this.setSize(150,200);
        this.currentPlayer=currentPlayer;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.playerName = new JButton("Player 1");
        
        
        
        this.numArmies=new JLabel();
        this.numContinents=new JLabel();
        this.numCountries=new JLabel();
        
        this.add(playerName);
        this.add(this.numArmies);
        this.add(this.numCountries);
        this.add(this.numContinents);
        
        
        this.cards = new JLabel[5];
        
        // work:
        /*
        1. make the player panel more beautiful (title, border, card image, fix the width)
        2. 
        */
       
    }

    public void updatePlayer(Player currentPlayer) {
        this.currentPlayer=currentPlayer;
        playerName.setText("Player " + currentPlayer.getName());
        playerName.setBackground(currentPlayer.getColor());
        this.numArmies.setText(Integer.toString(currentPlayer.getNumArmies()));
        this.numCountries.setText(Integer.toString(currentPlayer.getContriesOwned().size()));
        this.numContinents.setText(Integer.toString(currentPlayer.getContinentsOwned().size()));
        for (CardModel hand : this.currentPlayer.getCardsOwned().getHand()) {
            JLabel aux = new JLabel();
            System.out.println(hand.getCountryName() + "  " + hand.getTypeOfArmie());
            aux.setText(hand.getCountryName() + "  " + hand.getTypeOfArmie());
            this.add(aux);
        }
        
        
    }

    public void setCurrentPlayer(PlayerModel currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the playerName
     */
    public JButton getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(JButton playerName) {
        this.playerName = playerName;
    }

}
