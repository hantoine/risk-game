/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;

/**
 *
 * @author timot
 */
public class ContinentListPanel extends CustomListPanel {
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
        this.add(addButton);
    }
    
    @Override
    public void addElement(Component newElement, String name) {  
        this.items.put(name, newElement);
        this.add(newElement, items.size()-1);
        this.setVisible(true);
        revalidate();
        repaint();
    }
}
