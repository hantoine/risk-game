/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.controllers.MapEditorController;
import com.risk.models.TerritoryModel;
import com.risk.observable.MapModelObserver;
import com.risk.observable.UpdateTypes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * View on the map being edited. Part of the map editor.
 *
 * @author timot
 * @see MapModelObserver
 */
public class MapView extends JPanel implements MapModelObserver {

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
     * @param object New name of the territory
     */
    public void updateTerritoryName(Object object) {
        CountryButton2 territoryButton;
        Map<String, String> data = (Map<String, String>) object;
        String formerName = data.get("name");
        String newName = data.get("newName");

        territoryButton = this.countriesButtons.remove(formerName);
        if (territoryButton != null) {
            territoryButton.setName(newName);
            countriesButtons.put(newName, territoryButton);
        }
    }

    /**
     * Update the position of a button representing a territory in response to a
     * notification from observable model.
     *
     * @param object model of the territory to get its coordinates.
     */
    public void updateTerritoryPos(Object object) {
        CountryButton2 territoryButton;
        TerritoryModel territoryModel = (TerritoryModel) object;
        territoryButton = this.countriesButtons.get(territoryModel.getName());
        territoryButton.setPosition(territoryModel.getPositionX(), territoryModel.getPositionY());
    }

    /**
     * Remove a link between two territories in response to a notification from
     * observable model.
     *
     * @param object names of the two countries that are linked.
     */
    public void removeLink(Object object) {
        String name1;
        String name2;
        String[] neighbours = (String[]) object;
        String linkName = "";

        Iterator linkIt = this.links.keySet().iterator();
        for (int i = 0; i < this.links.keySet().size(); i++) {
            String existingLinkName = (String) linkIt.next();
            String[] existingNeighbours = existingLinkName.split(";");
            if (existingNeighbours.length == 2) {
                name1 = existingNeighbours[0];
                name2 = existingNeighbours[1];
                if (neighbours[0].equals(name1) && neighbours[1].equals(name2) || neighbours[0].equals(name2) && neighbours[1].equals(name1)) {
                    linkName = existingLinkName;
                    break;
                }
            }
        }

        //remove from list of links
        if (!linkName.equals("")) {
            links.remove(linkName);
            //undraw
            repaint();
        }
    }

    /**
     * Add a new link on the map between two territories in response to a
     * notification from observable model.
     *
     * @param object Names of the territories to be linked.
     */
    public void addLink(Object object) {
        String[] linkNames = (String[]) object;
        String name1;
        String name2;

        CountryButton2 firstItem = this.countriesButtons.get(linkNames[0]);
        CountryButton2 secondItem = this.countriesButtons.get(linkNames[1]);

        //create line to be drawn
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
     * Function of the observer interface that update the view in response to an
     * update of the model.
     *
     * @param updateType type of the change that has been performed on the
     * model.
     * @param object Can be different regarding the update type.
     */
    @Override
    public void update(UpdateTypes updateType, Object object) {
        String territoryName;
        TerritoryModel territoryModel;

        switch (updateType) {
            case ADD_TERRITORY:
                TerritoryModel newTerritory = (TerritoryModel) object;
                addTerritory(newTerritory.getPositionX(), newTerritory.getPositionY(), newTerritory.getName());
                break;
            case REMOVE_TERRITORY:
                territoryName = (String) object;
                removeTerritory(territoryName);
                break;
            case UPDATE_TERRITORY_NAME:
                updateTerritoryName(object);
                break;
            case UPDATE_TERRITORY_POS:
                updateTerritoryPos(object);
                break;
            case UPDATE_BACKGROUND_IMAGE:
                break;
            case UPDATE_CONTINENT:
                break;
            case REMOVE_LINK:
                removeLink(object);
                break;
            case ADD_LINK:
                addLink(object);
                break;
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
     * neighbour to a given territory he/she selected.
     *
     * @param territoryArray List of the territories that can be neighbours.
     * @param territoryName name of the territory that is modified.
     * @return the name of the new neighbour that has been selected by the user.
     */
    public String createLink(String[] territoryArray, String territoryName) {

        String boxName = "Add link to " + territoryName;
        String neighbourName = (String) JOptionPane.showInputDialog(
                null,
                "Select a new neighbour for " + territoryName,
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
     * @param neighbourStringList the string list of the neighbour
     * @return the name of the neighbour that the user wants to remove.
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
     * Show a popup error to the user to inform of an error
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
