/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.controllers.MapEditorController;
import com.risk.models.ContinentModel;
import com.risk.models.MapModel;
import com.risk.models.TerritoryModel;
import com.risk.observable.MapModelObserver;
import com.risk.observable.UpdateTypes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * View on the map being edited. Part of the map editor.
 *
 * @author timot
 * @see MapModelObserver
 */
public class MapView extends JPanel implements Observer {

    /**
     * Background image of the map being edited.
     */
    protected Image backgroundImage;

    /**
     * List of the buttons representing countries.
     */
    protected HashMap<String, CountryButton2> countriesButtons;

    /**
     * Dimension of the buttons representing territories.
     */
    protected Dimension buttonsDims = new Dimension(100, 20);

    /**
     * *
     * Controller of the map editor
     */
    private MapEditorController controller;

    /**
     * *
     * Current tool in use.
     */
    protected Tools selectedTool;

    /**
     * Links between countries to be rendered calling the repaint method.
     */
    protected HashMap<String, Line2D> links;

    /**
     * Constructor of a map view
     *
     * @param editorController controller of the editor
     */
    public MapView(MapEditorController editorController) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.countriesButtons = new HashMap<>();
        this.links = new HashMap<>();
        this.setBackground(Color.white);
        this.setLayout(null);
        controller = editorController;
        this.addMouseListener(controller.getMapMouseListener());
    }

    /**
     * Getter of the current tool in use
     *
     * @return the current tool in use in the editor
     */
    public Tools getCurrentTool() {
        return this.selectedTool;
    }

    /**
     * Set the new tool to use in the editor
     *
     * @param toolInUse tool which gets the focus.
     */
    public void setCurrentTool(Tools toolInUse) {
        this.selectedTool = toolInUse;
    }

    /**
     * Change the background image of the map
     *
     * @param backgroundImage the img of background
     */
    public void setImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        repaint(); //call to paintComponent
    }

    @Override
    /**
     * To paint the background image and the links between territories into the
     * JPanel
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }

        //links
        Graphics2D g2 = (Graphics2D) g;
        this.links.values().forEach((link) -> {
            g2.draw(link);
        });
    }

    /**
     * Remove a country by name as each country as a unique name. Removing
     * implies deleting the associated button.
     *
     * @param countryName the name of the country
     */
    public void removeTerritory(String countryName) {
        //remove from the list
        CountryButton2 buttonToDelete = countriesButtons.get(countryName);

        if (buttonToDelete != null) {
            this.remove(buttonToDelete);
            this.countriesButtons.remove(countryName);

            //redraw the view
            revalidate();
            repaint();
        }
    }

    /**
     * Getter on the button's dimensions
     *
     * @return the dimension of the buttons
     */
    public Dimension getButtonDimension() {
        return buttonsDims;
    }

    /**
     * Add a new territory on the map.
     *
     * @param posX the x coordinate of the territory position
     * @param posY the y coordinate of the territory position
     * @param newName Name of the new territory
     */
    protected void addTerritory(int posX, int posY, String newName) {
        //add to view
        CountryButton2 newCountryButton = new CountryButton2(posX, posY, newName, buttonsDims);
        this.add(newCountryButton);
        newCountryButton.addMouseListener(controller.getCountryMouseListener());
        newCountryButton.setVisible(true);

        //add to internal list
        countriesButtons.put(newName, newCountryButton);

        //draw the new button
        revalidate();
        repaint();
    }

    /**
     * Update the name of a territory in response to a notification from
     * observable model.
     *
     * @param mapModel
     * @param territoriesInModel
     * @param territoriesInView
     * @param newName
     */
    public void updateTerritoryName(MapModel mapModel, List territoriesInModel, List<String> territoriesInView, String newName) {
        //search for the territory which is in the view and not in the model 
        //(because the territory in the model have been modified already)
        String formerName = null;
        for (String name : territoriesInView) {
            if (!territoriesInModel.contains(name)) {
                formerName = name;
            }
        }
        
        CountryButton2 territoryButton;
        
        if(formerName != null){
            //pop territory button to update
            territoryButton = this.countriesButtons.remove(formerName);
            
            //set new name
            territoryButton.setName(newName);
            
            //push the updated button back into the map
            countriesButtons.put(newName, territoryButton);
        }
    }

    /**
     * Update the position of a button representing a territory in response to a
     * notification from observable model.
     *
     * @param mapModel
     */
    public void updateTerritoryPos(MapModel mapModel) {
        TerritoryModel updatedTerritory = (TerritoryModel) mapModel.getLastUpdate();
        CountryButton2 territoryButtonToUpdate;
        territoryButtonToUpdate = this.countriesButtons.get(updatedTerritory.getName());
        territoryButtonToUpdate.setPosition(updatedTerritory.getPositionX(), updatedTerritory.getPositionY());
    }

    /**
     * Returns the list of neighbors of a given territory given its name
     * @param nameOfUpdatedTerritory
     * @return the list of the neighbors (list of Strings)
     */
    private LinkedList<String> getNeighborsOfTerritory(String nameOfUpdatedTerritory){
        //initialize the list
        LinkedList<String> neighborsInView = new LinkedList();
        
        //get iterator on the links
        Iterator linkIt = this.links.keySet().iterator(); 
        
        //iterate over all links using iterator
        for (int i = 0; i < this.links.keySet().size(); i++) {
            
            //get names of the two territories of the current link
            String existingLinkName = (String) linkIt.next();
            String[] existingNeighbours = existingLinkName.split(";");

            //if one name is the name of the nameOfUpdatedTerritory we add the other name to the list of neighbors of nameOfUpdatedTerritory
            if (existingNeighbours[0].equals(nameOfUpdatedTerritory)) {
                neighborsInView.add(existingNeighbours[1]);
            } else if (existingNeighbours[1].equals(nameOfUpdatedTerritory)) {
                neighborsInView.add(existingNeighbours[0]);
            }
        }
        
        //return the resulting list
        return neighborsInView;
    }
    
    /**
     * Remove a link between two territories in response to a notification from
     * observable model.
     *
     * @param mapModel
     */
    public void updateRemoveLink(MapModel mapModel) {
        String nameOfUpdatedTerritory = ((TerritoryModel) mapModel.getLastUpdate()).getName();

        //get neigbors of nameOfUpdatedTerritory in the view
        LinkedList<String> neighborsInView = getNeighborsOfTerritory(nameOfUpdatedTerritory);

        //get neighbors of nameOfUpdatedTerritory in the model
        List<TerritoryModel> neighborsModelsInModel = ((TerritoryModel) mapModel.getLastUpdate()).getAdj();
        LinkedList<String> neighborsInModel = new LinkedList();
        for (TerritoryModel neighborModel : neighborsModelsInModel) {
            neighborsInModel.add(neighborModel.getName());
        }

        //search for the link to delete:
        
        //for all neighbors in the view
        for (String viewNeighbor : neighborsInView) {
            if (neighborsInModel.contains(viewNeighbor)) {
            } else {
                //if the neighbor is not in the model, remove it:
                
                //get the two possible names of the link in the view
                String possibleName1 = viewNeighbor + ";" + nameOfUpdatedTerritory;
                String possibleName2 = nameOfUpdatedTerritory + ";" + viewNeighbor;
                
                //if one does exist, remove it
                if (this.links.keySet().contains(possibleName1)) {
                    links.remove(possibleName1);
                } 
                if (this.links.keySet().contains(possibleName2)) {
                    links.remove(possibleName2);
                }
            }
        }

        //undraw
        repaint();
    }

    /**
     * Add a new link on the map between two territories in response to a
     * notification from observable model.
     *
     * @param mapModel
     */
    public void updateAddLink(MapModel mapModel) {
        String nameOfUpdatedTerritory = ((TerritoryModel) mapModel.getLastUpdate()).getName();

        //get neigbors of nameOfUpdatedTerritory in the view:
        LinkedList<String> neighborsInView = getNeighborsOfTerritory(nameOfUpdatedTerritory);

        //get neighbors of nameOfUpdatedTerritory in the model
        List<TerritoryModel> neighborsModelsInModel = ((TerritoryModel) mapModel.getLastUpdate()).getAdj();
        LinkedList<String> neighborsInModel = new LinkedList();
        for (TerritoryModel neighborModel : neighborsModelsInModel) {
            neighborsInModel.add(neighborModel.getName());
        }

        //search for the link to add:
        
        //for all neighbors in the model
        String target = null;
        for (String modelNeighbor : neighborsInModel) {
            if (neighborsInView.contains(modelNeighbor)) {
            } else {
                //if the neighbor is not in the view, add it
                target = modelNeighbor;
            }
        }

        //if there is no difference found between the model and the view, do nothing
        if (target == null) {
            return;
        }

        //else, actually add the new neighbor
        addLink(nameOfUpdatedTerritory, target);
    }

    /**
     * Add a new link between two country buttons
     * @param territory1
     * @param territory2 
     */
    private void addLink(String territory1, String territory2){
        String[] linkNames = {territory1, territory2};
        
        //get the two buttons corresponding to the two countries
        CountryButton2 firstItem = this.countriesButtons.get(linkNames[0]);
        CountryButton2 secondItem = this.countriesButtons.get(linkNames[1]);

        //create line to be drawn given the positions of the buttons
        Line2D newLink = new Line2D.Double();
        newLink.setLine(firstItem.getBounds().x + ((double) this.buttonsDims.width / 2.),
                firstItem.getBounds().y + ((double) this.buttonsDims.height / 2.),
                secondItem.getBounds().x + ((double) this.buttonsDims.width / 2.),
                secondItem.getBounds().y + ((double) this.buttonsDims.height / 2.));

        //add to list
        links.put(linkNames[0] + ";" + linkNames[1], newLink);

        //draw line
        repaint();
    }
    
    /**
     * Update method of the Observer pattern
     *
     * @param object
     * @param arg
     */
    @Override
    public void update(Observable object, Object arg) {
        if(arg == null || !(arg instanceof UpdateTypes))
            return;
        
        //get update type
        UpdateTypes updateType = (UpdateTypes) arg;

        //get observable model
        MapModel mapModel = (MapModel) object;

        //get list of names of territories in the view
        LinkedList<String> territoriesInView = new LinkedList();
        territoriesInView.addAll(this.countriesButtons.keySet());

        //get list of names of territories in the model
        List<String> territoriesInModel = (mapModel).getTerritoryList();

        switch (updateType) {
            case ADD_TERRITORY:
                updateAddTerritory(mapModel, territoriesInModel, territoriesInView);
                break;
            case REMOVE_TERRITORY:
                updateRemoveTerritory(territoriesInModel, territoriesInView);
                break;
            case UPDATE_TERRITORY_NAME:
                updateTerritoryName(mapModel, territoriesInModel, territoriesInView, ((TerritoryModel) mapModel.getLastUpdate()).getName());
                break;
            case UPDATE_TERRITORY_POS:
                updateTerritoryPos(mapModel);
                break;
            case UPDATE_BACKGROUND_IMAGE:
                break;
            case UPDATE_CONTINENT:
                break;
            case REMOVE_LINK:
                updateRemoveLink(mapModel);
                break;
            case ADD_LINK:
                updateAddLink(mapModel);
                break;
        }
    }

    /**
     * Remove a territory in the view according to the update of the model
     * @param territoriesInModel
     * @param territoriesInView 
     */
    private void updateRemoveTerritory(List<String> territoriesInModel, List<String> territoriesInView) {
        //search for the territory which is in the view and not in the model
        for (String name : territoriesInView) {
            if (!territoriesInModel.contains(name)) {
                removeTerritory(name);
                break;
            }
        }
    }

    /**
     * Add a territory in the view according to the update of the model
     * @param mapModel
     * @param territoriesInModel
     * @param territoriesInView 
     */
    private void updateAddTerritory(MapModel mapModel, List<String> territoriesInModel, List<String> territoriesInView) {
        //search for the territory which is in the model and not in the view
        for (String name : territoriesInModel) {
            if (!territoriesInView.contains(name)) {

                //get target territory model to add
                TerritoryModel target = mapModel.getTerritoryByName(name);

                //add the missing territory to the view
                this.addTerritory(target.getPositionX(), target.getPositionY(), target.getName());
                break;
            }
        }
    }

    /**
     * Ask the user to modify a Territory's informations. It shows up when the
     * user clicked left on a Territory button.
     *
     * @param continentsList list of the continents that can be selected as
     * continent by the territory.
     * @param territoryName name of the current territory that will be modified.
     * @param continentName name of the current continent of the territory to be
     * modified.
     * @return the new territory informations gathered from the user.
     */
    public Map<String, String> modifyTerritory(List<String> continentsList, String territoryName, String continentName) {
        Map<String, String> data = new HashMap<>();

        ModifyCountryPanel modifyPanel = new ModifyCountryPanel(continentsList, territoryName, continentName);
        String boxName = "Modifying " + territoryName;
        int result = JOptionPane.showConfirmDialog(null,
                modifyPanel,
                boxName,
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            //check if name not already used
            if (!territoryName.equals(modifyPanel.getTerritoryName()) && this.countriesButtons.keySet().contains(modifyPanel.getTerritoryName())) {
                this.showError("This name is already used");
                return data;
            }

            data.put("newName", modifyPanel.getTerritoryName());
            data.put("continent", modifyPanel.getTerritoryContinent());
            return data;
        } else {
            return data;
        }
    }

    /**
     * When this tool is selected, opens a dialog to ask the user to add a
     * neighbor to a given territory he/she selected.
     *
     * @param territoryArray List of the territories that can be neighbors.
     * @param territoryName name of the territory that is modified.
     * @return the name of the new neighbor that has been selected by the user.
     */
    public String createLink(String[] territoryArray, String territoryName) {

        String boxName = "Add link to " + territoryName;
        String neighbourName = (String) JOptionPane.showInputDialog(
                null,
                "Select a new neighbor for " + territoryName,
                boxName,
                JOptionPane.PLAIN_MESSAGE,
                null,
                territoryArray,
                territoryArray[0]);

        if (neighbourName == null || neighbourName.equals("")) {
            return neighbourName;
        }

        /* if neighbour name not empty
        get name of the future link to check if it already exists */
        String newLinkName = territoryName + ";" + neighbourName;

        //check if already exists
        for (String linkName : this.links.keySet()) {
            if (linkName.equals(newLinkName)) {
                this.showError("This link already exists");
                return "";
            }
        }

        return neighbourName;
    }

    /**
     * Tool of the map panel that allows the user to remove a link between two
     * territories.
     *
     * @param neighbourStringList the string list of the neighbor
     * @return the name of the neighbor that the user wants to remove.
     */
    public String removeLink(LinkedList<String> neighbourStringList) {
        String boxName = "Remove link";
        String[] territoryArray = neighbourStringList.toArray(new String[neighbourStringList.size()]);
        String neighbour = (String) JOptionPane.showInputDialog(
                null,
                "Choose a link to remove",
                boxName,
                JOptionPane.PLAIN_MESSAGE,
                null,
                territoryArray,
                territoryArray[0]);
        return neighbour;
    }

    /**
     * Show a pop-up error to the user to inform of an error
     *
     * @param errorMessage message to be displayed into the dialog.
     */
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
