/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;

/**
 * Interface for strategy implementation
 *
 * @author hantoine
 */
public interface Strategy extends Serializable {

    /**
     * Strategy type
     */
    public enum Type {
        HUMAN,
        AGGRESSIVE,
        BENEVOLENT,
        RANDOM,
        CHEATER
    }

    /**
     * Placement of armies
     *
     * @param playGame risk model
     */
    public abstract void startup(RiskModel playGame);

    /**
     * Reinforcement movement
     *
     * @param playGame risk model
     */
    public abstract void reinforcement(RiskModel playGame);

    /**
     * Attack movement
     *
     * @param playGame risk model
     */
    public abstract void attack(RiskModel playGame);

    /**
     * Fortification movement
     *
     * @param playGame risk model
     */
    public abstract void fortification(RiskModel playGame);

    /**
     * Move armies after conquering a territory
     *
     * @param rm risk model
     */
    public abstract void moveArmies(RiskModel rm);

    /**
     * Card exchange
     *
     * @param rm risk model
     * @return boolean
     */
    abstract boolean exchangeCardsToArmies(RiskModel rm);

    /**
     * Defense
     *
     * @param rm risk model
     */
    public abstract void defense(RiskModel rm);
}
