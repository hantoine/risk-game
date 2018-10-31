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
     * countryName There is as many cards as countries in the game
     */
    private String countryName;
    /**
     * typeOfArmie Type of army in the card
     */
    private String typeOfArmie;

    /**
     * Constructor
     *
     * @param countryName name of a country in the card
     * @param typeOfArmies name of a type of armies
     */
    CardModel(String countryName, String typeOfArmies) {
        this.countryName = countryName;
        this.typeOfArmie = typeOfArmies;
    }

    /**
     * Getter of the countryName attribute
     *
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter of the countryName attribute
     *
     * @param countryName the countryName to set
     */
    void setCountryName(String countryName) {
        this.countryName = countryName;
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
