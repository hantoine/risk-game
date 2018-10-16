/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 * It represents a territory/country in the map
 *
 * @author n_irahol
 */
public class TerritoryModel {

    /**
     * adj list of adjacent countries
     */
    private LinkedList<TerritoryModel> adj;
    /**
     * positionX x coordinate of the location of the country
     */
    private int positionX;
    /**
     * positionY y coordinate of the location of the country
     */
    private int positionY;
    /**
     * continentName the name of the continent which it belongs
     */
    private String continentName;
    /**
     * numArmies the number of armies in the country
     */
    private int numArmies;
    /**
     * owner reference to the player that owns the country
     */
    private PlayerModel owner;
    /**
     * name the name of the country
     */
    private String name;

    /**
     * Constructor
     *
     * @param name name of a country
     * @param positionX position in x in the image
     * @param positionY position in y in the image
     */
    public TerritoryModel(String name, int positionX, int positionY) {
        this.name = name;
        this.adj = new LinkedList();
        this.positionX = positionX;
        this.positionY = positionY;
        this.numArmies = 0;
        this.owner = null;
    }

    /**
     * Constructor
     *
     * @param name name of a country
     */
    public TerritoryModel(String name) {
        this.name = name;
        this.numArmies = 0;
        this.positionX = -1;
        this.positionY = -1;
        this.owner = null;
        this.adj = new LinkedList();
    }

    /**
     * Add an adjacent country
     *
     * @param neighbour
     */
    public void addNeighbour(TerritoryModel neighbour) {
        this.adj.add(neighbour);
    }

    /**
     * Eliminates an adjacent country
     *
     * @param neighbour
     */
    public void removeNeighbour(TerritoryModel neighbour) {
        this.adj.remove(neighbour);
    }

    /**
     * Setter for the position of a country
     *
     * @param positionX the position X of this country
     * @param positionY the position Y of this country
     */
    public void countrySetter(int positionX, int positionY) {

        this.setPositionX(positionX);
        this.setPositionY(positionY);

    }

    /**
     * Getter for the positionX attribute
     *
     * @return positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Getter for the positionY attribute
     *
     * @return positionY
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Getter for the adj attribute
     *
     * @return the adj
     */
    public LinkedList<TerritoryModel> getAdj() {
        return adj;
    }

    /**
     * Setter for the adj attribute
     *
     * @param adj the adj to set
     */
    public void setAdj(LinkedList<TerritoryModel> adj) {
        this.adj = adj;
    }

    /**
     * Getter for the numArmies attribute
     *
     * @return the numArmies
     */
    public int getNumArmies() {
        return numArmies;
    }

    /**
     * Increase the number of armies on the territory by one
     *
     * @return The new number of armies on this territory
     */
    public int incrementNumArmies() {
        return ++numArmies;
    }

    /**
     * Decrease the number of armies on the territory by one
     *
     * @return The new number of armies on this territory
     */
    public int decrementNumArmies() throws IllegalStateException {
        if (numArmies == 0) {
            throw new IllegalStateException("Negative number of armies on a territory not allowed.");
        }
        return --numArmies;
    }

    /**
     * Setter for the numArmies attribute
     *
     * @param numArmies the numArmies to set
     */
    public void setNumArmies(int numArmies) {
        this.numArmies = numArmies;
    }

    /**
     * Getter for the owner attribute
     *
     * @return the owner
     */
    public PlayerModel getOwner() {
        return owner;
    }

    /**
     * Setter for the owner attribute
     *
     * @param owner the owner to set
     */
    public void setOwner(PlayerModel owner) {
        this.owner = owner;
    }

    /**
     * Getter for the name attribute
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name attribute
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the continentName attribute
     *
     * @return the continentName
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Setter for the continentName attribute
     *
     * @param continentName the continentName to set
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Setter for the positionX attribute
     *
     * @param positionX the positionX to set
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Setter for the positionY attribute
     *
     * @param positionY the positionY to set
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}
