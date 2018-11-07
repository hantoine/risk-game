/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;

/**
 * It represents an AI player (child of PlayerModel)
 *
 * @author Nellybett
 */
public class AIPlayerModel extends PlayerModel {

    /**
     * Constructor from Player Model
     *
     * @param name name of the player
     * @param color color of the player
     * @param game Game in which this players belongs
     */
    AIPlayerModel(String name, Color color, RiskModel game) {
        super(name, color, false, game);
    }

    /**
     * Reinforcement phase for AI Player
     */
    @Override
    public void reinforcement(RiskModel playGame) {
        addNewLogEvent(String.format(
                "%s starts its reinforcement phase",
                getName()
        ), true);

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Fortification phase for AI Player
     */
    @Override
    public void fortification(RiskModel playGame) {
        addNewLogEvent(String.format(
                "%s starts its fortification phase",
                getName()
        ), true);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Attack phase for AI Player
     */
    @Override
    public void attack(RiskModel playGame) {
        addNewLogEvent(String.format(
                "%s starts its attack phase",
                getName()
        ), true);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method is to exchange cards to armies
     * @return the boolean if it's exchanged or not
     */
    @Override
    public boolean exchangeCardsToArmies() {
        int[] cardDuplicates = this.getHand().getCardDuplicates();

        if (cardDuplicates[0] >= 3) {
            this.getHand().removeCards("infantry", super.game.getDeck());
        } else if (cardDuplicates[1] >= 3) {
            this.getHand().removeCards("cavalry", super.game.getDeck());
        } else if (cardDuplicates[2] >= 3) {
            this.getHand().removeCards("artillery", super.game.getDeck());
        } else {
            this.getHand().removeCards("different", super.game.getDeck());
        }
        armiesCardAssignation();
        return true;
    }

}
