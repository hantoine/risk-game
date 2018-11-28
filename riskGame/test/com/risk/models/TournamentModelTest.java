/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class TournamentModelTest {

    TournamentModel instance;

    public TournamentModelTest() {
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
        Set<MapPath> expResult = new HashSet<>();

        Set<MapPath> result = instance.getMapsPaths();

        assertEquals(expResult, result);
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel.
     */
    @Test
    public void testAddMapsPath() {
        MapPath mapPath = new MapPath(
                Paths.get("maps", "Europe.map").toString()
        );

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
        MapPath mapPath1 = new MapPath(
                Paths.get("maps", "Europe.map").toString()
        );
        MapPath mapPath2 = new MapPath(
                Paths.get("maps", "Old Yorkshire.map").toString()
        );
        MapPath mapPath3 = new MapPath(
                Paths.get("maps", "Di'Mul_Jar.map").toString()
        );
        MapPath mapPath4 = new MapPath(
                Paths.get("maps", "Twin Volcano valid.map").toString()
        );
        MapPath mapPath5 = new MapPath(
                Paths.get("maps", "USA.map").toString()
        );
        MapPath mapPath6 = new MapPath(
                Paths.get("maps", "Polygons.map").toString()
        );

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
        MapPath mapPath = new MapPath(
                Paths.get("maps", "Europe.map").toString()
        );

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
        MapPath mapPath = new MapPath(
                Paths.get("maps", "TestCaseNonExistantMapFile.map").toString()
        );

        instance.addMapsPath(mapPath);
    }

    /**
     * Test of addMapsPath method, of class TournamentMenuModel when file is
     * invalid
     */
    @Test(expected = IllegalStateException.class)
    public void testAddMapsPathMapFileInvalid() {
        MapPath mapPath = new MapPath(
                Paths.get("maps", "presentation", "noterritoryTAG.map")
                        .toString()
        );

        instance.addMapsPath(mapPath);
    }

    /**
     * Test of removeMapsPath method, of class TournamentMenuModel.
     */
    @Test
    public void testRemoveMapsPath() {
        MapPath mapPath = new MapPath(
                Paths.get("maps", "Europe.map").toString()
        );
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

    /**
     * Test of playTournament method, of class TournamentModel. Should not throw
     * exception
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPlayTournament() throws Exception {
        playTestTournament();
    }

    /**
     * Play a test tournament
     */
    private void playTestTournament() throws MapFileManagement.MapFileManagementException {
        instance.addMapsPath(
                new MapPath(Paths.get("maps", "Atlantis.map").toString()));
        instance.addMapsPath(
                new MapPath(Paths.get("maps", "Di'Mul_Jar.map").toString()));
        instance.addPlayerStategies(Strategy.Type.CHEATER);
        instance.addPlayerStategies(Strategy.Type.RANDOM);
        instance.addPlayerStategies(Strategy.Type.BENEVOLENT);
        instance.addPlayerStategies(Strategy.Type.AGGRESSIVE);
        instance.setMaximumTurnPerGame(40);
        instance.setNbGamePerMap(1);
        instance.playTournament();
    }

    /**
     * Test of getGames method, of class TournamentModel.
     */
    @Test
    public void testGetGames() {
        System.out.println("getGames");
        Map<MapPath, List<RiskModel>> result = instance.getGames();
        assertNull(result);
    }

    /**
     * Test of getRowCount method, of class TournamentModel.
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     */
    @Test
    public void testGetRowCount() throws MapFileManagement.MapFileManagementException {
        playTestTournament();
        int expResult = 3;
        int result = instance.getRowCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColumnCount method, of class TournamentModel.
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     */
    @Test
    public void testGetColumnCount() throws MapFileManagement.MapFileManagementException {
        playTestTournament();
        int expResult = 2;
        int result = instance.getColumnCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColumnName method, of class TournamentModel.
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     */
    @Test
    public void testGetColumnName() throws MapFileManagement.MapFileManagementException {
        playTestTournament();
        int i = 0;
        String expResult = "Maps";
        String result = instance.getColumnName(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of getColumnName method, of class TournamentModel.
     */
    @Test
    public void testGetColumnName2() {
        int i = 1;
        String expResult = "Game 1";
        String result = instance.getColumnName(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of getColumnClass method, of class TournamentModel.
     */
    @Test
    public void testGetColumnClass() {
        int i = 0;
        Class expResult = String.class;
        Class result = instance.getColumnClass(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of isCellEditable method, of class TournamentModel.
     */
    @Test
    public void testIsCellEditable() {
        int i = 0;
        int j = 0;
        boolean expResult = false;
        boolean result = instance.isCellEditable(i, j);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValueAt method, of class TournamentModel.
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     */
    @Test
    public void testGetValueAt() throws MapFileManagement.MapFileManagementException {
        playTestTournament();
        System.out.println("getValueAt");
        int i = 1;
        int j = 1;
        Object result = instance.getValueAt(i, j);
        assertTrue(result.equals("CHEATER") || result.equals("DRAW"));
    }

    /**
     * Test of setValueAt method, of class TournamentModel.
     */
    @Test
    public void testSetValueAt() {
        Object o = null;
        int i = 0;
        int j = 0;
        instance.setValueAt(o, i, j);
    }
}
