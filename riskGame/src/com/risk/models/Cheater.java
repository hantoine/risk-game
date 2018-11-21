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
public class Cheater implements Strategy{

    @Override
    public void reinforcement(RiskModel rm) {
        rm.getCurrentPlayer().getTerritoryOwned().stream()
                .forEach(t -> t.setNumArmies(t.getNumArmies()*2));
        rm.finishPhase();
    }

    @Override
    public void attack(RiskModel rm) {
        rm.finishPhase();
    }

    @Override
    public void fortification(RiskModel rm) {
        rm.finishPhase();
    }
    
}
