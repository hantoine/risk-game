package com.risk.views;


import com.risk.controllers.GameListener;
import com.risk.models.Board;
import com.risk.models.Country;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.TransferHandler;


public class MapPanel extends JPanel {

   
    Board board;
    private HashMap<String,CountryButton> countriesButtons;

    /**
     *
     * @param board
     */
    public MapPanel(Board board) {
        super(null);
        this.board = board;
        this.setSize(this.board.getImage().getWidth(null), this.board.getImage().getHeight(null));
        this.countriesButtons=new HashMap<>();
        MouseListener countryListener= new GameListener();
        this.addMouseListener(countryListener);
        
        
        Collection<Country> territories = board.getGraphTerritories().values();
        territories.stream().forEach((currentCountry) -> {
            CountryButton aux=new CountryButton(currentCountry.getPositionX(),currentCountry.getPositionY(),currentCountry.getName());
            
            aux.setTransferHandler(new TransferHandler("country"));
            countriesButtons.put(currentCountry.getName(), aux);
            this.add(aux);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.board.getImage(), 0, 0, null);
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
