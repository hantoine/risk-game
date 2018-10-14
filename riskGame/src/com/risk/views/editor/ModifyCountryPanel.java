/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author timot
 */
public class ModifyCountryPanel extends JPanel {

    protected JComboBox continentListBox;
    protected JTextField territoryNameField;

    public ModifyCountryPanel(String[] continentList, String territoryName, String territoryContinent) {
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

        continentListBox = new JComboBox(continentList);
        if (!territoryContinent.equals("")) {
            continentListBox.setSelectedItem(territoryContinent);
        }

        listPanel.add(continentListBox);

        this.add(namePanel);
        this.add(listPanel);
    }
    
    
    
    public String getTerritoryName() {
        return this.territoryNameField.getText();
    }

    public String getTerritoryContinent() {
        return (String) this.continentListBox.getSelectedItem();
    }
}
