/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.mapeditor.CountryButton2;
import com.risk.mapeditor.MapModel2;
import com.risk.mapeditor.MapView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    System.out.println("test");
                    this.newMap.removeTerritory(countryName);
                }
            }
            else if(SwingUtilities.isLeftMouseButton(e)){
                Object sourceObj = e.getSource();
                String className = sourceObj.getClass().getName();
                if(className == "com.risk.mapeditor.CountryButton2"){
                    CountryButton2 targetButton = (CountryButton2)sourceObj;
                    JPanel mapPanel = (JPanel)targetButton.getParent();
                    String territoryName = targetButton.getName();
                    Set<String> continentList = newMap.getGraphContinents().keySet();
                    Map<String,String> data = ((MapView)mapPanel).modifyTerritory(continentList, territoryName);
                    
                    if (!data.isEmpty())                
                        this.newMap.updateTerritory(data);
                    else
                        return;
                }
            }
        }

    }
    
}
