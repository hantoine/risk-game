/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.interfaces.PlayerModel;
import javax.swing.BoxLayout;
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

        this.add(this.playerName);
        this.add(this.numArmies);
        this.add(this.numCountries);
        this.add(this.numContinents);
        this.add(this.numCards);

        updatePlayer(currentPlayer);
    }

    /**
     * Updates player information
     *
     * @param currentPlayer
     */
    public void updatePlayer(PlayerModel currentPlayer) {
        // update player name and color
        playerName.setText(currentPlayer.getName());
        playerName.setBackground(currentPlayer.getColor());

        //update the number of armies
        this.numArmies.setText("Army Owned: " + currentPlayer.getNumArmies());

        // update countries and continents
        this.numCountries.setText("Countries Owned: " + Integer.toString(currentPlayer
                .getContriesOwned().size()));
        this.numContinents.setText("Continents Owned: " + Integer.toString(currentPlayer
                .getContinentsOwned().size()));

        // update cards
        this.numCards.setText("Card Owned: " + currentPlayer.getCardsOwned().getCards().size());
    }
}
