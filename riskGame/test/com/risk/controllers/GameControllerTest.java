/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.ContinentModel;
import com.risk.models.FortificationMove;
import com.risk.models.GamePhase;
import com.risk.models.MapModel;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.RiskViewInterface;
import com.risk.views.menu.NewGamePanel;
import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
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

        HashMap<String, ContinentModel> graphContinents = new HashMap<>();
        String name;
        name = "ContinentA";
        graphContinents.put(name, new ContinentModel(name, 3));

        HashMap<String, TerritoryModel> graphTerritories = new HashMap<>();
        name = "TerritoryA";
        graphTerritories.put(name, new TerritoryModel(name, 50, 50));
        name = "TerritoryB";
        graphTerritories.put(name, new TerritoryModel(name, 100, 50));

        LinkedList<TerritoryModel> memberTerritories = new LinkedList();
        graphTerritories.get("TerritoryA").setContinentName("ContinentA");
        memberTerritories.add(graphTerritories.get("TerritoryA"));
        graphTerritories.get("TerritoryB").setContinentName("ContinentA");
        memberTerritories.add(graphTerritories.get("TerritoryB"));
        graphContinents.get("ContinentA").setMembers(memberTerritories);

        LinkedList<TerritoryModel> adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryB"));
        graphTerritories.get("TerritoryA").setAdj(adjacentTerritories);

        adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryA"));
        graphTerritories.get("TerritoryB").setAdj(adjacentTerritories);

        map.setGraphTerritories(graphTerritories);
        map.setGraphContinents(graphContinents);

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
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        playerA.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryB);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

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
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryC";
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get("TerritoryB");
        TerritoryModel territoryC = new TerritoryModel(destTerritoryName, 150, 50);

        rm.getMap().getGraphTerritories().put(territoryC.getName(), territoryC);
        territoryC.setContinentName("ContinentA");
        rm.getMap().getGraphContinents().get("ContinentA").setMember(territoryC);
        territoryB.addNeighbour(territoryC);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        playerA.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryC);
        territoryA.setNumArmies(2);
        territoryC.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(2, territoryA.getNumArmies());
        assertEquals(1, territoryC.getNumArmies());
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
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        PlayerModel playerB = rm.getPlayerList().getLast();
        playerB.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryB);
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
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        PlayerModel playerB = rm.getPlayerList().getLast();
        playerA.addCountryOwned(territoryA);
        playerB.addCountryOwned(territoryB);
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
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        playerA.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryB);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        rm.getPlayerList().getFirst().setCurrentFortificationMove(new FortificationMove(territoryB, territoryA));

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
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        playerA.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryB);
        territoryA.setNumArmies(2);
        territoryB.setNumArmies(1);

        rm.getPlayerList().getFirst().setCurrentFortificationMove(new FortificationMove(territoryA, territoryB));

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
        System.out.println("dragNDropTerritory");
        String sourceTerritoryName = "TerritoryA";
        String destTerritoryName = "TerritoryB";
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.ATTACK);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        playerA.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryB);
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
        TerritoryModel territoryA = rm.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel territoryB = rm.getMap().getGraphTerritories().get(destTerritoryName);

        rm.setStage(GamePhase.FORTIFICATION);
        rm.setCurrentPlayer(rm.getPlayerList().getFirst());
        PlayerModel playerA = rm.getPlayerList().getFirst();
        playerA.addCountryOwned(territoryA);
        playerA.addCountryOwned(territoryB);
        territoryA.setNumArmies(1);
        territoryB.setNumArmies(1);

        instance.dragNDropTerritory(sourceTerritoryName, destTerritoryName);

        assertEquals(1, territoryA.getNumArmies());
        assertEquals(1, territoryB.getNumArmies());
        System.out.println("done");
        assertEquals("There is only one army in the source country !", drv.getMessage());
    }

    private static class DummyRiskView implements RiskViewInterface {

        String message;

        public DummyRiskView() {
        }

        @Override
        public void updateView(RiskModel rm) {
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
    }

}
