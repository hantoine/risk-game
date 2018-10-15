/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.controllers.MapEditorController;
import com.risk.observable.UpdateTypes;
import com.risk.observable.MapModelObserver;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author timot
 */
public class ContinentListPanel extends CustomListPanel implements MapModelObserver {

    private MapEditorController controller;
    private HashMap<String, Component> items;
    private JButton addButton;

    public ContinentListPanel(Integer width, Integer height, MapEditorController editorController, String[] continentList) {
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

        addDummyLabel();
        for (String continentName : continentList) {
            this.addContinent(continentName);
        }
    }

    public void addDummyLabel() {
        GridBagConstraints dummyGbc = new GridBagConstraints();
        dummyGbc.weighty = 1;
        dummyGbc.weightx = 1;
        dummyGbc.gridx = 0;
        dummyGbc.gridy = gbc.gridy + 1;
        this.add(this.dummyLabel, dummyGbc);
    }

    private void customize(JLabel label, String name) {
        label.setText(name);
        label.setBackground(Color.white);
        label.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    }

    @Override
    public void addElement(Component newComponent, String name) {
        if (!"javax.swing.JLabel".equals(newComponent.getClass().getName())) {
            return;
        }

        //create element
        JLabel newElement = (JLabel) newComponent;
        customize(newElement, name);

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

    @Override
    /**
     * Remove a component by name
     *
     * @param name
     */
    public void removeElement(String name) {
        this.remove(items.get(name));
        this.items.remove(name);
        gbc.gridy -= 1;
        revalidate();
        repaint();
    }

    public void addContinent(String continentName) {
        addElement(new JLabel(continentName), continentName);
    }

    public void removeContinent(String continentName) {
        removeElement(continentName);
    }

    @Override
    public void update(UpdateTypes updateType, Object object) {
        String continentName;
        switch (updateType) {
            case ADD_CONTINENT:
                continentName = (String) object;
                this.addContinent(continentName);
                break;
            case REMOVE_CONTINENT:
                continentName = (String) object;
                this.removeContinent(continentName);
                break;
            case UPDATE_CONTINENT:
                Map<String, String> data = (Map<String, String>) object;
                String formerName = data.get("name");
                String newName = data.get("newName");

                JLabel elementToModify = (JLabel) this.items.get(formerName);
                elementToModify.setText(newName);
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
