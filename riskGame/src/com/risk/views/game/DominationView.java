/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.RiskModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * This class represents the domination percentage of each player
 *
 * @author hantoine
 */
public class DominationView extends JPanel implements Observer {

    /**
     * the player game information panel
     */
    Map<String, PlayerGameInfoPanel> playerGameInfoPanels;

    /**
     * This method is for set the new layout
     */
    public DominationView() {
        playerGameInfoPanels = new HashMap<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * This method is for update the view
     *
     * @param rm the risk model
     */
    public void updateView(RiskModel rm) {
        rm.getPlayerList().stream().forEach((pl) -> {
            if (!playerGameInfoPanels.keySet().contains(pl.getName())) {
                PlayerGameInfoPanel newPlayerPanel = new PlayerGameInfoPanel();
                this.add(newPlayerPanel);
                playerGameInfoPanels.put(pl.getName(), newPlayerPanel);
            }
            playerGameInfoPanels.get(pl.getName()).updateView(pl);

        });

        List<String> deadPlayersNames = playerGameInfoPanels.keySet().stream()
                .filter((panelPlayerName) -> rm.getPlayerList().stream()
                .noneMatch((pl) -> pl.getName().equals(panelPlayerName))
                ).collect(Collectors.toList());

        deadPlayersNames.forEach((pl) -> {
            this.remove(playerGameInfoPanels.get(pl));
            playerGameInfoPanels.remove(pl);
        });

        this.revalidate();
        this.repaint();
    }

    /**
     * This method is for update the view
     *
     * @param o Observable
     * @param o1 Object
     */
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof RiskModel) {
            this.updateView((RiskModel) o);
        }
    }

}
