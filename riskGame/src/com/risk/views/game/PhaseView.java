/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.LogEvent;
import com.risk.models.RiskModel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author hantoine
 */
public class PhaseView extends JPanel implements Observer {

    JLabel phaseName;
    JLabel playerName;
    JTextArea actions;

    public PhaseView() {
        phaseName = new JLabel();
        playerName = new JLabel();
        actions = new JTextArea();
        actions.setEditable(false);
        actions.setColumns(120);
        actions.setRows(4);

        setVisible(false);

        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        phaseName.setBorder(BorderFactory.createCompoundBorder(phaseName.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        playerName.setBorder(BorderFactory.createCompoundBorder(playerName.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JPanel phaseAndplayerPanel = new JPanel();
        phaseAndplayerPanel.setLayout(new BoxLayout(phaseAndplayerPanel, BoxLayout.X_AXIS));
        phaseAndplayerPanel.add(phaseName);
        phaseAndplayerPanel.add(playerName);

        JScrollPane scrollableActions = new JScrollPane(actions);
        scrollableActions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableActions.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(phaseAndplayerPanel);
        this.add(scrollableActions);
    }

    public void updateView(RiskModel rm) {
        setVisible(true);

        if (rm.getWinningPlayer() != null) {
            phaseName.setVisible(false);
            playerName.setText("Winner : "
                    + rm.getWinningPlayer().getName());
            return;
        }

        phaseName.setVisible(true);
        phaseName.setText("Current phase: " + rm.getPhase().toString());
        playerName.setText("Current player: "
                + rm.getCurrentPlayer().getName());
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof RiskModel) {
            this.updateView((RiskModel) o);
        }
        if (o1 instanceof LogEvent) {
            actions.append((LogEvent) o1 + "\n");
        }
    }

}