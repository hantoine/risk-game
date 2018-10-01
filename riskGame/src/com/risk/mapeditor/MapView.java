/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.observers.MapModelObserver;
import com.risk.views.map.CountryButton;
import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author timot
 */
public class MapView extends JPanel implements MapModelObserver  {
    protected Image image;
    protected HashMap<String, CountryButton> countriesButtons;
    
    /**
     * Constructor of a map view
     */
    public MapView() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.countriesButtons = new HashMap<>();
        
    }
    
    /**
     * Create a new button for the country to be created and add it to the view.
     * @param countryName
     * @param x
     * @param y 
     */
    public void addCountry(String countryName, int x, int y)
    {
        //test if countryName, x or y not already used
        
        //create a button
        CountryButton newButton = new CountryButton(x, y, countryName);
        
        //add to internal list
        this.countriesButtons.put(countryName, newButton);
        
        //draw the new button
        this.add(newButton);
        revalidate();
        repaint();
    }
    
    /**
     * Remove a country by name as each country as a unique name.
     * Removing implies deleting the associated button.
     * @param countryName 
     */
    public void removeCountry(String countryName)
    {
        
        this.countriesButtons.remove(countryName);
        
        revalidate();
        repaint();
    }
    
     protected void addCountry(int posX, int posY, String newName){
         //add to internal list
        CountryButton countryButton = new CountryButton(posX, posY, newName);
        countriesButtons.put(newName, countryButton);
        this.add(countryButton);
        countryButton.setVisible(true);
        
        //draw the new button
        revalidate();
        repaint();
    }
    
    protected void addContinent(){
        
    }
    
    protected void editCountry(){
        
    }
    
    public void update(int posX, int posY, String newName){
        System.out.println("Update call received!");
        addCountry(posX, posY, newName);
    }
    
}
