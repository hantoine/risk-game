/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.controllers.MapEditorController;
import com.risk.models.TerritoryModel;
import com.risk.observers.MapModelObserver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
    protected Image backgroundImage;
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
     * Change background image of the map
     * @param backgroundImage 
     */
    public void setImage(Image backgroundImage){
        this.backgroundImage = backgroundImage;
        repaint(); //call to paintComponent
    }
    
    @Override
    /**
     * To paint the images into the JPanel
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundImage!=null)
            g.drawImage(backgroundImage, 0, 0, null);
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
        String territoryName;
        
        switch(updateType){
            case ADD_TERRITORY:
                TerritoryModel newTerritory = (TerritoryModel)object;
                addTerritory(newTerritory.getPositionX(), newTerritory.getPositionY(), newTerritory.getName());
                break;
            case REMOVE_TERRITORY:
                territoryName = (String)object;
                removeTerritory(territoryName);
                break;
            case UPDATE_TERRITORY:
                Map<String,String> data= (Map<String,String>)object;
                String formerName = data.get("name");
                String newName = data.get("newName");
                
                CountryButton2 territoryButton = this.countriesButtons.remove(formerName);
                if(territoryButton != null){
                    territoryButton.setName(newName);
                    countriesButtons.put(newName, territoryButton);
                }
                break;
        }
    }
    
    /**
     * Ask the user to modify a Territory's informations.
     * It shows up when the user clicked left on a Territory button.
     * @param continentsList
     * @param territoryName
     * @param continentName
     * @return 
     */
    public Map<String,String> modifyTerritory(String[] continentsList, String territoryName, String continentName){
        Map<String,String> data = new HashMap<>();
        
        ModifyCountryPanel modifyPanel = new ModifyCountryPanel(continentsList, territoryName, continentName);
        String boxName="Modifying " + territoryName;
        int result = JOptionPane.showConfirmDialog(null, 
               modifyPanel, 
               boxName, 
               JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            data.put("newName", modifyPanel.getTerritoryName());
            data.put("continent", modifyPanel.getTerritoryContinent());
            return data;
        }
        else
           return data;
    }
}
