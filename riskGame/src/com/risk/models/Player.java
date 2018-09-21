/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import java.util.Collection;

/**
 *
 * @author n_irahol
 */
public class Player {
    private boolean isHuman;
    private String name;
    private Color color;
    private Collection<Country> contriesOwned;
    private Collection<Continent> continentsOwned;
    private Hand cardsOwned;
    private int numArmies;

    public Player(String name, Color color, boolean isHuman) {
        this.name = name;
        this.color = null;
        this.contriesOwned = new LinkedList<>();
        this.continentsOwned = new LinkedList<>();
        this.numArmies = 0;
        this.isHuman = isHuman;
        this.cardsOwned = new Hand();
        this.numArmies = 0;
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
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the contriesOwned
     */
    public Collection<Country> getContriesOwned() {
        return contriesOwned;
    }

    /**
     * @param contriesOwned the contriesOwned to set
     */
    public void setContriesOwned(Collection<Country> contriesOwned) {
        this.contriesOwned = contriesOwned;
    }
    
    /**
     * @param countryOwned the additional country owned by this player
     */
    public void addCountryOwned(Country countryOwned) {
        this.contriesOwned.add(countryOwned);
    }

    /**
     * @return the continentsOwned
     */
    public Collection<Continent> getContinentsOwned() {
        return continentsOwned;
    }

    /**
     * @param continentsOwned the continentsOwned to set
     */
    public void setContinentsOwned(Collection<Continent> continentsOwned) {
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

    /**
     * @return the numArmies
     */
    public boolean getType() {
        return this.isHuman;
    }

    /**
     * @return the cardsOwned
     */
    public Hand getCardsOwned() {
        return cardsOwned;
    }

    /**
     * @param cardsOwned the cardsOwned to set
     */
    public void setCardsOwned(Hand cardsOwned) {
        this.cardsOwned = cardsOwned;
    }
    
    
}
