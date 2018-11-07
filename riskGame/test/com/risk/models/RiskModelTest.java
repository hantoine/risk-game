/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.RiskModel.ArmyPlacementImpossible;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author liyixuan
 */
public class RiskModelTest {

    /**
     * riskModel instance of the risk model
     */
    RiskModel riskModel = new RiskModel();
    /**
     * mapModel instance of the map model
     */
    MapModel mapModel;
    /**
     * Dummy Observer observing the risk model
     */
    DummyObserver dummyObserver;

    /**
     * Initialize mapModel to a valid MapModel that will be the base for all
     * test cases
     */
    @Before
    public void setUp() {
        mapModel = new MapModel();

        HashMap<String, ContinentModel> graphContinents = new HashMap<>();
        String name;
        name = "ContinentA";
        graphContinents.put(name, new ContinentModel(name, 3));
        name = "ContinentB";
        graphContinents.put(name, new ContinentModel(name, 2));

        HashMap<String, TerritoryModel> graphTerritories = new HashMap<>();
        name = "TerritoryA";
        graphTerritories.put(name, new TerritoryModel(name, 50, 50));
        name = "TerritoryB";
        graphTerritories.put(name, new TerritoryModel(name, 100, 50));
        name = "TerritoryC";
        graphTerritories.put(name, new TerritoryModel(name, 50, 100));
        name = "TerritoryD";
        graphTerritories.put(name, new TerritoryModel(name, 200, 200));
        name = "TerritoryE";
        graphTerritories.put(name, new TerritoryModel(name, 250, 250));

        LinkedList<TerritoryModel> memberTerritories = new LinkedList();
        graphTerritories.get("TerritoryA").setContinentName("ContinentA");
        memberTerritories.add(graphTerritories.get("TerritoryA"));
        graphTerritories.get("TerritoryB").setContinentName("ContinentA");
        memberTerritories.add(graphTerritories.get("TerritoryB"));
        graphTerritories.get("TerritoryC").setContinentName("ContinentA");
        memberTerritories.add(graphTerritories.get("TerritoryC"));
        graphContinents.get("ContinentA").setMembers(memberTerritories);
        memberTerritories = new LinkedList();
        graphTerritories.get("TerritoryD").setContinentName("ContinentB");
        memberTerritories.add(graphTerritories.get("TerritoryD"));
        graphTerritories.get("TerritoryE").setContinentName("ContinentB");
        memberTerritories.add(graphTerritories.get("TerritoryE"));
        graphContinents.get("ContinentB").setMembers(memberTerritories);

        LinkedList<TerritoryModel> adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryB"));
        adjacentTerritories.add(graphTerritories.get("TerritoryC"));
        graphTerritories.get("TerritoryA").setAdj(adjacentTerritories);

        adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryB"));
        adjacentTerritories.add(graphTerritories.get("TerritoryC"));
        graphTerritories.get("TerritoryA").setAdj(adjacentTerritories);

        adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryA"));
        adjacentTerritories.add(graphTerritories.get("TerritoryC"));
        graphTerritories.get("TerritoryB").setAdj(adjacentTerritories);

        adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryA"));
        adjacentTerritories.add(graphTerritories.get("TerritoryB"));
        adjacentTerritories.add(graphTerritories.get("TerritoryD"));
        graphTerritories.get("TerritoryC").setAdj(adjacentTerritories);

        adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryE"));
        adjacentTerritories.add(graphTerritories.get("TerritoryC"));
        graphTerritories.get("TerritoryD").setAdj(adjacentTerritories);

        adjacentTerritories = new LinkedList<>();
        adjacentTerritories.add(graphTerritories.get("TerritoryD"));
        graphTerritories.get("TerritoryE").setAdj(adjacentTerritories);

        mapModel.setGraphTerritories(graphTerritories);
        mapModel.setGraphContinents(graphContinents);

        riskModel.setMap(mapModel);

        dummyObserver = new DummyObserver();
        riskModel.addObserver(dummyObserver);
    }

    /**
     * Test of assignCoutriesToPlayers method, of class RiskModel.
     */
    @Test
    public void testAssignCoutriesToPlayers() {
        riskModel.assignTerritoriesToPlayers();
        Collection<TerritoryModel> territories = mapModel.getGraphTerritories().values();
        territories.stream().forEach((c) -> {
            assertNotNull(c.getOwner());
        });
    }

