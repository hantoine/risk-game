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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    protected Tools selectedTool;
    protected HashMap<String,Line2D> links;
    
    /**
     * Constructor of a map view
     */
    public MapView(MapEditorController editorController) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.countriesButtons = new HashMap<>();
        this.links = new HashMap<>();
        this.setBackground(Color.white);
        this.setLayout(null);
        
        controller = editorController; 
        this.addMouseListener(controller.getMapMouseListener());
    }
    
    public Tools getCurrentTool(){
        return this.selectedTool;
    }
    
    public void setCurrentTool(Tools toolInUse){
        this.selectedTool=toolInUse;
    }
    
    /**
     * Change background image of the map
     * @param backgroundImage 
     */
    public void setImage(BufferedImage backgroundImage){
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
        
        Graphics2D g2 = (Graphics2D) g;
        this.links.values().forEach((link) -> {
            g2.draw(link);
        });
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
    
    public Dimension getButtonDimension(){
        return buttonsDims;
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
    
    @Override
    public void update(UpdateTypes updateType, Object object){
        String territoryName;
        CountryButton2 territoryButton;
        TerritoryModel territoryModel;
        String name1;
        String name2;
        String[] linkNames;
        
        switch(updateType){
            case ADD_TERRITORY:
                TerritoryModel newTerritory = (TerritoryModel)object;
                addTerritory(newTerritory.getPositionX(), newTerritory.getPositionY(), newTerritory.getName());
                break;
            case REMOVE_TERRITORY:
                territoryName = (String)object;
                removeTerritory(territoryName);
                break;
            case UPDATE_TERRITORY_NAME:
                Map<String,String> data= (Map<String,String>)object;
                String formerName = data.get("name");
                String newName = data.get("newName");
                
                territoryButton = this.countriesButtons.remove(formerName);
                if(territoryButton != null){
                    territoryButton.setName(newName);
                    countriesButtons.put(newName, territoryButton);
                }
                break;
            case UPDATE_TERRITORY_POS:
                territoryModel = (TerritoryModel)object;
                territoryButton = this.countriesButtons.get(territoryModel.getName());
                territoryButton.setPosition(territoryModel.getPositionX(), territoryModel.getPositionY());
                break;
            case UPDATE_BACKGROUND_IMAGE:
                break;
            case UPDATE_CONTINENT:
                break;
            case REMOVE_LINK:  
                linkNames = (String[])object;
                
                //get key of the link
                if(getAsciiValue(linkNames[0])>getAsciiValue(linkNames[1])){
                    name1=linkNames[0];
                    name2=linkNames[1];
                }
                else{
                    name1=linkNames[1];
                    name2=linkNames[0];
                }
                String linkName= name1+";"+name2;
                
                //remove from list of links
                links.remove(linkName);
                
                //undraw
                repaint();
                break;
            case ADD_LINK:
                linkNames = (String[])object;
                CountryButton2 firstItem = this.countriesButtons.get(linkNames[0]);
                CountryButton2 secondItem = this.countriesButtons.get(linkNames[1]);
                
                //create line to be drawn
                Line2D newLink = new Line2D.Double();
                newLink.setLine(firstItem.getBounds().x + ((double)this.buttonsDims.width/2.), 
                        firstItem.getBounds().y + ((double)this.buttonsDims.height/2.),
                        secondItem.getBounds().x + ((double)this.buttonsDims.width/2.),
                        secondItem.getBounds().y + ((double)this.buttonsDims.height/2.));
                
                //define a name
                if(getAsciiValue(linkNames[0])>getAsciiValue(linkNames[1])){
                    name1=linkNames[0];
                    name2=linkNames[1];
                }
                else{
                    name1=linkNames[1];
                    name2=linkNames[0];
                }
                
                //add to list
                links.put(name1+";"+name2, newLink);
                
                //draw line
                repaint();
                break;
        }
    }
    
    /**
     * Trick to find an order between two Strings
     * @param string
     * @return 
     */
    public int getAsciiValue(String string){
        int sum = 0;
        for(Character c : string.toCharArray()){
            sum+=(int)c;
        }
        return sum;
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
            //check if name not already used
            for(String otherName: this.countriesButtons.keySet()){
                if(modifyPanel.getTerritoryName().equals(otherName))
                {
                    this.showError("This name is already used");
                    return data;
                }
            }
            
            data.put("newName", modifyPanel.getTerritoryName());
            data.put("continent", modifyPanel.getTerritoryContinent());
            return data;
        }
        else
           return data;
    }
    
    public String createLink(String[] territoryArray, String territoryName){
        
        String boxName="Add link to " + territoryName;
        String neighbour = (String)JOptionPane.showInputDialog(
                    null,
                    "Select a new neighbour for " + territoryName,
                    boxName,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    territoryArray,
                    territoryArray[0]);
        return neighbour;
    }
    
    public String removeLink(LinkedList<String> neighbourStringList){
        String boxName="Remove link";
        String[] territoryArray = neighbourStringList.toArray(new String[neighbourStringList.size()]);
        String neighbour = (String)JOptionPane.showInputDialog(
                    null,
                    "Choose a link to remove",
                    boxName,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    territoryArray,
                    territoryArray[0]);
        return neighbour;
    }
    
    /**
     * Show a popup error to the user to inform of an error
     * @param errorMessage 
     */
    public void showError(String errorMessage){
        JOptionPane.showMessageDialog(null,
            errorMessage,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
