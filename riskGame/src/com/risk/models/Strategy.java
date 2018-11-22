/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author hantoine
 */
public interface Strategy {

    public enum Type {
        HUMAN,
        AGGRESSIVE,
        BENEVOLENT,
        RANDOM,
        CHEATER
    }
    
    public abstract void reinforcement(RiskModel playGame);
    public abstract void attack(RiskModel playGame);
    public abstract void fortification(RiskModel playGame);
    public abstract void moveArmies(RiskModel rm);
    abstract boolean exchangeCardsToArmies(RiskModel rm);
    public abstract void defense(RiskModel rm);
}
