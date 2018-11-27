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
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

/**
 * This class represents the tournament view
 *
 * @author hantoine
 */
public class TournamentMenuView extends JFrame implements Observer {

    /**
     * Panel for adding strategies
     */
    StrategyListPanel strategyListPanel;
    /**
     * Panel for choosing the map path
     */
    MapPathListPanel mapPathListPanel;
    /**
     * Number of game for each map
     */
    JLabel nbGamePerMapLabel;
    /**
     * Number of game for each map
     */
    JSpinner nbGamePerMap;
    /**
     * Maximum number of turn in a game
     */
    JLabel nbMaximumTurnPerGameLabel;
    /**
     * Maximum number of turn in a game
     */
    JSpinner nbMaximumTurnPerGame;
    /**
     * Play tournament button
     */
    JButton playButton;

    /**
     * Constructor
     *
     * @throws HeadlessException HeadlessException
     */
    public TournamentMenuView() throws HeadlessException {
        this.setSize(604, 432);
        this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.centerWindow();

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

        this.setVisible(true);
    }

    /**
     * Set the window in the center
     */
    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    /**
     * Set and add listeners for strategy panel and maps panel
     *
     * @param ctrl Tournament controller
     */
    public void setController(TournamentController ctrl) {
        strategyListPanel.setListener(ctrl);
        mapPathListPanel.setListener(ctrl);
        this.playButton.addActionListener((al) -> {
            ctrl.playTournament();
        });
        this.nbMaximumTurnPerGame.addChangeListener((ChangeEvent ce) -> {
            ctrl.nbMaximumTurnPerGameChanged((int) ((JSpinner) ce.getSource()).getValue());
        });

        this.nbGamePerMap.addChangeListener((ChangeEvent ce) -> {
            ctrl.nbGamePerMapChanged((int) ((JSpinner) ce.getSource()).getValue());
        });
    }

    /**
     * Observer method
     *
     * @param o a tournament model
     * @param o1 object
     */
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof TournamentModel) {
            TournamentModel tm = (TournamentModel) o;
            this.strategyListPanel.updateView(tm);
            this.mapPathListPanel.updateView(tm);
        }
    }

    /**
     * Show error message
     *
     * @param message error message
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

}
