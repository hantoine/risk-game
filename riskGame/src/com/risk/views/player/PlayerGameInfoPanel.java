/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.Card;
import com.risk.models.Hand;
import com.risk.models.Player;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class PlayerGameInfoPanel extends JPanel {

    private Player currentPlayer;
    private JButton playerName;
    JLabel numArmies;
    JLabel numCountries;
    JLabel numContinents;
    JLabel[] cards;
    
    public PlayerGameInfoPanel(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.setLayout(new FlowLayout());
        
	this.setSize(150,200);
        this.playerName = new JButton("Player 1");
        this.add(playerName);

        this.cards = new JLabel[5];
        this.add(this.numArmies);
        this.add(this.numCountries);
        this.add(this.numContinents);
    }

    public void updatePlayer(Player currentPlayer) {
        playerName.setText("Player " + currentPlayer.getName());
        playerName.setBackground(currentPlayer.getColor());
        this.numArmies.setText(Integer.toString(currentPlayer.getNumArmies()));
        this.numCountries.setText(Integer.toString(currentPlayer.getCountriesOwned().size()));
        this.numContinents.setText(Integer.toString(currentPlayer.getContinentsOwned().size()));
        for (int i = 0; i < this.cards.length; i++) {
            JLabel aux = new JLabel();
            Card aux1 = currentPlayer.getCardsOwned().getHand()[i];
            aux.setText(aux1.getCountryName() + "  " + aux1.getTypeOfArmie());
            this.cards[i] = aux;
            this.add(aux);
        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
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
