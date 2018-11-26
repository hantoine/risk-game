/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.models.Strategy;
import com.risk.models.TournamentModel;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Panel that contains multiple panels with players information
 *
 * @author Nellybett
 */
public class StrategyListPanel extends JPanel {

    JComboBox<Strategy.Type> strategyCombo;
    JButton addButton;
    JPanel strategiesList;
    StrategyListPanelListener listener;

    public StrategyListPanel() {
        strategyCombo = new JComboBox<>(
                Arrays.stream(Strategy.Type.values())
                        .filter((s -> s != Strategy.Type.HUMAN))
                        .toArray(Strategy.Type[]::new)
        );

        addButton = new JButton("+");
        strategiesList = new JPanel();
        this.strategiesList.setLayout(
                new BoxLayout(strategiesList, BoxLayout.Y_AXIS));

        GroupLayout gl = new GroupLayout(this);
        this.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup()
                        .addComponent(
                                strategyCombo,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(addButton)
                )
                .addComponent(strategiesList)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup()
                        .addComponent(
                                strategyCombo,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(addButton)
                )
                .addComponent(strategiesList)
        );

        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                "Strategies"
        ));
    }

    public void updateView(TournamentModel tm) {
        this.strategiesList.removeAll();

        tm.getPlayerStategies().forEach((s) -> {
            RemovableItemPanel<Strategy.Type> newStrategy
                    = new RemovableItemPanel<>(s);
            newStrategy.setListener(() -> {
                listener.strategyRemoved(s);
            });
            this.strategiesList.add(newStrategy);
        });

        int i;
        for (i = 0; i < this.strategyCombo.getItemCount(); i++) {
            if (!tm.getPlayerStategies()
                    .contains(this.strategyCombo.getItemAt(i))) {
                this.strategyCombo.setSelectedIndex(i);
                break;
            }
        }
        this.addButton.setEnabled(i != this.strategyCombo.getItemCount());

        this.strategyCombo.setSelectedItem(tm);

        this.revalidate();
        this.repaint();
    }

    public void setListener(StrategyListPanelListener listener) {
        this.addButton.addActionListener((ActionEvent ae) -> {
            listener.strategyAdded(
                    strategyCombo.getItemAt(strategyCombo.getSelectedIndex()));
        });

        this.listener = listener;
    }

    public static interface StrategyListPanelListener {

        void strategyAdded(Strategy.Type strategyType);

        void strategyRemoved(Strategy.Type strategyType);

    }

}
