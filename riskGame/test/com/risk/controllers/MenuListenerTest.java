/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskViewInterface;
import com.risk.views.menu.PlayerPanel;
import java.io.File;
import java.util.LinkedList;
import java.util.Observable;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class MenuListenerTest {

    RiskModel rm;
    DummyRiskView rv;
    RiskController rc;
    MenuListener instance;

    public MenuListenerTest() {
    }

    @Before
    public void setUp() {
        rm = new RiskModel();
        rv = new DummyRiskView();

        instance = new MenuListener(rm, rv, null);
    }

    @Test
    public void testPlayButtonNoPathSelected() {
        rv.setMapPathForNewGame("");

        instance.playButton();

        assertEquals("You have not selected a map", rv.getMessage());
    }

    @Test
    public void testPlayButtonNoMapFileInvalid() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "nonexistentmapfile.map");

        instance.playButton();

        assertEquals("Error reading the file for map nonexistentmapfile", rv.getMessage());
    }

    @Test
    public void testPlayButtonMapFileInvalidErrorConfigInfo() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "errorInConfigInfos.map");

        instance.playButton();

        assertEquals("Error in parameters to configurate the map errorInConfigInfos", rv.getMessage());
    }

    @Test
    public void testPlayButtonMapFileInvalidErrorContinentInfo() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "errorInContinentInfos.map");

        instance.playButton();

        assertEquals("Error in continent information for map errorInContinentInfos", rv.getMessage());
    }

    @Test
    public void testPlayButtonMapFileInvalidErrorTerritoryInfo() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "continentmissing.map");

        instance.playButton();

        assertEquals("Error in territory information for map continentmissing", rv.getMessage());
    }

    @Test
    public void testPlayButtonMapFileInvalidNoTerritorySeparator() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "noterritoryTAG.map");

        instance.playButton();

        assertEquals("No territories separator in file for map noterritoryTAG", rv.getMessage());
    }

    @Test
    public void testPlayButtonMapFileInvalidNoContinentSeparator() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "testcases" + File.separator
                + "noContinentTag.map");

        instance.playButton();

        assertEquals("No continents separator in file for map noContinentTag", rv.getMessage());
    }

    @Test
    public void testPlayButtonMapInvalid() {
        rv.setMapPathForNewGame("." + File.separator
                + "maps" + File.separator + "Twin Volcano.map");

        instance.playButton();

        assertEquals("The map Twin Volcano is not valid.", rv.getMessage());
    }

    /**
     * Dummy view used to test that the view is correctly notified
     */
    private static class DummyRiskView implements RiskViewInterface {

        String message;
        String mapPathForNewGame;

        public DummyRiskView() {
        }

        @Override
        public void observeModel(RiskModel rm) {
            rm.addObserver(this);
        }

        @Override
        public void setController(RiskController rc) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void initialMenu(RiskModel riskModel, MenuListener menuListener) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void closeMenu() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public void update(Observable o, Object o1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getMapPathForNewGame() {
            return mapPathForNewGame;
        }

        public void setMapPathForNewGame(String mapPathForNewGame) {
            this.mapPathForNewGame = mapPathForNewGame;
        }

        @Override
        public LinkedList<PlayerPanel> getPlayersForNewGame() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showMessage(String message) {
            this.message = message;
        }

        @Override
        public void setVisible(boolean b) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void showError(String errorMsg) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public LinkedList<String> getLogs() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setLogs(LinkedList<String> logs) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
