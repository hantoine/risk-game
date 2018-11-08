/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * It represents a card in the game
 *
 * @author l_yixu
 */
public class CardModel {

    /**
     * territoryName There is as many cards as territories in the game
     */
    private String territoryName;
    /**
     * typeOfArmie Type of army in the card
     */
    private String typeOfArmie;

    /**
     * Constructor
     *
     * @param territoryName name of a territory in the card
     * @param typeOfArmies name of a type of armies
     */
    CardModel(String territoryName, String typeOfArmies) {
        this.territoryName = territoryName;
        this.typeOfArmie = typeOfArmies;
    }

    /**
     * Getter of the territoryName attribute
     *
     * @return the territoryName
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * Setter of the territoryName attribute
     *
     * @param territoryName the territoryName to set
     */
    void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * Getter of the typeOfArmie attribute
     *
     * @return the typeOfArmie
     */
    public String getTypeOfArmie() {
        return typeOfArmie;
    }

    /**
     * Setter of the typeOfArmie attribute
     *
     * @param typeOfArmie the typeOfArmie to set
     */
    void setTypeOfArmie(String typeOfArmie) {
        this.typeOfArmie = typeOfArmie;
    }

}
