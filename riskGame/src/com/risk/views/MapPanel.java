package com.risk.views;


import com.risk.controllers.MapListener;
import com.risk.models.Board;
import com.risk.models.Country;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JPanel;



public class MapPanel extends JPanel {

   
    Image image;
    private HashMap<String,CountryButton> countriesButtons;

    /**
     *
     * @param board
     */
    public MapPanel(Board board) {
        super(null);
        this.image = board.getImage();
        this.setSize(board.getImage().getWidth(null), board.getImage().getHeight(null));
        this.countriesButtons=new HashMap<>();
        MouseListener countryListener= new MapListener();
        this.addMouseListener(countryListener);
        
        
        Collection<Country> territories = board.getGraphTerritories().values();
        territories.stream().forEach((currentCountry) -> {
            CountryButton aux=new CountryButton(currentCountry.getPositionX(),currentCountry.getPositionY(),currentCountry.getName());
            countriesButtons.put(currentCountry.getName(), aux);
            this.add(aux);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, null);
    }
    
     /**
     * @return the countriesButtons
     */
    public HashMap<String,CountryButton> getCountriesButtons() {
        return countriesButtons;
    }

    /**
     * @param countriesButtons the countriesButtons to set
     */
    public void setCountriesButtons(HashMap<String,CountryButton> countriesButtons) {
        this.countriesButtons = countriesButtons;
    }

}
