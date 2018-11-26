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
import com.risk.views.menu.PlayerPanel;
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Observable;
import static org.junit.Assert.assertEquals;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for GameController
 *
 * @author hantoine
 */
public class GameControllerTest {

    /**
     * Reference to the game controller
     */
    private GameController instance;
    /**
     * Reference to the main risk model
     */
    private RiskModel rm;
    /**
     * reference to the dummy view used to check that the view is notified
     */
    private DummyRiskView drv;

    /**
     * Set up the tests
     */
    @Before
    public void setUp() {
        rm = new RiskModel();
        removeAllPlayers();
        rm.addPlayerToPlayerList("PlayerA", Color.yellow, true);
        rm.addPlayerToPlayerList("PlayerB", Color.red, true);
        rm.setMap(getTestMap());
        drv = new DummyRiskView();
        drv.observeModel(rm);
        instance = new GameController(rm);
    }

    /**
     * Remove all player in the game
     */
    private void removeAllPlayers() {
        Method method;
        try {
            method = RiskModel.class
                    .getDeclaredMethod("removePlayer", int.class);
            method.setAccessible(true);
            while (!rm.getPlayerList().isEmpty()) {
                method.invoke(rm, 0);
            }
        } catch (SecurityException
                | ReflectiveOperationException
                | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }
    }

    /**
     * Generate test map
     *
     * @return test map
     */
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

        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        addTerritoryOwned(playerA, territoryA);
        addTerritoryOwned(playerA, territoryB);

        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(2, territoryB.getNumArmies());
        assertEquals(null, drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the territories are not adjacent
     */
    @Test
    public void testDragNDropTerritoryNotAdjacentTerritories() {

        rm.getMap().loadTerritory(150, 50, "TerritoryC", "ContinentA");
        rm.getMap().addLink("TerritoryB", "TerritoryC");

        TerritoryModel terrA = rm.getMap().getTerritoryByName("TerritoryA");
        TerritoryModel terrC = rm.getMap().getTerritoryByName("TerritoryC");

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        addTerritoryOwned(playerA, terrA);
        addTerritoryOwned(playerA, terrC);
        setNumArmies(terrA, 2);
        setNumArmies(terrC, 1);

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
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        PlayerModel playerB = rm.getPlayerList().get(1);
        addTerritoryOwned(playerB, territoryA);
        addTerritoryOwned(playerA, territoryB);
        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        assertEquals("You don't own this territory !", drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the player is not the owner of the destination
     * territory
     */
    @Test
    public void testDragNDropTerritoryDestinationTerritorNotOwned() {
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        PlayerModel playerB = rm.getPlayerList().get(1);
        addTerritoryOwned(playerA, territoryA);
        addTerritoryOwned(playerB, territoryB);
        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        assertEquals("You don't own this territory !", drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, in
     * fortification stage when the player has already started another move
     */
    @Test
    public void testDragNDropTerritoryOtherMoveStarted() {
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        addTerritoryOwned(playerA, territoryA);
        addTerritoryOwned(playerA, territoryB);
        setNumArmies(territoryA, 1);
        setNumArmies(territoryB, 2);
        instance.dragNDropTerritory(destTerritoryName, sourceTerritoryName);
        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);

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
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        addTerritoryOwned(playerA, territoryA);
        addTerritoryOwned(playerA, territoryB);
        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);
        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);
        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(2, territoryB.getNumArmies());
        assertEquals(null, drv.getMessage());
    }

    /**
     * Test of dragNDropTerritory method, of class GameController, when not in
     * Fortification stage
     */
    @Test
    public void testDragNDropTerritoryNotFortification() {
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getPlayerList().get(1);
        addTerritoryOwned(playerA, territoryA);
        addTerritoryOwned(playerA, territoryB);
        setNumArmies(territoryA, 2);
        setNumArmies(territoryB, 1);

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
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getTerritoryByName(destTerritoryName);

        rm.nextPhase();
        rm.nextPhase();
        rm.nextPhase();
        PlayerModel playerA = rm.getCurrentPlayer();
        addTerritoryOwned(playerA, territoryA);
        addTerritoryOwned(playerA, territoryB);
        setNumArmies(territoryA, 1);
        setNumArmies(territoryB, 1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());

        assertEquals("There is only one army in the source territory !",
                drv.getMessage());
    }

    /**
     * Wrapper for setNumArimes to make it accessible
     *
     * @param ter Territory for which to set the number of armies
     * @param numArmies Number of armies to set
     */
    private void setNumArmies(TerritoryModel ter, int numArmies) {
        Method method;
        try {
            method = TerritoryModel.class
                    .getDeclaredMethod("setNumArmies", int.class);
            method.setAccessible(true);
            method.invoke(ter, numArmies);
        } catch (SecurityException
                | ReflectiveOperationException
                | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }
    }

    /**
     * Wrapper for addTerritoryOwned to make it accessible
     *
     * @param player Player for which to add owned territory
     * @param territory Territory to add to the owned territories of the player
     */
    private void addTerritoryOwned(PlayerModel player, TerritoryModel territory) {
        Method method;
        try {
            method = PlayerModel.class
                    .getDeclaredMethod("addTerritoryOwned", TerritoryModel.class);
            method.setAccessible(true);
            method.invoke(player, territory);
        } catch (SecurityException
                | ReflectiveOperationException
                | IllegalArgumentException ex) {
            throw new AssumptionViolatedException("Cannot arrange");
        }
    }

    /**
     * Dummy view used to test that the view is correctly notified
     */
    private static class DummyRiskView implements RiskViewInterface {

        String message;

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
            if (o instanceof RiskModel && o1 instanceof String) {
                this.message = (String) o1;
            }
        }

        @Override
        public String getMapPathForNewGame() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public LinkedList<PlayerPanel> getPlayersForNewGame() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showMessage(String message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
