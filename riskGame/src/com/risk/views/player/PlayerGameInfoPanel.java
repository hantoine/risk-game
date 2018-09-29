/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.interfaces.PlayerModel;
import java.awt.Image;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View of the player information
 *
 * @author Will
 */
public final class PlayerGameInfoPanel extends JPanel {

    private final JButton playerName;
    private final JButton numArmies;
    private final JButton numCountries;
    private final JButton numContinents;
    private final Box cardBox;
    private final JButton numCards;

    /**
     * Constructor
     *
     * @param currentPlayer player that has the turn
     */
    public PlayerGameInfoPanel(PlayerModel currentPlayer) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(150, 450);
        
        this.playerName = new JButton();
        this.numArmies = new JButton();
        this.numContinents = new JButton();
        this.numCountries = new JButton();
        this.numCards = new JButton();
        this.cardBox = new Box(BoxLayout.X_AXIS);
  
        this.add(this.playerName);
        this.add(this.numArmies);
        this.add(this.numCountries);
        this.add(this.numContinents);
        this.add(this.numCards);
        this.add(this.cardBox);
        
        updatePlayer(currentPlayer);
    }

    /**
     *
     * @param currentPlayer
     */
    public void updatePlayer(PlayerModel currentPlayer) {
        // update player name and color
        playerName.setText(currentPlayer.getName());
        playerName.setBackground(currentPlayer.getColor());

        //update the number of armies
        this.numArmies.setText("Army Owned: " + currentPlayer.getNumArmiesOwned());

        // update countries and continents
        this.numCountries.setText("Countries Owned: " + Integer.toString(currentPlayer
                .getContriesOwned().size()));
        this.numContinents.setText("Continents Owned: " + Integer.toString(currentPlayer
                .getContinentsOwned().size()));

        // update cards
        this.numCards.setText("Card Owned: " + currentPlayer.getCardsOwned().getHand().size());
        cardBox.removeAll();
        currentPlayer.getCardsOwned().getHand().stream().forEach((card) -> {
            // get the corresponding card ImageIcon and resize the card image
            ImageIcon cardIcon = new ImageIcon("." + File.separator + "images"
                    + File.separator + card.getTypeOfArmie() + ".png");
            Image image = cardIcon.getImage();
            Image newImage = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(newImage);
            JButton aux = new JButton();
            aux.setIcon(cardIcon);
            aux.setText("");
            aux.setBackground(currentPlayer.getColor());
            cardBox.add(aux);
        });
    }
}