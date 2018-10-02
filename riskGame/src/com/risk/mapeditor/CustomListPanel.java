/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author timot
 */
public class CustomListPanel extends JPanel {
    private HashMap<String, Component> items;
    private JButton addButton;

    public CustomListPanel(Integer width, Integer height) {
        //setup component
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //setup list of objects to display
        this.items = new HashMap<>();
        
        //setup add button
        this.addButton = new JButton("+");
        this.add(addButton);
    }
    
    /**
     * If developers want to change the name of the button
     * @param newName 
     */
    public void setAddButtonName(String newName){
        this.addButton.setName(newName);
    }
    
    /**
     * Add a new component to the list to be displayed
     * @param newElement
     * @param name 
     */
    public void addElement(Component newElement, String name) {  
        this.items.put(name, newElement);
        this.add(newElement, items.size()-1);
        this.setVisible(true);
        revalidate();
        repaint();
    }
    
    /**
     * Remove a component by name
     * @param name 
     */
    public void removeElement(String name){
        this.remove(items.get(name));
        this.items.remove(name);
        revalidate();
        repaint();
    }
}