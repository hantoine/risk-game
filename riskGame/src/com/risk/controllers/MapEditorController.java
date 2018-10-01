/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.mapeditor.CountryButton2;
import com.risk.mapeditor.MapModel2;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

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
                    this.newMap.removeTerritory(countryName);
                }
            }
        }

    }
    
}
