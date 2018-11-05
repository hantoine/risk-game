package com.risk.models;

/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
import com.risk.observable.MapModelObservable;
import com.risk.observable.MapModelObserver;
import com.risk.observable.UpdateTypes;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Stream;

/**
 * MAP object temporary model to replace the former later.
 *
 * @author timot
 */
public final class MapModel extends Observable implements MapModelObservable {

    /**
     * mapConfig configurations of the map like author, wrap, image, and others
     */
    private MapConfig mapConfig;
    /**
     * image the image of the map
     */
    private BufferedImage image;
    /**
     * mapHeight the height of the map
     */
    private int mapHeight;
    /**
     * mapWidth the width of the map
     */
    private int mapWidth;
    /**
     * graphContinents the continents of the map
     */
    private HashMap<String, ContinentModel> graphContinents;
    /**
     * graphTerritories the territories of the map
     */
    private HashMap<String, TerritoryModel> graphTerritories;

    /**
     * List of the observers of the model.
     */
    private LinkedList<MapModelObserver> observers;

    /**
     * Constructor.
     */
    public MapModel() {
        graphContinents = new HashMap<>();
        graphTerritories = new HashMap<>();
        mapConfig = new MapConfig();
        image = null;
        observers = new LinkedList<>();
        addDefaultContinent();
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
        boolean a = this.isConnectedGraph();
        boolean b = this.getGraphContinents().values().stream().allMatch((c) -> (c.check()));
        if (!b) {
            System.out.println("graph continents failure");
        } else if (!a) {
            System.out.println("isConnected failure");
        }
        return (a && b);
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
    HashMap<String, ContinentModel> getGraphContinents() {
        return graphContinents;
    }

    /**
     * Setter of the graphContinents attribute
     *
     * @param graphContinents HashMap containing all continents of the map with
     * their name as a key
     */
    void setGraphContinents(HashMap<String, ContinentModel> graphContinents) {
        this.graphContinents = graphContinents;

        setChanged();
        notifyObservers();
    }

    /**
     * Get one continent model instance by its name
     *
     * @param continentName name of continent
     * @return the target name get from the method
     */
    public ContinentModel getContinentByName(String continentName) {
        return graphContinents.get(continentName);
    }

    /**
     * Getter of the graphTerritories attribute
     *
     * @return the graphTerritories, a HashMap containing all continents of the
     * map with their name as a key
     */
    HashMap<String, TerritoryModel> getGraphTerritories() {
        return graphTerritories;
    }

    /**
     * Get one territory model instance by its name
     *
     * @param territoryName name of territory
     * @return the target name get from the method
     */
    public TerritoryModel getTerritoryByName(String territoryName) {
        return graphTerritories.get(territoryName);
    }

    /**
     * Return the list of territories in this map
     *
     * @return the list of territories in this map
     */
    public Collection<TerritoryModel> getTerritories() {
        return Collections.unmodifiableCollection(graphTerritories.values());
    }

    /**
     * Return the list of continents in this map
     *
     * @return the list of territories in this map
     */
    public Collection<ContinentModel> getContinents() {
        return Collections.unmodifiableCollection(graphContinents.values());
    }

    /**
     * Setter of the graphTerritories attribute
     *
     * @param graphTerritories HashMap containing all territories of the map
     * with their name as a key
     */
    void setGraphTerritories(HashMap<String, TerritoryModel> graphTerritories) {
        this.graphTerritories = graphTerritories;

        setChanged();
        notifyObservers();
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

        setChanged();
        notifyObservers(true);
    }

    /**
     * Setter of the mapHeight attribute
     *
     * @param mapHeight the height of the map
     */
    public void setMapHeight(int mapHeight) {
        if (this.getImage() == null) {
            this.mapHeight = mapHeight;
        }

        setChanged();
        notifyObservers(true);
    }

    /**
     * Setter of the mapWith attribute
     *
     * @param mapWidth the width of the map
     */
    public void setMapWidth(int mapWidth) {
        if (this.getImage() == null) {
            this.mapWidth = mapWidth;
        }

        setChanged();
        notifyObservers(true);
    }

    /**
     * It returns the height of the map or the image
     *
     * @return mapHeight
     */
    public int getMapHeight() {
        if (this.getImage() == null) {
            return mapHeight;
        } else {
            return this.getImage().getHeight(null);
        }
    }

    /**
     * It returns the width of the map or the image
     *
     * @return mapWidth
     */
    public int getMapWidth() {
        if (this.getImage() == null) {
            return mapWidth;
        } else {
            return this.getImage().getWidth(null);
        }
    }

    //############################################## methods of editable map model :
    /**
     * Add an edge between two vertices (territories).
     *
     * @param territoryName the name of the territory
     * @param neighbour the name of the neighbor
     */
    public void addLink(String territoryName, String neighbour) {
        //get models
        TerritoryModel neighbourModel = this.getGraphTerritories().get(neighbour);
        TerritoryModel territoryModel = this.getGraphTerritories().get(territoryName);

        if (territoryModel.getAdj().contains(neighbourModel) || neighbourModel.getAdj().contains(territoryModel)) { //undirected graph
            return;
        }

        //add neighbours
        this.getGraphTerritories().get(territoryName).addNeighbour(neighbourModel);
        this.getGraphTerritories().get(neighbour).addNeighbour(territoryModel);

        String[] newLink = {territoryName, neighbour};
        notifyObserversCustom(UpdateTypes.ADD_LINK, newLink);

        setChanged();
        notifyObservers();
    }

    /**
     * Remove a link between two vertices.
     *
     * @param territoryName name of territory
     * @param neighbour name of the neighbor
     */
    public void removeLink(String territoryName, String neighbour) {
        //get models
        TerritoryModel neighbourModel = this.getGraphTerritories().get(neighbour);
        TerritoryModel territoryModel = this.getGraphTerritories().get(territoryName);

        //remove neighbours
        this.getGraphTerritories().get(territoryName).removeNeighbour(neighbourModel);
        this.getGraphTerritories().get(neighbour).removeNeighbour(territoryModel);

        //remove from the view
        String[] link = {territoryName, neighbour};
        notifyObserversCustom(UpdateTypes.REMOVE_LINK, link);

        setChanged();
        notifyObservers();
    }

    /**
     * Get a new name when creating a new element (territory or continent)
     *
     * @param continent boolean that the continent is in the list or not
     * @return the new name of the continent
     */
    protected String getNewName(boolean continent) {
        int i = 0;
        String prefix = new String();
        String newName = new String();
        List<String> nameList;

        if (continent) {
            prefix = "Continent";
            nameList = this.getContinentList();
        } else {
            prefix = "Territory";
            nameList = this.getTerritoryList();
        }

        newName = prefix + Integer.toString(i);
        while (nameList.contains(newName)) {
            i += 1;
            newName = prefix + Integer.toString(i);
        }

        return newName;
    }

    /**
     * Add a new continent to the model using addContinent.
     * This time the continent is a default one as we must have 
     * at least one continent. That is why there is a call to getNewName 
     * and the default continentBonus is set to 1.
     *
     * @return whether the continent is added or not
     */
    public boolean addDefaultContinent() {
        String newName = getNewName(true);
        int continentBonus = 1;
        addContinent(newName, continentBonus);
        return true;
    }

    /**
     * Add a new continent to the model and notify the observers to change the
     * view
     * @param continentName
     * @param continentBonus
     * @return 
     */
    public boolean addContinent(String continentName, int continentBonus) {
        ContinentModel newContinent = new ContinentModel(continentName, continentBonus);
        getGraphContinents().put(continentName, newContinent);
        notifyObserversCustom(UpdateTypes.ADD_CONTINENT, continentName);

        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * Find a continent that can be assigned to a given territory.
     * To that aim, this function looks at the neighbors of the given 
     * territory and pick a random continent from them.
     * 
     * @param territory
     * @return 
     */
    public String getAvailableContinent(TerritoryModel territory) {
        List<TerritoryModel> neighbours = territory.getAdj();
        if (neighbours != null && !neighbours.isEmpty()) {
            return neighbours.get(0).getContinentName();
        } else {
            return null;
        }
    }

    /**
     * Clear the map being edited
     */
    public void clearMap() {
        List<String> territoryList = this.getTerritoryList();
        List<String> continentList = this.getContinentList();

        //delete components
        for (String territoryName : territoryList) {
            this.removeTerritory(territoryName);
        }
        for (String continentName : continentList) {
            this.removeContinent(continentName);
        }

        //add first continent
        this.addDefaultContinent();

        //reset map configuration
        this.setScrollConfig("none");
        this.setWarnConfig(false);
        this.setWrapConfig(false);
        this.setAuthorConfig("New Author");
        this.setImage(null, new Dimension(200, 50));
    }

    /**
     * Remove a continent and notify the observers
     *
     * @param continentName the name of the continent
     * @return return if the continent is removed or not
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
        int nbContinents = this.getContinentList().size();
        System.out.println("nb continents : " + Integer.toString(nbContinents));
        notifyObserversCustom(UpdateTypes.REMOVE_CONTINENT, continentName);

        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * Add a new territory to the model and notify the observers
     *
     * @param posX the coordinate x
     * @param posY the coordinate Y
     * @return if the territory is added or not
     */
    public boolean addTerritory(int posX, int posY) {
        String newName = getNewName(false);

        //create territory
        TerritoryModel newTerritory = new TerritoryModel(newName, posX, posY);

        //add it to a continent
        String continentName = this.getGraphContinents().entrySet().iterator().next().getKey();
        newTerritory.setContinentName(continentName);
        this.getGraphContinents().get(continentName).addMember(newTerritory);

        //add territory to list
        this.getGraphTerritories().put(newName, newTerritory);

        //update views
        notifyObserversCustom(UpdateTypes.ADD_TERRITORY, newTerritory);

        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * Add territory from file.
     *
     * @param posX the coordinate x
     * @param posY the coordinate y
     * @param newName the new terriroty's name
     * @param continentName the name of the continent
     * @return if the territory is loaded or not
     */
    public boolean loadTerritory(int posX, int posY, String newName, String continentName) {

        //create territory
        TerritoryModel newTerritory = new TerritoryModel(newName, posX, posY);

        //add it to its continent
        newTerritory.setContinentName(continentName);
        this.getGraphContinents().get(continentName).addMember(newTerritory);

        //add territory to list
        this.getGraphTerritories().put(newName, newTerritory);

        //update views
        notifyObserversCustom(UpdateTypes.ADD_TERRITORY, newTerritory);

        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * Remove a territory and notify the observers
     *
     * @param territoryName the name of the territory
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
            notifyObserversCustom(UpdateTypes.REMOVE_LINK, link);
        }

        //delete the territory
        this.getGraphTerritories().remove(territoryName);
        notifyObserversCustom(UpdateTypes.REMOVE_TERRITORY, territoryName);

        setChanged();
        notifyObservers();
    }

    /**
     * Update a territory model and update the views.
     *
     * @param data contains information to update the territory.
     */
    public void updateTerritory(Map<String, String> data) {
        //get data
        String formerName = data.get("name");
        String newName = data.get("newName");
        String formerContinent = data.get("formerContinent");
        String newContinent = data.get("continent");

        //get territory to be modified
        TerritoryModel modifiedTerritory = this.getGraphTerritories().get(formerName);

        //modify territory
        modifiedTerritory.setName(newName);
        modifiedTerritory.setContinentName(newContinent);

        if (!formerContinent.equals(newContinent)) {
            this.graphContinents.get(formerContinent).removeMember(this.getTerritoryByName(newName));
            this.graphContinents.get(newContinent).addMember(modifiedTerritory);
        }

        //replace the old entry by the updated one
        //this.getGraphTerritories().put(newName, modifiedTerritory);
        notifyObserversCustom(UpdateTypes.UPDATE_TERRITORY_NAME, data);

        setChanged();
        notifyObservers();
    }

    /**
     * Update a continent model and update the views.
     *
     * @param data contains information to update the continent.
     */
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

        notifyObserversCustom(UpdateTypes.UPDATE_CONTINENT, data);

        setChanged();
        notifyObservers();
    }

    /**
     * Add a new view that will be informed of changes in the model to update
     * itself.
     *
     * @param newObserver the new map model observer
     */
    @Override
    public void addObserverCustom(MapModelObserver newObserver) {
        observers.add(newObserver);
    }

    /**
     * Notify the views that a change occurred in the model so that they update
     * themselves.
     *
     * @param updateType type of the update.
     * @param object data to update the observers.
     */
    @Override
    public void notifyObserversCustom(UpdateTypes updateType, Object object) {
        for (MapModelObserver observer : observers) {
            observer.update(updateType, object);
        }
    }

    /**
     * Check if an Dimension element is in the list.
     *
     * @param list list of the dimension
     * @param element the element inside of the link
     * @return a boolean value to tell if the element is in the list.
     * @see Dimension
     */
    public boolean isInDimList(Dimension[] list, Dimension element) {
        return Stream.of(list).anyMatch(x -> x.equals(element));
    }

    /**
     * Check if all territories are inside the map image and move them if it is
     * not the case
     *
     * @param width the width of the map
     * @param height the height of the map
     * @param buttonDims Dimension of the buttons on the map.
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
            notifyObserversCustom(UpdateTypes.UPDATE_TERRITORY_POS, entryValue);
            verifiedValues.add(newDim);

            setChanged();
            notifyObservers();
        }
    }

    /**
     * Setter of the image attribute
     *
     * @param image the image to set
     * @param buttonDims Dimension of buttons for auto-adjust
     */
    public void setImage(BufferedImage image, Dimension buttonDims) {
        this.setImage(image);
        notifyObserversCustom(UpdateTypes.UPDATE_BACKGROUND_IMAGE, image);
        if (image != null) {
            checkTerritoriesPositions(image.getWidth(), image.getHeight(), buttonDims);
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Return an array containing all the names of the continents
     *
     * @return the list of the continent
     */
    public List<String> getContinentList() {
        return Collections.unmodifiableList(
                new ArrayList<>(this.getGraphContinents().keySet())
        );
    }

    /**
     * Return an array containing all the names of the territories
     *
     * @return the array of the territory
     */
    public List<String> getTerritoryList() {
        return Collections.unmodifiableList(
                new ArrayList<>(this.getGraphTerritories().keySet())
        );
    }

    /**
     * Get territories that can be neighbours of a given territory passed in
     * parameter
     *
     * @param territoryName the name of the territory
     * @return the array of the territory
     */
    public String[] getPotentialNeighbours(String territoryName) {
        Set<String> territorySet = this.getGraphTerritories().keySet();
        List<String> territoryList = new ArrayList<>();
        territoryList.addAll(territorySet);
        territoryList.remove(territoryName);

        TerritoryModel territoryModel = this.getGraphTerritories().get(territoryName);
        List<TerritoryModel> neighbours = territoryModel.getAdj();
        for (TerritoryModel neighbour : neighbours) {
            territoryList.remove(neighbour.getName());
        }

        String[] territoryArray = Arrays.copyOf(territoryList.toArray(),
                territoryList.size(),
                String[].class);
        return territoryArray;
    }

    /**
     * Setter of the scroll configuration parameter of the map.
     *
     * @param scrollConfig the string of the config
     */
    public void setScrollConfig(String scrollConfig) {
        this.getConfigurationInfo().setScroll(scrollConfig);

        setChanged();
        notifyObservers();
    }

    /**
     * Setter of the wrap configuration parameter of the map.
     *
     * @param wrapConfig the boolean of the config
     */
    public void setWrapConfig(boolean wrapConfig) {
        this.mapConfig.setWrap(wrapConfig);

        setChanged();
        notifyObservers();
    }

    /**
     * Setter of the warn configuration parameter of the map.
     *
     * @param warnConfig the boolean of warning config
     */
    public void setWarnConfig(boolean warnConfig) {
        this.mapConfig.setWarn(warnConfig);

        setChanged();
        notifyObservers();
    }

    /**
     * Setter of the author of the map.
     *
     * @param authorName the author of the map
     */
    public void setAuthorConfig(String authorName) {
        this.mapConfig.setAuthor(authorName);

        setChanged();
        notifyObservers();
    }

    /**
     * Setter of the path to the image of background
     *
     * @param path the path of the img
     */
    public void setImagePath(String path) {
        this.mapConfig.setImagePath(path);

        setChanged();
        notifyObservers();
    }
}
