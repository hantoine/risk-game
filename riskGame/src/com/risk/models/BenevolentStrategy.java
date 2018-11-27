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
 * Benevolent Strategy
 * @author Nellybett
 */
public class BenevolentStrategy implements Strategy {

    /**
     * Reinforces the weakest territory
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {
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

    /**
     * No attack move
     * @param rm risk model
     */
    @Override
    public void attack(RiskModel rm) {
        rm.finishPhase();
    }

    /**
     * Fortifies the weakest territory
     * @param rm risk model
     */
    @Override
    public void fortification(RiskModel rm) {
        TerritoryModel dest = null;
        TerritoryModel source = null;

        List<TerritoryModel> sortedTerrs = 
                new LinkedList<>(rm.getCurrentPlayer().getTerritoryOwned());
        sortedTerrs.sort((a,b) -> a.getNumArmies()==b.getNumArmies()?0: a.getNumArmies()> b.getNumArmies() ? 1 : -1);
        
        for (TerritoryModel d : sortedTerrs) {
            TerritoryModel s = 
                    rm.getCurrentPlayer().getTerritoryOwned().stream()
                            .filter((t) -> t.getAdj().contains(d) && t.getNumArmies() > 1 && !t.getName().equals(d.getName()))
                            .findFirst().orElse(null);
            
            if(s != null) {
                source = s;
                dest = d;
                break;
            }
            
            System.out.println("Destination:"+d+"source: "+s);
        }

        // no fortification move possible
        if(source != null) {
            while (source.getNumArmies() > 1) {
                rm.fortificationIntent(source, dest);
            }
        }

        rm.finishPhase();
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
     * Startup movement
     * @param rm risk model 
     */
    @Override
    public void startup(RiskModel rm) {
       
        TerritoryModel territoryClicked=rm.randomTerritory((List < TerritoryModel >)rm.getMap().getTerritories().stream()
                                                                                                            .filter(t -> t.getOwner()==null || t.getOwner()==rm.getCurrentPlayer())
                                                                                                            .collect(Collectors.toCollection(LinkedList::new)));
        rm.startupMove(territoryClicked);
    }

}
