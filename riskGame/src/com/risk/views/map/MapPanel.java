package com.risk.views.map;

import com.risk.models.MapModel;
import com.risk.models.TerritoryModel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 * JPanel that contains the map of the game
 * @author Nellybett
 */
public class MapPanel extends JPanel {

    Image image;
    HashMap<String,Line2D> adj=new HashMap<>();
    private HashMap<String, CountryButton> countriesButtons;

    /**
     *Constructor
     * @param board map in the model
     */
    public MapPanel(MapModel board, MouseListener countryListener) {
        super(null);
        this.image = board.getImage();
        this.setSize(board.getImage().getWidth(null), board.getImage().getHeight(null));
        this.countriesButtons = new HashMap<>();
        this.addMouseListener(countryListener);

        Collection<TerritoryModel> territories = board.getGraphTerritories().values();
        territories.stream().forEach((currentCountry) -> {
            CountryButton aux = new CountryButton(currentCountry.getPositionX(), currentCountry.getPositionY(), currentCountry.getName());
            countriesButtons.put(currentCountry.getName(), aux);
            this.add(aux);
        
            for(TerritoryModel d:currentCountry.getAdj()){
                Line2D adje=new Line2D.Double();
                adje.setLine(Double.valueOf(currentCountry.getPositionX()), Double.valueOf(currentCountry.getPositionY()),Double.valueOf(d.getPositionX()),Double.valueOf(d.getPositionY()));
                adj.put(currentCountry.getName()+"-"+d.getName(),adje);
            }
        });
    }

    /**
     * It paints the adj and the image in the panel
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(this.image, 0, 0, null);
        
        for(Line2D line:this.adj.values()){
            g2.draw(line);
        }
    }

    /**
     * Getter of the countriesButton attribute
     * @return the countriesButtons
     */
    public HashMap<String, CountryButton> getCountriesButtons() {
        return countriesButtons;
    }

    /**
     * Setter of the countriesButton attribute
     * @param countriesButtons the countriesButtons to set
     */
    public void setCountriesButtons(HashMap<String, CountryButton> countriesButtons) {
        this.countriesButtons = countriesButtons;
    }

}
