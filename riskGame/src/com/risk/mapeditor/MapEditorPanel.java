/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.models.MapModel;
import com.risk.views.map.MapPanel;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * It represents the map editor panel in the menu
 * @author timot
 */
public class MapEditorPanel extends javax.swing.JFrame {

    protected MapView mapPanel;
    
    /**
     * Constructor
     * @param width
     * @param height
     */
    public MapEditorPanel(int width, int height) {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        
        mapPanel = new MapView();
        
        //add elements
        getContentPane().add(mapPanel);
    }
}
