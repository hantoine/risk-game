/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;

/**
 * Human strategy
 *
 * @author Nellybett
 */
public class HumanStrategy implements Strategy, Serializable {

    /**
     * Reinforcement for human
     *
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {
        rm.getCurrentPlayer().setHanded(false);
        rm.getCurrentPlayer().assignNewArmies();
    }

    /**
     * Depends on events
     *
     * @param playGame risk model
     */
    @Override
    public void attack(RiskModel playGame) {

    }

    /**
     * Depends on events
     *
     * @param playGame risk model
     */
    @Override
    public void fortification(RiskModel playGame) {

    }

    /**
     * Depends on events
     *
     * @param rm risk model
     */
    @Override
    public void moveArmies(RiskModel rm) {

    }

    /**
     * Exchange cards for human
     *
     * @param rm risk model
     * @return true if cards were exchange
     */
    @Override
    public boolean exchangeCardsToArmies(RiskModel rm) {
        return rm.getCurrentPlayer().exchangeCardsToArmiesHuman();
    }

    /**
     * Depends on events
     *
     * @param rm risk model
     */
    @Override
    public void defense(RiskModel rm) {

    }

    /**
     * Depends on events
     *
     * @param playGame risk model
     */
    @Override
    public void startup(RiskModel playGame) {

    }

    /**
     * Alias for the equal function in order to test equality between two objects of the same class.
     * @param obj object of the same class we want to compare to this instance.
     * @return boolean to know if the objects are equal or not
     */
    @Override
    public boolean identical(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HumanStrategy other = (HumanStrategy) obj;
        
        return true;
    }

}
