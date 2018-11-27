/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import static java.lang.Integer.min;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Random Strategy
 *
 * @author Nellybett
 */
public class RandomStrategy implements Strategy {

    /**
     * random number of attacks
     */
    private int randomNumber;
    /**
     * It is the first attack or not
     */
    private boolean firstAttack = true;

    TerritoryModel selectedTerritoryAttack;
    TerritoryModel selectedAttacked;
        
    /**
     * Reinforces a random territory
     *
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {
        System.out.println("random reinforcement");
        rm.aIReinforcement();
        int armiesReinforcement = rm.getCurrentPlayer().getNbArmiesAvailable();

        while (armiesReinforcement > 0) {
            TerritoryModel territorySelected = rm.randomTerritory(rm.getCurrentPlayer().getTerritoryOwned());
            rm.reinforcementIntent(territorySelected);
            armiesReinforcement--;
        }

    }

    /**
     * Attacks a random number of times a random territory
     *
     * @param rm risk model
     */
    @Override
    public void attack(RiskModel rm) {
        System.out.println("random attack");
        setRandom(min(getTotalAttacks(rm),15));
        selectTerritory(rm);
        if (selectedTerritoryAttack != null) {
            //If not last attack
            if (selectedTerritoryAttack.getNumArmies() > 1) {
                this.randomNumber--;
                //If max random number of attacks are done 
                if (this.randomNumber == 0) {
                    this.firstAttack=true;
                    rm.finishPhase();
                }else{
                    int numDice = min(selectedTerritoryAttack.getNumArmies() - 1, 3);
                    rm.attackIntent(selectedTerritoryAttack, selectedAttacked);
                    rm.continueAttack(numDice);
                }
            //Last attack
            } else {
                rm.executeAttack();
            }

        } else {
            this.firstAttack=true;
            rm.finishPhase();
        }

    }

    /**
     * Fortifies a random territory
     *
     * @param rm risk model
     */
    @Override
    public void fortification(RiskModel rm) {
        System.out.println("random fortification");
        TerritoryModel dest = null;
        TerritoryModel source = null;

        List<TerritoryModel> sortedTerrs = 
                new LinkedList<>(rm.getCurrentPlayer().getTerritoryOwned());
        Collections.shuffle(sortedTerrs);
        
        
        for (TerritoryModel d : sortedTerrs) {
            TerritoryModel s = 
                    rm.getCurrentPlayer().getTerritoryOwned().stream()
                            .filter((t) -> t.getAdj().contains(d) && t.getNumArmies()>1 && !t.getName().equals(d.getName()))
                            .findFirst().orElse(null);
           
            if(s != null) {
                source = s;
                dest = d;
                break;
            }
            
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
        TerritoryModel territoryClicked = rm.randomTerritory(rm.getMap().getTerritories().stream()
                .filter(t -> t.getOwner() == null || t.getOwner() == rm.getCurrentPlayer())
                .collect(Collectors.toCollection(LinkedList::new)));
        rm.startupMove(territoryClicked);
    }

    /**
     * Sets the random number of attacks
     *
     * @param range the possible number of attacks
     */
    private void setRandom(int range) {
        if (this.firstAttack) {
            this.randomNumber=((int) (Math.random() * range + 1));
            this.firstAttack=false;
        }
        
    }

    /**
     * Returns the possible number of attacks
     *
     * @param rm risk model
     * @return numberAttacks number of attacks
     */
    private int getTotalAttacks(RiskModel rm) {
        int numberAttacks = 0;
        LinkedList<TerritoryModel> possibleAttackers = 
                rm.getCurrentPlayer().getTerritoryOwned().stream()
                .filter(t-> (t.getAdj().stream()
                                      .filter(ta -> !rm.getCurrentPlayer().getTerritoryOwned().contains(ta))
                                      .findAny()
                                      .orElse(null))!=null)
                .collect(Collectors.toCollection(LinkedList::new));

        for (TerritoryModel t : possibleAttackers) {
            numberAttacks = numberAttacks + t.getNumArmies() - 1;
        }

        return numberAttacks;
    }
    
    /**
     * Select the territory for the attack
     * @param rm risk model
    */
    public void selectTerritory(RiskModel rm) {

        selectedTerritoryAttack = null;
        selectedAttacked=null;
        TerritoryModel aux;
        List<TerritoryModel> sortedTerr = new LinkedList<>(rm.getCurrentPlayer().getTerritoryOwned());
        Collections.shuffle(sortedTerr);
        
        for (TerritoryModel t : sortedTerr) {
            aux=null;
            if (t.getNumArmies() > 1){
                aux=t.getAdj().stream()
                            .filter(ta -> (!(rm.getCurrentPlayer().getTerritoryOwned().contains(ta))))
                            .findAny()
                            .orElse(null);
            }
                    
            if (aux!=null) {
                selectedTerritoryAttack = t;
                selectedAttacked=aux;
            }
        }
        
    }

}
