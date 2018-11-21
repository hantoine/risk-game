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
public class Benevolent implements Strategy {

    @Override
    public void reinforcement(RiskModel rm) {
        
        rm.getCurrentPlayer().setHanded(false);
        rm.getCurrentPlayer().assignNewArmies();
        
        while(rm.getCurrentPlayer().getHand().cardHandingPossible())
            rm.getCurrentPlayer().exchangeCardsToArmies();
        
        rm.getGc().closeCardExchangeView();
        
        int armiesReinforcement=rm.getCurrentPlayer().getNbArmiesAvailable();
        TerritoryModel selectedTerritory= rm.getCurrentPlayer().getTerritoryOwned().get(1);
        
        while(armiesReinforcement>0){
            for(TerritoryModel t: rm.getCurrentPlayer().getTerritoryOwned()){
                if(t.getNumArmies()<selectedTerritory.getNumArmies())
                    selectedTerritory=t;
            }
            
            try {
                rm.placeArmy(rm.getCurrentPlayer(), selectedTerritory);
                armiesReinforcement=armiesReinforcement-1;
                    
            } catch (RiskModel.ArmyPlacementImpossible ex) {
                rm.addNewEvent(ex.getReason());
            }
        }
        
        rm.finishPhase();
    }

    @Override
    public void attack(RiskModel rm) {
        rm.finishPhase();
    }


    @Override
    public void fortification(RiskModel rm) {
        
        TerritoryModel dest=null;
        for(TerritoryModel t: rm.getCurrentPlayer().getTerritoryOwned()){          
            if(((TerritoryModel)t).getAdj().stream()
                   .anyMatch(ta -> rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))
            {
                if(dest==null || dest.getNumArmies()>t.getNumArmies())
                    dest=t;
            }
        }
        
        TerritoryModel source=null;
        if(dest!=null){    
            for(TerritoryModel t: dest.getAdj()){
                    if(rm.getCurrentPlayer().getTerritoryOwned().contains(t) && (source==null || source.getNumArmies()<t.getNumArmies()))
                            source=t;                
            }                 
        }
                 
        try {
            if(source!=null && dest!=null ){                
                rm.tryFortificationMove(source, dest);
            }
        } catch (RiskModel.FortificationMoveImpossible ex) {
            if (ex.getReason() != null) {
                rm.addNewEvent(ex.getReason());
            }
        }
        
        rm.finishPhase();
    }

}
