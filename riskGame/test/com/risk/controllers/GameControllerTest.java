/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class GameControllerTest {

    public GameControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of finishPhase method, of class GameController.
     */
    @Test
    public void testFinishPhase() {
        System.out.println("finishPhase");
        GameController instance = null;
        instance.finishPhase();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clickOnTerritory method, of class GameController.
     */
    @Test
    public void testClickOnTerritory() {
        System.out.println("clickOnTerritory");
        String territoryClickedName = "";
        GameController instance = null;
        instance.clickOnTerritory(territoryClickedName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dragNDropTerritory method, of class GameController.
     */
    @Test
    public void testDragNDropTerritory() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "";
        String destTerritoryName = "";
        GameController instance = null;
        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clickHand method, of class GameController.
     */
    @Test
    public void testClickHand() {
        System.out.println("clickHand");
        GameController instance = null;
        instance.clickHand();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
