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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        addContinent();
    }
    
    public void addLink(String territoryName, String neighbour){
        TerritoryModel neighbourModel = this.graphTerritories.get(neighbour);
        this.graphTerritories.get(territoryName).addNeighbour(neighbourModel);
    }
    
    public TerritoryModel getTerritoryByName(String territoryName){
        TerritoryModel target = this.graphTerritories.get(territoryName);
        return target;
    }
    
    public void addContinent(){
        String newName = "Continent"+Integer.toString(graphContinents.size());
        ContinentModel newContinent = new ContinentModel(newName, 1);
        graphContinents.put(newName, newContinent);
        notifyObservers(UpdateTypes.ADD_CONTINENT, newName);
    }
    
    public void removeContinent(String continentName){
        graphContinents.remove(continentName);
        notifyObservers(UpdateTypes.REMOVE_CONTINENT, continentName);
    }
    
    public void addTerritory(int posX, int posY){
        String newName = "Country"+Integer.toString(this.graphTerritories.size());
        TerritoryModel newTerritory = new TerritoryModel(newName, posX, posY);
        newTerritory.setContinentName(this.graphContinents.entrySet().iterator().next().getKey());
        this.graphTerritories.put(newName, newTerritory);
        notifyObservers(UpdateTypes.ADD_TERRITORY, newTerritory);
    }
    
    public void removeTerritory(String countryName){
        graphTerritories.remove(countryName);
        notifyObservers(UpdateTypes.REMOVE_TERRITORY, countryName);
    }
    
    public void updateTerritory(Map<String,String> data){
        //get data
        String formerName = data.get("name");
        String newName = data.get("newName");
        String newContinent = data.get("continent");
        
        //get territory to be modified
        TerritoryModel modifiedTerritory = graphTerritories.remove(formerName);
        
        //modify territory
        modifiedTerritory.setName(newName);
        modifiedTerritory.setContinentName(newContinent);
        
        //replace the old entry by the updated one
        graphTerritories.put(newName, modifiedTerritory);
        
        notifyObservers(UpdateTypes.UPDATE_TERRITORY, data);
    }
    
    @Override
    public void addObserver(MapModelObserver newObserver){
        observers.add(newObserver);
    }
    
    @Override
    public void notifyObservers(UpdateTypes updateType, Object object){
        for (MapModelObserver observer : observers){
            observer.update(updateType, object);
        }
    }
    
    /**
     * Getter of the image attribute
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Setter of the image attribute
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        notifyObservers(UpdateTypes.UPDATE_BACKGROUND_IMAGE, image);
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

    /**
     * Return an array containing all the names of the continents
     * @return 
     */
    public String[] getContinentList(){
        Set<String> continentsList = this.getGraphContinents().keySet();
        String[] continentList = Arrays.copyOf(continentsList.toArray(), 
                continentsList.size(),
                String[].class);
        return continentList;
    }
    
    /**
     * Return an array containing all the names of the territories
     * @return 
     */
    public String[] getTerritoryList(){
        Set<String> territorySet = this.getGraphTerritories().keySet();
        String[] territoryArray = Arrays.copyOf(territorySet.toArray(), 
                territorySet.size(),
                String[].class);
        return territoryArray;
    }
    
    
    public String[] getPotentialNeighbours(String territoryName){
        Set<String> territorySet = this.graphTerritories.keySet();
        List<String> territoryList = new ArrayList<String>();
        territoryList.addAll(territorySet);
        territoryList.remove(territoryName);
        
        TerritoryModel territoryModel = this.graphTerritories.get(territoryName);
        LinkedList<TerritoryModel> neighbours = territoryModel.getAdj();
        for(TerritoryModel neighbour:neighbours){
            territoryList.remove(neighbour.getName());
        }
        
        String[] territoryArray = Arrays.copyOf(territoryList.toArray(), 
                territoryList.size(),
                String[].class);
        return territoryArray;
    }
}
