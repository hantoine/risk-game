/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.CountryButton;
import com.risk.views.MapPanel;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;


/**
 *
 * @author Nellybett
 */
public class GameListener extends MouseAdapter{
    
    String countrySource;
    String countryReceive;
    private String countryReinforce;

    @Override
    public void mousePressed(MouseEvent e) {
       MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c!=null && c instanceof MapPanel) {
            mapPanel=(MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if(cAux!=null && cAux instanceof CountryButton) {
                CountryButton source = (CountryButton)cAux;
                this.countrySource=source.getName();
                System.out.println(source.getName());
            }
        
        }
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c!=null && c instanceof MapPanel) {
            mapPanel=(MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if(cAux!=null && cAux instanceof CountryButton) {
                CountryButton source = (CountryButton)cAux;
                System.out.println(source.getName());
                this.countryReceive=source.getName();
            }
        
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c!=null && c instanceof MapPanel) {
            mapPanel=(MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if(cAux!=null && cAux instanceof CountryButton) {
                CountryButton source = (CountryButton)cAux;
                System.out.println(source.getName());
                this.countryReinforce=source.getName();
            }
        
        }
    }
    
}
