/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.controllers.TournamentController;
import com.risk.models.TournamentModel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author hantoine
 */
public class TournamentMenuView extends JFrame implements Observer {

    StrategyListPanel strategyListPanel;
    MapPathListPanel mapPathListPanel;
    JLabel nbGamePerMapLabel;
    JSpinner nbGamePerMap;
    JLabel nbMaximumTurnPerGameLabel;
    JSpinner nbMaximumTurnPerGame;
    JButton playButton;

    public TournamentMenuView() throws HeadlessException {
        this.setSize(600, 409);
        this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.centerWindow();
        this.setVisible(true);

        strategyListPanel = new StrategyListPanel();
        mapPathListPanel = new MapPathListPanel();
        nbGamePerMapLabel = new JLabel(
                "Number of game for each map:");
        nbGamePerMap = new JSpinner(
                new SpinnerNumberModel(4, 1, 5, 1));
        nbMaximumTurnPerGameLabel = new JLabel(
                "Maximum number of turn in a game:");
        nbMaximumTurnPerGame = new JSpinner(
                new SpinnerNumberModel(30, 10, 50, 1));
        playButton = new JButton("Play");

        GroupLayout gl = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup()
                        .addComponent(strategyListPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mapPathListPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
                .addGroup(gl.createParallelGroup()
                        .addComponent(nbGamePerMapLabel)
                        .addComponent(nbGamePerMap,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(nbMaximumTurnPerGameLabel)
                        .addComponent(nbMaximumTurnPerGame,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
                .addComponent(playButton)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup()
                        .addComponent(strategyListPanel)
                        .addComponent(mapPathListPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
                .addGroup(gl.createSequentialGroup()
                        .addComponent(nbGamePerMapLabel)
                        .addComponent(nbGamePerMap,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(nbMaximumTurnPerGameLabel)
                        .addComponent(nbMaximumTurnPerGame,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                )
                .addComponent(playButton,
                        0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void setController(TournamentController ctrl) {
        strategyListPanel.setListener(ctrl);
        mapPathListPanel.setListener(ctrl);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof TournamentModel) {
            TournamentModel tm = (TournamentModel) o;
            this.strategyListPanel.updateView(tm);
            this.mapPathListPanel.updateView(tm);
            System.out.println(this.getPreferredSize());
        }
    }

}
