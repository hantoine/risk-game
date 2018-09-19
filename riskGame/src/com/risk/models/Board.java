/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author n_irahol
 */
public class Board {

    private HashMap<String, String> configurationInfo;
    private BufferedImage image;
    private HashMap<String, Continent> graphContinents;
    private HashMap<String, Country> graphTerritories;

    
    
    public Board() {
    }

    
    public HashMap<String, Continent> getGraphContinents() {
        return graphContinents;
    }

    public void setGraphContinents(HashMap<String, Continent> graphContinents) {
        this.graphContinents = graphContinents;
    }

    public HashMap<String, Country> getGraphTerritories() {
        return graphTerritories;
    }

    public void setGraphTerritories(HashMap<String, Country> graphTerritories) {
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
