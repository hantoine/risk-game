package com.risk.views.game;

import com.risk.models.MapModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * JPanel that contains the map of the game
 *
 * @author Nellybett
 */
public class MapPanel extends JPanel implements Observer {

    /**
     * image Reference to the image of the map
     */
    Image image;
    /**
     * adj lines that represent the adjacencies
     */
    HashMap<String, Line2D> adj = new HashMap<>();
    /**
     * territoriesButtons the territories in the map
     */
    private HashMap<String, TerritoryLabel> territoriesButtons;

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
     *
     * @param mapModel the map model to be created
     */
    private void createMap(MapModel mapModel) {
        clearMap();
        this.image = mapModel.getImage();
        this.setSize(mapModel.getMapWidth(), mapModel.getMapHeight());
        this.territoriesButtons = new HashMap<>();

        mapModel.getTerritories().forEach((currentTerr) -> {
            TerritoryLabel aux = new TerritoryLabel(
                    currentTerr.getPositionX(),
                    currentTerr.getPositionY(),
                    currentTerr.getName()
            );
            territoriesButtons.put(currentTerr.getName(), aux);
            this.add(aux);

            if (mapModel.getImage() == null) {
                for (TerritoryModel d : currentTerr.getAdj()) {
                    Line2D adje = new Line2D.Double();
                    adje.setLine(
                            Double.valueOf(currentTerr.getPositionX()),
                            Double.valueOf(currentTerr.getPositionY()),
                            Double.valueOf(d.getPositionX()),
                            Double.valueOf(d.getPositionY())
                    );
                    adj.put(currentTerr.getName() + "-" + d.getName(), adje);
                }
            }
        });
    }

    /**
     * Attach the listener to the map
     *
     * @param territoryListener the mouselistener of the territory
     */
    public void setListener(MouseListener territoryListener) {
        Arrays.stream(this.getMouseListeners())
                .forEach((m)->{
            this.removeMouseListener(m);
        });
        
        this.addMouseListener(territoryListener);
    }

    /**
     * Updates the view with the changes in the model
     *
     * @param map reference to the model of the map
     * @param mapChanged the map which is changed
     */
    public void updateView(MapModel map, boolean mapChanged) {
        if (mapChanged) {
            this.createMap(map);
        }

        this.territoriesButtons.values()
                .forEach((cb) -> {
                    TerritoryModel terri = map.getTerritoryByName(cb.getName());
                    if (terri != null) {
                        cb.setText(Integer.toString(terri.getNumArmies()));
                        if (terri.getOwner() != null) {
                            cb.setForeground(terri.getOwner().getColor());
                        }
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
     * Getter of the territoriesButton attribute
     *
     * @return the territoriesButtons
     */
    public HashMap<String, TerritoryLabel> getTerritoriesButtons() {
        return territoriesButtons;
    }

    /**
     * Clears the map to its initial state
     */
    private void clearMap() {
        if (this.territoriesButtons != null) {
            this.territoriesButtons.values().stream().forEach((cb) -> {
                this.remove(cb);
            });
            this.territoriesButtons = null;
        }

        this.adj = new HashMap<>();
    }

    /**
     * Getter of the image of the map
     *
     * @return the image of the map
     */
    public Image getImage() {
        return image;
    }

    /**
     * This method is to attach the observeer
     * @param o the observable
     * @param o1 the object
     */
    @Override
    public void update(Observable o, Object o1) {
        boolean mapChanged = false;
        if (o1 instanceof Boolean) {
            mapChanged = (Boolean) o1;
        }
        if (o instanceof MapModel) {
            this.updateView((MapModel) o, mapChanged);
        } else if (o instanceof RiskModel) {
            this.updateView(((RiskModel) o).getMap(), mapChanged);
        }
    }

}
