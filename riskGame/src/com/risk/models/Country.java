/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;
import java.util.LinkedList;

/**
 *
 * @author n_irahol
 */
public class Country {
    
    private LinkedList<Country> adj;
    private String continentName;
    private int numArmies;
    private Player owner;
    private String name;
    
    public Country(String name) {
        this.name=name;
        this.numArmies=0;
        this.owner=null;
    }

    /**
     * @return the adj
     */
    public LinkedList<Country> getAdj() {
        return adj;
    }

    /**
     * @param adj the adj to set
     */
    public void setAdj(LinkedList<Country> adj) {
        this.adj = adj;
    }

    /**
     * @return the numArmies
     */
    public int getNumArmies() {
        return numArmies;
    }

    /**
     * @param numArmies the numArmies to set
     */
    public void setNumArmies(int numArmies) {
        this.numArmies = numArmies;
    }

    /**
     * @return the owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the continentName
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * @param continentName the continentName to set
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }
    
}
