/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.HashMap;
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
public class FileManagementTest {
    
    public FileManagementTest() {
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
     * Test of createBoard method, of class FileManagement.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateBoard() throws Exception {
        System.out.println("createBoard");
        String path = "";
        FileManagement instance = new FileManagement();
        Board expResult = null;
        Board result = instance.createBoard(path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFile method, of class FileManagement.
     * @throws java.lang.Exception
     */
    @Test
    public void testReadFile() throws Exception {
        System.out.println("readFile");
        String path = "";
        FileManagement instance = new FileManagement();
        String expResult = "";
        String result = instance.readFile(path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of configurationInf method, of class FileManagement.
     * @throws java.lang.Exception
     */
    @Test
    public void testConfigurationInf() throws Exception {
        System.out.println("configurationInf");
        String info = "";
        String path = "";
        Board board = null;
        FileManagement instance = new FileManagement();
        HashMap<String, String> expResult = null;
        HashMap<String, String> result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of continentCreator method, of class FileManagement.
     * @throws java.lang.Exception
     */
    @Test
    public void testContinentCreator() throws Exception {
        System.out.println("continentCreator");
        String info = "";
        FileManagement instance = new FileManagement();
        HashMap<String, Continent> expResult = null;
        HashMap<String, Continent> result = instance.continentCreator(info);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countryCreator method, of class FileManagement.
     * @throws java.lang.Exception
     */
    @Test
    public void testCountryCreator() throws Exception {
        System.out.println("countryCreator");
        String info = "";
        HashMap<String, Continent> graphContinents = null;
        FileManagement instance = new FileManagement();
        HashMap<String, Country> expResult = null;
        HashMap<String, Country> result = instance.countryCreator(info, graphContinents);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
