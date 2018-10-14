/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * It proves all the functions in the MapFileManagement class
 *
 * @author Nellybett
 */
public class MapFileManagementTest {

    /**
     * board is a not null instance of the map instance is the instance of the
     * class being tested path is a valid path of a map in the client's computer
     */
    MapModel board;
    MapFileManagement instance;
    String path;

    /**
     * Constructor
     */
    public MapFileManagementTest() {
    }

    @Before
    public void before() {
        board = new MapModel();
        instance = new MapFileManagement();
        path = "." + File.separator + "maps" + File.separator + "Old Yorkshire.map";

        ContinentModel auxContinent = new ContinentModel("York", 2);
        board.getGraphContinents().put("York", auxContinent);
    }

    /**
     * Test of createBoard method, of class MapFileManagement.Null board.
     */
    @Ignore
    public void testCreateBoard() {

        int expResult = -7;
        board = null;
        int result = instance.createBoard(path, board);
        //Good path with correct file but not initialize board

        assertEquals(expResult, result);

    }

    /**
     * Test of createBoard method, of class MapFileManagement.
     */
    @Test
    public void testCreateBoard1() {

        //Good path with correct file and board!=null
        int result = instance.createBoard(path, board);
        int expResult = 0;
        assertEquals(expResult, result);

    }

    /**
     * Test of readFile method, of class MapFileManagement.
     */
    @Ignore
    public void testReadFile() {
        System.out.println("readFile");
        path = "";
        String result = instance.readFile(path);
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
        String result = instance.readFile(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of readFile method, of class MapFileManagement.
     */
    @Test
    public void testReadFile2() {
        //Test case path without .map extension
        path = "." + File.separator + "maps";
        String result = instance.readFile(path);
        String expResult = "-1";
        assertEquals(expResult, result);

    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Ignore
    public void testConfigurationInf() {
        System.out.println("configurationInf");
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "image=old yorkshire.bmp\n" + "wrap=no\n" + "scroll=horizontal";
        int expResult = 0;

        //Test Case with all the parameters and a valid file path
        int result = instance.configurationInf(info, path, board);
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
        int result = instance.configurationInf(info, path, board);
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
        int result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement. Without
     * image parameter and valid file path
     */
    @Test
    public void testConfigurationInf3() {

        //Test Case only without image parameter and valid file path
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "wrap=no\n" + "scroll=horizontal";
        int expResult = -1;
        int result = instance.configurationInf(info, path, board);
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
        int result = instance.continentCreator(info, board);
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
        int result = instance.continentCreator(info, board);
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
        int result = instance.continentCreator(info, board);
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
        int result = instance.countryCreator(info, board);
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
        int result = instance.countryCreator(info, board);
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
        int result = instance.countryCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator3() {

        //Correct countries string
        String info = "Anisty Liberty,363,294,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        int result = instance.countryCreator(info, board);
        int expResult = 0;
        assertEquals(expResult, result);

    }

    /**
     * Test of generateBoardFile method, of class MapFileManagement.
     */
    @Ignore
    public void testGenerateBoardFile() {
        System.out.println("generateBoardFile");
        String path = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.generateBoardFile(path, board);
        assertEquals(expResult, result);

    }
}
