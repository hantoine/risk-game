/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Nellybett
 */
public class BenevolentStrategy implements Strategy {

    @Override
    public void reinforcement(RiskModel rm) {
        System.out.println("BENEVOLENTE REINFORCEMENT");
        rm.aIReinforcement();

        int armiesReinforcement = rm.getCurrentPlayer().getNbArmiesAvailable();
        TerritoryModel selectedTerritory = rm.getCurrentPlayer().getTerritoryOwned().get(0);

        while (armiesReinforcement > 0) {
            for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
                if (t.getNumArmies() < selectedTerritory.getNumArmies()) {
                    selectedTerritory = t;
                }
            }

            rm.reinforcementIntent(selectedTerritory);
            armiesReinforcement = armiesReinforcement - 1;
        }

    }

    @Override
    public void attack(RiskModel rm) {
        System.out.println("BENEVOLENTE ATAQUE");
        rm.finishPhase();
    }

    @Override
    public void fortification(RiskModel rm) {
        System.out.println("BENEVOLENTE FORTIFICACION");
        TerritoryModel dest = null;
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (((TerritoryModel) t).getAdj().stream()
                    .anyMatch(ta -> rm.getCurrentPlayer().getTerritoryOwned().contains(ta))) {
                if (dest == null || dest.getNumArmies() > t.getNumArmies()) {
                    dest = t;
                }
            }
        }

        TerritoryModel source = null;
        if (dest != null) {
            for (TerritoryModel t : dest.getAdj()) {
                if (rm.getCurrentPlayer().getTerritoryOwned().contains(t) && (source == null || source.getNumArmies() < t.getNumArmies())) {
                    source = t;
                }
            }
        }

        while (source != null && dest != null && source.getNumArmies() > 1) {
            rm.fortificationIntent(source, dest);
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
       
        TerritoryModel territoryClicked=rm.randomTerritory((List < TerritoryModel >)rm.getMap().getTerritories().stream()
                                                                                                            .filter(t -> t.getOwner()==null || t.getOwner()==rm.getCurrentPlayer())
                                                                                                            .collect(Collectors.toCollection(LinkedList::new)));
        rm.startupMove(territoryClicked);
    }

}
