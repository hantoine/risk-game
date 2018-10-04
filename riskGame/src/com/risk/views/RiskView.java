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
import com.risk.controllers.RiskController;
import com.risk.models.GameStage;
import com.risk.models.RiskModel;
import com.risk.views.menu.MenuView;
import com.risk.views.phases.StagePanel;
import com.risk.views.player.PlayerGameHandPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Main View of the game
 *
 * @author n_irahol
 */
public final class RiskView extends javax.swing.JFrame {

    private MenuView menuPanel;
    private JPanel optionPanel;
    private JPanel battlePanel;
    private MapPanel mapPanel;
    private PlayerGameInfoPanel playerPanel;
    private PlayerGameHandPanel playerHandPanel;
    private RiskController riskController;
    private JButton phase;
    private StagePanel stagePanel;

    /**
     * Constructor of main view
     */
    public RiskView() {
        super("Risk Game");

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setStagePanel(new StagePanel());
        cp.add(this.stagePanel, BorderLayout.NORTH);
    }

    public void updateView(RiskModel rm) {
        this.getStagePanel().updatePhase(rm.getStage(), rm.getCurrentPlayer().getNumArmiesAvailable());
        this.getMapPanel().updateView(rm);
        this.getPlayerPanel().updatePlayer(rm.getCurrentPlayer());
        this.getPlayerHandPanel().updatePlayer(rm.getCurrentPlayer());

    }

    /**
     * Initialize the map image and elements
     *
     * @param riskModel model of the game
     * @param countryListener listen for the mouse events in the map
     */
    public void initialMap(RiskModel riskModel, MouseListener countryListener) {
        this.setMapPanel(new MapPanel(riskModel));
        this.getMapPanel().setListener(countryListener);
    }

    /**
     * Initialize the new game menu
     *
     * @param riskModel model of the game
     * @param menuListener listen the events in the menu
     */
    public void initialMenu(RiskModel riskModel, MenuListener menuListener) {

        StartMenuView start = new StartMenuView(riskModel, menuListener);
        MenuView aux = new MenuView(start, this, "New Game");
        this.setMenuPanel(aux);
        aux.add(start);
        aux.setVisible(true);
        if (this.getMapPanel() == null) {
            this.setSize(800, 600);
        }
        aux.setSize(300, 500);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        aux.setLocation(dimension.width / 2 - 300 / 2, dimension.height / 2 - 500 / 2);
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    /**
     * Initialize the information of a player
     *
     * @param riskModel model of the game
     */
    public void initialPlayer(RiskModel riskModel) {
        if (this.getPlayerPanel() != null) {
            this.remove(this.getPlayerPanel());
        }
        this.setPlayerPanel(new PlayerGameInfoPanel());
        this.getPlayerPanel().updatePlayer(riskModel.getCurrentPlayer());
        getContentPane().add(this.getPlayerPanel(), BorderLayout.EAST);
    }

    /**
     * Initialize the player hand panel in charge of displaying the cards of the
     * player
     *
     * @param riskModel model of the game
     */
    public void initialPlayerHandPanel(RiskModel riskModel) {
        this.setPlayerHandPanel(new PlayerGameHandPanel());
        this.getPlayerHandPanel().updatePlayer(riskModel.getCurrentPlayer());
    }

    public PlayerGameHandPanel getPlayerHandPanel() {
        return playerHandPanel;
    }

    private void setPlayerHandPanel(PlayerGameHandPanel playerHandPanel) {
        if (this.playerHandPanel != null) {
            this.remove(this.playerHandPanel);
        }
        this.playerHandPanel = playerHandPanel;
        this.getContentPane().add(this.playerHandPanel, BorderLayout.SOUTH);
    }

    /**
     * Initialize the menu bar of the game
     */
    public void addMenuBar() {
        JMenuBar menuBar;
        JMenu menuFile, menuOption;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Build the first menu.
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_A);
        menuFile.getAccessibleContext().setAccessibleDescription("File");
        menuBar.add(menuFile, BorderLayout.NORTH);

        //a group of JMenuItems
        menuFile.setLayout(new BoxLayout(menuFile, BoxLayout.Y_AXIS));
        menuItem = new JMenuItem("New Game");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Show New Game");
        menuItem.addActionListener(riskController);
        menuFile.add(menuItem);

        //Build 2do menu
        menuOption = new JMenu("Options");
        menuOption.setMnemonic(KeyEvent.VK_A);
        menuOption.getAccessibleContext().setAccessibleDescription("Options");
        menuBar.add(menuOption, BorderLayout.NORTH);

        this.setJMenuBar(menuBar);
        this.getJMenuBar().setVisible(true);
    }

