/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.CardModel;
import com.risk.models.interfaces.PlayerModel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View of the player information
 *
 * @author Will
 */
public class PlayerGameInfoPanel extends JPanel {

    private JButton playerName;
    private BufferedImage image;
    private ImageIcon background;
    private JButton numArmies;
    private Box armyBox;
    private JButton numCountries;
    private JButton numContinents;
    private Box cardBox;
    private JButton numCards;
    private JLabel title;

    /**
     * Constructor
     *
     * @param currentPlayer player that has the turn
     * @throws IOException
     */
    public PlayerGameInfoPanel(PlayerModel currentPlayer) throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(150, 450);
        this.playerName = new JButton();

        this.numArmies = new JButton("Army Owned: " + currentPlayer.getNumArmiesOwned());
        this.armyBox = new Box(BoxLayout.X_AXIS);
        this.numContinents = new JButton("Continent Owned: " + currentPlayer.getContinentsOwned().size());
        this.numCountries = new JButton("Country Owned: " + currentPlayer.getContriesOwned().size());
        this.numCards = new JButton("Card Owned: " + currentPlayer.getCardsOwned().getHand().size());
        this.cardBox = new Box(BoxLayout.X_AXIS);

        this.add(this.playerName);
        this.add(this.numArmies);
        this.add(this.armyBox);
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

        // update armies
        ImageIcon armyIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-1.png");
        JLabel numArmies = new JLabel(armyIcon);
        Box armyBox = new Box(BoxLayout.X_AXIS);
        
        this.armyBox.removeAll();
        this.armyBox.add(numArmies);
        this.armyBox.add(new JLabel("* " + currentPlayer.getNumArmiesOwned() + "     "));

                

        // update countries and continents
        this.numCountries.setText("Countries Owned: " + Integer.toString(currentPlayer
                .getContriesOwned().size()));
        this.numContinents.setText("Continents Owned: " + Integer.toString(currentPlayer
                .getContinentsOwned().size()));

        // update cards
        this.numCards.setText("Card Owned: " + currentPlayer.getCardsOwned().getHand().size());
        Box infantryCardBox = new Box(BoxLayout.Y_AXIS);
        Box cavalryCardBox = new Box(BoxLayout.Y_AXIS);
        Box artilleryCardBox = new Box(BoxLayout.Y_AXIS);
        for (CardModel hand : currentPlayer.getCardsOwned().getHand()) {
            // get the corresponding card ImageIcon and resize the card image
            ImageIcon cardIcon = new ImageIcon("." + File.separator + "images"
                    + File.separator + hand.getTypeOfArmie() + ".png");
            Image image = cardIcon.getImage();
            Image newImage = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(newImage);
            JButton aux = new JButton();
            aux.setIcon(cardIcon);
            aux.setText("");
            aux.setBackground(currentPlayer.getColor());
            switch (hand.getTypeOfArmie()) {
                case "infantry":
                    infantryCardBox.add(aux);
                    break;
                case "cavalry":
                    cavalryCardBox.add(aux);
                    break;
                case "artillery":
                    artilleryCardBox.add(aux);
                    break;
            }
        }
        cardBox.removeAll();
        cardBox.add(infantryCardBox);
        cardBox.add(cavalryCardBox);
        cardBox.add(artilleryCardBox);

    }

}
