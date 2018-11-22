/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.editor.MapViewInterface;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class MapEditorControllerTest {

    MapEditorController instance;
    DummyMapView dmv;

    public MapEditorControllerTest() {
    }

    @Before
    public void setUp() {
        dmv = new DummyMapView();
        instance = new MapEditorController(null);
    }

    @Test
    public void testloadMapFromFileNoFile() {
        instance.loadMapFromFile("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "nonexistentmapfile.map", dmv);

        assertEquals("Error reading the file", dmv.getMessage());
    }

    @Test
    public void testloadMapFromFileErrorInConfig() {
        instance.loadMapFromFile("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "errorInConfigInfos.map", dmv);

        assertEquals("Error in parameters to configurate the map.",
                dmv.getMessage());
    }

    @Test
    public void testloadMapFromFileErrorInContinents() {
        instance.loadMapFromFile("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "errorInContinentInfos.map", dmv);

        assertEquals("Error in continent information.",
                dmv.getMessage());
    }

    @Test
    public void testloadMapFromFileErrorInTerritory() {
        instance.loadMapFromFile("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "continentmissing.map", dmv);

        assertEquals("Error in territory information.",
                dmv.getMessage());
    }

    @Test
    public void testloadMapFromFileNoTerritorySep() {
        instance.loadMapFromFile("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "noterritoryTAG.map", dmv);

        assertEquals("No territories separator in file.",
                dmv.getMessage());
    }

    /**
     * Dummy view used to test that the view is correctly notified
     */
    private static class DummyMapView implements MapViewInterface {

        String message;

        @Override
        public void showError(String errorMessage) {
            message = errorMessage;
        }

        public String getMessage() {
            return message;
        }
    }

}
