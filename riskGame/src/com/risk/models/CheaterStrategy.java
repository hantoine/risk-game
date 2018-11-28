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
 * Cheater Strategy
 *
 * @author Nellybett
 */
public class CheaterStrategy implements Strategy {

    /**
     * Multiplies number of armies by two
     *
     * @param rm risk model
     */
    @Override
    public void reinforcement(RiskModel rm) {

        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            t.setNumArmies(t.getNumArmies() * 2);
        }

        rm.getCurrentPlayer().addNewLogEvent(String.format(
                "%s double the number of armies on all its territories",
                rm.getCurrentPlayer().getName()
        ));

        rm.finishPhase();
    }

    /**
     * Conquers all adjacent territories
     *
     * @param rm risk model
     */
    @Override
    public void attack(RiskModel rm) {

        LinkedList<TerritoryModel> auxiliar = new LinkedList<>();
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            t.getAdj().stream()
                    .filter(ta -> ta.getOwner() != rm.getCurrentPlayer())
                    .forEach(ad -> {
                        auxiliar.add(ad);
                    });
        }
        auxiliar.stream()
                .forEach(terr -> rm.getCurrentPlayer().addTerritoryOwned(terr));
        rm.getCurrentPlayer().addNewLogEvent(String.format(
                "%s conquer all territories accessible from his territories",
                rm.getCurrentPlayer().getName()
        ));

        rm.checkForDeadPlayers();
        rm.finishPhase();
    }

    /**
     * Doubles the number of armies for territories with neighbors own by other
     * player
     *
     * @param rm risk model
     */
    @Override
    public void fortification(RiskModel rm) {
        for (TerritoryModel t : rm.getCurrentPlayer().getTerritoryOwned()) {
            if (t.getAdj().stream()
                    .anyMatch(ta -> !(rm.getCurrentPlayer().getTerritoryOwned().contains(ta)))) {
                t.setNumArmies(t.getNumArmies() * 2);
            }
        }
        rm.getCurrentPlayer().addNewLogEvent(String.format(
                "%s double the number of armies on all its territories  "
                + "that have neighbors that belong to other players",
                rm.getCurrentPlayer().getName()
        ));

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
     * Startup movement
     *
     * @param rm risk model
     */
    @Override
    public void startup(RiskModel rm) {
        TerritoryModel territoryClicked = rm.randomTerritory((List< TerritoryModel>) rm.getMap().getTerritories().stream()
                .filter(t -> t.getOwner() == null || t.getOwner() == rm.getCurrentPlayer())
                .collect(Collectors.toCollection(LinkedList::new)));
        rm.startupMove(territoryClicked);
    }

}
