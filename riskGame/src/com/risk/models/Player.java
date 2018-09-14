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
public class Player {
     private String name;
    private String color;
    private LinkedList<Country> contriesOwned;
    private LinkedList<Continent> continentsOwned;
    private int numArmies;

    public Player(String name,String color) {
        this.name = name;
        this.color=color;
        this.contriesOwned = null;
        this.continentsOwned = null;
        this.numArmies =0;
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
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the contriesOwned
     */
    public LinkedList<Country> getContriesOwned() {
        return contriesOwned;
    }

    /**
     * @param contriesOwned the contriesOwned to set
     */
    public void setContriesOwned(LinkedList<Country> contriesOwned) {
        this.contriesOwned = contriesOwned;
    }

    /**
     * @return the continentsOwned
     */
    public LinkedList<Continent> getContinentsOwned() {
        return continentsOwned;
    }

    /**
     * @param continentsOwned the continentsOwned to set
     */
    public void setContinentsOwned(LinkedList<Continent> continentsOwned) {
        this.continentsOwned = continentsOwned;
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
    
    
    
    
}