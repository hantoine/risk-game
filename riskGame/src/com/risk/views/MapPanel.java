package com.risk.views;

import com.risk.models.Board;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private final Board board;

    public MapPanel(Board board) {
        this.board = board;
        this.setSize(this.board.getImage().getWidth(null), this.board.getImage().getHeight(null));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.board.getImage(), 0, 0, null);
    }

}
