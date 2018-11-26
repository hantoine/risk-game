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

    /**
     * Reinforces a random territory
     *
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {
        rm.aIReinforcement();
        int armiesReinforcement = rm.getCurrentPlayer().getNbArmiesAvailable();

        while (armiesReinforcement > 0) {
            TerritoryModel territorySelected = rm.randomTerritory(rm.getCurrentPlayer().getTerritoryOwned());
            rm.reinforcementIntent(territorySelected);
            armiesReinforcement = armiesReinforcement - 1;
        }

    }

    /**
     * Attacks a random number of times a random territory
     *
     * @param rm risk model
     */
    @Override
    public void attack(RiskModel rm) {

        setRandom(getTotalAttacks(rm));

        TerritoryModel selectedTerritoryAttack = null;
        LinkedList<TerritoryModel> aux = new LinkedList<TerritoryModel>();

        for (TerritoryModel terAux : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (terAux.getAdj().stream()
                    .anyMatch(ta -> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ta))) && terAux.getNumArmies() > 1) {
                aux.add(terAux);
            }
        }

        if (!aux.isEmpty()) {
            selectedTerritoryAttack = rm.randomTerritory(aux);
        }

        if (selectedTerritoryAttack != null) {
            TerritoryModel dest = selectedTerritoryAttack.getAdj().stream()
                    .filter(ad -> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ad)))
                    .findAny()
                    .orElse(null);

            if (selectedTerritoryAttack.getNumArmies() > 1 && dest != null) {
                setRandomNumber(getRandomNumber() - 1);
                int numDice = min(selectedTerritoryAttack.getNumArmies() - 1, 3);
                rm.attackIntent(selectedTerritoryAttack, dest);
                rm.continueAttack(numDice);

                if (getRandomNumber() == 0) {
                    setFirstAttack(true);
                    rm.finishPhase();
                }

            } else {
                rm.executeAttack();
            }

        } else {
            setFirstAttack(true);
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

        TerritoryModel dest = rm.randomTerritory(rm.getCurrentPlayer().getTerritoryOwned().stream()
                .filter(t -> t.getAdj().stream()
                .anyMatch(ta -> rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))
                .collect(Collectors.toCollection(LinkedList::new)));

        TerritoryModel source = null;
        if (dest != null) {
            for (TerritoryModel t : dest.getAdj()) {
                if (rm.getCurrentPlayer().getTerritoryOwned().contains(t) && (source == null || source.getNumArmies() < t.getNumArmies())) {
                    source = t;
                }
            }

            while (source != null && source.getNumArmies() > 1) {
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
        if (isFirstAttack()) {
            this.setRandomNumber((int) (Math.random() * range + 1));
            setFirstAttack(false);
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
        LinkedList<TerritoryModel> possibleAttackers = rm.getCurrentPlayer().getTerritoryOwned().stream()
                .filter(t -> t.getAdj().stream()
                .anyMatch(ta -> !rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))
                .collect(Collectors.toCollection(LinkedList::new));

        for (TerritoryModel t : possibleAttackers) {
            numberAttacks = numberAttacks + t.getNumArmies() - 1;
        }

        return numberAttacks;
    }

    /**
     * @return the randomNumber
     */
    public int getRandomNumber() {
        return randomNumber;
    }

    /**
     * @param randomNumber the randomNumber to set
     */
    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    /**
     * @return the firstAttack
     */
    public boolean isFirstAttack() {
        return firstAttack;
    }

    /**
     * @param firstAttack the firstAttack to set
     */
    public void setFirstAttack(boolean firstAttack) {
        this.firstAttack = firstAttack;
    }

}
