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
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     */
    @Test(expected = MapFileManagement.MapInvalidException.class)
    public void testCreateBoard()
            throws MapFileManagement.MapFileManagementException {
        board = null;
        MapFileManagement.createBoard(path, board);
    }

    /**
     * Test of createBoard method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     */
    @Test
    public void testCreateBoard1()
            throws MapFileManagement.MapFileManagementException {
        //Good path with correct file and board!=null
        MapFileManagement.createBoard(path, board);
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
     *
     * @throws com.risk.models.MapFileManagement.MapFileConfigException
     */
    @Test
    public void testConfigurationInf()
            throws MapFileManagement.MapFileConfigException {
        System.out.println("configurationInf");
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "image=old yorkshire.bmp\n" + "wrap=no\n" + "scroll=horizontal";
        //Test Case with all the parameters and a valid file path
        MapFileManagement.configurationInf(info, path, board);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileConfigException
     */
    @Test
    public void testConfigurationInf1()
            throws MapFileManagement.MapFileConfigException {
        //Test Case only with image parameter and valid file path
        String info = "[Map]\n" + "image=old yorkshire.bmp\n";
        MapFileManagement.configurationInf(info, path, board);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement.
     */
    @Test(expected = MapFileManagement.MapFileConfigException.class)
    public void testConfigurationInf2()
            throws MapFileManagement.MapFileConfigException {
        //Test Case only with image parameter and invalid file path
        String info = "[Map]\n" + "image=old yorkshire.bmp\n";
        path = "";
        MapFileManagement.configurationInf(info, path, board);
    }

    /**
     * Test of configurationInf method, of class MapFileManagement. With a null
     * image parameter and valid file path
     *
     * @throws com.risk.models.MapFileManagement.MapFileConfigException
     */
    @Test
    public void testConfigurationInf3()
            throws MapFileManagement.MapFileConfigException {

        //Test Case only with null image parameter and valid file path
        String info = "[Map]\n" + "author=Stewart Ainsworth\n" + "wrap=no\n" + "scroll=horizontal\n" + "image=null";
        MapFileManagement.configurationInf(info, path, board);
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileContinentException
     */
    @Test
    public void testContinentCreator()
            throws MapFileManagement.MapFileContinentException {
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=8\n" + "West Riding=11";
        // String with continents information
        MapFileManagement.continentCreator(info, board);
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileContinentException
     */
    @Test(expected = MapFileManagement.MapFileContinentException.class)
    public void testContinentCreator1()
            throws MapFileManagement.MapFileContinentException {
        // It fails if the continent and bonus armies are not separated by =
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=8\n" + "West Riding11";
        MapFileManagement.continentCreator(info, board);
    }

    /**
     * Test of continentCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileContinentException
     */
    @Test(expected = MapFileManagement.MapFileContinentException.class)
    public void testContinentCreator2()
            throws MapFileManagement.MapFileContinentException {
        // It fails if the bonus armies is not a number
        String info = "York=2\n" + "East Riding=6\n" + "North Riding=a\n" + "West Riding=11";
        MapFileManagement.continentCreator(info, board);
    }

    /**
     * Test of territoryCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileTerritoryException
     */
    @Test(expected = MapFileManagement.MapFileTerritoryException.class)
    public void testTerritoryCreator()
            throws MapFileManagement.MapFileTerritoryException {
        board = new MapModel();
        String info = "Anisty Liberty,363,300,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";

        //territory with not existing continent
        MapFileManagement.territoryCreator(info, board);
    }

    /**
     * Test of territoryCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileTerritoryException
     */
    @Test(expected = MapFileManagement.MapFileTerritoryException.class)
    public void testTerritoryCreator1()
            throws MapFileManagement.MapFileTerritoryException {
        //territory without adj
        String info = "Anisty Liberty,363,300,York\n" + "Tadcaster,370,327,York,Anisty Liberty";
        MapFileManagement.territoryCreator(info, board);
    }

    /**
     * Test of territoryCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileTerritoryException
     */
    @Test(expected = MapFileManagement.MapFileTerritoryException.class)
    public void testTerritoryCreator2() throws MapFileManagement.MapFileTerritoryException {

        //territory with no number position
        String info = "Anisty Liberty,363,a,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        MapFileManagement.territoryCreator(info, board);
    }

    /**
     * Test of territoryCreator method, of class MapFileManagement.
     *
     * @throws com.risk.models.MapFileManagement.MapFileTerritoryException
     */
    @Test
    public void testTerritoryCreator3() throws MapFileManagement.MapFileTerritoryException {
        //Correct territories string
        String info = "Anisty Liberty,363,294,York,Tadcaster\n" + "Tadcaster,370,327,York,Anisty Liberty";
        MapFileManagement.territoryCreator(info, board);
    }
}
