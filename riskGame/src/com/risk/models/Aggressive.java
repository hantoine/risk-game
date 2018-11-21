/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import static java.lang.Integer.min;

/**
 *
 * @author Nellybett
 */
public class Aggressive implements Strategy {

    TerritoryModel selectedTerritoryAttack;
    
    @Override
    public void reinforcement(RiskModel rm) {
        System.out.println("AGRESIVO REINFORCEMENT");
        rm.aIReinforcement();
        int armiesReinforcement=rm.getCurrentPlayer().getNbArmiesAvailable();
        TerritoryModel selectedTerritory= rm.getCurrentPlayer().getTerritoryOwned().get(1);
        
        for(TerritoryModel t: rm.getCurrentPlayer().getTerritoryOwned()){
            if(t.getNumArmies()>selectedTerritory.getNumArmies())
                selectedTerritory=t;
        }
            
        while(armiesReinforcement>0){ 
            rm.reinforcementIntent(selectedTerritory);
            armiesReinforcement=armiesReinforcement-1;
        }
        
    }

    @Override
    public void attack(RiskModel rm) {   
        System.out.println("AGRESIVO ATAQUE");
        selectTerritory(rm);
        if(selectedTerritoryAttack!=null){
            TerritoryModel dest=selectedTerritoryAttack.getAdj().stream()
                            .filter(ad-> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ad)))
                            .findFirst()
                            .orElse(null);
                
            if(selectedTerritoryAttack.getNumArmies()>1 && dest!=null){
                int numDice=min(selectedTerritoryAttack.getNumArmies()-1,3);
                rm.attackIntent(selectedTerritoryAttack,dest);
                rm.getGc().clickAttack(numDice);
                        
            }else{
                System.out.println("SE ACABO EL ATAQUE AGRESIVO");
                rm.executeBeginningOfPhaseSteps();
            }
        }else{
            rm.finishPhase();
        }
    }

    @Override
    public void fortification(RiskModel rm) {
        System.out.println("AGRESIVO FORTI");
        rm.finishPhase();
    }
    
    public void selectTerritory(RiskModel rm){
        
        selectedTerritoryAttack=null;
        for(TerritoryModel t: rm.getCurrentPlayer().getTerritoryOwned()){          
            if(((TerritoryModel)t).getAdj().stream()
                   .anyMatch(ta -> (!(rm.getCurrentPlayer().getTerritoryOwned().contains(ta))&& t.getNumArmies()>1)))
            {
                if(selectedTerritoryAttack==null || selectedTerritoryAttack.getNumArmies()<t.getNumArmies())
                    selectedTerritoryAttack=t;
            }
        }
    }
    
}
