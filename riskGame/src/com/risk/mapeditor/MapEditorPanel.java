/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import java.awt.Component;
import java.awt.Dimension;
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
        imageSelectorPanel.setMaximumSize(new Dimension(400,20));
        imageSelectorPanel.setLabel("Select a background image for your map.");
        JLabel mapLabel= new JLabel("<html>Click left in an empty space to add a new country.<br>"
                + "Click left on an existing country to modify it.<br>"
                + "Click right on an existing country to delete it.<br></html>");
        mapPanel = new MapView();
        
        //set alignemnt
        imageSelectorPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        mapLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
        mapPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        
        //add elements
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(imageSelectorPanel);
        contentPanel.add(mapLabel);
        contentPanel.add(mapPanel);
        getContentPane().add(contentPanel);
    }
}
