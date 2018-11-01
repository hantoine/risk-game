/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * This class represents a fortification move
 *
 * @author hantoine
 */
class FortificationMove {

    /**
     * territorySource the country with the armies
     */
    private final TerritoryModel territorySource;
    /**
     * territoryDest the countries where the player wants to move the armies
     */
    private final TerritoryModel territoryDest;

    /**
     * Constructor
     *
     * @param territorySource the source of the territory
     * @param territoryDest the destination of the territory
     */
    FortificationMove(
            TerritoryModel territorySource, TerritoryModel territoryDest) {
        this.territorySource = territorySource;
        this.territoryDest = territoryDest;
    }

    /**
     * Getter of the territorySource attribute
     *
     * @return territorySource
     */
    public TerritoryModel getTerritorySource() {
        return territorySource;
    }

    /**
     * Getter of the territoryDest attribute
     *
     * @return the territory of destination
     */
    public TerritoryModel getTerritoryDest() {
        return territoryDest;
    }

    /**
     * It compares two countries for the fortification move If the move its
     * coming from the same country
     *
     * @param obj the object to check whether it is the same country
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

    boolean compatible(FortificationMove last) {
        return last != null && !last.equals(this);
    }
}
