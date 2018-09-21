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
public class Board {

    private HashMap<String, String> configurationInfo;
    private BufferedImage image;
    private HashMap<String, Continent> graphContinents;
    private HashMap<String, Country> graphTerritories;

    public Board() {
    }

    public void printBoard(){
        if(this.getGraphTerritories().keySet().size()>0){
            System.out.println("Number of countries: "+this.getGraphTerritories().keySet().size());
            for(String country: this.getGraphTerritories().keySet()){
                System.out.println(country);
                Country auxCountry=this.getGraphTerritories().get(country);
                for(Country c:auxCountry.getAdj()){
                    System.out.println(country+"->"+c.getName());
                }
            }
        }
    }
    
    public boolean connectedGraph(){
        int size=this.getGraphTerritories().keySet().size();
        HashMap<String,Boolean> visited=new HashMap();
        HashMap<String,Country> map=this.getGraphTerritories();
        Map.Entry<String,Country> entry = map.entrySet().iterator().next();
        Country value = entry.getValue();
        
       
        if(size>0){
            visited.put(value.getName(), Boolean.TRUE);
            this.dfsConnected(value,visited);
        }
       
        return(visited.size()==size);
        
    }
    
    public void dfsConnected(Country v, HashMap<String,Boolean> visited){
        visited.put(v.getName(), Boolean.TRUE);
        LinkedList<Country> aux=new LinkedList();
        aux=v.getAdj();
      
        for (Country c : aux) {
            
            if(!(visited.containsKey(c.getName()))){   
                dfsConnected(c, visited);
            }
        }
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