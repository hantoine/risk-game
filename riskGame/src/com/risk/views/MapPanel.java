package com.risk.views;

import com.risk.models.Board;
import com.risk.models.Country;
import java.awt.Graphics;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private final Board board;

    public MapPanel(Board board) {
        super(null);
        this.board = board;
        this.setSize(this.board.getImage().getWidth(null), this.board.getImage().getHeight(null));
        Collection<Country> territories = board.getGraphTerritories().values();
        territories.stream().forEach((currentCountry) -> {
            this.add(new CountryButton(currentCountry));
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.board.getImage(), 0, 0, null);
    }

}
