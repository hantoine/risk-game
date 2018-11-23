/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author Nellybett
 */
public class HumanStrategy implements Strategy{

    @Override
    public void reinforcement(RiskModel rm) {
        rm.getCurrentPlayer().setHanded(false);
        rm.getCurrentPlayer().assignNewArmies();
    }

    @Override
    public void attack(RiskModel playGame) {
        
    }

    @Override
    public void fortification(RiskModel playGame) {
        
    }

    @Override
    public void moveArmies(RiskModel rm) {
        
    }

    @Override
    public boolean exchangeCardsToArmies(RiskModel rm) {
        return rm.getCurrentPlayer().exchangeCardsToArmiesHuman();
    }

    @Override
    public void defense(RiskModel rm) {
        
    }
    
}