    /**
     * Getter of the menuPanel attribute
     *
     * @return the menuPanel
     */
    public MenuView getMenuPanel() {
        return menuPanel;
    }

    /**
     * Setter of the menuPanel attribute
     *
     * @param menuPanel the menuPanel to set
     */
    public void setMenuPanel(MenuView menuPanel) {
        this.menuPanel = menuPanel;
    }

    /**
     * Getter of the optionPanel attribute
     *
     * @return the optionPanel
     */
    public JPanel getOptionPanel() {
        return optionPanel;
    }

    /**
     * Setter of the optionPanel attribute
     *
     * @param optionPanel the optionPanel to set
     */
    public void setOptionPanel(JPanel optionPanel) {
        this.optionPanel = optionPanel;
    }

    /**
     * Getter of the battlePanel attribute
     *
     * @return the battlePanel
     */
    public JPanel getBattlePanel() {
        return battlePanel;
    }

    /**
     * Setter of the battlePanel attribute
     *
     * @param battlePanel the battlePanel to set
     */
    public void setBattlePanel(JPanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    /**
     * Getter of the mapPanel attribute
     *
     * @return the mapPanel
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    /**
     * Setter of the mapPanel attribute
     *
     * @param mapPanel the mapPanel to set
     */
    public void setMapPanel(MapPanel mapPanel) {
        if (this.mapPanel != null) {
            this.remove(this.mapPanel);
        }

        this.mapPanel = mapPanel;

        getContentPane().add(this.mapPanel, BorderLayout.CENTER);

        this.setSize(
                this.mapPanel.getWidth() + this.getPlayerPanel().getWidth() + 150,
                this.getStagePanel().getHeight() + Math.max(this.mapPanel.getHeight(), this.getPlayerPanel().getHeight()) + this.getPlayerHandPanel().getHeight() + 50
        );

        this.centerWindow();
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    /**
     * Getter of the playerPanel attribute
     *
     * @return the playerPanel
     */
    public PlayerGameInfoPanel getPlayerPanel() {
        return playerPanel;
    }

    /**
     * Setter of the playerPanel attribute
     *
     * @param playerPanel the playerPanel to set
     */
    public void setPlayerPanel(PlayerGameInfoPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    /**
     * Setter of the riskController attribute
     *
     * @param riskController the riskController to set
     */
    public void setRiskController(RiskController riskController) {
        this.riskController = riskController;
    }

    /**
     * Getter of the phase attribute
     *
     * @return the phase
     */
    public JButton getPhase() {
        return phase;
    }

    /**
     * Setter of the phase attribute
     *
     * @param phase the phase to set
     */
    public void setPhase(JButton phase) {
        this.phase = phase;
    }

    /**
     * @return the reinforcementArmies
     */
    public StagePanel getStagePanel() {
        return stagePanel;
    }

    /**
     * Setter of the stagePanel attribute
     *
     * @param stagePanel the stagePanel to set
     *
     */
    public void setStagePanel(StagePanel stagePanel) {
        this.stagePanel = stagePanel;
    }

    /**
     * Open a Message Dialog to show message to user
     *
     * @param message Text to be displayed in the message dialog
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}
