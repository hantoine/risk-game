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
 *
 * @author Nellybett
 */
public class AggressiveStrategy implements Strategy {

    /**
     * Territory selected to attack
     */
    TerritoryModel selectedTerritoryAttack;
    TerritoryModel selectedAttacked;
    TerritoryModel selectDestFortification;
    TerritoryModel selectSourceFortification;
    /**
     * Reinforces the territory with more armies that can attack
     *
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {
        
        rm.aIReinforcement();
        int armiesReinforcement = rm.getCurrentPlayer().getNbArmiesAvailable();
        TerritoryModel selectedTerritory = null;

        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            TerritoryModel adj=t.getAdj().stream()
                                    .filter(ta->!(rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))
                                    .findAny()
                                    .orElse(null);
                               
            if (adj!=null) {
                if(selectedTerritory==null || selectedTerritory.getNumArmies()<t.getNumArmies())
                    selectedTerritory = t;
            }
        }
        

        if (selectedTerritory == null) {
            selectedTerritory = rm.getCurrentPlayer().getTerritoryOwned().get(0);

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
     *
     * @param rm risk model
     */
    @Override
    public void attack(RiskModel rm) {
            
        selectTerritory(rm);
        if (selectedTerritoryAttack != null) {
            if (selectedTerritoryAttack.getNumArmies() > 1) {
                int numDice = min(selectedTerritoryAttack.getNumArmies() - 1, 3);
                rm.attackIntent(selectedTerritoryAttack, selectedAttacked);
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
     *
     * @param rm risk model
     */
    @Override
    public void fortification(RiskModel rm) {
        
        
        List<TerritoryModel> sortedTerrs = 
                new LinkedList<>(rm.getCurrentPlayer().getTerritoryOwned());
        sortedTerrs.sort((a,b) -> a.getNumArmies()==b.getNumArmies()?0: a.getNumArmies()< b.getNumArmies() ? 1 : -1);
        
        
        List<TerritoryModel> sortedTerrsOp =sortedTerrs.stream()
                                    .filter(tr -> (tr.getAdj().stream()
                                          .filter(ta -> !rm.getCurrentPlayer().getTerritoryOwned().contains(ta))
                                          .findAny()
                                          .orElse(null))!=null)
                                    .collect(Collectors.toCollection(LinkedList::new));
        
        
        findSource(sortedTerrsOp, rm);
        if(this.selectSourceFortification==null)
            findSource(sortedTerrs, rm);
                
        
        // no fortification move possible
        if(this.selectSourceFortification != null) {
            while (this.selectSourceFortification.getNumArmies() > 1) {
                rm.fortificationIntent(this.selectSourceFortification, this.selectDestFortification);
            }
        }

        rm.finishPhase();
    }

    /**
     * Select the territory for the attack
     *
     * @param rm risk model
     */
    public void selectTerritory(RiskModel rm) {

        selectedTerritoryAttack = null;
        selectedAttacked=null;
        TerritoryModel aux;
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            aux=null;
            if (t.getNumArmies() > 1){
                aux=t.getAdj().stream()
                            .filter(ta -> (!(rm.getCurrentPlayer().getTerritoryOwned().contains(ta))))
                            .findFirst()
                            .orElse(null);
            }
                    
                if ((selectedTerritoryAttack == null || selectedTerritoryAttack.getNumArmies() < t.getNumArmies()) && aux!=null) {
                    selectedTerritoryAttack = t;
                    selectedAttacked=aux;
                }
        }
        
    }

    /**
     * Move armies after conquering a territory
     *
     * @param rm risk model
     */
    @Override
    public void moveArmies(RiskModel rm) {
        rm.getCurrentPlayer().moveArmiesAI();
    }

    /**
     * Exchange card for computer players
     *
     * @param rm risk model
     * @return true if it is successful
     */
    @Override
    public boolean exchangeCardsToArmies(RiskModel rm) {
        return rm.getCurrentPlayer().exchangeCardsToArmiesAI();
    }

    /**
     * Defense for computer player
     *
     * @param rm risk model
     */
    @Override
    public void defense(RiskModel rm) {
        rm.getCurrentPlayer().defenseAI();
    }

    /**
     * Startup placement
     *
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

        if (territoryClicked == null) {
            territoryClicked = rm.randomTerritory((List< TerritoryModel>) rm.getMap().getTerritories().stream()
                    .filter(t -> t.getOwner() == null || t.getOwner() == rm.getCurrentPlayer())
                    .collect(Collectors.toCollection(LinkedList::new)));
        }

        rm.startupMove(territoryClicked);
    }
    
    /**
     *
     * @param sorted
     * @param rm
     */
    public void findSource(List<TerritoryModel> sorted, RiskModel rm){
        selectSourceFortification=null;
        selectDestFortification=null;
                
        for (TerritoryModel d : sorted) {
            TerritoryModel s = 
                    rm.getCurrentPlayer().getTerritoryOwned().stream()
                            .filter((t) -> t.getAdj().contains(d) && t.getNumArmies() > 1 && !t.getName().equals(d.getName()))
                            .findFirst().orElse(null);
            
            if(s != null) {
                selectSourceFortification = s;
                selectDestFortification = d;
                break;
            }
            
        }
                
    }

}
