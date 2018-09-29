/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author n_irahol
 */
public class PlayerModel {

    private boolean isHuman;
    private String name;
    private Color color;
    private Collection<TerritoryModel> contriesOwned;
    private Collection<ContinentModel> continentsOwned;
    private HandModel cardsOwned;
    private int numArmies;

    public PlayerModel(String name, Color color, boolean isHuman) {
        this.name = name;
        this.color = null;
        this.contriesOwned = new LinkedList<>();
        this.continentsOwned = new LinkedList<>();
        this.numArmies = 0;
        this.isHuman = isHuman;
        this.cardsOwned = new HandModel();
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
    public Collection<TerritoryModel> getContriesOwned() {
        return contriesOwned;
    }

    /**
     * @param contriesOwned the contriesOwned to set
     */
    public void setContriesOwned(Collection<TerritoryModel> contriesOwned) {
        this.contriesOwned = contriesOwned;
    }

    /**
     * @param countryOwned the additional country owned by this player
     */
    public void addCountryOwned(TerritoryModel countryOwned) {
        this.contriesOwned.add(countryOwned);
    }

    /**
     * @return the continentsOwned
     */
    public Collection<ContinentModel> getContinentsOwned() {
        return continentsOwned;
    }

    /**
     * @param continentsOwned the continentsOwned to set
     */
    public void setContinentsOwned(Collection<ContinentModel> continentsOwned) {
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
     *
     */
    public void initializeArmies(int nbPlayers) {
        switch (nbPlayers) {
            case 2:
                this.numArmies = 40;
                break;
            case 3:
                this.numArmies = 35;
                break;
            case 4:
                this.numArmies = 30;
                break;
            case 5:
                this.numArmies = 25;
                break;
            case 6:
                this.numArmies = 20;
                break;
            default:
                throw new IllegalArgumentException("Invalid number of players");
        }
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
    public HandModel getCardsOwned() {
        return cardsOwned;
    }

    /**
     * @param cardsOwned the cardsOwned to set
     */
    public void setCardsOwned(HandModel cardsOwned) {
        this.cardsOwned = cardsOwned;
    }

    public void initializeNumArmies(int numPlayerInGame) {
        this.numArmies = 0;
        //TODO
    }
}
