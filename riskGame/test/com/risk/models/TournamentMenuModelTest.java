/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class TournamentMenuModelTest {

    TournamentModel instance;

    public TournamentMenuModelTest() {
    }

    @Before
    public void setUp() {
        instance = new TournamentModel();
    }

    /**
     * Test of getMapsPath method, of class TournamentMenuModel.
     */
    @Test
    public void testGetMapsPaths() {
        Set<String> expResult = new HashSet<>();

        Set<String> result = instance.getMapsPaths();

        assertEquals(expResult, result);
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel.
     */
    @Test
    public void testAddMapsPath() {
        String mapPath = Paths.get("maps", "Europe.map").toString();

        instance.addMapsPath(mapPath);

        assertTrue(instance.getMapsPaths().contains(mapPath));
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel. The number of
     * map cannot exceed 5
     */
    @Test(expected = IllegalStateException.class)
    public void testAddMapsPathMax() {
        System.out.println("addMapsPath");
        String mapPath1 = Paths.get("maps", "Europe.map").toString();
        String mapPath2 = Paths.get("maps", "Old Yorkshire.map").toString();
        String mapPath3 = Paths.get("maps", "Di'Mul_Jar.map").toString();
        String mapPath4 = Paths.get("maps", "Twin Volcano valid.map").toString();
        String mapPath5 = Paths.get("maps", "USA.map").toString();
        String mapPath6 = Paths.get("maps", "Polygons.map").toString();

        instance.addMapsPath(mapPath1);
        instance.addMapsPath(mapPath2);
        instance.addMapsPath(mapPath3);
        instance.addMapsPath(mapPath4);
        instance.addMapsPath(mapPath5);
        instance.addMapsPath(mapPath6);
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel. When a map path
     * is added twice, it still appears only once
     */
    @Test
    public void testAddMapsPathOnce() {
        System.out.println("addMapsPath");
        String mapPath = Paths.get("maps", "Europe.map").toString();

        instance.addMapsPath(mapPath);
        instance.addMapsPath(mapPath);
        instance.removeMapsPath(mapPath);

        assertFalse(instance.getMapsPaths().contains(mapPath));
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel when file does
     * not exists
     */
    @Test(expected = IllegalStateException.class)
    public void testAddMapsPathMapFileNotExist() {
        String mapPath = Paths.get("maps", "TestCaseNonExistantMapFile.map")
                .toString();

        instance.addMapsPath(mapPath);
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel when file is
     * invalid
     */
    @Test(expected = IllegalStateException.class)
    public void testAddMapsPathMapFileInvalid() {
        String mapPath = Paths.get("maps", "presentation", "noterritoryTAG.map")
                .toString();

        instance.addMapsPath(mapPath);
    }

    /**
     * Test of removeMapsPath method, of class TournamentMenuModel.
     */
    @Test
    public void testRemoveMapsPath() {
        String mapPath = Paths.get("maps", "Europe.map").toString();
        instance.addMapsPath(mapPath);

        instance.removeMapsPath(mapPath);

        assertFalse(instance.getMapsPaths().contains(mapPath));
    }

    /**
     * Test of getPlayerStategies method, of class TournamentMenuModel.
     */
    @Test
    public void testGetPlayerStategies() {
        Set<Strategy.Type> expResult = new HashSet<>();

        Set<Strategy.Type> result = instance.getPlayerStategies();

        assertEquals(expResult, result);
    }

    /**
     * Test of addPlayerStategies method, of class TournamentMenuModel.
     */
    @Test
    public void testAddPlayerStategies() {
        Strategy.Type playerStategy = Strategy.Type.CHEATER;
        instance.addPlayerStategies(playerStategy);
        assertTrue(instance.getPlayerStategies()
                .contains(Strategy.Type.CHEATER));
    }

    /**
     * Test of addPlayerStategies method, of class TournamentMenuModel. Human
     * strategy is not allowed in tournament mode
     */
    @Test(expected = IllegalStateException.class)
    public void testAddPlayerStategiesHuman() {
        Strategy.Type playerStategy = Strategy.Type.HUMAN;
        instance.addPlayerStategies(playerStategy);
    }

    /**
     * Test of addPlayerStategies method, of class TournamentMenuModel. When a
     * strategy is added twice, it still appears only once
     */
    @Test
    public void testAddPlayerStategiesOnce() {
        Strategy.Type playerStategy = Strategy.Type.CHEATER;

        instance.addPlayerStategies(playerStategy);
        instance.addPlayerStategies(playerStategy);
        instance.removePlayerStategies(playerStategy);

        assertFalse(instance.getPlayerStategies()
                .contains(Strategy.Type.CHEATER));
    }

    /**
     * Test of removePlayerStategies method, of class TournamentMenuModel.
     */
    @Test
    public void testRemovePlayerStategies() {
        Strategy.Type playerStategy = Strategy.Type.CHEATER;
        instance.addPlayerStategies(playerStategy);

        instance.removePlayerStategies(playerStategy);

        assertFalse(instance.getPlayerStategies()
                .contains(Strategy.Type.CHEATER));
    }

    /**
     * Test of getNbGamePerMap method, of class TournamentMenuModel.
     */
    @Test
    public void testGetNbGamePerMap() {
        int expResult = 4;

        int result = instance.getNbGamePerMap();

        assertEquals(expResult, result);
    }

    /**
     * Test of setNbGamePerMap method, of class TournamentMenuModel.
     */
    @Test
    public void testSetNbGamePerMap() {
        int nbGamePerMap = 3;
        instance.setNbGamePerMap(nbGamePerMap);

        assertEquals(nbGamePerMap, instance.getNbGamePerMap());
    }

    /**
     * Test of setNbGamePerMap method, of class TournamentMenuModel. Cannot
     * exceed 5 turns
     */
    @Test(expected = IllegalStateException.class)
    public void testSetNbGamePerMapMax() {
        int nbGamePerMap = 6;
        instance.setNbGamePerMap(nbGamePerMap);
    }

    /**
     * Test of getMaximumTurnPerGame method, of class TournamentMenuModel.
     */
    @Test
    public void testGetMaximumTurnPerGame() {
        int expResult = 40;

        int result = instance.getMaximumTurnPerGame();

        assertEquals(expResult, result);
    }

    /**
     * Test of setMaximumTurnPerGame method, of class TournamentMenuModel.
     */
    @Test
    public void testSetMaximumTurnPerGame() {
        System.out.println("setMaximumTurnPerGame");
        int maximumTurnPerGame = 30;

        instance.setMaximumTurnPerGame(maximumTurnPerGame);

        assertEquals(maximumTurnPerGame, instance.getMaximumTurnPerGame());
    }

    /**
     * Test of setMaximumTurnPerGame method, of class TournamentMenuModel.
     * cannot exceed 50 turns
     */
    @Test(expected = IllegalStateException.class)
    public void testSetMaximumTurnPerGameMax() {
        System.out.println("setMaximumTurnPerGame");
        int maximumTurnPerGame = 60;

        instance.setMaximumTurnPerGame(maximumTurnPerGame);
    }

}
