/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.views.player.PlayerGameInfoPanel;
import com.risk.views.map.MapPanel;
import com.risk.views.menu.StartMenuView;
import com.risk.controllers.MenuListener;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author n_irahol
 */
public class RiskView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    private StartMenuView menuPanel;
    private JPanel optionPanel;
    private JPanel battlePanel;
    private MapPanel mapPanel;
    private PlayerGameInfoPanel playerPanel;

    public RiskView() {
        super("Risk Game");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Menu Panel
        this.optionPanel = new JPanel();
        this.optionPanel.setSize(300, 50);
        JButton newGame = new JButton("New Game");
        JButton saveGame = new JButton("Create Map File");

        optionPanel.setLayout(new FlowLayout());
        optionPanel.add(newGame);
        optionPanel.add(saveGame);

        //Battle panel
        this.battlePanel = new JPanel();
        this.battlePanel.setSize(300, 50);
        JButton playerOne = new JButton("Player 1");
        JButton playerTwo = new JButton("Player 2");
        JButton playerThree = new JButton("Player 3");

        battlePanel.setLayout(new FlowLayout());
        battlePanel.add(playerOne);
        battlePanel.add(playerTwo);
        battlePanel.add(playerThree);

        //Adding Panels
        optionPanel.setVisible(false);
        battlePanel.setVisible(false);

        cp.add(battlePanel, BorderLayout.SOUTH);
        cp.add(optionPanel, BorderLayout.NORTH);

    }

    public void initialMap(RiskModel riskModel, MouseListener countryListener) {
        Container cp = getContentPane();
        this.setMapPanel(new MapPanel(riskModel.getBoard(), countryListener));
        this.setPlayerPanel(new PlayerGameInfoPanel(riskModel.getCurrentPlayer()));
        this.getPlayerPanel().updatePlayer(riskModel.getCurrentPlayer());
        cp.add(this.getMapPanel(), BorderLayout.CENTER);
        cp.add(this.getPlayerPanel(), BorderLayout.EAST);
        this.setSize(this.getMapPanel().getWidth() + this.getPlayerPanel().getWidth(), this.getMapPanel().getHeight() + this.getBattlePanel().getHeight() + this.getOptionPanel().getHeight());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    public void initialMenu(RiskModel riskModel, MenuListener menuListener) {
        Container cp = getContentPane();
        setMenuPanel(new StartMenuView(riskModel, menuListener));

        cp.add(getMenuPanel(), BorderLayout.CENTER);

        this.setSize(300, 500);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    /**
     * @return the menuPanel
     */
    public StartMenuView getMenuPanel() {
        return menuPanel;
    }

    /**
     * @param menuPanel the menuPanel to set
     */
    public void setMenuPanel(StartMenuView menuPanel) {
        this.menuPanel = menuPanel;
    }

    /**
     * @return the optionPanel
     */
    public JPanel getOptionPanel() {
        return optionPanel;
    }

    /**
     * @param optionPanel the optionPanel to set
     */
    public void setOptionPanel(JPanel optionPanel) {
        this.optionPanel = optionPanel;
    }

    /**
     * @return the battlePanel
     */
    public JPanel getBattlePanel() {
        return battlePanel;
    }

    /**
     * @param battlePanel the battlePanel to set
     */
    public void setBattlePanel(JPanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    /**
     * @return the mapPanel
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    /**
     * @param mapPanel the mapPanel to set
     */
    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    /**
     * @return the playerPanel
     */
    public PlayerGameInfoPanel getPlayerPanel() {
        return playerPanel;
    }

    /**
     * @param playerPanel the playerPanel to set
     */
    public void setPlayerPanel(PlayerGameInfoPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

}
