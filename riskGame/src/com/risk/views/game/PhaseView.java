/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

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
 * Phase view displaying the current game phase, the current player, and the
 * actions happened during this phase
 *
 * @author hantoine
 */
public class PhaseView extends JPanel implements Observer {

    /**
     * label displaying the current game phase
     */
    JLabel phaseName;
    /**
     * label displaying the current player
     */
    JLabel playerName;
    /**
     * TextArea listing the actions happened during the phase
     */
    JTextArea actions;

    /**
     * Constructor
     */
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

    /**
     * This method is for update the view of risk model
     *
     * @param rm the risk model which is gonna be updated the view
     */
    public void updateView(RiskModel rm) {
        setVisible(true);

        actions.setText(rm.getLogContent());
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
        playerName.setForeground(rm.getCurrentPlayer().getColor());
        phaseName.setForeground(rm.getCurrentPlayer().getColor());
    }

    /**
     * This method is to attach the observer
     *
     * @param o the observer
     * @param o1 the object
     */
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof RiskModel) {
            this.updateView((RiskModel) o);
        }
    }

}
