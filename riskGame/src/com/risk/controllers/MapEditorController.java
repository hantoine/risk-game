/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.mapeditor.CountryButton2;
import com.risk.mapeditor.MapModel2;
import com.risk.mapeditor.MapView;
import com.risk.models.TerritoryModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author timot
 */
public class MapEditorController {
    protected MapModel2 newMap;
    
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
            newMap.addContinent();
        }
    }
    
    /**
     * Class to handle changes of the name of continents
     */
    public class ContinentTextListener implements DocumentListener{
        protected MapModel2 newMap;
        
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
        protected MapModel2 newMap;
        
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
                    this.newMap.removeContinent(continentName);
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
        protected MapModel2 newMap;
        
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
                int posX = e.getX();
                int posY = e.getY();
                this.newMap.addTerritory(posX, posY);
            }
        }

    }
    
    /**
     * Listeners for buttons
     */
    protected class ButtonMouseController implements MouseListener {
        protected MapModel2 newMap;
        
        public ButtonMouseController(MapModel2 mapModel){
            newMap = mapModel;
        }
        
        public void mouseClicked(MouseEvent e){
            if(SwingUtilities.isLeftMouseButton(e)){
                Object sourceObj = e.getSource();
                String className = sourceObj.getClass().getName();
                
                if(className == "com.risk.mapeditor.CountryButton2"){
                    CountryButton2 targetButton = (CountryButton2)sourceObj;
                    String territoryName = targetButton.getName();
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
                    else
                        return;
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
}
