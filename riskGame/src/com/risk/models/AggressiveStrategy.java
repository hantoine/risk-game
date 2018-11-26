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
 * Aggressive strategy implementation
 * @author Nellybett
 */
public class AggressiveStrategy implements Strategy {

    /**
     * Territory selected to attack
     */
    TerritoryModel selectedTerritoryAttack;

    /**
     * Reinforces the territory with more armies that can attack 
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {
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

    /**
     * Attack as as many times as possible
     * @param rm risk model
     */
    @Override
    public void attack(RiskModel rm) {
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
                rm.executeAttack();
            }
        } else {
            rm.finishPhase();
        }
    }

    /**
     * Fortifies the strongest territory
     * @param rm risk model
     */
    @Override
    public void fortification(RiskModel rm) {

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

    /**
     * Select the territory for the attack
     * @param rm risk model
     */
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

    /**
     * Move armies after conquering a territory
     * @param rm risk model 
     */
    @Override
    public void moveArmies(RiskModel rm) {
        rm.getCurrentPlayer().moveArmiesAI();
    }

    /**
     * Exchange card for computer players
     * @param rm risk model
     * @return true if it is successful
     */
    @Override
    public boolean exchangeCardsToArmies(RiskModel rm) {
        return rm.getCurrentPlayer().exchangeCardsToArmiesAI();
    }

    /**
     * Defense for computer player
     * @param rm risk model 
     */
    @Override
    public void defense(RiskModel rm) {
        rm.getCurrentPlayer().defenseAI();
    }

    /**
     * Startup placement
     * @param rm risk model 
     */
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
