/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel that is showed when editing a territory.
 *
 * @author timot
 */
public class ModifyTerritoryPanel extends JPanel {

    /**
     * Used to select a continent for the territory being edited
     */
    protected JComboBox continentListBox;

    /**
     * Used to modify the name of the territory being edited
     */
    protected JTextField territoryNameField;

    /**
     * Constructor
     *
     * @param continentList List of the continents that can be the continent of
     * the territory
     * @param territoryName name of the territory being edited
     * @param territoryContinent continent of the territory being edited.
     */
    public ModifyTerritoryPanel(List<String> continentList, String territoryName, String territoryContinent) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(500, 500);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(new JLabel("Name:"));
        territoryNameField = new JTextField(territoryName);
        namePanel.add(territoryNameField);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        listPanel.add(new JLabel("Belongs to:"));

        System.out.println("continentList:" + continentList);
        System.out.println("territoryName:" + territoryName);
        System.out.println("territoryContinent:" + territoryContinent);

        continentListBox = new JComboBox(continentList.toArray());
        if (!territoryContinent.equals("")) {
            continentListBox.setSelectedItem(territoryContinent);
        }

        listPanel.add(continentListBox);

        this.add(namePanel);
        this.add(listPanel);
    }

    /**
     * Getter of the territory name
     *
     * @return the territory name
     */
    public String getTerritoryName() {
        return this.territoryNameField.getText();
    }

    /**
     * Getter on the territory's continent
     *
     * @return the continent of the territory
     */
    public String getTerritoryContinent() {
        return (String) this.continentListBox.getSelectedItem();
    }
}
