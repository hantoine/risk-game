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
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
    protected int maxNbContinents;
    protected int maxNbTerritories;

    /**
     * Constructor
     */
    public MapModel2() {
        graphContinents = new HashMap<>();
        graphTerritories = new HashMap<>();
        observers = new LinkedList<>();
        
        this.maxNbContinents = 8;
        this.maxNbTerritories = 25;
        
        addContinent();
    }
    
    /**
     * Get the maximum number of continents we can create
     * @return 
     */
    public int getMaxNbContinents(){
        return this.maxNbContinents;
    }
    
    /**
     * Get the maximum number of territories we can create
     * @return 
     */
    public int getMaxNbTerritories(){
        return this.maxNbTerritories;
    }
    
    /**
     * Add an edge between two vertices (territories)
     * @param territoryName
     * @param neighbour 
     */
    public void addLink(String territoryName, String neighbour){
        //get models
        TerritoryModel neighbourModel = this.graphTerritories.get(neighbour);
        TerritoryModel territoryModel = this.graphTerritories.get(territoryName);
        
        //add neighbours
        this.graphTerritories.get(territoryName).addNeighbour(neighbourModel);
        this.graphTerritories.get(neighbour).addNeighbour(territoryModel);
    }
    
    /**
     * Get one territory model instance by its name
     * @param territoryName
     * @return 
     */
    public TerritoryModel getTerritoryByName(String territoryName){
        TerritoryModel target = this.graphTerritories.get(territoryName);
        return target;
    }
    
    /**
     * Check if a given string is in a given list of strings
     * @param list
     * @param element
     * @return 
     */
    public boolean isInList(String[] list, String element){
        return Stream.of(list).anyMatch(x -> x.equals(element));
    }
    
    /**
     * Get a new name when creating a new element (territory or continent)
     * @param continent
     * @return 
     */
    protected String getNewName(boolean continent){
        int i=0;
        String prefix=new String();
        String newName=new String();
        String[] nameList;
        
        if(continent){
            prefix = "Continent";
            nameList=this.getContinentList();
        }
        else{
            prefix = "Territory";
            nameList=this.getTerritoryList();
        }   
        
        newName = prefix+Integer.toString(i);
        while(isInList(nameList, newName)){
            i+=1;
            newName = prefix+Integer.toString(i);
        }
        
        return newName;
    }
    
    /**
     * Add a new continent to the model and notify the observers to change the view
     * @return 
     */
    public boolean addContinent(){
        System.out.println("nb continents : "+Integer.toString(this.getContinentList().length));
        if (this.maxNbContinents <= this.getContinentList().length)
            return false;
        
        String newName = getNewName(true);
        
        ContinentModel newContinent = new ContinentModel(newName, 1);
        graphContinents.put(newName, newContinent);
        notifyObservers(UpdateTypes.ADD_CONTINENT, newName);
        return true;
    }
    
    /**
     * Remove a continent and notify the observers
     * @param continentName 
     */
    public boolean removeContinent(String continentName){
        if (this.getContinentList().length==1)
            return false;
        graphContinents.remove(continentName);
        int nbContinents = this.getContinentList().length;
        System.out.println("nb continents : "+Integer.toString(nbContinents));
        notifyObservers(UpdateTypes.REMOVE_CONTINENT, continentName);
        return true;
    }
    
    /**
     * Add a new territory to the model and notify the observers
     * @param posX
     * @param posY 
     */
    public boolean addTerritory(int posX, int posY){
        if (this.maxNbTerritories <= this.getTerritoryList().length)
            return false;
        
        String newName = getNewName(false);
        
        TerritoryModel newTerritory = new TerritoryModel(newName, posX, posY);
        String continentName = this.graphContinents.entrySet().iterator().next().getKey();
        newTerritory.setContinentName(continentName);
        this.graphTerritories.put(newName, newTerritory);
        notifyObservers(UpdateTypes.ADD_TERRITORY, newTerritory);
        return true;
    }
    
    /**
     * Remove a territory and notify the observers
     * @param countryName 
     */
    public void removeTerritory(String countryName){
        graphTerritories.remove(countryName);
        notifyObservers(UpdateTypes.REMOVE_TERRITORY, countryName);
    }
    
    /**
     * Update a territory model and update the views
     * @param data 
     */
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
        
        notifyObservers(UpdateTypes.UPDATE_TERRITORY_NAME, data);
    }
    
    @Override
    /**
     * Add a new view that will be informed of changes in the model to update itself
     */
    public void addObserver(MapModelObserver newObserver){
        observers.add(newObserver);
    }
    
    @Override
    /**
     * Notify the views that a change occured in the model so that they update themselves
     */
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

    public boolean isInDimList(Dimension[] list, Dimension element){
        return Stream.of(list).anyMatch(x -> x.equals(element));
    }
    
    /**
     * Check if all territories are inside the map image and move them if it is not the case
     * @param width
     * @param height 
     * @param buttonDims 
     */
    public void checkTerritoriesPositions(int width, int height, Dimension buttonDims){
        int x,y;
        LinkedList<Dimension> verifiedValues = new LinkedList<>();
        
        for(Map.Entry<String, TerritoryModel> territoryEntry : this.graphTerritories.entrySet()){
            TerritoryModel entryValue = territoryEntry.getValue();
            x=entryValue.getPositionX();
            y=entryValue.getPositionY();
            
            if(y>height)
                y=height-(int)(buttonDims.height/2);
            if(x>width)
                x=width-(int)(buttonDims.width/2);
            
            Dimension newDim = new Dimension(x,y);
            if(verifiedValues!=null)
            {
                Dimension[] dimArray = verifiedValues.toArray(new Dimension[verifiedValues.size()]);
                if(isInDimList(dimArray, newDim)){
                    while(isInDimList(dimArray, newDim))
                    {
                        x-=1;
                        y-=1;
                    }
                }
            }
            
            entryValue.setPositionX(x);
            entryValue.setPositionY(y);
            notifyObservers(UpdateTypes.UPDATE_TERRITORY_POS, entryValue);
            verifiedValues.add(newDim);
        }
    }
    
    /**
     * Setter of the image attribute
     * @param image the image to set
     */
    public void setImage(BufferedImage image, Dimension buttonDims) {
        this.image = image;
        notifyObservers(UpdateTypes.UPDATE_BACKGROUND_IMAGE, image);
        checkTerritoriesPositions(image.getWidth(), image.getHeight(), buttonDims);
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
    
    /**
     * Get territories that can be neighbours of a given territory passed in parameter
     * @param territoryName
     * @return 
     */
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
