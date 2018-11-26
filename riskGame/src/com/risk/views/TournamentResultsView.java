/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.TournamentController;
import com.risk.models.TournamentModel;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author hantoine
 */
public class TournamentResultsView extends JFrame {

    JTable results;

    public TournamentResultsView(TournamentModel tm) {
        this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.centerWindow();
        this.setVisible(true);
        results = new JTable(tm);

        GroupLayout gl = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(results)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(results)
        );

        this.setSize(this.getPreferredSize());
        this.setTitle("Tournament Result");
        this.setVisible(true);
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void setController(TournamentController ctrl) {

    }

}