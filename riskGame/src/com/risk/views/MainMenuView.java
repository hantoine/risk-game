/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.MainMenuController;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author hantoine
 */
public class MainMenuView extends JFrame {

    JButton singleGameMode;
    JButton tournamentMode;
    JButton mapEditor;
    JFrame backgroundWindow;

    public MainMenuView() {

        singleGameMode = new JButton("Single Game Mode");
        tournamentMode = new JButton("Tournament Mode");
        mapEditor = new JButton("Map Editor");
        backgroundWindow = new JFrame();
        backgroundWindow.setSize(1000, 563);
        backgroundWindow.setVisible(true);

        this.setSize(200, 150);
        this.setResizable(false);
        this.backgroundWindow.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        backgroundWindow.setLocation(dimension.width / 2 - 500, dimension.height / 2 - 281);

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        backgroundWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GroupLayout gl = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(this.singleGameMode)
                .addComponent(this.tournamentMode)
                .addComponent(this.mapEditor)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(this.singleGameMode)
                .addComponent(this.tournamentMode)
                .addComponent(this.mapEditor)
        );

        this.centerWindow();
    }

    public void setController(MainMenuController ctrl) {
        singleGameMode.addActionListener(e -> {
            ctrl.startSingleGameMode();
        });

        tournamentMode.addActionListener(e -> {
            ctrl.startTournamentMode();
        });

        mapEditor.addActionListener(e -> {
            ctrl.startMapEditor();
        });
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void close() {
        this.backgroundWindow.setVisible(false);
        this.setVisible(false);
        this.dispose();
        this.backgroundWindow.dispose();
    }
}
