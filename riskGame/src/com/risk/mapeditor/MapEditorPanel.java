/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * It represents the map editor panel in the menu
 * @author timot
 */
public class MapEditorPanel extends javax.swing.JFrame {

    protected JPanel contentPanel;
    protected FileSelectorPanel imageSelectorPanel;
    protected MapView mapPanel;
    
    /**
     * Constructor
     * @param width
     * @param height
     */
    public MapEditorPanel(int width, int height) {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        
        contentPanel = new JPanel();
        imageSelectorPanel = new FileSelectorPanel(210,30, new FileNameExtensionFilter(".png", ".jpg",".jpeg"));
        imageSelectorPanel.setLabel("lol");
        JLabel mapLabel= new JLabel("<html>Select a background image for your map.<br>"
                + "Click left in an empty space to add a new country.<br>"
                + "Click left on an existing country to modify it.<br>"
                + "Click right on an existing country to delete it.<br></html>");
        mapPanel = new MapView();
        
        //add elements
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(imageSelectorPanel);
        contentPanel.add(mapLabel);
        contentPanel.add(mapPanel);
        getContentPane().add(contentPanel);
    }
}
