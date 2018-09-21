package com.risk.views.map;

import com.risk.models.Board;
import com.risk.models.Country;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    Image image;
    HashMap<String,Line2D> adj=new HashMap<>();
    private HashMap<String, CountryButton> countriesButtons;

    /**
     *
     * @param board
     */
    public MapPanel(Board board, MouseListener countryListener) {
        super(null);
        this.image = board.getImage();
        this.setSize(board.getImage().getWidth(null), board.getImage().getHeight(null));
        this.countriesButtons = new HashMap<>();
        this.addMouseListener(countryListener);

        Collection<Country> territories = board.getGraphTerritories().values();
        territories.stream().forEach((currentCountry) -> {
            CountryButton aux = new CountryButton(currentCountry.getPositionX(), currentCountry.getPositionY(), currentCountry.getName());
            countriesButtons.put(currentCountry.getName(), aux);
            this.add(aux);
        
            for(Country d:currentCountry.getAdj()){
                Line2D adje=new Line2D.Double();
                adje.setLine(Double.valueOf(currentCountry.getPositionX()), Double.valueOf(currentCountry.getPositionY()),Double.valueOf(d.getPositionX()),Double.valueOf(d.getPositionY()));
                adj.put(currentCountry.getName()+"-"+d.getName(),adje);
            }
        
        });
        
            
    }

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
     * @return the countriesButtons
     */
    public HashMap<String, CountryButton> getCountriesButtons() {
        return countriesButtons;
    }

    /**
     * @param countriesButtons the countriesButtons to set
     */
    public void setCountriesButtons(HashMap<String, CountryButton> countriesButtons) {
        this.countriesButtons = countriesButtons;
    }

}
