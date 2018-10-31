/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.MapModel;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.RiskViewInterface;
import com.risk.views.menu.NewGamePanel;
import java.awt.Color;
import java.lang.reflect.Method;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class GameControllerTest {

    private GameController instance;
    private RiskModel rm;
    private DummyRiskView drv;

    @Before
    public void setUp() {
        rm = new RiskModel();
        rm.removePlayer(0);
        rm.removePlayer(0);
        rm.removePlayer(0);
        rm.addPlayerToPlayerList("PlayerA", Color.yellow, true);
        rm.addPlayerToPlayerList("PlayerB", Color.red, true);
        rm.setMap(getTestMap());
        drv = new DummyRiskView();
        instance = new GameController(rm, drv);
    }

    private static MapModel getTestMap() {
        MapModel map = new MapModel();

        map.removeContinent(map.getContinentList().get(0));
        map.addContinent("ContinentA", 3);
        map.loadTerritory(50, 100, "TerritoryA", "ContinentA");
        map.loadTerritory(100, 50, "TerritoryB", "ContinentA");
        map.addLink("TerritoryA", "TerritoryB");

        return map;
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when everything is fine
     */
    @Test
    public void testDragNDropTerritory() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        Method method;
        try {
            method = PlayerModel.class.getDeclaredMethod("addCountryOwned", TerritoryModel.class);
            method.setAccessible(true);
            method.invoke(playerA, territoryA);
            method.invoke(playerA, territoryB);
        } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }

        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(2, territoryB.getNumArmies());
        assertEquals(null, drv.getMessage());
        assertTrue(drv.isUpdated());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the territories are not adjacent
     */
    @Test
    public void testDragNDropTerritoryNotAdjacentTerritories() {
        System.out.println("dragNDropTerritory");

        rm.getMap().loadTerritory(150, 50, "TerritoryC", "ContinentA");
        rm.getMap().addLink("TerritoryB", "TerritoryC");

        TerritoryModel terrA = rm.getMap().getTerritoryByName("TerritoryA");
        TerritoryModel terrC = rm.getMap().getTerritoryByName("TerritoryC");

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        terrA.setOwner(playerA);
        terrC.setOwner(playerA);
        terrA.setNumArmies(2);
        terrC.setNumArmies(1);

        instance.dragNDropTerritory("TerritoryA", "TerritoryC");

        assertEquals(2, terrA.getNumArmies());
        assertEquals(1, terrC.getNumArmies());
        assertEquals(null, drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the player is not the owner of the source
     * territory
     */
    @Test
    public void testDragNDropTerritorySourceTerritorNotOwned() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        PlayerModel playerB = rm.getPlayerList().getLast();
        territoryA.setOwner(playerB);
        territoryB.setOwner(playerA);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        assertEquals("You don't own this country !", drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the player is not the owner of the destination
     * territory
     */
    @Test
    public void testDragNDropTerritoryDestinationTerritorNotOwned() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        PlayerModel playerB = rm.getPlayerList().getLast();
        territoryA.setOwner(playerA);
        territoryB.setOwner(playerB);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        assertEquals("You don't own this country !", drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the player has already started another move
     */
    @Test
    public void testDragNDropTerritoryOtherMoveStarted() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        Method method;
        try {
            method = PlayerModel.class
                    .getDeclaredMethod("addCountryOwned", TerritoryModel.class);
            method.setAccessible(true);
            method.invoke(playerA, territoryA);
            method.invoke(playerA, territoryB);
        } catch (SecurityException
                | ReflectiveOperationException
                | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }
        territoryA.setNumArmies(1);
        territoryB.setNumArmies(2);
        instance.dragNDropTerritory(destTerritoryName, sourceTerritoryName);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        assertEquals("You can only make one move !", drv.getMessage());

    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the player has already started this move
     */
    @Test
    public void testDragNDropTerritorySameMoveStarted() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        Method method;
        try {
            method = PlayerModel.class
                    .getDeclaredMethod("addCountryOwned", TerritoryModel.class);
            method.setAccessible(true);
            method.invoke(playerA, territoryA);
            method.invoke(playerA, territoryB);
        } catch (SecurityException
                | ReflectiveOperationException
                | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);
        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(2, territoryB.getNumArmies());
        assertEquals(null, drv.getMessage());
        assertTrue(drv.isUpdated());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, when not in
     * Fortification stage
     */
    @Test
    public void testDragNDropTerritoryNotFortification() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getPlayerList().getFirst();
        territoryA.setOwner(playerA);
        territoryB.setOwner(playerA);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, when in
     * Fortification stage, but only one army left on source territory
     */
    @Test
    public void testDragNDropTerritoryNotFortificationOnlyOneArmy() {
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        Method method;
        try {
            method = PlayerModel.class
                    .getDeclaredMethod("addCountryOwned", TerritoryModel.class);
            method.setAccessible(true);
            method.invoke(playerA, territoryA);
            method.invoke(playerA, territoryB);
        } catch (SecurityException
                | ReflectiveOperationException
                | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }
        territoryA.setNumArmies(1);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        System.out.println("done");
        assertEquals("There is only one army in the source country !",
                drv.getMessage());
    }

    private static class DummyRiskView implements RiskViewInterface {

        String message;
        boolean updated;

        public DummyRiskView() {
            updated = false;
        }

        @Override
        public void updateView(RiskModel rm) {
            updated = true;
        }

        @Override
        public void updateViewWithNewMap(RiskModel rm) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showMessage(String message) {
            this.message = message;
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

        @Override
        public NewGamePanel getNewGamePanel() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getMessage() {
            return this.message;
        }

        public boolean isUpdated() {
            return updated;
        }
    }

}
