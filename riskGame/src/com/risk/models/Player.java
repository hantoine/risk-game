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
    
    String name;
    String color;
    LinkedList<Country> contriesOwned;
    LinkedList<Continent> continentsOwned;
    int numArmies;

    public Player(String name,String color) {
        this.name = name;
        this.color=color;
        this.contriesOwned = null;
        this.continentsOwned = null;
        this.numArmies =0;
    }
    
    
    
}
