/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import com.risk.models.TerritoryModel;
import com.risk.observers.MapModelObserver;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author timot
 */
public class ContinentListPanel extends CustomListPanel implements MapModelObserver {
    private MapEditorController controller;
    private HashMap<String, Component> items;
    private JButton addButton;
    
    public ContinentListPanel(Integer width, Integer height, MapEditorController editorController) {
        //setup component
        super(width, height);
        this.controller = editorController;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //setup list of objects to display
        this.items = new HashMap<>();
        
        //setup add button
        this.addButton = new JButton("+");
        this.addButton.addActionListener(editorController.getAddContinentButtonListener());
        this.add(addButton);
    }
    
    @Override
    public void addElement(Component newComponent, String name) {  
        if(!"javax.swing.JTextField".equals(newComponent.getClass().getName()) )
            return;

        
        
        //add listeners
        JTextField newElement = (JTextField)newComponent;
        newElement.setMaximumSize(new Dimension(400, 50));
        newElement.getDocument().addDocumentListener(this.controller.getContinentTextListener());
        newElement.addMouseListener(this.controller.getContinentMouseListener());

        //add to the list and the view
        this.items.put(name, newElement);
        this.add(newElement, items.size()-1);

        //draw
        this.setVisible(true);
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
                addContinent(continentName);
                break;
            case REMOVE_CONTINENT:
                continentName = (String)object;
                removeContinent(continentName);
                break;
        }
    }
}
