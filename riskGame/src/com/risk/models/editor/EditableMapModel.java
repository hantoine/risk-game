package com.risk.models.editor;

/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
import com.risk.models.MapConfig;
import com.risk.observers.UpdateTypes;
import com.risk.models.ContinentModel;
import com.risk.models.MapModel;
import com.risk.models.TerritoryModel;
import com.risk.observers.MapModelObservable;
import com.risk.observers.MapModelObserver;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * MAP object temporary model to replace the former later
 *
 * @author timot
 */
public final class EditableMapModel implements MapModelObservable {

    MapModel map;
    private LinkedList<MapModelObserver> observers;

    /**
     * Constructor
     */
    public EditableMapModel() {
        this.map = new MapModel();
        observers = new LinkedList<>();
        addContinent();
    }

    /**
     * Add an edge between two vertices (territories)
     *
     * @param territoryName
     * @param neighbour
     */
    public void addLink(String territoryName, String neighbour) {
        //get models
        TerritoryModel neighbourModel = this.getGraphTerritories().get(neighbour);
        TerritoryModel territoryModel = this.getGraphTerritories().get(territoryName);

        if (neighbourModel.getAdj().contains(neighbourModel)) {
            return;
        }

        //add neighbours
        this.getGraphTerritories().get(territoryName).addNeighbour(neighbourModel);
        this.getGraphTerritories().get(neighbour).addNeighbour(territoryModel);

        String[] newLink = {territoryName, neighbour};
        notifyObservers(UpdateTypes.ADD_LINK, newLink);
    }

    public void removeLink(String territoryName, String neighbour) {
        //get models
        TerritoryModel neighbourModel = this.getGraphTerritories().get(neighbour);
        TerritoryModel territoryModel = this.getGraphTerritories().get(territoryName);

        //remove neighbours
        this.getGraphTerritories().get(territoryName).removeNeighbour(neighbourModel);
        this.getGraphTerritories().get(neighbour).removeNeighbour(territoryModel);

        //remove from the view
        String[] link = {territoryName, neighbour};
        notifyObservers(UpdateTypes.REMOVE_LINK, link);
    }

    /**
     * Get one territory model instance by its name
     *
     * @param territoryName
     * @return
     */
    public TerritoryModel getTerritoryByName(String territoryName) {
        TerritoryModel target = this.getGraphTerritories().get(territoryName);
        return target;
    }

    /**
     * Check if a given string is in a given list of strings
     *
     * @param list
     * @param element
     * @return
     */
    public boolean isInList(String[] list, String element) {
        return Stream.of(list).anyMatch(x -> x.equals(element));
    }

    /**
     * Get a new name when creating a new element (territory or continent)
     *
     * @param continent
     * @return
     */
    protected String getNewName(boolean continent) {
        int i = 0;
        String prefix = new String();
        String newName = new String();
        String[] nameList;

        if (continent) {
            prefix = "Continent";
            nameList = this.getContinentList();
        } else {
            prefix = "Territory";
            nameList = this.getTerritoryList();
        }

        newName = prefix + Integer.toString(i);
        while (isInList(nameList, newName)) {
            i += 1;
            newName = prefix + Integer.toString(i);
        }

        return newName;
    }

    /**
     * Add a new continent to the model and notify the observers to change the
     * view
     *
     * @return
     */
    public boolean addContinent() {
        String newName = getNewName(true);

        ContinentModel newContinent = new ContinentModel(newName, 1);
        getGraphContinents().put(newName, newContinent);
        notifyObservers(UpdateTypes.ADD_CONTINENT, newName);
        return true;
    }

    public String getAvailableContinent(TerritoryModel territory) {
        LinkedList<TerritoryModel> neighbours = territory.getAdj();
        if (neighbours != null && !neighbours.isEmpty()) {
            return neighbours.get(0).getContinentName();
        } else {
            return null;
        }
    }

    public MapModel getInternalMap() {
        return map;
    }

