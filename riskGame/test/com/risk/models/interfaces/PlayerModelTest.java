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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nellybett
 */
public class PlayerModelTest {
    
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
        player=new HumanPlayerModel("Player 1", Color.yellow, true);
        LinkedList<TerritoryModel> territories=new LinkedList<>();
        LinkedList<ContinentModel> continents=new LinkedList<>();
        
        territories.add(new TerritoryModel("Venezuela"));
        territories.add(new TerritoryModel("Argetina"));
        territories.add(new TerritoryModel("Peru"));
        territories.add(new TerritoryModel("Bolivia"));
        territories.add(new TerritoryModel("Canada"));
        territories.add(new TerritoryModel("USA"));
        territories.add(new TerritoryModel("Ecuador"));
        
        continents.add(new ContinentModel("South America", 4));
        continents.add(new ContinentModel("North America", 3));
        
        player.setContinentsOwned(continents);
        player.setContriesOwned(territories);
        
    }
    
   
    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignation() {
        System.out.println("armiesAssignation");
        PlayerModel instance = player;
        int expResult = 9;
        int result = instance.armiesAssignation();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of armiesCardAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationCards() {
        System.out.println("armiesAssignationCards");
        PlayerModel instance = player;
        
        //returned cards >18
        instance.setReturnedCards(27);
        int expResult = 35;
        int result = instance.armiesAssignationCards();
        assertEquals(expResult, result);
        
        //First time returnig cards =0
        instance.setReturnedCards(0);
        expResult = 4;
        result = instance.armiesAssignationCards();
        assertEquals(expResult, result);
        
    }

    
}
