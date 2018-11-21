/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import static java.lang.Integer.min;

/**
 * It represents an AI player (child of PlayerModel)
 *
 * @author Nellybett
 */
public class AIPlayerModel extends PlayerModel {

    private Strategy strategy;
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
        strategy.reinforcement(playGame);
        addNewLogEvent(String.format(
                "%s starts its reinforcement phase",
                getName()
        ), true);

    }

    /**
     * Fortification phase for AI Player
     */
    @Override
    public void fortification(RiskModel playGame) {
        strategy.fortification(playGame);
        addNewLogEvent(String.format(
                "%s starts its fortification phase",
                getName()
        ), true);
    }

    /**
     * Attack phase for AI Player
     */
    @Override
    public void attack(RiskModel playGame) {
        strategy.attack(playGame);
        addNewLogEvent(String.format(
                "%s starts its attack phase",
                getName()
        ), true);
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

    @Override
    public void defense() {
        this.game.getCurrentPlayer().setDefenseValues(2);
        this.game.performAttack(this.game.getCurrentPlayer());
        this.game.setAttackPhase(true);
    }

    /**
     * @return the strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * @param strategy the strategy to set
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    

}
