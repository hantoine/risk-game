/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import com.risk.observers.MapModelObserver;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
        for(String continentName:continentList){
            this.addContinent(continentName);
        }
    }
    
    public void addDummyLabel(){
        GridBagConstraints dummyGbc = new GridBagConstraints();
        dummyGbc.weighty=1;
        dummyGbc.weightx=1;
        dummyGbc.gridx=0;
        dummyGbc.gridy=gbc.gridy+1;
        this.add(this.dummyLabel, dummyGbc);
    }
    
    @Override
    public void addElement(Component newComponent, String name) {  
        if(!"javax.swing.JTextField".equals(newComponent.getClass().getName()) )
            return;
        
        //add listeners
        JTextField newElement = (JTextField)newComponent;
        this.remove(this.dummyLabel);
        
        gbc.gridy+=1;
        newElement.getDocument().addDocumentListener(this.controller.getContinentTextListener());
        newElement.addMouseListener(this.controller.getContinentMouseListener());

        //add to the list and the view
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
     * @param name 
     */
    public void removeElement(String name){
        this.remove(items.get(name));
        this.items.remove(name);
        gbc.gridy-=1;
        revalidate();
        repaint();
    }

    public void addContinent(String continentName){
        addElement(new JTextField(continentName), continentName);
    }
    
    public void removeContinent(String continentName){
        removeElement(continentName);
    }
    
    @Override
    public void update(UpdateTypes updateType, Object object) {
        String continentName;
        switch(updateType){
            case ADD_CONTINENT:
                continentName = (String)object;
                this.addContinent(continentName);
                break;
            case REMOVE_CONTINENT:
                continentName = (String)object;
                this.removeContinent(continentName);
                break;
            case UPDATE_TERRITORY_NAME:
                break;
            case UPDATE_TERRITORY_POS:
                break;
            case UPDATE_BACKGROUND_IMAGE:
                break;
        }
    }
}
