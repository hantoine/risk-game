/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.mapeditor.ContinentListPanel;
import com.risk.mapeditor.CountryButton2;
import com.risk.mapeditor.MapEditorView;
import com.risk.mapeditor.EditableMapModel;
import com.risk.mapeditor.MapView;
import com.risk.mapeditor.Tools;
import com.risk.models.ContinentModel;
import com.risk.models.TerritoryModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author timot
 */
public class MapEditorController {
    public EditableMapModel newMap;
    
    public MapEditorController(EditableMapModel mapModel){
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
    
    public selectBackImgListener getSelectBackImgListener(MapView mapPanel, MapEditorView editorPanel){
        return new selectBackImgListener(mapPanel, editorPanel, this.newMap);
    }
    
    /**
     * Class to handle clicks on the "add" button listener to add continents
     */
    public class AddContinentButtonListener implements ActionListener{
        protected EditableMapModel newMap;
        
        public AddContinentButtonListener(EditableMapModel mapModel){
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
     * Class to handle mouse events on continents objects
     */
    public class ContinentMouseListener implements MouseListener{
        public EditableMapModel newMap;
        
        public ContinentMouseListener(EditableMapModel mapModel){
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
                     
            //get object we clicked on (jlabel)
            Object sourceObj = e.getSource();
            String className = sourceObj.getClass().getName();
                
            //if JLabel then process
            if(className != "javax.swing.JLabel")
                return;
            
            JLabel clickedLabel = (JLabel)sourceObj;
            ContinentListPanel clickedPanel=(ContinentListPanel)clickedLabel.getParent();
            
            if (SwingUtilities.isRightMouseButton(e)){
                String continentName = ((JLabel)sourceObj).getText();
                boolean success = this.newMap.removeContinent(continentName);
                if(!success){
                    clickedPanel.showError("Any map needs at least one continent.");
                }
            }
            else if (SwingUtilities.isLeftMouseButton(e)){
                //save original name of continent
                String formerName = clickedLabel.getText();
                
                ContinentModel continentToModify = newMap.getGraphContinents().get(formerName);
                int bonusScore = continentToModify.getBonusScore();
                
                //get information from the user
                Map<String, String> data = clickedPanel.modifyContinent(formerName, bonusScore);

                //if succeeded update the model's data
                if (!data.isEmpty()) {
                    data.put("name", formerName);
                    this.newMap.updateContinent(data);
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
        public EditableMapModel newMap;
        
        public MapMouseController(EditableMapModel mapModel){
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
        public EditableMapModel newMap;
        
        public ButtonMouseController(EditableMapModel mapModel){
            newMap = mapModel;
        }
            
        public void mouseClicked(MouseEvent e){
            if (SwingUtilities.isLeftMouseButton(e)){
                
                CountryButton2 targetButton = (CountryButton2)e.getSource();
                String className = targetButton.getClass().getName();
                
                MapView clickedPanel = (MapView)targetButton.getParent();
                Tools toolName = clickedPanel.getCurrentTool();
                String territoryName = targetButton.getName();
                String neighbourName;
                        
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
                        
                        neighbourName = clickedPanel.createLink(territoryList, territoryName);
                        
                        if (!"".equals(neighbourName) && neighbourName != null){
                            newMap.addLink(territoryName, neighbourName);
                        }
                        break;
                        
                    case UNLINK:
                        //get list of neighbours' names
                        LinkedList<TerritoryModel> neighbourList = newMap.getTerritoryByName(territoryName).getAdj();
                        
                        if(neighbourList.isEmpty()){
                            clickedPanel.showError("No neighbour found for this territory");
                            return;
                        }
                        
                        LinkedList<String> neighbourStringList = new LinkedList<>();
                        for(TerritoryModel neighbourModel: neighbourList){
                            neighbourStringList.add(neighbourModel.getName());
                        }
                        
                        neighbourName = clickedPanel.removeLink(neighbourStringList);
                        if (!"".equals(neighbourName) && neighbourName != null){
                            newMap.removeLink(territoryName, neighbourName);
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
        protected MapEditorView editorPanel;
        public EditableMapModel mapModel;
        
        public selectBackImgListener(MapView mapPanel, MapEditorView editorPanel, EditableMapModel mapModel){
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
                Logger.getLogger(MapEditorView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MapEditorView.class.getName()).log(Level.SEVERE, null, ex);
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
