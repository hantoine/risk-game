/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * Class to represent an attack
 * @author Nellybett
 */
public class AttackMove {
    /**
     * Source of the attack
     */
    private TerritoryModel source;
    /**
     * Destiny of the attack
     */
    private TerritoryModel dest;
  
    /**
     * Constructor
     * @param source source of the attack
     * @param dest  destiny of the attack
     */
    public AttackMove(TerritoryModel source, TerritoryModel dest) {
        this.source = source;
        this.dest = dest;
        
    }

    /**
     * Getter of source attribute
     * @return the source
     */
    public TerritoryModel getSource() {
        return source;
    }

    /**
     * Setter of source attribute
     * @param source the source to set
     */
    public void setSource(TerritoryModel source) {
        this.source = source;
    }

    /**
     * Getter of dest attribute
     * @return the dest
     */
    public TerritoryModel getDest() {
        return dest;
    }

    /**
     * Setter of dest attribute
     * @param dest the dest to set
     */
    public void setDest(TerritoryModel dest) {
        this.dest = dest;
    }
    
    
}
