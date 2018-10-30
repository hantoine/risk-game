/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View of the player information
 *
 * @author Will
 */
public final class PlayerGameInfoPanel extends JPanel {

    /**
     * playerName name of the player
     */
    private final JButton playerName;
    /**
     * numArmiesOwned the number of armies owned by the player
     */
    private final JButton numArmiesOwned;
    /**
     * numCountries the number of countries owned by the player
     */
    private final JButton numCountries;
    /**
     * numContinents the number of continent owned by the player
     */
    private final JButton numContinents;
    /**
     * numCards the number of cards owned by the player
     */
    private final JButton numCards;

    /**
     * Constructor
     *
     */
    public PlayerGameInfoPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(false);

        this.playerName = new JButton();
        this.numArmiesOwned = new JButton();
        this.numContinents = new JButton();
        this.numCountries = new JButton();
        this.numCards = new JButton();

        this.add(this.playerName);
        this.add(this.numArmiesOwned);
        this.add(this.numCountries);
        this.add(this.numContinents);
        this.add(this.numCards);
    }

    /**
     * Update the information displayed by the PlayerGameInfoPanel according to
     * the information of the current player
     *
     * @param rm The model of the game containing all information about the
     * current player
     */
    public void updateView(RiskModel rm) {
        PlayerModel currentPlayer = rm.getCurrentPlayer();
        this.setVisible(true);

        // update player name and color
        playerName.setText(currentPlayer.getName());
        playerName.setBackground(currentPlayer.getColor());

        //update the number of armies
        this.numArmiesOwned.setText("Army Owned: "
                + currentPlayer.getNumArmiesOwned());

        // update countries and continents
        this.numCountries.setText("Countries Owned: "
                + Integer.toString(currentPlayer.getNbCountriesOwned()));
        this.numContinents.setText("Continents Owned: "
                + Integer.toString(currentPlayer.getNbContinentsOwned()));

        // update cards
        this.numCards.setText("Card Owned: "
                + currentPlayer.getCardsOwned().getNbCards());
    }
}
