/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models.interfaces;

import com.risk.models.ContinentModel;
import com.risk.models.HumanPlayerModel;
import com.risk.models.TerritoryModel;
import java.awt.Color;
import java.util.LinkedList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test player model class 
 * @author Nellybett
 */
public class PlayerModelTest {

    /**
     * player reference to an instance of the class to be tested
     */
    PlayerModel player;

    /**
     * Constructor
     */
    public PlayerModelTest() {
    }

    /**
     * Setting the player attributes
     */
    @Before
    public void setUp() {
        player = new HumanPlayerModel("Player 1", Color.yellow, null);
        LinkedList<TerritoryModel> territories = new LinkedList<>();
        LinkedList<ContinentModel> continents = new LinkedList<>();

        territories.add(new TerritoryModel("Venezuela"));
        territories.add(new TerritoryModel("Argetina"));
        territories.add(new TerritoryModel("Peru"));
        territories.add(new TerritoryModel("Bolivia"));
        territories.add(new TerritoryModel("Canada"));
        territories.add(new TerritoryModel("USA"));
        territories.add(new TerritoryModel("Ecuador"));

        player.setContinentsOwned(continents);
        player.setContriesOwned(territories);

    }

    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationMinimum() {
        System.out.println("armiesAssignation");

        int expResult = 3;
        int result = player.armiesAssignation();
        assertEquals(expResult, result);
    }

    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationWithMoreCountries() {
        System.out.println("armiesAssignation");

        player.getContriesOwned().add(new TerritoryModel("France"));
        player.getContriesOwned().add(new TerritoryModel("Germany"));
        player.getContriesOwned().add(new TerritoryModel("Italy"));
        player.getContriesOwned().add(new TerritoryModel("UK"));
        player.getContriesOwned().add(new TerritoryModel("Spain"));

        int expResult = 4;
        int result = player.armiesAssignation();
        assertEquals(expResult, result);
    }

    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationWithContinents() {
        System.out.println("armiesAssignation");

        LinkedList<ContinentModel> continents = new LinkedList<>();
        continents.add(new ContinentModel("South America", 4));
        continents.add(new ContinentModel("North America", 3));
        player.setContinentsOwned(continents);

        int expResult = 9;
        int result = player.armiesAssignation();
        assertEquals(expResult, result);
    }

    /**
     * Test of armiesCardAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationCards() {
        System.out.println("armiesAssignationCards");

        //returned cards >18
        player.setReturnedCards(27);
        int expResult = 35;
        int result = player.armiesAssignationCards();
        assertEquals(expResult, result);

    }

    /**
     * Test of armiesCardAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationCardsWithoutReturnedCards() {

        //First time returnig cards =0
        player.setReturnedCards(0);
        int expResult = 4;
        int result = player.armiesAssignationCards();
        assertEquals(expResult, result);

    }

}
