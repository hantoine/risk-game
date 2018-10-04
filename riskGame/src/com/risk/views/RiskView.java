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
import com.risk.models.RiskModel;
import com.risk.views.menu.MenuView;
import com.risk.views.phases.PhasePanel;
import com.risk.views.player.PlayerGameHandPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
    private JPanel battlePanel;
    private MapPanel mapPanel;
    final private PlayerGameInfoPanel playerPanel;
    final private PlayerGameHandPanel playerHandPanel;
    private JButton phase;
    final private PhasePanel stagePanel;

    /**
     * Constructor of main view
     */
    public RiskView() {
        super("Risk Game");

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.playerHandPanel = new PlayerGameHandPanel();
        this.stagePanel = new PhasePanel();
        this.playerPanel = new PlayerGameInfoPanel();
        this.mapPanel = new MapPanel();

        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(this.playerHandPanel, BorderLayout.SOUTH);
        cp.add(this.stagePanel, BorderLayout.NORTH);
        cp.add(this.playerPanel, BorderLayout.EAST);
        cp.add(this.mapPanel, BorderLayout.CENTER);

        this.addMenuBar();

        setSize(1000, 563);
        this.centerWindow();
    }

    /**
     * Update the information displayed in the view to match the ones in the
     * model
     *
     * @param rm Model of the game
     */
    public void updateView(RiskModel rm) {
        this.getStagePanel().updateView(rm);
        this.getMapPanel().updateView(rm, false);
        this.getPlayerPanel().updatePlayer(rm.getCurrentPlayer());
        this.getPlayerHandPanel().updatePlayer(rm.getCurrentPlayer());

    }

    /**
     * Update the information displayed in the view to match the ones in the
     * model Update also the map which can have changed (if not changed just
     * overhead)
     *
     * @param rm model of the game
     */
    public void updateViewWithNewMap(RiskModel rm) {
        this.getStagePanel().updateView(rm);
        this.getMapPanel().updateView(rm, true);
        this.getPlayerPanel().updatePlayer(rm.getCurrentPlayer());
        this.getPlayerHandPanel().updatePlayer(rm.getCurrentPlayer());

        this.setSize(
                this.mapPanel.getWidth() + this.getPlayerPanel().getWidth() + 40,
                this.getStagePanel().getHeight() + Math.max(this.mapPanel.getHeight(), this.getPlayerPanel().getHeight()) + this.getPlayerHandPanel().getHeight() + 175
        );

        this.centerWindow();
    }

    /**
     * Link the view to the controller by setting all required listeners
     *
     * @param rc Controller
     */
    public void setController(RiskController rc) {
        this.getMapPanel().setListener(rc.getCountryListener());

        this.getStagePanel().getEndPhase().addActionListener(e -> {
            rc.getPlayGame().finishStage();
        });

        Component c = this.getJMenuBar().getMenu(0).getMenuComponent(0);
        if (c instanceof JMenuItem) {
            JMenuItem j = (JMenuItem) c;
            j.addActionListener(e -> {
                rc.newGameMenuItemPressed();
            });
        }
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

    public void hideMenu() {
        this.menuPanel.setVisible(false);
    }

    public PlayerGameHandPanel getPlayerHandPanel() {
        return playerHandPanel;
    }

    /**
     * Initialize the menu bar of the game
     *
     */
    private void addMenuBar() {
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
    private void setMenuPanel(MenuView menuPanel) {
        this.menuPanel = menuPanel;
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
    public PhasePanel getStagePanel() {
        return stagePanel;
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
