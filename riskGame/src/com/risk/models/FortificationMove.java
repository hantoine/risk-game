/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;

/**
 * This class represents a fortification move
 *
 * @author hantoine
 */
class FortificationMove implements Serializable  {

    
    
    /**
     * territorySource the territory with the armies
     */
    private final TerritoryModel territorySource;
    /**
     * territoryDest the territories where the player wants to move the armies
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
     * It compares two territories for the fortification move If the move its
     * coming from the same territory
     *
     * @param obj the object to check whether it is the same territory
     * @return true if it is the same territory; false if it is not
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

    /**
     * Evaluates if the movements are the same
     *
     * @param last las fortification move
     * @return true if compatible; false other case
     */
    boolean compatible(FortificationMove last) {
        return last != null && !last.equals(this);
    }
    
    /**
     * Alias for the equal function in order to test equality between two objects of the same class.
     * @param obj object of the same class we want to compare to this instance.
     * @return boolean to know if the objects are equal or not
     */
    public boolean identical(Object obj) {
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
        if ((this.territorySource != null && other.territorySource != null) && !this.territorySource.identical(other.territorySource)) {
            return false;
        }
        if ((this.territoryDest != null && other.territoryDest != null) && !this.territoryDest.identical(other.territoryDest) ){
            return false;
        }
        
        return true;
    }
}
