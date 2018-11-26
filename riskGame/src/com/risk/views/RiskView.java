/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.MenuListener;
import com.risk.controllers.RiskController;
import com.risk.models.MapFileManagement;
import com.risk.models.RiskModel;
import com.risk.views.game.DominationView;
import com.risk.views.game.InstructionsPanel;
import com.risk.views.game.MapPanel;
import com.risk.views.game.PhaseAuxiliar;
import com.risk.views.game.PhaseView;
import com.risk.views.menu.MenuView;
import com.risk.views.menu.NewGamePanel;
import com.risk.views.menu.StartMenuView;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Main View of the game
 *
 * @author n_irahol
 */
public final class RiskView extends javax.swing.JFrame implements RiskViewInterface {

    /**
     * menuPanel reference to the menu panel
     */
    private MenuView menuPanel;
    /**
     * mapPanel reference to the view that manages the map
     */
    final private MapPanel mapPanel;
    /**
     * Reference to the domination view which displays the information about the players.
     * This panel is located on the right of the window.
     */
    final private DominationView dominationView;
    
    /**
     * Displays the attack process interface (dices...) 
     */
    final private PhaseAuxiliar phaseAuxiliarPanel;

    /**
     *  Displays the instructions about the current phase to the user on the top of the window.
     */
    final private InstructionsPanel stagePanel;
    
    /**
     *  Displays the log messages for the current phase (on the bottom of the window).  
     */
    final private PhaseView phaseView;

    /**
     * Constructor of main view
     */
    public RiskView() {
        super("Risk Game");

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(true);

        this.phaseAuxiliarPanel = new PhaseAuxiliar();

        this.stagePanel = new InstructionsPanel();
        this.dominationView = new DominationView();
        this.mapPanel = new MapPanel();
        this.phaseView = new PhaseView();

        Container mainContainer = this.getContentPane();
        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BorderLayout());
        southContainer.add(this.phaseAuxiliarPanel, BorderLayout.NORTH);
        southContainer.add(phaseView, BorderLayout.SOUTH);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(southContainer, BorderLayout.SOUTH);
        mainContainer.add(this.stagePanel, BorderLayout.NORTH);
        mainContainer.add(this.dominationView, BorderLayout.EAST);
        mainContainer.add(this.mapPanel, BorderLayout.CENTER);

        this.addMenuBar();

