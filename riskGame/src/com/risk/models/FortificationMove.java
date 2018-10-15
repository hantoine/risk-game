/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * This class represents a fortification move
 * @author hantoine
 */
public class FortificationMove {

    /**
     * territorySource the country with the armies
     */
    private TerritoryModel territorySource;
    /**
     * territoryDest the countries where the player wants to move the armies
     */
    private TerritoryModel territoryDest;

    /**
     * Constructor
     * @param territorySource
     * @param territoryDest 
     */
    public FortificationMove(TerritoryModel territorySource, TerritoryModel territoryDest) {
        this.territorySource = territorySource;
        this.territoryDest = territoryDest;
    }

    /**
     * Getter of the territorySource attribute
     * @return territorySource
     */
    public TerritoryModel getTerritorySource() {
        return territorySource;
    }

    /**
     * Getter of the territoryDest attribute
     * @return 
     */
    public TerritoryModel getTerritoryDest() {
        return territoryDest;
    }

    /**
     * It compares two countries for the fortification move
     * If the move its comming from the same country
     * @param obj
     * @return true if it is the same country; false if it is not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FortificationMove other = (FortificationMove) obj;
        if (this.territorySource != other.territorySource) {
            return false;
        }
        if (this.territoryDest != other.territoryDest) {
            return false;
        }
        return true;
    }
}
