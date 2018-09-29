/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

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
public class MapFileManagementTest {
    
    public MapFileManagementTest() {
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
     * Test of createBoard method, of class MapFileManagement.
     */
    @Test
    public void testCreateBoard() {
        System.out.println("createBoard");
        String path = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.createBoard(path, board);
        assertEquals(expResult, result);
      
    }

    /**
     * Test of readFile method, of class MapFileManagement.
     */
    @Test
    public void testReadFile() {
        System.out.println("readFile");
        String path = "";
        MapFileManagement instance = new MapFileManagement();
        String expResult = "-1";
        
        //Test case path=""
        String result = instance.readFile(path);
        assertEquals(expResult, result);
      
        //Test case path="&&"
        path="&&";
        result = instance.readFile(path);
        assertEquals(expResult, result);
        
        //Test case path="&&"
        path="C:\\Users\\Nellybett\\Desktop\\map";
        result = instance.readFile(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Test
    public void testConfigurationInf() {
        System.out.println("configurationInf");
        String info = "";
        String path = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     */
    @Test
    public void testContinentCreator() {
        System.out.println("continentCreator");
        String info = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.continentCreator(info, board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator() {
        System.out.println("countryCreator");
        String info = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.countryCreator(info, board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateBoardFile method, of class MapFileManagement.
     */
    @Test
    public void testGenerateBoardFile() {
        System.out.println("generateBoardFile");
        String path = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.generateBoardFile(path, board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
