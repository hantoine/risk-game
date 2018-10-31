/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * It proves all the functions in the MapFileManagement class
 *
 * @author Nellybett
 */
public class MapFileManagementTest {

    /**
     * board is a not null instance of the map instance is the instance of the
     * class being tested
     */
    MapModel board;
    /**
     * path is a valid path of a map in the client's computer
     */
    String path;

    /**
     * Constructor
     */
    public MapFileManagementTest() {
    }

    /**
     * Load the map and create a valid MapModel that will be the base for all
     * test cases
     */
    @Before
    public void before() {
        board = new MapModel();
        path = "." + File.separator + "maps" + File.separator + "Old Yorkshire.map";

        board.addContinent("York", 2);
    }

    /**
     * Test of createBoard method, of class MapFileManagement.Null board.
     */
    @Test
    public void testCreateBoard() {

        int expResult = -7;
        board = null;
        int result = MapFileManagement.createBoard(path, board);
        //Good path with correct file but not initialize board

        assertEquals(expResult, result);

    }

    /**
     * Test of createBoard method, of class MapFileManagement.
     */
    @Test
    public void testCreateBoard1() {

        //Good path with correct file and board!=null
        int result = MapFileManagement.createBoard(path, board);
        int expResult = 0;
        assertEquals(expResult, result);

    }

    /**
     * Test of readFile method, of class MapFileManagement.
     */
    @Test
    public void testReadFile() {
        System.out.println("readFile");
        path = "";
        String result = MapFileManagement.readFile(path);
        String expResult = "-1";

        //Test case path=""
        assertEquals(expResult, result);

    }

    /**
     * Test of readFile method, of class MapFileManagement.
     */
    @Test
    public void testReadFile1() {

        //Test case path="&&"
        path = "&&";
        String expResult = "-1";
        String result = MapFileManagement.readFile(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of readFile method, of class MapFileManagement.
     */
    @Test
    public void testReadFile2() {
        //Test case path without .map extension
        path = "." + File.separator + "maps";
        String result = MapFileManagement.readFile(path);
        String expResult = "-1";
        assertEquals(expResult, result);

    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Test
    public void testConfigurationInf() {
        System.out.println("configurationInf");
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "image=old yorkshire.bmp\n" + "wrap=no\n" + "scroll=horizontal";
        int expResult = 0;

        //Test Case with all the parameters and a valid file path
        int result = MapFileManagement.configurationInf(info, path, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Test
    public void testConfigurationInf1() {

        //Test Case only with image parameter and valid file path
        int expResult = 0;
        String info = "[Map]\n" + "image=old yorkshire.bmp\n";
        int result = MapFileManagement.configurationInf(info, path, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Test
    public void testConfigurationInf2() {

        //Test Case only with image parameter and invalid file path
        String info = "[Map]\n" + "image=old yorkshire.bmp\n";
        path = "";
        int expResult = -1;
        int result = MapFileManagement.configurationInf(info, path, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement. With a null
     * image parameter and valid file path
     */
    @Test
    public void testConfigurationInf3() {

        //Test Case only with null image parameter and valid file path
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "wrap=no\n" + "scroll=horizontal\n" + "image=null";
        int expResult = 0;
        int result = MapFileManagement.configurationInf(info, path, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     */
    @Test
    public void testContinentCreator() {
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=8\n" + "West Riding=11";
        int expResult = 0;

        // String with continents information
        int result = MapFileManagement.continentCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     */
    @Test
    public void testContinentCreator1() {
        // It fails if the continent and bonus armies are not separated by =
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=8\n" + "West Riding11";
        int expResult = -1;
        int result = MapFileManagement.continentCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     */
    @Test
    public void testContinentCreator2() {

        // It fails if the bonus armies is not a number
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=a\n" + "West Riding=11";
        int expResult = -1;
        int result = MapFileManagement.continentCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator() {
        board = new MapModel();
        String info = "Anisty Liberty,363,300,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        int expResult = -1;

        //Country with not existing continent
        int result = MapFileManagement.countryCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator1() {

        //Country without adj
        String info = "Anisty Liberty,363,300,York\n" + "Tadcaster,370,327,York,Anisty Liberty";
        int expResult = -1;
        int result = MapFileManagement.countryCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator2() {

        //Country with no number position
        String info = "Anisty Liberty,363,a,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        int expResult = -1;
        int result = MapFileManagement.countryCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator3() {

        //Correct countries string
        String info = "Anisty Liberty,363,294,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        int result = MapFileManagement.countryCreator(info, board);
        int expResult = 0;
        assertEquals(expResult, result);

    }
}
