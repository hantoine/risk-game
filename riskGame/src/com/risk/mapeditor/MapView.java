/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import com.risk.models.ContinentModel;
import com.risk.models.TerritoryModel;
import com.risk.observers.MapModelObserver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author timot
 */
public class MapView extends JPanel implements MapModelObserver  {
    protected Image image;
    protected HashMap<String, CountryButton2> countriesButtons;
    protected Dimension buttonsDims = new Dimension(100,20);
    private MapEditorController controller;
    
    /**
     * Constructor of a map view
     */
    public MapView(MapEditorController editorController) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.countriesButtons = new HashMap<>();
        this.setBackground(Color.white);
        this.setLayout(null);
        
        controller = editorController; 
        this.addMouseListener(controller.getMapMouseListener());
        
    }
    
    /**
     * Remove a country by name as each country as a unique name.
     * Removing implies deleting the associated button.
     * @param countryName 
     */
    public void removeTerritory(String countryName)
    {
        //remove from the list
        CountryButton2 buttonToDelete = countriesButtons.get(countryName);
        
        if(buttonToDelete != null){
            this.remove(buttonToDelete);
            this.countriesButtons.remove(countryName);  
        
            //redraw the view
            revalidate();
            repaint();
        }
    }
    
     protected void addTerritory(int posX, int posY, String newName){
        //add to view
        CountryButton2 newCountryButton = new CountryButton2(posX, posY, newName, buttonsDims);
        this.add(newCountryButton);
        newCountryButton.addMouseListener(controller.getCountryMouseListener());
        newCountryButton.setVisible(true);
        
        //add to internal list
        countriesButtons.put(newName, newCountryButton);
        
        //draw the new button
        revalidate();
        repaint();
    }
    
    public void update(UpdateTypes updateType, Object object){
        switch(updateType){
            case ADD_TERRITORY:
                TerritoryModel newTerritory = (TerritoryModel)object;
                addTerritory(newTerritory.getPositionX(), newTerritory.getPositionY(), newTerritory.getName());
                break;
            case REMOVE_TERRITORY:
                String territoryName = (String)object;
                System.out.println("test:"+territoryName);
                removeTerritory(territoryName);
                break;
        }
    }
    
    /**
     * Ask the user to modify a Territory's informations.
     * It shows up when the user clicked left on a Territory button.
     * @param continentsList
     * @param territoryName
     * @return 
     */
    public Map<String,String> modifyTerritory(Set<String> continentsList, String territoryName){
        Map<String,String> data = new HashMap<>();
        
        ModifyCountryPanel modifyPanel = new ModifyCountryPanel(continentsList, territoryName);
        
        int result = JOptionPane.showConfirmDialog(null, 
               modifyPanel, 
               "Modifying " + territoryName, 
               JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            return data;
        }
        else
           return data;
    }
}
