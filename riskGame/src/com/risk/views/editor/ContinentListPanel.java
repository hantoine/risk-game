/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.controllers.MapEditorController;
import com.risk.models.ContinentModel;
import com.risk.models.MapModel;
import com.risk.observable.MapModelObserver;
import com.risk.observable.UpdateTypes;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Panel that contains the informations of the continents and that allows to
 * update the list.
 *
 * @author timot
 * @see MapModelObserver
 * @see CustomListPanel
 */
public class ContinentListPanel extends CustomListPanel implements Observer {

    /**
     * Controller of the map editor useful to get the listeners to be attached
     * on this panel's components.
     */
    private MapEditorController controller;

    /**
     * Hashmap containing the list of continents.
     */
    private HashMap<String, Component> items;

    /**
     * Button to add new continents.
     */
    private JButton addButton;

    Random color = new Random();

    /**
     * Constructor.
     *
     * @param width width of the panel
     * @param height height of the panel
     * @param editorController Controller of the map editor.
     * @param continentList List of the continents to be added on this panel.
     */
    public ContinentListPanel(Integer width, Integer height, MapEditorController editorController, List<String> continentList) {
        //setup component
        super(width, height);
        this.controller = editorController;
        this.setLayout(new GridBagLayout());

        //setup list of objects to display
        this.items = new HashMap<>();

        //setup add button
        this.addButton = new JButton("Add Continent");
        this.addButton.addActionListener(editorController.getAddContinentButtonListener());
        this.add(addButton, gbc);

        //add a dummy label for the display
        for (String continentName : continentList) {
            this.addContinent(continentName);
        }
        addDummyLabel();
    }

    public Color getColor(String continentName) {
        if (this.items.keySet().contains(continentName)) {
            return this.items.get(continentName).getBackground();
        } else {
            return null;
        }
    }

    /**
     * Add a dummy label for having a good display of the elements using
     * GridBagConstraints.
     *
     * @see GridBagConstraints
     */
    public void addDummyLabel() {
        GridBagConstraints dummyGbc = new GridBagConstraints();
        dummyGbc.weighty = 1;
        dummyGbc.weightx = 1;
        dummyGbc.gridx = 0;
        dummyGbc.gridy = this.gbc.gridy + 1;
        this.add(this.dummyLabel, dummyGbc);
    }

    /**
     * Add a new continent to the list of continents.
     *
     * @param newComponent Continent's container.
     * @param name Name of the continent.
     */
    @Override
    public void addElement(Component newComponent, String name) {
        if (!"javax.swing.JButton".equals(newComponent.getClass().getName())) {
            return;
        }

        //create element
        JButton newElement = (JButton) newComponent;

        //add listeners
        newElement.addMouseListener(this.controller.getContinentMouseListener());

        //add to the list and the view
        this.remove(this.dummyLabel);
        gbc.gridy += 1;
        this.items.put(name, newElement);
        this.add(newElement, gbc);
        addDummyLabel();

        //draw
        this.setVisible(true);
        revalidate();
        repaint();
    }

    /**
     * Remove a continent from the list.
     *
     * @param name name of the remove element
     */
    @Override
    public void removeElement(String name) {
        this.remove(items.get(name));
        this.items.remove(name);
        revalidate();
        repaint();
    }

    /**
     * Add a new continent calling the inherited method from CustomListPanel.
     *
     * @param continentName name of the continent
     * @see CustomListPanel
     */
    public void addContinent(String continentName) {

        JButton newContinent = new JButton(continentName);
        newContinent.setBackground(new Color(color.nextInt(255), color.nextInt(255), color.nextInt(255)));
        addElement(newContinent, continentName);
    }

    /**
     * Remove a new continent calling the inherited method from CustomListPanel.
     *
     * @param continentName name of the continent
     * @see CustomListPanel
     */
    public void removeContinent(String continentName) {
        removeElement(continentName);
    }

    /**
     * Update method of the Observer pattern
     *
     * @param object
     * @param arg
     */
    @Override
    public void update(Observable object, Object arg) {
        if (arg == null || !(arg instanceof UpdateTypes)) {
            return;
        }

        //get update type
        UpdateTypes updateType = (UpdateTypes) arg;

        //get model that has changed
        MapModel mapModel = (MapModel) object;

        //get the list of continents in the model
        List<String> continentsInModel = (mapModel).getContinentList();

        //get the list of continents in the view
        LinkedList<String> continentsInView = new LinkedList();
        Set<String> continentsInViewSet = this.items.keySet();
        continentsInView.addAll(continentsInViewSet);

        switch (updateType) {
            default:
                break;
            case ADD_CONTINENT:

                //search for the continent which is in the model and not in the view
                for (String name : continentsInModel) {
                    if (!continentsInView.contains(name)) {

                        //add the missing continent to the view
                        this.addContinent(name);
                        break;
                    }
                }
                break;

            case REMOVE_CONTINENT:

                //search for the continent which is in the view and not in the model
                for (String name : continentsInView) {
                    if (!continentsInModel.contains(name)) {

                        //remove the missing continent from the view
                        this.removeContinent(name);
                        break;
                    }
                }
                break;

            case UPDATE_CONTINENT:
                ContinentModel updatedContinent = (ContinentModel) mapModel.getLastUpdate();
                String newName = updatedContinent.getName();
                String formerName = null;

                for (String name : continentsInView) {
                    if (!continentsInModel.contains(name)) {
                        formerName = name;
                    }
                }

                if (formerName == null) {
                    return;
                }

                JButton elementToModify = (JButton) this.items.get(formerName);
                elementToModify.setText(newName);
                elementToModify.setName(newName);
                this.items.remove(formerName);
                this.items.put(newName, elementToModify);
                break;
            case UPDATE_TERRITORY_NAME:
                break;
            case UPDATE_TERRITORY_POS:
                break;
            case UPDATE_BACKGROUND_IMAGE:
                break;
        }
    }

    /**
     * Get modification informations from the user.
     *
     * @param formerName Current name to be printed which will become the former
     * name of the continent to be modified.
     * @param bonusScore Bonus score to be printed.
     * @return an object containing the user inputs.
     */
    public Map<String, String> modifyContinent(String formerName, int bonusScore) {
        Map<String, String> data = new HashMap<>();

        //create panel inside the jdialog
        ModifyContinentPanel modifyPanel = new ModifyContinentPanel(formerName, bonusScore);
        String boxName = "Modifying " + formerName;
        int result = JOptionPane.showConfirmDialog(null,
                modifyPanel,
                boxName,
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            data.put("newName", modifyPanel.getContinentName());
            data.put("bonusScore", modifyPanel.getBonusScore());
            return data;
        } else {
            return data;
        }
    }
}