        setSize(1000, 563);
        this.centerWindow();
    }

    /**
     * Interface with phase view to get the logs being displayed
     * @return linked list of logs being currently displayed
     */
    public LinkedList<String> getLogs(){
        return this.phaseView.getLogs();
    }
    
    /**
     * Interface with phase view to set the logs being displayed
     * @param logs 
     */
    public void setLogs(LinkedList<String> logs){
        this.phaseView.setLogs(logs);
    }
    
    /**
     * This method is for add the observer
     *
     * @param rm the risk model which is gonna be added the observer
     */
    @Override
    public void observeModel(RiskModel rm) {
        updateView(rm, true);
        rm.getPlayerList().forEach((pl) -> {
            pl.addObserver(this.phaseView);
        });
        rm.addObserver(this.stagePanel);
        rm.addObserver(this.mapPanel);
        rm.getMap().addObserver(this.mapPanel);
        rm.addObserver(this);
        rm.addObserver(this.phaseAuxiliarPanel);
        rm.addObserver(this.dominationView);
        this.phaseView.updateView(rm);
        rm.addObserver(this.phaseView);
    }

    /**
     *
     * @param rm the model which is gonna be updated the view
     * @param newMap the new map which is gonna be updated
     */
    void updateView(RiskModel rm, boolean newMap) {
        this.stagePanel.updateView(rm);
        this.mapPanel.updateView(rm.getMap(), newMap);
        this.dominationView.updateView(rm);

        this.setSize(
                rm.getMap().getMapWidth() + 250,
                Math.max(
                        rm.getMap().getMapHeight() + 230,
                        215 + 135 * rm.getPlayerList().size()
                        + 17 * rm.getMap().getContinents().size()
                )
        );

        this.centerWindow();
    }

    /**
     * Show a pop-up error to the user to inform of an error
     *
     * @param errorMessage message to be displayed into the dialog.
     */
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Open a Message Dialog to show message to user
     *
     * @param message Text to be displayed in the message dialog
     */
    void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Link the view to the controller by setting all required listeners
     *
     * @param rc Controller
     */
    @Override
    public void setController(RiskController rc) {
        this.getMapPanel().setListener(rc.getTerritoryListener());

        Arrays.stream(this.getStagePanel().getEndPhase().getActionListeners())
                .forEach((a)->{
            this.getStagePanel().getEndPhase().removeActionListener(a);
        });
        
        this.getStagePanel().getEndPhase().addActionListener(e -> {
            rc.getGameController().endPhaseButtonPressed();
        });

        //action for new game selection in the menu bar
        Component c = this.getJMenuBar().getMenu(0).getMenuComponent(0);
        if (c instanceof JMenuItem) {
            JMenuItem j = (JMenuItem) c;
            //remove old listeners
            Arrays.stream(j.getActionListeners()).forEach((a)->{
                j.removeActionListener(a);
            });
            
            j.addActionListener(e -> {
                rc.newGameMenuItemPressed();
            });
        }

        //action for save game selection in the menu bar
        c = this.getJMenuBar().getMenu(0).getMenuComponent(1);
        if (c instanceof JMenuItem) {
            JMenuItem j = (JMenuItem) c;
            
            //remove old listeners
            Arrays.stream(j.getActionListeners()).forEach((a)->{
                j.removeActionListener(a);
            });
            
            //add new one
            j.addActionListener(e -> {
                JFileChooser fileChooser;
                FileNameExtensionFilter filter = new FileNameExtensionFilter("saved risk model", "ser");
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File("." + File.separator));

                //open dialog
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    rc.saveGame(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });
        }

        //action for load game selection in the menu bar
        c = this.getJMenuBar().getMenu(0).getMenuComponent(2);
        if (c instanceof JMenuItem) {
            JMenuItem j = (JMenuItem) c;
            
            //remove old listeners
            Arrays.stream(j.getActionListeners()).forEach((a)->{
                j.removeActionListener(a);
            });
            
            //add new one
            j.addActionListener(e -> {
                //create a new file chooser
                JFileChooser fileChooser;
                fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("saved risk model", "ser");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File("." + File.separator));

                //handle selection on file chooser
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    rc.loadGame(fileChooser.getSelectedFile().getAbsolutePath());
                    
                }
            });
        }

        if (this.menuPanel != null) {
            JButton b = this.getMenuPanel().getStartMenu().getNewGamePanel()
                    .getOpenMapEditor();

            //remove old listeners
            Arrays.stream(b.getActionListeners()).forEach((a)->{
                b.removeActionListener(a);
            });
            
            b.addActionListener(e -> {
                rc.openMapEditor();
            });
        }

        phaseAuxiliarPanel.setListeners(rc.getGameController());
    }

    /**
     * Initialize the new game menu
     *
     * @param riskModel model of the game
     * @param menuListener listen the events in the menu
     */
    @Override
    public void initialMenu(RiskModel riskModel, MenuListener menuListener) {

        StartMenuView start = new StartMenuView(riskModel, menuListener);
        MenuView menu = new MenuView(start, this, "New Game");
        this.setMenuPanel(menu);
        menu.add(start);
        menu.setVisible(true);
        menu.setSize(300, 500);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        menu.setLocation(dimension.width / 2 - 300 / 2, dimension.height / 2 - 500 / 2);
    }

    /**
     * Close menu action
     */
    @Override
    public void closeMenu() {
        this.menuPanel.setVisible(false);
        this.remove(this.menuPanel);
        this.setMenuPanel(null);
    }

    /**
     * Getter of the new game panel inside the menu panel
     *
     * @return the new panel
     */
    @Override
    public NewGamePanel getNewGamePanel() {
        return this.getMenuPanel().getStartMenu().getNewGamePanel();
    }

    /**
     * Getter of the player hands panel
     *
     * @return the hands panel of the player
     */
    PhaseAuxiliar getPhaseAuxiliarPanel() {
        return phaseAuxiliarPanel;
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

        menuFile.setLayout(new BoxLayout(menuFile, BoxLayout.Y_AXIS));
        JMenuItem menuItemSaver = new JMenuItem("Save Game");
        menuItemSaver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItemSaver.getAccessibleContext().setAccessibleDescription("Save current game");
        menuFile.add(menuItemSaver);

        menuFile.setLayout(new BoxLayout(menuFile, BoxLayout.Y_AXIS));
        JMenuItem menuItemLoader = new JMenuItem("Load Game");
        menuItemLoader.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItemLoader.getAccessibleContext().setAccessibleDescription("Load saved game");
        menuFile.add(menuItemLoader);

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
     * Getter of the mapPanel attribute
     *
     * @return the mapPanel
     */
    MapPanel getMapPanel() {
        return mapPanel;
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }

    /**
     * @return the reinforcementArmies
     */
    InstructionsPanel getStagePanel() {
        return stagePanel;
    }

    /**
     *
     * @param o the observer
     * @param o1 the object
     */
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof RiskModel) {
            if (o1 instanceof String) {
                String eventMessage = (String) o1;
                this.showMessage(eventMessage);
            }
        }
    }
}