    /**
     * Test of assignCoutriesToPlayers method, of class RiskModel. No territory
     * without armies
     */
    @Test
    public void testAssignCoutriesToPlayersArmies() {
        riskModel.assignTerritoriesToPlayers();
        Collection<TerritoryModel> territories = mapModel.getGraphTerritories().values();
        territories.stream().forEach((c) -> {
            assertEquals(c.getNumArmies(), 1);
        });
    }

    /**
     * Test of assignCoutriesToPlayers method, of class RiskModel. Check that
     * continent are assigned
     */
    @Test
    public void testAssignCoutriesToPlayersContinents() {
        ContinentModel continentC = new ContinentModel("ContinentC", 3);
        TerritoryModel territoryA = mapModel.getGraphTerritories().get("TerritoryA");
        mapModel.getGraphContinents().put(continentC.getName(), continentC);
        mapModel.getGraphContinents().get("ContinentA").removeMember(territoryA);
        continentC.addMember(territoryA);
        territoryA.setContinentName("ContinentC");

        riskModel.assignTerritoriesToPlayers();

        assertTrue(riskModel.getPlayerList().stream()
                .anyMatch((p) -> (p.checkOwnContinent(continentC))));
    }

    /**
     * Test for army placement: Valid case
     *
     * @throws com.risk.models.RiskModel.ArmyPlacementImpossible
     */
    @Test
    public void testPlaceArmy() throws RiskModel.ArmyPlacementImpossible {
        PlayerModel player = this.riskModel.getCurrentPlayer();
        TerritoryModel terr = this.mapModel.getTerritoryByName("TerritoryB");

        player.setNumArmiesAvailable(1);
        player.addCountryOwned(terr);
        terr.setNumArmies(0);

        riskModel.placeArmy(player, terr);

        assertEquals(1, terr.getNumArmies());
        assertEquals(1, player.getNbArmiesOwned());
        assertEquals(0, player.getNbArmiesAvailable());
        assertEquals(
                "Player 1 place one army on territory TerritoryB",
                dummyObserver.getMessage()
        );

    }

    /**
     * Test for army placement: Player does not have enough armies available
     *
     * @throws com.risk.models.RiskModel.ArmyPlacementImpossible
     */
    @Test
    public void testPlaceArmyNoArmyAvailable()
            throws RiskModel.ArmyPlacementImpossible {
        PlayerModel player = this.riskModel.getCurrentPlayer();
        TerritoryModel terr = this.mapModel.getTerritoryByName("TerritoryB");
        ArmyPlacementImpossible exception = null;

        player.setNumArmiesAvailable(0);
        player.addCountryOwned(terr);
        terr.setNumArmies(0);

        try {
            riskModel.placeArmy(player, terr);
        } catch (ArmyPlacementImpossible ex) {
            exception = ex;
        }

        assertEquals(0, terr.getNumArmies());
        assertEquals(0, player.getNbArmiesOwned());
        assertEquals(0, player.getNbArmiesAvailable());
        assertEquals(null, dummyObserver.getMessage());
        assertNotEquals(null, exception);
        if (exception != null) {
            assertEquals(
                    "You have no armies left to deploy !",
                    exception.getReason()
            );
        }
    }

    /**
     * Test for army placement: Player does not own the territory
     *
     * @throws com.risk.models.RiskModel.ArmyPlacementImpossible
     */
    @Test
    public void testPlaceTerritoryNotOwned()
            throws RiskModel.ArmyPlacementImpossible {
        PlayerModel player = this.riskModel.getCurrentPlayer();
        TerritoryModel terr = this.mapModel.getTerritoryByName("TerritoryB");
        ArmyPlacementImpossible exception = null;

        player.setNumArmiesAvailable(1);
        terr.setNumArmies(0);

        try {
            riskModel.placeArmy(player, terr);
        } catch (ArmyPlacementImpossible ex) {
            exception = ex;
        }

        assertEquals(0, terr.getNumArmies());
        assertEquals(1, player.getNbArmiesOwned());
        assertEquals(1, player.getNbArmiesAvailable());
        assertEquals(null, dummyObserver.getMessage());
        assertNotEquals(null, exception);
        if (exception != null) {
            assertEquals(
                    "You don't own this country !",
                    exception.getReason()
            );
        }
    }

    /**
     * Class implementing Observer to test RiskModel is notifying observers
     * correctly
     */
    class DummyObserver implements Observer {

        String message;

        @Override
        public void update(Observable o, Object o1) {
            if (o1 instanceof LogEvent) {
                message = ((LogEvent) o1).toString();
            }
        }

        public String getMessage() {
            return message;
        }
    }
}
