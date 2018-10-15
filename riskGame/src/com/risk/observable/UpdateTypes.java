/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observable;

/**
 * Types of updates that are possible when map model notifies the observers.
 * @author timot
 */
public enum UpdateTypes {
    ADD_TERRITORY,
    REMOVE_TERRITORY,
    UPDATE_TERRITORY_NAME,
    UPDATE_TERRITORY_POS, //update the position of a territory on the map
    ADD_CONTINENT,
    REMOVE_CONTINENT,
    UPDATE_CONTINENT,
    UPDATE_BACKGROUND_IMAGE,
    ADD_LINK, //add a link between two countries
    REMOVE_LINK; //remove a link between two countries
}