    /**
     * Remove a continent and notify the observers
     *
     * @param continentName
     */
    public boolean removeContinent(String continentName) {
        ContinentModel continentToDel = this.getGraphContinents().get(continentName);
        if (continentToDel == null) {
            return false;
        }
        LinkedList<TerritoryModel> members = continentToDel.getMembers();

        for (TerritoryModel member : members) {
            member.setContinentName(getAvailableContinent(member));
        }

        getGraphContinents().remove(continentName);
        int nbContinents = this.getContinentList().length;
        System.out.println("nb continents : " + Integer.toString(nbContinents));
        notifyObservers(UpdateTypes.REMOVE_CONTINENT, continentName);
        return true;
    }

    /**
     * Add a new territory to the model and notify the observers
     *
     * @param posX
     * @param posY
     */
    public boolean addTerritory(int posX, int posY) {
        String newName = getNewName(false);

        //create territory
        TerritoryModel newTerritory = new TerritoryModel(newName, posX, posY);

        //add it to a continent
        String continentName = this.getGraphContinents().entrySet().iterator().next().getKey();
        newTerritory.setContinentName(continentName);
        this.getGraphContinents().get(continentName).setMember(newTerritory);

        //add territory to list
        this.getGraphTerritories().put(newName, newTerritory);

        //update views
        notifyObservers(UpdateTypes.ADD_TERRITORY, newTerritory);
        return true;
    }

    /**
     * Remove a territory and notify the observers
     *
     * @param territoryName
     */
    public void removeTerritory(String territoryName) {

        TerritoryModel territoryToDel = this.getGraphTerritories().get(territoryName);

        //update continent owning the territory
        String continent = territoryToDel.getContinentName();
        this.getGraphContinents().get(continent).removeMember(territoryToDel);

        //remove territory from other territories' lists of neighbours
        LinkedList<TerritoryModel> neighbours = new LinkedList<>(territoryToDel.getAdj());
        for (TerritoryModel neighbour : neighbours) {
            neighbour.removeNeighbour(territoryToDel);

            //remove from the view
            String[] link = {territoryToDel.getName(), neighbour.getName()};
            notifyObservers(UpdateTypes.REMOVE_LINK, link);
        }

        //delete the territory
        this.getGraphTerritories().remove(territoryName);
        notifyObservers(UpdateTypes.REMOVE_TERRITORY, territoryName);
    }

    /**
     * Update a territory model and update the views
     *
     * @param data
     */
    public void updateTerritory(Map<String, String> data) {
        //get data
        String formerName = data.get("name");
        String newName = data.get("newName");
        String newContinent = data.get("continent");

        //get territory to be modified
        TerritoryModel modifiedTerritory = this.getGraphTerritories().remove(formerName);

        //modify territory
        modifiedTerritory.setName(newName);
        modifiedTerritory.setContinentName(newContinent);
        this.getGraphContinents().get(newContinent).setMember(modifiedTerritory);

        //replace the old entry by the updated one
        this.getGraphTerritories().put(newName, modifiedTerritory);

        notifyObservers(UpdateTypes.UPDATE_TERRITORY_NAME, data);
    }

    public void updateContinent(Map<String, String> data) {
        String formerName = data.get("name");
        String newName = data.get("newName");
        int bonusScore = Integer.parseInt(data.get("bonusScore"));

        //update continent
        ContinentModel continentToModify = this.getGraphContinents().remove(formerName);
        continentToModify.setName(data.get("newName"));
        continentToModify.setBonusScore(bonusScore);

        getGraphContinents().put(newName, continentToModify);

        //update name of the continent in all members of the continent
        if (!formerName.equals(newName)) {
            LinkedList<TerritoryModel> members = continentToModify.getMembers();
            for (TerritoryModel member : members) {
                member.setContinentName(newName);
            }
        }

        notifyObservers(UpdateTypes.UPDATE_CONTINENT, data);
    }

    @Override
    /**
     * Add a new view that will be informed of changes in the model to update
     * itself
     */
    public void addObserver(MapModelObserver newObserver) {
        observers.add(newObserver);
    }

    @Override
    /**
     * Notify the views that a change occurred in the model so that they update
     * themselves
     */
    public void notifyObservers(UpdateTypes updateType, Object object) {
        for (MapModelObserver observer : observers) {
            observer.update(updateType, object);
        }
    }

    /**
     * Getter of the image attribute
     *
     * @return the image
     */
    public BufferedImage getImage() {
        return this.map.getImage();
    }

