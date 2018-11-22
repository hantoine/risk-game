/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.editor.MapViewInterface;
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
    public void testloadMapFromFile() {
        instance.loadMapFromFile("", dmv);

        //assertEquals();
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

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
