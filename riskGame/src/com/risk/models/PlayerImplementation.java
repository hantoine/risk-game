/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;


/**
 * It represents an human player (child of PlayerModel)
 *
 * @author Nellybett
 */
public class PlayerImplementation extends PlayerModel {

    private Strategy strategy;
    /**
     * Constructor from Player Model
     *
     * @param name name of the player
     * @param color color of the player
     * @param game Game in which this players belongs
     */
    public PlayerImplementation(String name, Color color, RiskModel game) {
        super(name, color, true, game);
    }

    /**
     * Reinforcement phase for Human Player
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
     * Fortification phase for Human Player
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
     * Attack phase for Human Player
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

    
    @Override
    boolean exchangeCardsToArmies() {
        return strategy.exchangeCardsToArmies(this.game);
    }

    
    @Override
    public void defense() {
        strategy.defense(this.game);
    }

    
    @Override
    public void moveArmies() {
        strategy.moveArmies(this.game);
    }

}
