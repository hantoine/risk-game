/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * It proves all the functions in the MapFileManagement class
 *
 * @author Nellybett
 */
public class MapFileManagementTest {

    /**
     * Constructor
     */
    public MapFileManagementTest() {
    }

    /**
     * Test of createBoard method, of class MapFileManagement.
     */
    @Test
    public void testCreateBoard() {
        System.out.println("createBoard");
        //Change to a valid .map file in your computer
        String path = "." + File.separator + "maps" + File.separator + "Old Yorkshire.map";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = -7;

        //Good path with correct file but not initialize board
        int result = instance.createBoard(path, board);
        assertEquals(expResult, result);

        //Good path with correct file and board!=null
        board = new MapModel();
        result = instance.createBoard(path, board);
        expResult = 0;
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
        path = "&&";
        result = instance.readFile(path);
        assertEquals(expResult, result);

        //Test case path without .map extension
        path = "C:\\Users\\Nellybett\\Desktop\\map";
        result = instance.readFile(path);
        assertEquals(expResult, result);

    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Test
    public void testConfigurationInf() {
        System.out.println("configurationInf");
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "image=old yorkshire.bmp\n" + "wrap=no\n" + "scroll=horizontal";
        String path = "." + File.separator + "maps" + File.separator + "Old Yorkshire.map";
        MapModel board = new MapModel();
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;

        //Test Case with all the parameters and a valid file path
        int result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);

        //Test Case only with image parameter and valid file path
        info = "[Map]\n" + "image=old yorkshire.bmp\n";
        board = new MapModel();
        result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);

        //Test Case only with image parameter and invalid file path
        info = "[Map]\n" + "image=old yorkshire.bmp\n";
        path = "";
        board = new MapModel();
        expResult = -1;
        result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);

        //Test Case only without image parameter and valid file path
        info = "[Map]\n" + "author=Stewart Ainsworth\n" + "wrap=no\n" + "scroll=horizontal";
        board = new MapModel();
        path = "C:\\Users\\Nellybett\\Desktop\\Old Yorkshire.map";
        expResult = -1;
        result = instance.configurationInf(info, path, board);
        assertEquals(expResult, result);

    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     */
    @Test
    public void testContinentCreator() {
        System.out.println("continentCreator");
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=8\n" + "West Riding=11";
        MapModel board = new MapModel();
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;

        // String with continents information
        int result = instance.continentCreator(info, board);
        assertEquals(expResult, result);

        // It fails if the continent and bonus armies are not separated by =
        info = "York=2\n" + "East Riding=6\n" + "North Riding=8\n" + "West Riding11";
        expResult = -1;
        result = instance.continentCreator(info, board);
        assertEquals(expResult, result);

        // It fails if the bonus armies is not a number
        info = "York=2\n" + "East Riding=6\n" + "North Riding=a\n" + "West Riding=11";
        expResult = -1;
        result = instance.continentCreator(info, board);
        assertEquals(expResult, result);
    }

    /**
     * Test of countryCreator method, of class MapFileManagement.
     */
    @Test
    public void testCountryCreator() {
        System.out.println("countryCreator");
        String info = "Anisty Liberty,363,300,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        MapModel board = new MapModel();
        MapFileManagement instance = new MapFileManagement();
        int expResult = -1;

        //Country with not existing continent
        int result = instance.countryCreator(info, board);
        assertEquals(expResult, result);

        //Country without adj
        info = "Anisty Liberty,363,300,York\n" + "Tadcaster,370,327,York,Anisty Liberty";
        ContinentModel auxContinent = new ContinentModel("York", 2);
        board.getGraphContinents().put("York", auxContinent);
        result = instance.countryCreator(info, board);
        assertEquals(expResult, result);

        //Country with no number position
        info = "Anisty Liberty,363,a,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        result = instance.countryCreator(info, board);
        assertEquals(expResult, result);

        //Correct countries string
        info = "Anisty Liberty,363,294,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        result = instance.countryCreator(info, board);
        expResult = 0;
        assertEquals(expResult, result);

    }

    /**
     * Test of generateBoardFile method, of class MapFileManagement.
     */
    /*@Test
    public void testGenerateBoardFile() {
        System.out.println("generateBoardFile");
        String path = "";
        MapModel board = null;
        MapFileManagement instance = new MapFileManagement();
        int expResult = 0;
        int result = instance.generateBoardFile(path, board);
        assertEquals(expResult, result);

    }
     */
}
