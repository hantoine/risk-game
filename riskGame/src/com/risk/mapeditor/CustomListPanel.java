/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author timot
 */
public class CustomListPanel extends JPanel {
    protected HashMap<String, Component> items;
    protected JButton addButton;
    protected GridBagConstraints gbc;
    protected JLabel dummyLabel;
            
    public CustomListPanel(Integer width, Integer height) {
        //setup component
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        //setup list of objects to display
        this.items = new HashMap<>();
        dummyLabel = new JLabel(" ");
        addDummyLabel();
    }
    
    /**
     * If developers want to change the name of the button
     * @param newName 
     */
    public void setAddButtonName(String newName){
        this.addButton.setName(newName);
        addDummyLabel();
    }
    
    /**
     * Add a new component to the list to be displayed
     * @param newElement
     * @param name 
     */
    public void addElement(Component newElement, String name) {
        this.items.put(name, newElement);
        
        this.remove(this.dummyLabel);
        
        gbc.gridy+=1;
        this.add(newElement, this.gbc, items.size()-1);
        this.setVisible(true);
        
        addDummyLabel();
        
        revalidate();
        repaint();
    }
    
    protected void addDummyLabel(){
        GridBagConstraints dummyGbc = new GridBagConstraints();
        dummyGbc.weighty=1;
        dummyGbc.weightx=1;
        dummyGbc.gridx=0;
        dummyGbc.gridy=gbc.gridy+1;
        this.add(dummyLabel, dummyGbc);
    }
    
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
}