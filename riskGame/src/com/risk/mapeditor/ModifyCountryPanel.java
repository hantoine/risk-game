/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.models.ContinentModel;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author timot
 */
public class ModifyCountryPanel extends JPanel{
    public ModifyCountryPanel(Set<String> continentsList, String territoryName){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(500,500);
        
        JPanel namePanel  = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(new JLabel("Name:"));
        namePanel.add(new JTextField(territoryName));        
        
        JPanel listPanel  = new JPanel();
        listPanel.setLayout(new FlowLayout());
        listPanel.add(new JLabel("Belongs to:"));
        String[] continentList = Arrays.copyOf(continentsList.toArray(), 
                continentsList.size(),
                String[].class);
        
        listPanel.add(new JComboBox(continentList));
        
        this.add(namePanel);
        this.add(listPanel);
    }
}
