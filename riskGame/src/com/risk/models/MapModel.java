/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author n_irahol
 */
public class MapModel {

    private HashMap<String, String> configurationInfo;
    private BufferedImage image;
    private HashMap<String, ContinentModel> graphContinents;
    private HashMap<String, TerritoryModel> graphTerritories;

    public MapModel() {
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
     * It calls dfsConnected to validate if the countries in the board represent a connected graph 
     * @return 
     */
    public boolean connectedGraph() {
        int size = this.getGraphTerritories().keySet().size();
        HashMap<String, Boolean> visited = new HashMap();
        HashMap<String, TerritoryModel> map = this.getGraphTerritories();
        Map.Entry<String, TerritoryModel> entry = map.entrySet().iterator().next();
        TerritoryModel value = entry.getValue();

        if (size > 0) {
            visited.put(value.getName(), Boolean.TRUE);
            this.dfsConnected(value, visited);
        }

        return (visited.size() == size);

    }
    
    /**
     * It uses dfs algorithm to validate that you can reach any country from another country
     * @param v
     * @param visited 
     */
    public void dfsConnected(TerritoryModel v, HashMap<String, Boolean> visited) {
        visited.put(v.getName(), Boolean.TRUE);
        LinkedList<TerritoryModel> aux = new LinkedList();
        aux = v.getAdj();

        for (TerritoryModel c : aux) {

            if (!(visited.containsKey(c.getName()))) {
                dfsConnected(c, visited);
            }
        }
    }

    public HashMap<String, ContinentModel> getGraphContinents() {
        return graphContinents;
    }

    public void setGraphContinents(HashMap<String, ContinentModel> graphContinents) {
        this.graphContinents = graphContinents;
    }

    public HashMap<String, TerritoryModel> getGraphTerritories() {
        return graphTerritories;
    }

    public void setGraphTerritories(HashMap<String, TerritoryModel> graphTerritories) {
        this.graphTerritories = graphTerritories;
    }

    public HashMap<String, String> getConfigurationInfo() {
        return configurationInfo;
    }

    public void setConfigurationInfo(HashMap<String, String> configurationInfo) {
        this.configurationInfo = configurationInfo;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
