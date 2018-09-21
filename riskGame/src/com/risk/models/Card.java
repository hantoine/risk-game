/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author l_yixu
 */
public class Card {
    private String countryName;
    private String typeOfArmie;
    
    public Card(String countryName, String typeOfArmies) {
        this.countryName = countryName;
        this.typeOfArmie = typeOfArmies;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the typeOfArmie
     */
    public String getTypeOfArmie() {
        return typeOfArmie;
    }

    /**
     * @param typeOfArmie the typeOfArmie to set
     */
    public void setTypeOfArmie(String typeOfArmie) {
        this.typeOfArmie = typeOfArmie;
    }
    
    
    
}
