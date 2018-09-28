/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models.interfaces;

import com.risk.models.ContinentModel;
import com.risk.models.HandModel;
import com.risk.models.TerritoryModel;
import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;

/**
 * It represents a Player in the game
 * It is the parent of HumanPlayerModel and AIPlayerModel
 * @author n_irahol
 */
public abstract class PlayerModel {

    private boolean isHuman;
    private String name;
    private Color color;
    private Collection<TerritoryModel> contriesOwned;
    private Collection<ContinentModel> continentsOwned;
    private HandModel cardsOwned;
    private int numArmies;

    /**
     * Constructor
     * @param name name of a player
     * @param color color of a player
     * @param isHuman true if the player is human
     */
    public PlayerModel(String name, Color color, boolean isHuman) {
        this.name = name;
        this.color = color;
        this.contriesOwned = new LinkedList<>();
        this.continentsOwned = new LinkedList<>();
        this.numArmies = 0;
        this.isHuman = isHuman;
        this.cardsOwned = new HandModel();
        this.numArmies = 0;
    }
    
    /**
     * Definition of the reinforcement phase
     */
    public abstract void reinforcement();
    
    /**
     * Definition of the fortification phase
     */
    public abstract void fortification();
    
    /**
     * Definition of the attack phase
     */
    public abstract void attack();
    
    /**
     * Getter of the name attribute
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the color attribute
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter of the color attribute
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter of the countriesOwned attribute
     * @return the contriesOwned
     */
    public Collection<TerritoryModel> getContriesOwned() {
        return contriesOwned;
    }

    /**
     * Setter of the countriesOwned attribute
     * @param contriesOwned the contriesOwned to set
     */
    public void setContriesOwned(Collection<TerritoryModel> contriesOwned) {
        this.contriesOwned = contriesOwned;
    }

    /**
     * Add a country to the conuntryOwned list
     * @param countryOwned the additional country owned by this player
     */
    public void addCountryOwned(TerritoryModel countryOwned) {
        this.contriesOwned.add(countryOwned);
    }

    /**
     * Getter of the continentsOwned attribute
     * @return the continentsOwned
     */
    public Collection<ContinentModel> getContinentsOwned() {
        return continentsOwned;
    }

    /**
     * Setter of the continentsOwned attribute
     * @param continentsOwned the continentsOwned to set
     */
    public void setContinentsOwned(Collection<ContinentModel> continentsOwned) {
        this.continentsOwned = continentsOwned;
    }

    /**
     * Getter of the numArmies attribute
     * @return the numArmies
     */
    public int getNumArmies() {
        return numArmies;
    }

    /**
     * Setter of the numArmies attribute
     * @param numArmies the numArmies to set
     */
    public void setNumArmies(int numArmies) {
        this.numArmies = numArmies;
    }

    /**
     * Getter of the isHuman attribute
     * @return the isHuman
     */
    public boolean getType() {
        return this.isHuman;
    }

    /**
     * Getter of the cardsOwned attribute
     * @return the cardsOwned
     */
    public HandModel getCardsOwned() {
        return cardsOwned;
    }

    /**
     * Setter of the cardsOwned attribute
     * @param cardsOwned the cardsOwned to set
     */
    public void setCardsOwned(HandModel cardsOwned) {
        this.cardsOwned = cardsOwned;
    }

}
