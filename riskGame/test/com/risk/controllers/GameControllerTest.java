/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class GameControllerTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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

}
