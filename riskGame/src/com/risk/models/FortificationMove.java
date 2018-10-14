/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author hantoine
 */
public class FortificationMove {

    private TerritoryModel territorySource;
    private TerritoryModel territoryDest;

    public FortificationMove(TerritoryModel territorySource, TerritoryModel territoryDest) {
        this.territorySource = territorySource;
        this.territoryDest = territoryDest;
    }

    public TerritoryModel getTerritorySource() {
        return territorySource;
    }

    public TerritoryModel getTerritoryDest() {
        return territoryDest;
    }

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
