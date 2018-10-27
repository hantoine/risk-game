/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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
    }

    /**
     * Test of assignCoutriesToPlayers method, of class RiskModel.
     */
    @Test
    public void testAssignCoutriesToPlayers() {
        riskModel.assignCoutriesToPlayers();
        Collection<TerritoryModel> territories = mapModel.getGraphTerritories().values();
        territories.stream().forEach((c) -> {
            assertNotNull(c.getOwner());
        });
    }

}
