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
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author n_irahol
 */
public class RiskView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    private MenuView menuPanel;
    private JPanel optionPanel;
    private JPanel battlePanel;
    private MapPanel mapPanel;
    private PlayerGameInfoPanel playerPanel;
    private RiskController riskController;
    
    public RiskView() {
        super("Risk Game");
     
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        this.setResizable(false);
        this.addMenuBar();
        this.getJMenuBar().setVisible(true);
        this.setVisible(true);
        
    }
    
    
    public void initialMap(RiskModel riskModel, MouseListener countryListener) {
        if(this.getMapPanel()!=null && this.getPlayerPanel()!=null){
            this.remove(this.getMapPanel());
            this.remove(this.getPlayerPanel());
        }
        Container cp = getContentPane();

        this.setMapPanel(new MapPanel(riskModel.getBoard(), countryListener));
        this.setPlayerPanel(new PlayerGameInfoPanel(riskModel.getCurrentPlayer()));
        this.getPlayerPanel().updatePlayer(riskModel.getCurrentPlayer());
        this.setSize(new Dimension(this.getMapPanel().getWidth() + this.getPlayerPanel().getWidth(), this.getMapPanel().getHeight()+70));
        cp.add(this.getMapPanel(), BorderLayout.CENTER);
        cp.add(this.getPlayerPanel(), BorderLayout.EAST);
       
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
       
    }

    public void initialMenu(RiskModel riskModel, MenuListener menuListener) {
       
        StartMenuView start= new StartMenuView(riskModel, menuListener);
        MenuView aux=new MenuView(start,this,"New Game");
        this.setMenuPanel(aux);
        aux.add(start);
        aux.setVisible(true);
        aux.setSize(300, 500);
        this.setSize(800, 600);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        aux.setLocation(dimension.width / 2 - 300 / 2, dimension.height / 2 - 500 / 2);
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }
    
    public void addMenuBar(){
        JMenuBar menuBar;
        JMenu menuFile,menuOption;
        JMenuItem menuItem;
       
        //Create the menu bar.
        menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Build the first menu.
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_A);
        menuFile.getAccessibleContext().setAccessibleDescription("File");
        menuBar.add(menuFile,BorderLayout.NORTH);

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
        menuBar.add(menuOption,BorderLayout.NORTH);
        
        this.setJMenuBar(menuBar);
    }
    /**
     * @return the menuPanel
     */
    public MenuView getMenuPanel() {
        return menuPanel;
    }

    /**
     * @param menuPanel the menuPanel to set
     */
    public void setMenuPanel(MenuView menuPanel) {
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

    /**
     * @param riskController the riskController to set
     */
    public void setRiskController(RiskController riskController) {
        this.riskController = riskController;
    }


}
