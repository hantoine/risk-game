/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import static java.lang.Math.floor;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * It represents the map editor panel in the menu
 * @author timot
 */
public class MapEditorPanel extends javax.swing.JFrame{

    protected JPanel contentPanel;
    protected FileSelectorPanel imageSelectorPanel;
    protected MapView mapPanel;
    private MapEditorController controler;
    
    /**
     * Constructor
     * @param width
     * @param height
     * @param editorController
     */
    public MapEditorPanel(int width, int height, MapEditorController editorController) {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        controler = editorController;
        this.setTitle("Map Editor");
        
        //all content is being stored in this panel
        contentPanel = new JPanel();
        
        //selector of a background image
        imageSelectorPanel = new FileSelectorPanel(210,30, new FileNameExtensionFilter(".png", ".jpg", ".jpeg"));
        imageSelectorPanel.setMaximumSize(new Dimension(600,20));
        imageSelectorPanel.setLabel("Select a background image for your map.");
        
        contentPanel.setSize(width-imageSelectorPanel.getMaximumSize().width, height-imageSelectorPanel.getMaximumSize().height);
        
        //map view plus explanations for the user
        JLabel mapLabel= new JLabel("<html>Click left in an empty space to add a new country.<br>"
                + "Click left on an existing country to modify it.<br>"
                + "Click right on an existing country to delete it.<br></html>");
        mapPanel = new MapView(editorController);
        
        //panel to add continents
        CustomListPanel continentsPanel = new CustomListPanel(200,600);
        
        //set alignment
        imageSelectorPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        mapLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
        mapPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        
        //add elements
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(BorderLayout.NORTH, imageSelectorPanel);
        contentPanel.add(BorderLayout.SOUTH, mapLabel);
        contentPanel.add(BorderLayout.EAST, continentsPanel);
        contentPanel.add(BorderLayout.CENTER, mapPanel);
        setContentPane(contentPanel);
    }
    
    /**
     * Return the view to the map within the map editor.
     * @return 
     */
    public MapView getMapView(){
        return mapPanel;
    }
}
