/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.controllers.MapEditorController;
import com.risk.models.MapConfig;
import com.risk.models.MapFileManagement;
import com.risk.models.MapModel;
import com.risk.observable.MapModelObserver;
import com.risk.observable.UpdateTypes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
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
 * JFrame representing the view of the map editor panel.
 *
 * @author timot
 */
public class MapEditorView extends javax.swing.JFrame implements Observer {

    /**
     * Main panel in which all other panels are included.
     */
    protected JPanel contentPanel;

    /**
     * Panel containing a filechooser to select a background image for the map.
     */
    protected FileSelectorPanel imageSelectorPanel;

    /**
     * Panel which contain the map being editing.
     */
    protected MapView mapPanel;

    /**
     * Panel which contains the list of continents and allow to edit them and
     * create new one.
     */
    protected ContinentListPanel continentsPanel;

    /**
     * Panel which contains the tools of the editor like adding country, editing
     * country etc.
     */
    protected CustomListPanel toolsPanel;

    /**
     * Panel which allow to modify the configuration informations of the map
     * like the author for example.
     */
    protected MapConfigPanel mapConfigPanel;

    /**
     * Constructor.
     *
     * @param width the width of the map editor
     * @param height the height of the map editor
     * @param editorController Controller of the editor using to retrieve
     * listeners.
     * @param initMapModel Model of the map which is being edited, used to
     * intialize the map editor.
     * @see MapEditorController
     * @see EditableMapModel
     */
    public MapEditorView(int width, int height, MapEditorController editorController, MapModel initMapModel) {
        init(width, height);

        //all content is being stored in this panel
        contentPanel = new JPanel();

        //selector of a background image
        imageSelectorPanel = new FileSelectorPanel(210, 30, new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        imageSelectorPanel.setMaximumSize(new Dimension(600, 20));
        imageSelectorPanel.setLabel("Select a background image for your map.");

        contentPanel.setSize(width - imageSelectorPanel.getMaximumSize().width, height - imageSelectorPanel.getMaximumSize().height);

        //panel for the map being edited
        mapPanel = new MapView(editorController);
        this.imageSelectorPanel.getTextField().getDocument().addDocumentListener(editorController.getSelectBackImgListener(mapPanel, this));

        //panel to add continents
        this.continentsPanel = new ContinentListPanel(120, 600, editorController, initMapModel.getContinentList());

        //panel with buttons to select a mode
        String[] toolsList = {"Add Territory", "Edit Territory", "Create Link", "Remove Link"};
        this.mapPanel.setCurrentTool(Tools.CREATE);
        this.toolsPanel = new ToolsListPanel(120, 600, toolsList, getToolButtonListener());

        //panel to set map configuration
        this.mapConfigPanel = new MapConfigPanel(editorController, initMapModel.getConfigurationInfo());

        //add elements
        addPanes();
        addMenuBar(editorController);
    }

    /**
     * Initialize the view by setting its size and location.
     *
     * @param width the width of the maple editor
     * @param height the height of the maple editor
     */
    public void init(int width, int height) {
        setSize(new Dimension(width, height));
        this.setTitle("Map Editor");
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getWidth() / 2, dimension.height / 2 - this.getHeight() / 2);
    }

    /**
     * Add the panels that have been created to the frame.
     */
    public void addPanes() {
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(BorderLayout.NORTH, imageSelectorPanel);
        contentPanel.add(BorderLayout.WEST, toolsPanel);
        contentPanel.add(BorderLayout.EAST, continentsPanel);
        contentPanel.add(BorderLayout.CENTER, mapPanel);
        contentPanel.add(BorderLayout.SOUTH, mapConfigPanel);
        setContentPane(contentPanel);
    }

    /**
     * Create the items for the menu bar, attach their listeners and add them to
     * the menu bar.
     *
     * @param menuFile Menu which will get the new items.
     * @param editorController Controller of the map editor which will be used
     * for the listeners.
     */
    private void addMenuItems(JMenu menuFile, MapEditorController editorController) {
        JMenuItem menuItemOpen;
        JMenuItem menuItemSave;
        JMenuItem menuItemNew;

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

        addOpenItemListener(menuItemOpen, editorController);
        addSaveItemListener(menuItemSave, editorController);
        addClearItemListener(menuItemNew, editorController);
    }

