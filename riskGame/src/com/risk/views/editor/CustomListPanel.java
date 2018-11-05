/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Panel that will be used to build lists of components, allowing to dynamically
 * add/remove new components on it.
 *
 * @author timot
 */
public class CustomListPanel extends JPanel {

    /**
     * Items of the list with each item being a component to be displayed on the
     * panel.
     */
    protected HashMap<String, Component> items;

    /**
     * Button that can be clicked by the user to add new Components.
     */
    protected JButton addButton;

    /**
     * Tool of the layout to display the components.
     */
    protected GridBagConstraints gbc;

    /**
     * Dummy label to be used to make the list good looking.
     */
    protected JLabel dummyLabel;

    /**
     * Contructor.
     *
     * @param width the width of the panel
     * @param height the height of the panel
     */
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
     *
     * @param newName the name of the button 
     */
    public void setAddButtonName(String newName) {
        this.addButton.setName(newName);
        this.add(addButton);
        addDummyLabel();
    }

    /**
     * Add a new component to the list to be displayed
     *
     * @param newElement the new element which we want to add into
     * @param name the name of the item which we want to add
     */
    public void addElement(Component newElement, String name) {        
        this.items.put(name, newElement);
        gbc.gridy += 1;
        this.add(newElement, this.gbc, items.size() - 1);
        this.setVisible(true);
        addDummyLabel();
        revalidate();
        repaint();
        
    }

    /**
     * Add a dummy label to fill the space of GridBag (just a matter of
     * graphism)
     */
    protected void addDummyLabel() {        
        GridBagConstraints dummyGbc = new GridBagConstraints();
        dummyGbc.weighty = 1;
        dummyGbc.weightx = 1;
        dummyGbc.gridx = 0;
        dummyGbc.gridy = gbc.gridy + 1;
        this.add(dummyLabel, dummyGbc);
    }

    /**
     * Remove a component by name
     *
     * @param name name of the remove element
     */
    public void removeElement(String name) {
        this.remove(items.get(name));
        this.items.remove(name);
        //gbc.gridy -= 1;
        addDummyLabel();
        revalidate();
        repaint();
    }

    /**
     * Show a popup error to the user to inform of an error
     *
     * @param errorMessage Message to be displayed.
     */
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
