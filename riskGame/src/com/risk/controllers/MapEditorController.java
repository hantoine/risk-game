/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.mapeditor.ContinentListPanel;
import com.risk.mapeditor.CountryButton2;
import com.risk.mapeditor.MapEditorPanel;
import com.risk.mapeditor.MapModel2;
import com.risk.mapeditor.MapView;
import com.risk.mapeditor.Tools;
import com.risk.models.TerritoryModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author timot
 */
public class MapEditorController {
    public MapModel2 newMap;
    
    public MapEditorController(MapModel2 mapModel){
        newMap = mapModel;
    }
    
    /**
     * Getters
     * @return 
     */
    public MapMouseController getMapMouseListener(){
        return new MapMouseController(newMap);
    }
    
    public ButtonMouseController getCountryMouseListener(){
        return new ButtonMouseController(newMap);
    }
    
    public AddContinentButtonListener getAddContinentButtonListener(){
        return new AddContinentButtonListener(newMap);
    }
    
    public ContinentMouseListener getContinentMouseListener(){
        return new ContinentMouseListener(newMap);
    }
    
    public ContinentTextListener getContinentTextListener(){
        return new ContinentTextListener(newMap);
    }    
    
    public selectBackImgListener getSelectBackImgListener(MapView mapPanel, MapEditorPanel editorPanel){
        return new selectBackImgListener(mapPanel, editorPanel, this.newMap);
    }
    
    /**
     * Class to handle clicks on the "add" button listener to add continents
     */
    public class AddContinentButtonListener implements ActionListener{
        protected MapModel2 newMap;
        
        public AddContinentButtonListener(MapModel2 mapModel){
            newMap = mapModel;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean success = newMap.addContinent();
            if(success == false){
                JButton buttonClicked = (JButton)e.getSource();
                ContinentListPanel clickedPanel = (ContinentListPanel)buttonClicked.getParent();
                clickedPanel.showError("The maximum number of continents to be created has been reached.");
            }
        }
    }
    
    /**
     * Class to handle changes of the name of continents
     */
    public class ContinentTextListener implements DocumentListener{
        public MapModel2 newMap;
        
        public ContinentTextListener(MapModel2 mapModel){
            newMap = mapModel;
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
        }
    }
    
    /**
     * Class to handle mouse events on continents objects
     */
    public class ContinentMouseListener implements MouseListener{
        public MapModel2 newMap;
        
        public ContinentMouseListener(MapModel2 mapModel){
            newMap = mapModel;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)){
                Object sourceObj = e.getSource();
                String className = sourceObj.getClass().getName();
                if(className == "javax.swing.JTextField"){
                    String continentName = ((JTextField)sourceObj).getText();
                    boolean success = this.newMap.removeContinent(continentName);
                    if(!success){
                        JTextField clickedField = (JTextField)sourceObj;
                        ContinentListPanel clickedPanel = (ContinentListPanel)clickedField.getParent();
                        clickedPanel.showError("Any map needs at least one continent.");
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
    
    /**
     * Listener for the entire map panel (to add new elements on it)
     */
    protected class MapMouseController implements MouseListener {
        public MapModel2 newMap;
        
        public MapMouseController(MapModel2 mapModel){
            newMap = mapModel;
        }
        
        public void mouseClicked(MouseEvent e){
        }

        public void mouseEntered(MouseEvent e){
        }

        public void mouseExited(MouseEvent e){
        }

        public void mousePressed(MouseEvent e){
        }

        public void mouseReleased(MouseEvent e){
            if (SwingUtilities.isLeftMouseButton(e)){
                MapView clickedPanel = (MapView)e.getSource();
                Tools toolName = clickedPanel.getCurrentTool();
                        
                switch(toolName){
                    case CREATE:
                        int posX = e.getX();
                        int posY = e.getY();
                        boolean success = this.newMap.addTerritory(posX, posY);
                        if(success == false){
                            MapView mapView = (MapView)e.getSource();
                            mapView.showError("The maximum number of territories to be created has been reached.");
                        }
                            
                        break;
                    case EDIT:
                        break;
                    case LINK:
                        break;
                }
            }
        }
    }
    
    /**
     * Listeners for buttons
     */
    protected class ButtonMouseController implements MouseListener {
        public MapModel2 newMap;
        
        public ButtonMouseController(MapModel2 mapModel){
            newMap = mapModel;
        }
            
        public void mouseClicked(MouseEvent e){
            if (SwingUtilities.isLeftMouseButton(e)){
                
                CountryButton2 targetButton = (CountryButton2)e.getSource();
                String className = targetButton.getClass().getName();
                
                MapView clickedPanel = (MapView)targetButton.getParent();
                Tools toolName = clickedPanel.getCurrentTool();
                String territoryName = targetButton.getName();
                        
                switch(toolName){
                    case CREATE:
                        break;
                        
                    case EDIT:
                        String[] continentList = newMap.getContinentList();
                        TerritoryModel territoryModel = newMap.getTerritoryByName(territoryName);
                        String continentName = territoryModel.getContinentName(); 

                        //get information from the user
                        JPanel mapPanel = (JPanel)targetButton.getParent();
                        Map<String,String> data = ((MapView)mapPanel).modifyTerritory(continentList, territoryName, continentName);

                        //if succeeded update the model's data
                        if (!data.isEmpty()){
                            data.put("name", territoryName);
                            this.newMap.updateTerritory(data);
                        }
                        break;
                        
                    case LINK:
                        String[] territoryList = newMap.getPotentialNeighbours(territoryName);
                        
                        if(territoryList.length == 0){
                            clickedPanel.showError("No potential neighbour available.");
                            return;
                        }
                        
                        String neighbour = clickedPanel.createLink(territoryList, territoryName);
                        
                        if (!"".equals(neighbour)){
                            newMap.addLink(territoryName, neighbour);
                        }
                        break;
                }
            }
        }

        public void mouseEntered(MouseEvent e){
        }

        public void mouseExited(MouseEvent e){
        }

        public void mousePressed(MouseEvent e){
        }

        public void mouseReleased(MouseEvent e){
            if (SwingUtilities.isRightMouseButton(e)){
                Object sourceObj = e.getSource();
                String className = sourceObj.getClass().getName();
                if(className == "com.risk.mapeditor.CountryButton2"){
                    String countryName = ((CountryButton2)sourceObj).getName();
                    this.newMap.removeTerritory(countryName);
                }
            }
        }
    }
    
    public class selectBackImgListener implements DocumentListener{
        protected MapView mapPanel;
        protected MapEditorPanel editorPanel;
        public MapModel2 mapModel;
        
        public selectBackImgListener(MapView mapPanel, MapEditorPanel editorPanel, MapModel2 mapModel){
            this.mapPanel = mapPanel;
            this.editorPanel = editorPanel;
            this.mapModel = mapModel;
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            File sourceImage;
            try {
                sourceImage = new File(e.getDocument().getText(0,e.getDocument().getLength()));
                BufferedImage backgroundImage = ImageIO.read(sourceImage);
                
                Dimension buttonDim = mapPanel.getButtonDimension();
                mapModel.setImage(backgroundImage, buttonDim);
                
            } catch (BadLocationException ex) {
                Logger.getLogger(MapEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MapEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {  
        }
        
    }
}
