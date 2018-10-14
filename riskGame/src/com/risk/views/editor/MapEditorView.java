/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.models.editor.EditableMapModel;
import com.risk.controllers.MapEditorController;
import com.risk.models.MapConfig;
import com.risk.observers.UpdateTypes;
import com.risk.observers.MapModelObserver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
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
 * It represents the map editor panel in the menu
 *
 * @author timot
 */
public class MapEditorView extends javax.swing.JFrame implements MapModelObserver {

    protected JPanel contentPanel;
    protected FileSelectorPanel imageSelectorPanel;
    protected MapView mapPanel;
    private MapEditorController controller;
    protected ContinentListPanel continentsPanel;
    protected CustomListPanel toolsPanel;
    protected MapConfigPanel mapConfigPanel;

    /**
     * Constructor
     *
     * @param width
     * @param height
     * @param editorController
     */
    public MapEditorView(int width, int height, MapEditorController editorController, EditableMapModel initMapModel) {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(width, height));
        controller = editorController;
        this.setTitle("Map Editor");
        this.setResizable(false);

        //all content is being stored in this panel
        contentPanel = new JPanel();

        //selector of a background image
        imageSelectorPanel = new FileSelectorPanel(210, 30, new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        imageSelectorPanel.setMaximumSize(new Dimension(600, 20));
        imageSelectorPanel.setLabel("Select a background image for your map.");

        contentPanel.setSize(width - imageSelectorPanel.getMaximumSize().width, height - imageSelectorPanel.getMaximumSize().height);

        mapPanel = new MapView(editorController);
        this.imageSelectorPanel.getTextField().getDocument().addDocumentListener(controller.getSelectBackImgListener(mapPanel, this));

        //panel to add continents
        this.continentsPanel = new ContinentListPanel(120, 600, editorController, initMapModel.getContinentList());

        //panel with buttons to select a mode
        String[] toolsList = {"Add Territory", "Edit Territory", "Create Link", "Remove Link"};
        this.mapPanel.setCurrentTool(Tools.CREATE);
        this.toolsPanel = new ToolsListPanel(120, 600, toolsList, getToolButtonListener());

        //panel to set map configuration
        this.mapConfigPanel = new MapConfigPanel(editorController, initMapModel.getMapConfig());

        //add elements
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(BorderLayout.NORTH, imageSelectorPanel);
        contentPanel.add(BorderLayout.WEST, toolsPanel);
        contentPanel.add(BorderLayout.EAST, continentsPanel);
        contentPanel.add(BorderLayout.CENTER, mapPanel);
        contentPanel.add(BorderLayout.SOUTH, mapConfigPanel);
        setContentPane(contentPanel);
        this.addMenuBar(editorController);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getWidth() / 2, dimension.height / 2 - this.getHeight() / 2);

    }

    /**
     * Initialize the menu bar of the editor
     *
     */
    private void addMenuBar(MapEditorController editorController) {
        JMenuBar menuBar;
        JMenu menuFile;
        JMenuItem menuItemOpen;
        JMenuItem menuItemSave;
        JMenuItem menuItemNew;

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
        
        //add open file item
        menuItemOpen = new JMenuItem("Open File");
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItemOpen.getAccessibleContext().setAccessibleDescription("Open Map File");
        menuFile.add(menuItemOpen);
        
        //add save file item
        menuItemSave = new JMenuItem("Save File");
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItemSave.getAccessibleContext().setAccessibleDescription("Save Map File");
        menuFile.add(menuItemSave);
        
        //add new map item
        menuItemNew = new JMenuItem("New map");
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItemNew.getAccessibleContext().setAccessibleDescription("Clear the map being edited");
        menuFile.add(menuItemNew);

        //listener to open a map from file
        menuItemOpen.addActionListener(e -> {
            JFileChooser fileChooser;
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Conquest Map file", "map");
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File("." + File.separator + "maps"));

            //open dialog
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                
                //map loading 
                editorController.loadMapFromFile(filepath, this.getMapView());
                
                //update map configuration
                this.mapConfigPanel.setView(this.controller.getNewMap().getMapConfig());
            }
        });

        //listener to save a map being edited
        menuItemSave.addActionListener(e -> {
            JFileChooser fileChooser;
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Conquest Map file", "map");
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File("." + File.separator + "maps"));

            //open dialog
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                
                //map saving
                editorController.saveMapToFile(filepath);
            }
        });
        
        //listener to clear the map being edited and get a new one
        menuItemNew.addActionListener(e -> {
            //open dialog
            int v = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the current map ?",
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION);
            
            //if confirm
            if (v == JFileChooser.APPROVE_OPTION) {
                //clear map
                editorController.clearMapModel();
                MapConfig newConfig = editorController.getNewMap().getMapConfig();
                this.mapConfigPanel.setView(newConfig);
            }
        });

        this.setJMenuBar(menuBar);
        this.getJMenuBar().setVisible(true);
    }

    public ToolButtonListener getToolButtonListener() {
        return new ToolButtonListener(this.mapPanel);
    }

    /**
     * Button listener to change the mode when the user click on a tool to
     * select it
     */
    protected class ToolButtonListener implements ActionListener {

        private MapView mapPanel;

        public ToolButtonListener(MapView mapPanel) {
            this.mapPanel = mapPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String toolName = ((JButton) e.getSource()).getText();
            switch (toolName) {
                case "Add Territory":
                    this.mapPanel.setCurrentTool(Tools.CREATE);
                    break;
                case "Edit Territory":
                    this.mapPanel.setCurrentTool(Tools.EDIT);
                    break;
                case "Create Link":
                    this.mapPanel.setCurrentTool(Tools.LINK);
                    break;
                case "Remove Link":
                    this.mapPanel.setCurrentTool(Tools.UNLINK);
                    break;
            }
        }
    }

    /**
     * Return the view to the map within the map editor.
     *
     * @return
     */
    public MapView getMapView() {
        return this.mapPanel;
    }

    public ContinentListPanel getContinentListPanel() {
        return this.continentsPanel;
    }

    @Override
    public void update(UpdateTypes updateType, Object object) {
        switch (updateType) {
            case ADD_TERRITORY:
                break;
            case REMOVE_TERRITORY:
                break;
            case UPDATE_TERRITORY_NAME:
                break;
            case UPDATE_TERRITORY_POS:
                break;
            case UPDATE_BACKGROUND_IMAGE:
                if(object==null)
                    break;
                
                BufferedImage backgroundImage = (BufferedImage) object;

                //get current sizes of components
                int width = backgroundImage.getWidth();
                int height = backgroundImage.getHeight();
                Dimension mapPanelSize = this.mapPanel.getSize();
                Dimension parentSize = this.getSize();

                //compute new size to adapt to the image
                double difWidth = width - mapPanelSize.getWidth();
                double difHeight = height - mapPanelSize.getHeight();
                parentSize.width += difWidth;
                parentSize.height += difHeight;

                //set new size and set new background image
                this.setSize(parentSize);
                this.mapPanel.setImage(backgroundImage);
                this.repaint();

                break;
            case UPDATE_CONTINENT:
                break;
        }
    }
}
