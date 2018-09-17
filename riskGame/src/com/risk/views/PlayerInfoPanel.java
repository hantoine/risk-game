/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.Player;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author l_yixu
 */
public class PlayerInfoPanel extends JPanel {
    
    private int panelWidth;
    private int panelHeight;
    private Player player;
    private JLabel playerName;
    private JLabel playerColor;
    private JLabel playerOwnedContries;
    private JLabel playerOwnedContinents;
    private JLabel playerOwnedCards;
    private JLabel playerOwnedArmies;
    
    public PlayerInfoPanel() {
    }
    
    public PlayerInfoPanel(int width, int height, Player currentTurnPlayer) {
        this.panelWidth = width;
        this.panelHeight = height;
        this.player = currentTurnPlayer;
        this.playerName = new JLabel("Name: " + this.player.getName());
        this.playerColor = new JLabel("Color: " + this.player.getColor());
        this.playerOwnedArmies = new JLabel("Owned Armies: " + this.player.getNumArmies());
        // Question 3: For player owned contries, continents, cards, how should I show these three players' info on the Player Info Panel, show images or just string (e.g. owned continents' names, owned cards' names)
        // For owned contries and continents, it needs to show the number, and for owned cards, it needs to show the images (go to see and implement Hand class)
        this.playerOwnedContries = new JLabel("Owned Countries: " + this.player.getContriesOwned().size());
        this.playerOwnedContinents = new JLabel("Owned Continents: " + this.player.getContinentsOwned().size());
        this.playerOwnedCards = new JLabel("Owned Cards: " + this.player.getCardsOwned()); 
    }

    /**
     * @return the panelWidth
     */
    public int getPanelWidth() {
        return panelWidth;
    }

    /**
     * @param panelWidth the panelWidth to set
     */
    public void setPanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    /**
     * @return the panelHeight
     */
    public int getPanelHeight() {
        return panelHeight;
    }

    /**
     * @param panelHeight the panelHeight to set
     */
    public void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }
    
    /**
     * @param player the panelWidth to set
     */
    public void setPlayerObject(Player player) {
        this.player = player;
    }
    
    /**
     * @return the player
     */
    public Player getPlayerObject() {
        return player;
    }
    
    /**
     * Set the JLabel font color
     * 
     * @param color the font color to set
     */
    public void setFontColor(Color color) {
        this.playerName.setForeground(color);
        this.playerColor.setForeground(color);
        this.playerOwnedArmies.setForeground(color);
        this.playerOwnedContries.setForeground(color);
        this.playerOwnedContinents.setForeground(color);
        this.playerOwnedCards.setForeground(color);
    }
    
    /**
     * add JLabels to Player Info Panel
     */
    public void addElements() {
        this.add(new JLabel("<html>" 
                + "Name: " + player.getName() + "<br>" 
                + "Color: " + player.getColor() + "<br>"
                + "Owned Armies: " + player.getNumArmies() + "<br>"
                + "Owned Countries: " + player.getContriesOwned() + "<br>" 
                + "Owned Continents: " + player.getContinentsOwned() + "<br>"
                + "Owned Cards: " + player.getCardsOwned() + "<br>" 
                + "</html>"));
//        this.add(this.playerName);
//        this.add(this.playerColor);
//        this.add(this.playerOwnedArmies);
//        this.add(this.playerOwnedContries);
//        this.add(this.playerOwnedContinents);
//        this.add(this.playerOwnedCards);
    }
    
}
