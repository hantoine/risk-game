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
public class TerritoryModel {

    private LinkedList<TerritoryModel> adj;
    // why not private too ?
    int positionX;
    int positionY;
    private String continentName;
    private int numArmies;
    private PlayerModel owner;
    private String name;

    public TerritoryModel(String name, int positionX, int positionY) {
        this.name = name;
        this.adj = new LinkedList();
        this.positionX = positionX;
        this.positionY = positionY;
        this.numArmies = 0;
        this.owner = null;
    }

    public TerritoryModel(String name) {
        this.name = name;
        this.numArmies = 0;
         this.positionX = -1;
        this.positionY = -1;
        this.owner = null;
        this.adj = new LinkedList();
    }

    public void countrySetter(int positionX, int positionY) {

        this.positionX = positionX;
        this.positionY = positionY;

    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    /**
     * @return the adj
     */
    public LinkedList<TerritoryModel> getAdj() {
        return adj;
    }

    /**
     * @param adj the adj to set
     */
    public void setAdj(LinkedList<TerritoryModel> adj) {
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
    public PlayerModel getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(PlayerModel owner) {
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
