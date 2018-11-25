/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import static java.lang.Integer.min;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Nellybett
 */
public class AggressiveStrategy implements Strategy {

    TerritoryModel selectedTerritoryAttack;

    @Override
    public void reinforcement(RiskModel rm) {
        System.out.println("AGRESIVO REINFORCEMENT");
        rm.aIReinforcement();
        int armiesReinforcement = rm.getCurrentPlayer().getNbArmiesAvailable();
        TerritoryModel selectedTerritory = null;
        
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (t.getAdj().stream()
                    .anyMatch(ta -> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))) {
                
                if(selectedTerritory==null || selectedTerritory.getNumArmies()<t.getNumArmies())
                    selectedTerritory = t;
            }
        }
        
        if(selectedTerritory==null){
            selectedTerritory=rm.getCurrentPlayer().getTerritoryOwned().get(0);

            for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
                if (t.getNumArmies() > selectedTerritory.getNumArmies()) {
                    selectedTerritory = t;
                }
            }
        }
        
        while (armiesReinforcement > 0) {
            rm.reinforcementIntent(selectedTerritory);
            armiesReinforcement = armiesReinforcement - 1;
        }

    }

    @Override
    public void attack(RiskModel rm) {
        System.out.println("AGRESIVO ATAQUE");
        selectTerritory(rm);
        if (selectedTerritoryAttack != null) {
            TerritoryModel dest = selectedTerritoryAttack.getAdj().stream()
                    .filter(ad -> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ad)))
                    .findFirst()
                    .orElse(null);

            if (selectedTerritoryAttack.getNumArmies() > 1 && dest != null) {
                int numDice = min(selectedTerritoryAttack.getNumArmies() - 1, 3);
                rm.attackIntent(selectedTerritoryAttack, dest);
                rm.continueAttack(numDice);

            } else {
                System.out.println("SE ACABO EL ATAQUE AGRESIVO");
                rm.executeAttack();
            }
        } else {
            rm.finishPhase();
        }
    }

    @Override
    public void fortification(RiskModel rm) {
        System.out.println("AGRESIVO FORTI");

        TerritoryModel dest = null;
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (((TerritoryModel) t).getAdj().stream()
                    .anyMatch(ta -> rm.getCurrentPlayer().getTerritoryOwned().contains(ta))) {
                if (dest == null || dest.getNumArmies() < t.getNumArmies()) {
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

        while (source != null && source.getNumArmies() > 1) {
            rm.fortificationIntent(source, dest);
        }

        rm.finishPhase();
    }

    public void selectTerritory(RiskModel rm) {

        selectedTerritoryAttack = null;
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (((TerritoryModel) t).getAdj().stream()
                    .anyMatch(ta -> (!(rm.getCurrentPlayer().getTerritoryOwned().contains(ta)) && t.getNumArmies() > 1))) {
                if (selectedTerritoryAttack == null || selectedTerritoryAttack.getNumArmies() < t.getNumArmies()) {
                    selectedTerritoryAttack = t;
                }
            }
        }
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
       TerritoryModel territoryClicked=null;
       
        for(TerritoryModel t: rm.getCurrentPlayer().getTerritoryOwned()){
            territoryClicked=t.getAdj().stream()
                .filter(ta -> ta.getOwner()==null)
                .findFirst()
                .orElse(null);
        }       
              
        if(territoryClicked==null){
            territoryClicked=rm.randomTerritory((List < TerritoryModel >)rm.getMap().getTerritories().stream()
                                                                                                            .filter(t -> t.getOwner()==null || t.getOwner()==rm.getCurrentPlayer())
                                                                                                            .collect(Collectors.toCollection(LinkedList::new)));
        }
        
       rm.startupMove(territoryClicked);
    }

}
