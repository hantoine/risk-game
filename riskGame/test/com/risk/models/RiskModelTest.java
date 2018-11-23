/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.RiskModel.ArmyPlacementImpossible;
import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * TestCLass for RiskModel
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
     * Test for InitializePlayersArmies with 3 players
     */
    @Test
    public void testInitializePlayersArmies3() {
        riskModel.initializePlayersArmies();

        assertTrue(riskModel.getPlayerList().stream()
                .allMatch((p) -> p.getNbArmiesAvailable() == 35));
    }

    /**
     * Test for InitializePlayersArmies with 3 players
     */
    @Test
    public void testInitializePlayersArmies4() {
        riskModel.addPlayerToPlayerList("player 4", Color.yellow, true);
        riskModel.initializePlayersArmies();

        assertTrue(riskModel.getPlayerList().stream()
                .allMatch((p) -> p.getNbArmiesAvailable() == 30));
    }

    /**
     * Test for InitializePlayersArmies with 3 players
     */
    @Test
    public void testInitializePlayersArmies5() {
        riskModel.addPlayerToPlayerList("player 4", Color.yellow, true);
        riskModel.addPlayerToPlayerList("player 5", Color.black, true);
        riskModel.initializePlayersArmies();

        assertTrue(riskModel.getPlayerList().stream()
                .allMatch((p) -> p.getNbArmiesAvailable() == 25));
    }

    /**
     * Test for InitializePlayersArmies with 3 players
     */
    @Test
    public void testInitializePlayersArmies6() {
        riskModel.addPlayerToPlayerList("player 4", Color.yellow, true);
        riskModel.addPlayerToPlayerList("player 5", Color.black, true);
        riskModel.addPlayerToPlayerList("player 6", Color.ORANGE, true);
        riskModel.initializePlayersArmies();

        assertTrue(riskModel.getPlayerList().stream()
                .allMatch((p) -> p.getNbArmiesAvailable() == 20));
    }

    /**
     * Test for InitializePlayersArmies with 4 players
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
        player.addTerritoryOwned(terr);
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
        player.addTerritoryOwned(terr);
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
                    "You don't own this territory !",
                    exception.getReason()
            );
        }
    }

    /**
     * Test the startGane
     */
    @Test
    public void testStartGame() {
        int expectedNbArmies
                = PlayerModel.getNbInitialArmies(
                        riskModel.getPlayerList().size()
                );
        this.riskModel.startGame();
        assertEquals(null, riskModel.getWinningPlayer());
        assertEquals(GamePhase.STARTUP, riskModel.getPhase());
        assertTrue(
                this.mapModel.getTerritories().stream().allMatch(
                        (t) -> (t.getOwner() != null)
                )
        );
        assertEquals("The game starts", dummyObserver.getMessage());
        assertTrue(riskModel.getCurrentPlayer().isCurrentPlayer());
        riskModel.getPlayerList().forEach((p) -> {
            assertEquals(
                    expectedNbArmies + p.getNbTerritoriesOwned(),
                    p.getNbArmiesOwned()
            );
        }
        );
    }

    /**
     * Test of removePlayer: when only one player left the game is over
     */
    @Test
    public void testRemovePlayerEndOfGameDetected() {
        riskModel.removePlayer(riskModel.getPlayerList().get(0));
        riskModel.removePlayer(riskModel.getPlayerList().get(0));

        /*
        only one player left in the game, it should hence be detected the
        winning player detected
         */
        assertTrue(riskModel.getWinningPlayer() != null);
        assertEquals("Player 3 win the game", this.dummyObserver.getMessage());

    }

    /**
     * Test of checkForDeadPlayers
     */
    @Test
    public void testCheckForDeadPlayers() {
        this.riskModel.nextTurn();
        this.riskModel.nextTurn();

        PlayerModel currentPlayer = riskModel.getCurrentPlayer();
        PlayerModel playerToKill = this.riskModel.getPlayerList().get(1);

        this.riskModel.getPlayerList().get(0).addTerritoryOwned(
                this.mapModel.getTerritoryByName("TerritoryA"));
        this.riskModel.getPlayerList().get(2).addTerritoryOwned(
                this.mapModel.getTerritoryByName("TerritoryB"));

        riskModel.checkForDeadPlayers();

        assertEquals(2, riskModel.getPlayerList().size());
        assertFalse(riskModel.getPlayerList().contains(playerToKill));
        assertSame(currentPlayer, riskModel.getCurrentPlayer());
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
