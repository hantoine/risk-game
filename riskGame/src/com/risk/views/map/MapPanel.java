package com.risk.views.map;

import com.risk.models.MapModel;
import com.risk.models.RiskModel;
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
 *
 * @author Nellybett
 */
public class MapPanel extends JPanel {

    /**
     * image Reference to the image of the map
     */
    Image image;
    /**
     * adj lines that represent the adjacencies
     */
    HashMap<String, Line2D> adj = new HashMap<>();
    /**
     * countriesButtons the countries in the map
     */
    private HashMap<String, CountryLabel> countriesButtons;

    /**
     * Constructor
     *
     */
    public MapPanel() {
        super(null);
        setVisible(true);
        setSize(400, 600);
    }

    /**
     * Creates the map of the game from the model
     * @param mapModel 
     */
    private void createMap(MapModel mapModel) {
        clearMap();
        this.image = mapModel.getImage();
        this.setSize(mapModel.getMapWidth(), mapModel.getMapHeight());
        this.countriesButtons = new HashMap<>();

        Collection<TerritoryModel> territories = mapModel.getGraphTerritories().values();
        territories.stream().forEach((currentCountry) -> {
            CountryLabel aux = new CountryLabel(currentCountry.getPositionX(), currentCountry.getPositionY(), currentCountry.getName());
            countriesButtons.put(currentCountry.getName(), aux);
            this.add(aux);

            if (mapModel.getImage() == null) {
                for (TerritoryModel d : currentCountry.getAdj()) {
                    Line2D adje = new Line2D.Double();
                    adje.setLine(Double.valueOf(currentCountry.getPositionX()), Double.valueOf(currentCountry.getPositionY()), Double.valueOf(d.getPositionX()), Double.valueOf(d.getPositionY()));
                    adj.put(currentCountry.getName() + "-" + d.getName(), adje);
                }
            }
        });
    }

    /**
     * Attach the listener to the map
     * @param countryListener 
     */
    public void setListener(MouseListener countryListener) {
        this.addMouseListener(countryListener);
    }

    /**
     * Updates the view with the changes in the model
     * @param rm model reference
     * @param mapChanged 
     */
    public void updateView(RiskModel rm, boolean mapChanged) {
        if (mapChanged) {
            this.createMap(rm.getMap());
        }

        HashMap<String, TerritoryModel> graphTerritories = rm.getMap().getGraphTerritories();
        this.countriesButtons.values().stream()
                .forEach((countryButton) -> {
                    TerritoryModel territory = graphTerritories.get(countryButton.getName());
                    countryButton.setText(Integer.toString(territory.getNumArmies()));
                    if (territory.getOwner() != null) {
                        countryButton.setForeground(territory.getOwner().getColor());
                    }
                });
    }

    /**
     * It paints the adjacent and the image in the panel
     *
     * @param g Graphics object used to draw
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(this.image, 0, 0, null);

        for (Line2D line : this.adj.values()) {
            g2.draw(line);
        }
    }

    /**
     * Getter of the countriesButton attribute
     *
     * @return the countriesButtons
     */
    public HashMap<String, CountryLabel> getCountriesButtons() {
        return countriesButtons;
    }

    /**
     * Setter of the countriesButton attribute
     *
     * @param countriesButtons the countriesButtons to set
     */
    public void setCountriesButtons(HashMap<String, CountryLabel> countriesButtons) {
        this.countriesButtons = countriesButtons;
    }

    /**
     * Clears the map to its initial state
     */
    private void clearMap() {
        if (this.countriesButtons != null) {
            this.countriesButtons.values().stream().forEach((cb) -> {
                this.remove(cb);
            });
            this.countriesButtons = null;
        }

        this.adj = new HashMap<>();
    }

    /**
     * Getter of the image of the map
     * @return 
     */
    public Image getImage() {
        return image;
    }

}
