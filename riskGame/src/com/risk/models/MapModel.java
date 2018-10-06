/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * It represents the board
 *
 * @author n_irahol
 */
public class MapModel {

    private MapConfig mapConfig;
    private BufferedImage image;
    private int mapHeight;
    private int mapWidth;
    private HashMap<String, ContinentModel> graphContinents;
    private HashMap<String, TerritoryModel> graphTerritories;

    /**
     * Constructor
     */
    public MapModel() {
        graphContinents = new HashMap<>();
        graphTerritories = new HashMap<>();
        mapConfig = new MapConfig();
        image=null;

    }

    /**
     * It prints the countries and relationships between them
     */
    public void printBoard() {
        if (this.getGraphTerritories().keySet().size() > 0) {
            System.out.println("Number of countries: " + this.getGraphTerritories().keySet().size());
            for (String country : this.getGraphTerritories().keySet()) {
                System.out.println(country);
                TerritoryModel auxCountry = this.getGraphTerritories().get(country);
                for (TerritoryModel c : auxCountry.getAdj()) {
                    System.out.println(country + "->" + c.getName());
                }
            }
        }
    }

    /**
     * It calls dfsConnected to validate if the countries in the board with the
     * given continents represent a connected graph.
     *
     * @return true if it is a connected graph
     */
    private boolean isConnectedGraph() {
        List<String> visited = new ArrayList<>(this.getGraphTerritories().size());

        Iterator<TerritoryModel> it = this.getGraphTerritories().values().iterator();
        if (it.hasNext()) {
            this.dfsConnected(it.next(), visited);
        }

        return (visited.size() == this.getGraphTerritories().size());
    }

    /**
     * Check that the map is valid
     *
     * @return Returns true if the map is valid
     */
    public boolean isValid() {
        return this.isConnectedGraph()
                && this.getGraphContinents().values().stream().allMatch((c) -> (c.check()));
    }

    /**
     * It uses deep first search algorithm to validate that every territory is
     * reachable
     *
     * @param v a country or vertex in the graph
     * @param visited List of visited territories will be visited
     */
    private void dfsConnected(TerritoryModel v, List<String> visited) {
        visited.add(v.getName());

        v.getAdj().stream()
                .filter((c) -> (!visited.contains(c.getName())))
                .forEach((c) -> {
                    dfsConnected(c, visited);
                });
    }

    /**
     * Getter of the graphContinents attribute
     *
     * @return the graphContinents
     */
    public HashMap<String, ContinentModel> getGraphContinents() {
        return graphContinents;
    }

    /**
     * Setter of the graphContinents attribute
     *
     * @param graphContinents HashMap containing all continents of the map with
     * their name as a key
     */
    public void setGraphContinents(HashMap<String, ContinentModel> graphContinents) {
        this.graphContinents = graphContinents;
    }

    /**
     * Getter of the graphTerritories attribute
     *
     * @return the graphTerritories, a HashMap containing all continents of the
     * map with their name as a key
     */
    public HashMap<String, TerritoryModel> getGraphTerritories() {
        return graphTerritories;
    }

    /**
     * Setter of the graphTerritories attribute
     *
     * @param graphTerritories HashMap containing all territories of the map
     * with their name as a key
     */
    public void setGraphTerritories(HashMap<String, TerritoryModel> graphTerritories) {
        this.graphTerritories = graphTerritories;
    }

    /**
     * Getter of the mapConfig attribute
     *
     * @return the configuration information contained in the map file
     */
    public MapConfig getConfigurationInfo() {
        return mapConfig;
    }

    /**
     * Setter of the mapConfig attribute
     *
     * @param mapConfig the configuration information contained in the map file
     */
    public void setConfigurationInfo(MapConfig mapConfig) {
        this.mapConfig = mapConfig;
    }

    /**
     * Getter of the image attribute
     *
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Setter of the image attribute
     *
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setMapHeight(int mapHeight) {
        if (this.getImage() == null) {
            this.mapHeight = mapHeight;
        }
    }

    public void setMapWidth(int mapWidth) {
        if (this.getImage() == null) {
            this.mapWidth = mapWidth;
        }
    }

    public int getMapHeight() {
        if (this.getImage() == null) {
            return mapHeight;
        } else {
            return this.getImage().getHeight(null);
        }
    }

    public int getMapWidth() {
        if (this.getImage() == null) {
            return mapWidth;
        } else {
            return this.getImage().getWidth(null);
        }
    }

}
