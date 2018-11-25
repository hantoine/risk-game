/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import static java.lang.Integer.min;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Nellybett
 */
public class RandomStrategy implements Strategy{
    int random=0;

    @Override
    public void reinforcement(RiskModel rm) {
        rm.aIReinforcement();
        int armiesReinforcement = rm.getCurrentPlayer().getNbArmiesAvailable();
        
        while (armiesReinforcement > 0) {
            TerritoryModel territorySelected=rm.randomTerritory((LinkedList < TerritoryModel >)rm.getCurrentPlayer().getTerritoryOwned());
            rm.reinforcementIntent(territorySelected);
            armiesReinforcement = armiesReinforcement - 1;
        }
        
    }

    @Override
    public void attack(RiskModel rm) {
        
        if(random==0)
            setRandom(getTotalAttacks(rm));
        
        TerritoryModel selectedTerritoryAttack= rm.randomTerritory((LinkedList < TerritoryModel >)rm.getCurrentPlayer().getTerritoryOwned().stream()
                                                                                                            .filter(t -> t.getAdj().stream()
                                                                                                            .anyMatch(ta -> ta.getOwner()!=rm.getCurrentPlayer()))
                                                                                                            .collect(Collectors.toCollection(LinkedList::new)));
        if (selectedTerritoryAttack != null && random!=0) {
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
                random=random-1;
            }
        } else {
            random=0;
            rm.finishPhase();
        }
    }

    @Override
    public void fortification(RiskModel playGame) {
    
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
                                                                                                            .filter(t -> t.getOwner()==null || t.getOwner()==rm.getCurrentPlayer())
                                                                                                            .collect(Collectors.toCollection(LinkedList::new)));
        rm.startupMove(territoryClicked);
    }
    
    private void setRandom(int range){
        random=((int) (Math.random() * range +1));
    }
    
    private int getTotalAttacks(RiskModel rm){
        int numberAttacks=0;
        LinkedList<TerritoryModel> possibleAttackers=rm.getCurrentPlayer().getTerritoryOwned().stream()
                                                                                            .filter(t -> t.getAdj().stream()
                                                                                                .anyMatch(ta -> ta.getOwner()!=rm.getCurrentPlayer()))
                                                                                                .collect(Collectors.toCollection(LinkedList::new));
        
        for(TerritoryModel t:possibleAttackers){
            numberAttacks= numberAttacks+t.getNumArmies()-1;
        }
    
        return numberAttacks;
    }
    
}
