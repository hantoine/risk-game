/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.TournamentController;
import com.risk.views.menu.PlayerListPanel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author hantoine
 */
public class TournamentMenuView extends JFrame {

    PlayerListPanel playerListPanel;

    public TournamentMenuView() throws HeadlessException {
        this.setSize(200, 150);
        this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.centerWindow();
        this.setVisible(true);

        playerListPanel = new PlayerListPanel();
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void setController(TournamentController ctrl) {

    }

}
