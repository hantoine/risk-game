/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import com.risk.observers.MapModelObserver;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * It represents the map editor panel in the menu
 * @author timot
 */
public class MapEditorPanel extends javax.swing.JFrame implements MapModelObserver{

    protected JPanel contentPanel;
    protected FileSelectorPanel imageSelectorPanel;
    protected MapView mapPanel;
    private MapEditorController controler;
    protected ContinentListPanel continentsPanel;
    protected CustomListPanel toolsPanel;
    protected MapConfigPanel mapConfigPanel;
    
    
    /**
     * Constructor
     * @param width
     * @param height
     * @param editorController
     */
    public MapEditorPanel(int width, int height, MapEditorController editorController, MapModel2 initMapModel){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(width, height));
        controler = editorController;
        this.setTitle("Map Editor");
        this.setResizable(false);
        
        //all content is being stored in this panel
        contentPanel = new JPanel();
        
        //selector of a background image
        imageSelectorPanel = new FileSelectorPanel(210,30, new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        imageSelectorPanel.setMaximumSize(new Dimension(600,20));
        imageSelectorPanel.setLabel("Select a background image for your map.");
        
        contentPanel.setSize(width-imageSelectorPanel.getMaximumSize().width, height-imageSelectorPanel.getMaximumSize().height);
        
        mapPanel = new MapView(editorController);
        this.imageSelectorPanel.getTextField().getDocument().addDocumentListener(controler.getSelectBackImgListener(mapPanel, this));
        
        //panel to add continents
        this.continentsPanel = new ContinentListPanel(120,600, editorController, initMapModel.getContinentList());
        
        //panel with buttons to select a mode
        String[] toolsList = {"Add Territory", "Edit Territory", "Create Link", "Remove Link"};
        this.mapPanel.setCurrentTool(Tools.CREATE);
        this.toolsPanel = new ToolsListPanel(120,600, toolsList, getToolButtonListener());
        
        //panel to set map configuration
        this.mapConfigPanel = new MapConfigPanel();
        
        //add elements
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(BorderLayout.NORTH, imageSelectorPanel);
        contentPanel.add(BorderLayout.WEST, toolsPanel);
        contentPanel.add(BorderLayout.EAST, continentsPanel);
        contentPanel.add(BorderLayout.CENTER, mapPanel);
        contentPanel.add(BorderLayout.SOUTH, mapConfigPanel);
        setContentPane(contentPanel);
        
    }
    
    public ToolButtonListener getToolButtonListener(){
        return new ToolButtonListener(this.mapPanel);
    }
    
    /**
     * Button listener to change the mode when the user click on a tool to select it 
     */
    protected class ToolButtonListener implements ActionListener{

        private MapView mapPanel;
        
        public ToolButtonListener(MapView mapPanel){
            this.mapPanel = mapPanel;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String toolName = ((JButton)e.getSource()).getText();
            switch(toolName){
                case "Add Territory":
                    this.mapPanel.setCurrentTool(Tools.CREATE);
                    break;
                case "Edit Territory":
                    this.mapPanel.setCurrentTool(Tools.EDIT);
                    break;
                case "Create Link":
                    this.mapPanel.setCurrentTool(Tools.LINK);
                    break;
                case "Remove Link":
                    this.mapPanel.setCurrentTool(Tools.UNLINK);
                    break;
            }
        }
    }
    
    /**
     * Return the view to the map within the map editor.
     * @return 
     */
    public MapView getMapView(){
        return this.mapPanel;
    }
    
    public ContinentListPanel getContinentListPanel(){
        return this.continentsPanel;
    }

    @Override
    public void update(UpdateTypes updateType, Object object){        
        switch(updateType){
            case ADD_TERRITORY:
                break;
            case REMOVE_TERRITORY:
                break;
            case UPDATE_TERRITORY_NAME:
                break;
            case UPDATE_TERRITORY_POS:
                break;
            case UPDATE_BACKGROUND_IMAGE:
                BufferedImage backgroundImage = (BufferedImage) object;
                
                //get current sizes of components
                int width = backgroundImage.getWidth();
                int height = backgroundImage.getHeight();
                Dimension mapPanelSize = this.mapPanel.getSize();
                Dimension parentSize = this.getSize();
                
                //compute new size to adapt to the image
                double difWidth = width-mapPanelSize.getWidth();
                double difHeight= height-mapPanelSize.getHeight();
                parentSize.width+=difWidth;
                parentSize.height+=difHeight;
                
                //set new size and set new background image
                this.setSize(parentSize);
                this.mapPanel.setImage(backgroundImage);
                this.repaint();  
                
                break;
            case UPDATE_CONTINENT:
                break;
        }
    }
}