    /**
     * Allow to select a file and then tells the editor controller to update the
     * model from the file.
     *
     * @param menuItemOpen Item on which we attach the listener.
     * @param editorController Controller of map editor.
     */
    private void addOpenItemListener(JMenuItem menuItemOpen, MapEditorController editorController) {
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

                int nbLinks = this.mapPanel.links.size();
                System.out.println("nb links loaded:"+ Integer.toString(nbLinks));
                
                //update map configuration
                this.mapConfigPanel.setView(editorController.getNewMap().getConfigurationInfo());
            }
        });
    }

    /**
     * Allow to save the current map model in a new file.
     *
     * @param menuItemSave Item on which we attach the listener.
     * @param editorController Controller of map editor.
     */
    private void addSaveItemListener(JMenuItem menuItemSave, MapEditorController editorController) {
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
                int errorCode = editorController.saveMapToFile(filepath);
                System.out.println("error code: " + Integer.toString(errorCode));
                if (errorCode != 0) {

                    this.mapPanel.showError(MapFileManagement.readingError(errorCode));
                }
            }
        });
    }

    /**
     * Allow to clear the map being editing. It asks for the user's confirmation
     * before clear.
     *
     * @param menuItemNew Item on which we attach the listener.
     * @param editorController Controller of map editor.
     */
    private void addClearItemListener(JMenuItem menuItemNew, MapEditorController editorController) {
        //listener to clear the map being edited and get a new one
        menuItemNew.addActionListener((ActionEvent e) -> {
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
                
                int nbLinks = this.mapPanel.links.size();
                System.out.println("nb links after clear:"+ Integer.toString(nbLinks));
                if(nbLinks>0){
                    Set<String> keys = this.mapPanel.links.keySet();
                    Iterator keyIterator = keys.iterator();
                    String key;
                    System.out.println("links not deleted:");
                    for(int i=0;i<keys.size();i++){
                        key = (String)keyIterator.next();
                        System.out.println(key);
                    }
                }
                
                
                MapConfig newConfig = editorController.getNewMap().getConfigurationInfo();
                this.mapConfigPanel.setView(newConfig);
            }
        });
    }

    /**
     * Initialize the menu bar on top of the editor with save, load and clear
     * options.
     *
     * @param editorController Controller of the map editor.
     */
    private void addMenuBar(MapEditorController editorController) {
        JMenuBar menuBar;
        JMenu menuFile;

        //Create the menu bar.
        menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Build the first menu.
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_A);
        menuFile.getAccessibleContext().setAccessibleDescription("File");
        menuBar.add(menuFile, BorderLayout.NORTH);

        //Add items.
        menuFile.setLayout(new BoxLayout(menuFile, BoxLayout.Y_AXIS));
        addMenuItems(menuFile, editorController);

        this.setJMenuBar(menuBar);
        this.getJMenuBar().setVisible(true);
    }

    /**
     * Retrieve a ToolButtonListener for the tools' panel.
     *
     * @return a new listener on the tools' panel.
     * @see ToolButtonListener
     */
    public ToolButtonListener getToolButtonListener() {
        return new ToolButtonListener(this.mapPanel);
    }


    /**
     * Button listener to change the current mode of the "map panel" when the
     * user click on a tool to select it. The map panel is the panel where the
     * map is being edited. This method change the mode of the map panel when a
     * tool is selected.
     *
     * @see MapView
     */
    protected class ToolButtonListener implements ActionListener {

        /**
         * Panel which will be updated when a tool will be selected.
         */
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
     * @return the panel representing the map being edited.
     */
    public MapView getMapView() {
        return this.mapPanel;
    }

    /**
     * Return the view allowing to see and edit the continents.
     *
     * @return the panel containing the list of continents.
     */
    public ContinentListPanel getContinentListPanel() {
        return this.continentsPanel;
    }

    /**
     * Update method of the Observer pattern
     *
     * @param object
     * @param arg
     */
    @Override
    public void update(Observable object, Object arg) {
        UpdateTypes updateType = (UpdateTypes)arg;
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
                if (object == null) {
                    this.mapPanel.setImage(null);
                    break;
                }

                BufferedImage backgroundImage = (BufferedImage) ((MapModel)object).getImage();

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