    public boolean isInDimList(Dimension[] list, Dimension element) {
        return Stream.of(list).anyMatch(x -> x.equals(element));
    }

    /**
     * Check if all territories are inside the map image and move them if it is
     * not the case
     *
     * @param width
     * @param height
     * @param buttonDims
     */
    public void checkTerritoriesPositions(int width, int height, Dimension buttonDims) {
        int x, y;
        LinkedList<Dimension> verifiedValues = new LinkedList<>();

        for (Map.Entry<String, TerritoryModel> territoryEntry : this.getGraphTerritories().entrySet()) {
            TerritoryModel entryValue = territoryEntry.getValue();
            x = entryValue.getPositionX();
            y = entryValue.getPositionY();

            if (y > height) {
                y = height - (int) (buttonDims.height / 2);
            }
            if (x > width) {
                x = width - (int) (buttonDims.width / 2);
            }

            Dimension newDim = new Dimension(x, y);
            if (verifiedValues != null) {
                Dimension[] dimArray = verifiedValues.toArray(new Dimension[verifiedValues.size()]);
                if (isInDimList(dimArray, newDim)) {
                    while (isInDimList(dimArray, newDim)) {
                        x -= 1;
                        y -= 1;
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
     *
     * @param image the image to set
     * @param buttonDims Dimension of buttons for auto-adjust
     */
    public void setImage(BufferedImage image, Dimension buttonDims) {
        this.map.setImage(image);
        notifyObservers(UpdateTypes.UPDATE_BACKGROUND_IMAGE, image);
        checkTerritoriesPositions(image.getWidth(), image.getHeight(), buttonDims);
    }

    /**
     * Getter of the getGraphContinents() attribute
     *
     * @return the getGraphContinents()
     */
    public HashMap<String, ContinentModel> getGraphContinents() {
        return this.map.getGraphContinents();
    }

    /**
     * Getter of the graphTerritories attribute
     *
     * @return the graphTerritories
     */
    public HashMap<String, TerritoryModel> getGraphTerritories() {
        return this.map.getGraphTerritories();
    }

    /**
     * Return an array containing all the names of the continents
     *
     * @return
     */
    public String[] getContinentList() {
        Set<String> continentsList = this.getGraphContinents().keySet();
        String[] continentList = Arrays.copyOf(continentsList.toArray(),
                continentsList.size(),
                String[].class);
        return continentList;
    }

    /**
     * Return an array containing all the names of the territories
     *
     * @return
     */
    public String[] getTerritoryList() {
        Set<String> territorySet = this.getGraphTerritories().keySet();
        String[] territoryArray = Arrays.copyOf(territorySet.toArray(),
                territorySet.size(),
                String[].class);
        return territoryArray;
    }

    /**
     * Get territories that can be neighbours of a given territory passed in
     * parameter
     *
     * @param territoryName
     * @return
     */
    public String[] getPotentialNeighbours(String territoryName) {
        Set<String> territorySet = this.getGraphTerritories().keySet();
        List<String> territoryList = new ArrayList<String>();
        territoryList.addAll(territorySet);
        territoryList.remove(territoryName);

        TerritoryModel territoryModel = this.getGraphTerritories().get(territoryName);
        LinkedList<TerritoryModel> neighbours = territoryModel.getAdj();
        for (TerritoryModel neighbour : neighbours) {
            territoryList.remove(neighbour.getName());
        }

        String[] territoryArray = Arrays.copyOf(territoryList.toArray(),
                territoryList.size(),
                String[].class);
        return territoryArray;
    }

    public void setScrollConfig(String scrollConfig) {
        this.getMapConfig().setScroll(scrollConfig);
    }

    public void setWrapConfig(boolean wrapConfig) {
        this.getMapConfig().setWrap(wrapConfig);
    }

    public void setWarnConfig(boolean warnConfig) {
        this.getMapConfig().setWarn(warnConfig);
    }

    public void setAuthorConfig(String authorName) {
        this.getMapConfig().setAuthor(authorName);
    }

    public void setImagePath(String path) {
        this.getMapConfig().setImagePath(path);
    }

    public MapConfig getMapConfig() {
        return this.map.getConfigurationInfo();
    }

}