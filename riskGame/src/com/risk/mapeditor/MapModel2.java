package com.risk.mapeditor;

/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.risk.models.ContinentModel;
import com.risk.models.TerritoryModel;
import com.risk.observers.MapModelObservable;
import com.risk.observers.MapModelObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * MAP object
 * temporary model to replace the former later
 * @author timot
 */
public class MapModel2 implements MapModelObservable {

    private BufferedImage image;
    private HashMap<String, ContinentModel> graphContinents;
    private HashMap<String, TerritoryModel> graphTerritories;
    private LinkedList<MapModelObserver> observers;

    /**
     * Constructor
     */
    public MapModel2() {
        graphContinents = new HashMap<>();
        graphTerritories = new HashMap<>();
        observers = new LinkedList<>();
        
    }
    
    public void addContinent(){
        String newName = "Continent"+Integer.toString(graphContinents.size());
        ContinentModel newContinent = new ContinentModel(newName, 1);
        graphContinents.put(newName, newContinent);
        notifyObserver(UpdateTypes.ADD_CONTINENT, newName);
    }
    
    public void removeContinent(String continentName){
        graphContinents.remove(continentName);
        notifyObserver(UpdateTypes.REMOVE_CONTINENT, continentName);
    }
    
    public void addTerritory(int posX, int posY){
        String newName = "Country"+Integer.toString(graphTerritories.size());
        TerritoryModel newTerritory = new TerritoryModel(newName, posX, posY);
        graphTerritories.put(newName, newTerritory);
        notifyObserver(UpdateTypes.ADD_TERRITORY, newTerritory);
    }
    
    public void removeTerritory(String countryName){
        graphTerritories.remove(countryName);
        notifyObserver(UpdateTypes.REMOVE_TERRITORY, countryName);
    }
    
    public void updateTerritory(Map<String,String> data){
        String name = data.get("name");
        String continent = data.get("continent");
    }
    
    @Override
    public void addObserver(MapModelObserver newObserver){
        observers.add(newObserver);
    }
    
    @Override
    public void notifyObserver(UpdateTypes updateType, Object object){
        for (MapModelObserver observer : observers){
            observer.update(updateType, object);
        }
    }
    
    /**
     * Getter of the image attribute
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter of the image attribute
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    /**
     * Getter of the graphContinents attribute
     * @return the graphContinents
     */
    public HashMap<String, ContinentModel> getGraphContinents() {
        return graphContinents;
    }

    /**
     * Getter of the graphTerritories attribute
     * @return the graphTerritories
     */
    public HashMap<String, TerritoryModel> getGraphTerritories() {
        return graphTerritories;
    }

}
