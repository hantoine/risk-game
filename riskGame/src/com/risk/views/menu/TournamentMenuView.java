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
import javax.swing.JFrame;

/**
 *
 * @author hantoine
 */
public class TournamentMenuView extends JFrame implements Observer {

    StrategyListPanel strategyListPanel;

    public TournamentMenuView() throws HeadlessException {
        this.setSize(200, 150);
        //this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.centerWindow();
        this.setVisible(true);

        strategyListPanel = new StrategyListPanel();

        GroupLayout gl = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(strategyListPanel)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(strategyListPanel)
        );
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void setController(TournamentController ctrl) {
        strategyListPanel.setListener(ctrl);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof TournamentModel) {
            TournamentModel tm = (TournamentModel) o;
            this.strategyListPanel.updateView(tm);
        }
    }

}
