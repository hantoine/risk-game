/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.mapeditor.MapModel2;
import com.risk.models.MapModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author timot
 */
public class MapEditorController {
    protected MapModel2 newMap;
    protected MouseController mouseController;
    
    public MapEditorController(MapModel2 mapModel){
        newMap = mapModel;
        mouseController = new MouseController(mapModel);
    }
    
    public MouseController getMouseListener(){
        return this.mouseController;
    }
    
    protected class MouseController implements MouseListener, MouseMotionListener {
        protected MapModel2 newMap;
        
        public MouseController(MapModel2 mapModel){
            newMap = mapModel;
        }
        
        public void mouseClicked(MouseEvent e){
            int posX = e.getX();
            int posY = e.getY();
            this.newMap.addTerritory(posX, posY);
        }

        public void mouseEntered(MouseEvent e){

        }

        public void mouseExited(MouseEvent e){

        }

        public void mousePressed(MouseEvent e){

        }

        public void mouseReleased(MouseEvent e){

        }

        public void mouseMoved(MouseEvent e){

        }

        public void mouseDragged(MouseEvent e){

        }
    }
    
}
