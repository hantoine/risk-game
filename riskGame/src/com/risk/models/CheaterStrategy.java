/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 *
 * @author Nellybett
 */
public class CheaterStrategy implements Strategy {

    @Override
    public void reinforcement(RiskModel rm) {

        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            t.setNumArmies(t.getNumArmies() * 2);
        }
        rm.finishPhase();
    }

    @Override
    public void attack(RiskModel rm) {

        LinkedList<TerritoryModel> auxiliar = new LinkedList<>();
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            t.getAdj().stream()
                    .filter(ta -> ta.getOwner() != rm.getCurrentPlayer())
                    .forEach(ad -> {
                        auxiliar.add(ad);
                    });
        }
        auxiliar.stream()
                .forEach(terr -> rm.getCurrentPlayer().addTerritoryOwned(terr));

        rm.checkForDeadPlayers();
        rm.finishPhase();
    }

    @Override
    public void fortification(RiskModel rm) {
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (t.getAdj().stream()
                    .anyMatch(ta -> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))) {
                t.setNumArmies(t.getNumArmies() * 2);
            }
        }
        rm.finishPhase();
    }

    @Override
    public void moveArmies(RiskModel rm) {
        rm.getCurrentPlayer().moveArmiesAI();
    }

    @Override
    public boolean exchangeCardsToArmies(RiskModel rm) {
        return rm.getCurrentPlayer().exchangeCardsToArmiesAI();
    }

    @Override
    public void defense(RiskModel rm) {
        rm.getCurrentPlayer().defenseAI();
    }

    @Override
    public void startup(RiskModel rm) {
        TerritoryModel territoryClicked=rm.randomTerritory((LinkedList < TerritoryModel >)rm.getMap().getTerritories().stream()
                                                                                                            .filter(t -> t.getOwner()==null));
        rm.startupMove(territoryClicked);
    }

}
