/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.ContinentModel;
import com.risk.models.MapFileManagement;
import com.risk.models.MapModel;
import com.risk.models.TerritoryModel;
import com.risk.views.editor.ContinentListPanel;
import com.risk.views.editor.MapEditorView;
import com.risk.views.editor.MapView;
import com.risk.views.editor.MapViewInterface;
import com.risk.views.editor.TerritoryButton2;
import com.risk.views.editor.Tools;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * Controller of the map editor
 *
 * @author timot
 */
public class MapEditorController {

    /**
     * Model of the map that is edited.
     */
    public MapModel editedMap;

    /**
     * Constructor
     *
     * @param mapModel model of the map being edited that will be called by the
     * controller for updates.
     */
    public MapEditorController(MapModel mapModel) {
        editedMap = mapModel;
    }

    /**
     * Get reference to the map being edited
     *
     * @return the map being edited
     */
    public MapModel getNewMap() {
        return editedMap;
    }

    /**
     * getter of the mouse listener which listens on the map editor panels
     *
     * @return the mouse listener of the view of the map being edited
     */
    public MapMouseController getMapMouseListener() {
        return new MapMouseController(editedMap);
    }

    /**
     * getter of the button listener which listens on the map editor panels
     *
     * @return the button listener used for the territories' buttons
     */
    public ButtonMouseController getTerritoryMouseListener() {
        return new ButtonMouseController(editedMap);
    }

    /**
     * getter of the continent button listener which listens on the continent
     * list panel.
     *
     * @return the listener of the button for adding new continents
     */
    public AddContinentButtonListener getAddContinentButtonListener() {
        return new AddContinentButtonListener(editedMap);
    }

    /**
     * getter on the mouse listener which listens on the continent list panel.
     *
     * @return the mouse listener to edit or delete a continent from the list.
     */
    public ContinentMouseListener getContinentMouseListener() {
        return new ContinentMouseListener(editedMap);
    }

    /**
     * getter on the listener for the background image selector
     *
     * @param mapPanel view on the map being edited
     * @param editorPanel view of the whole map editor
     * @return the listener that will tells the model if a new image has been
     * selected.
     */
    public selectBackImgListener getSelectBackImgListener(MapView mapPanel, MapEditorView editorPanel) {
        return new selectBackImgListener(mapPanel, editorPanel, this.editedMap);
    }

    /**
     * Method that tells the map model to clear itself. That will automatically
     * updates the views too.
     */
    public void clearMapModel() {
        this.editedMap.clearMap();
    }

    /**
     * Class to handle clicks on the "add" button listener to add continents
     */
    public class AddContinentButtonListener implements ActionListener {

        /**
         * map that is being edited
         */
        protected MapModel newMap;

        /**
         * constructor
         *
         * @param mapModel map that is being edited
         */
        public AddContinentButtonListener(MapModel mapModel) {
            newMap = mapModel;
        }

        /**
         * listener
         *
         * @param e the action which is captured
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (newMap.addDefaultContinent() == true) {
                return;
            }
            JButton buttonClicked = (JButton) e.getSource();
            ContinentListPanel clickedPanel
                    = (ContinentListPanel) buttonClicked.getParent();
            clickedPanel.showError("The maximum number of continents to be "
                    + "created has been reached.");

        }
    }

    /**
     * Class to handle mouse events on continents objects
     */
    public class ContinentMouseListener implements MouseListener {

        /**
         * map that is being edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param mapModel the maple which is constructed
         */
        public ContinentMouseListener(MapModel mapModel) {
            newMap = mapModel;
        }

        /**
         * handles clicks
         *
         * @param e the mouse click which is captured
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * handles pressings of mouse
         *
         * @param e the pressing of the mouse which is captured
         */
        @Override
        public void mousePressed(MouseEvent e) {
        }

        /**
         * handles the releases of the mouse
         *
         * @param e the mouse releasing which is captured
         */
        @Override
        public void mouseReleased(MouseEvent e) {

            //get object we clicked on (JButton)
            Object sourceObj = e.getSource();

            //if JButton then process
            if (!(sourceObj instanceof JButton)) {
                return;
            }

            releaseHandling(e, sourceObj);
        }

        /**
         * updates of the model when the mouse has been released
         *
         * @param e mouse event
         * @param sourceObj object that has been clicked on
         */
        private void releaseHandling(MouseEvent e, Object sourceObj) {
            JButton clickedLabel = (JButton) sourceObj;
            ContinentListPanel clickedPanel
                    = (ContinentListPanel) clickedLabel.getParent();

            if (SwingUtilities.isRightMouseButton(e)) {
                String continentName = ((JButton) sourceObj).getText();
                boolean success = this.newMap.removeContinent(continentName);
                if (!success) {
                    clickedPanel.showError("Any map needs at least one continent.");
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                //save original name of continent
                String formerName = clickedLabel.getText();

                ContinentModel continentToModify = newMap.getContinentByName(formerName);
                int bonusScore = continentToModify.getBonusScore();

                //get information from the user
                Map<String, String> data = clickedPanel.modifyContinent(formerName, bonusScore);

                //if succeeded update the model's data
                if (!data.isEmpty()) {
                    data.put("name", formerName);
                    this.newMap.updateContinent(data);
                }
            }
        }

        /**
         * handles the entering of the mouse into a component
         *
         * @param e the mouse entered which is captured
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * handles the exiting of the mouse out of a component
         *
         * @param e the mouse exit which is captured
         */
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * Load a map from a map file that contains all informations.
     *
     * @param path path to the file that contains the information on the map
     * being loaded
     * @param view view of the map being edited
     */
    public void loadMapFromFile(String path, MapViewInterface view) {
        MapModel newMap = new MapModel();

        try {
            MapFileManagement.createBoard(path, newMap);
        } catch (MapFileManagement.MapFileManagementException ex) {
            view.showError(ex.getMessage());
            return;
        }

        this.loadMapIntoMapEditorModel(newMap);

    }

    private void loadMapIntoMapEditorModel(MapModel newMap) {
        //clear existing map
        this.editedMap.clearMap();

        //get the default continent to delete
        List<String> remainingContinents = this.editedMap.getContinentList();
        if (remainingContinents.size() != 1) {
            System.out.println("Wrong number of continents after the clear");
        }
        String continentToDelete = remainingContinents.get(0);

        //set new image
        if (newMap.getImage() != null) {
            this.editedMap.setImage(newMap.getImage(), new Dimension(200, 50));
        }

        //add continents
        for (ContinentModel c : newMap.getContinents()) {
            if (c.getName().equals(continentToDelete)) {
                Map<String, String> data = new HashMap<>();
                data.put("name", c.getName());
                data.put("newName", c.getName());
                data.put("bonusScore", Integer.toString(c.getBonusScore()));
                this.editedMap.updateContinent(data);
                continentToDelete = "";
            } else {
                this.editedMap.addContinent(c.getName(), c.getBonusScore());
            }
        }

        if (!continentToDelete.equals("")) {
            this.editedMap.removeContinent(continentToDelete);
        }

        //add territories
        newMap.getTerritories().forEach((t) -> {
            this.editedMap.loadTerritory(t.getPositionX(), t.getPositionY(), t.getName(), t.getContinentName());
        });

        newMap.getTerritories().forEach((t) -> {
            t.getAdj().stream().forEach((ta) -> {
                this.editedMap.addLink(t.getName(), ta.getName());
            });
        });

        updateConfigurationInfo(newMap);
    }

    /**
     * Update the map model being edited with new configuration parameters
     *
     * @param map model of a map that contains the new configuration parameters
     */
    public void updateConfigurationInfo(MapModel map) {
        this.editedMap.setAuthorConfig(map.getConfigurationInfo().getAuthor());
        this.editedMap.setScrollConfig(map.getConfigurationInfo().getScroll());
        this.editedMap.setWarnConfig(map.getConfigurationInfo().isWarn());
        this.editedMap.setWrapConfig(map.getConfigurationInfo().isWrap());
        this.editedMap.setImagePath(map.getConfigurationInfo().getImagePath());
    }

    /**
     * call to the method that will save the current map being edited into a
     * file
     *
     * @param path path to the new file
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     * exception for the method
     */
    public void saveMapToFile(String path)
            throws MapFileManagement.MapFileManagementException {
        MapFileManagement.generateBoardFile(path, this.editedMap);
    }

    /**
     * Listener for the entire map panel (to add new elements on it)
     */
    protected class MapMouseController implements MouseListener {

        /**
         * Map being edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param mapModel map being edited
         */
        public MapMouseController(MapModel mapModel) {
            newMap = mapModel;
        }

        /**
         * handles clicks
         *
         * @param e the mouse click which is captured
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * handles pressings of mouse
         *
         * @param e the mouse pressing which is captured
         */
        @Override
        public void mousePressed(MouseEvent e) {
        }

        /**
         * handles the entering of the mouse into a component
         *
         * @param e the mouse enter which is captured
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * handles the exiting of the mouse out of a component
         *
         * @param e the mouse exit which is captured
         */
        @Override
        public void mouseExited(MouseEvent e) {
        }

        /**
         * handles the releasing of the mouse
         *
         * @param e the releasing which is captured
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                MapView clickedPanel = (MapView) e.getSource();
                Tools toolName = clickedPanel.getCurrentTool();

                switch (toolName) {
                    case CREATE:
                        int posX = e.getX();
                        int posY = e.getY();
                        boolean success = this.newMap.addTerritory(posX, posY);
                        if (success == false) {
                            MapView mapView = (MapView) e.getSource();
                            mapView.showError("The maximum number of territories to be created has been reached.");
                        }
                        break;
                    case EDIT:
                        break;
                    case LINK:
                        break;

                }
            }
        }
    }

    /**
     * Function to tell the model to update a territory
     *
     * @param territoryName territory to be updated
     * @param targetButton button of the territory on the map view
     */
    public void updateTerritory(String territoryName, TerritoryButton2 targetButton) {
        List<String> continentList = editedMap.getContinentList();
        TerritoryModel territoryModel = editedMap.getTerritoryByName(territoryName);
        String formerContinent = territoryModel.getContinentName();
        String continentName = territoryModel.getContinentName();

        //get information from the user
        JPanel mapPanel = (JPanel) targetButton.getParent();
        Map<String, String> data = ((MapView) mapPanel).modifyTerritory(continentList, territoryName, continentName);

        //if succeeded update the model's data
        if (!data.isEmpty()) {
            data.put("name", territoryName);
            data.put("formerContinent", formerContinent);
            this.editedMap.updateTerritoryName(data);
        }
    }

    /**
     * Function to tell the model to add a new link between two territories
     *
     * @param territoryName name of the territory
     * @param clickedPanel panel that have been clicked which contains the
     * button of the territory
     */
    public void linkTerritory(String territoryName, MapView clickedPanel) {
        String[] territoryList = editedMap.getPotentialNeighbours(territoryName);
        String neighbourName;

        if (territoryList.length == 0) {
            clickedPanel.showError("No potential neighbour available.");
            return;
        }

        neighbourName = clickedPanel.createLink(territoryList, territoryName);

        if (!"".equals(neighbourName) && neighbourName != null) {
            editedMap.addLink(territoryName, neighbourName);
        }
    }

    /**
     * Function to tell the model to remove link between two territories
     *
     * @param territoryName name of the territory
     * @param clickedPanel panel that have been clicked which contains the
     * button of the territory
     */
    public void unLinkTerritories(String territoryName, MapView clickedPanel) {
        String neighbourName;

        //get list of neighbours' names
        List<TerritoryModel> neighbourList = editedMap.getTerritoryByName(territoryName).getAdj();

        if (neighbourList.isEmpty()) {
            clickedPanel.showError("No neighbour found for this territory");
            return;
        }

        LinkedList<String> neighbourStringList = new LinkedList<>();
        for (TerritoryModel neighbourModel : neighbourList) {
            neighbourStringList.add(neighbourModel.getName());
        }

        neighbourName = clickedPanel.removeLink(neighbourStringList);
        if (!"".equals(neighbourName) && neighbourName != null) {
            editedMap.removeLink(territoryName, neighbourName);
        }
    }

    /**
     * Listeners for territories buttons on the map view
     */
    protected class ButtonMouseController implements MouseListener {

        /**
         * map model being edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param mapModel map model being edited
         */
        public ButtonMouseController(MapModel mapModel) {
            newMap = mapModel;
        }

        /**
         * handles clicks
         *
         * @param e the mouse clicked wHich is captured
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                TerritoryButton2 targetButton = (TerritoryButton2) e.getSource();
                MapView clickedPanel = (MapView) targetButton.getParent();
                Tools toolName = clickedPanel.getCurrentTool();
                String territoryName = targetButton.getName();

                switch (toolName) {
                    case CREATE:
                        break;

                    case EDIT:
                        updateTerritory(territoryName, targetButton);
                        break;

                    case LINK:
                        linkTerritory(territoryName, clickedPanel);
                        break;

                    case UNLINK:
                        unLinkTerritories(territoryName, clickedPanel);
                        break;

                    case REMOVE:
                        Object sourceObj = e.getSource();
                        String className = sourceObj.getClass().getName();
                        String terrName
                                = ((TerritoryButton2) sourceObj).getName();
                        this.newMap.removeTerritory(terrName);
                        break;
                }
            }
        }

        /**
         * handles pressings of mouse
         *
         * @param e the mouse press which is captured
         */
        @Override
        public void mousePressed(MouseEvent e) {
        }

        /**
         * handles the entering of the mouse into a component
         *
         * @param e the mouse press which is captured
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * handles the exiting of the mouse out of a component
         *
         * @param e the mouse exit which is captured
         */
        @Override
        public void mouseExited(MouseEvent e) {
        }

        /**
         * handles the releasing of the mouse
         *
         * @param e the releasing mouse which is captured
         */
        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }

    /**
     * Listener of the background image selector for the map being edited
     */
    public class selectBackImgListener implements DocumentListener {

        /**
         * view for the edition of the map
         */
        protected MapView mapPanel;

        /**
         * the whole map editor panel which contains the mapPanel
         */
        protected MapEditorView editorPanel;

        /**
         * map being edited
         */
        public MapModel mapModel;

        /**
         * Constructor.
         *
         * @param mapPanel view for the edition of the map
         * @param editorPanel the whole map editor panel which contains the
         * mapPanel
         * @param mapModel map being edited
         */
        public selectBackImgListener(MapView mapPanel, MapEditorView editorPanel, MapModel mapModel) {
            this.mapPanel = mapPanel;
            this.editorPanel = editorPanel;
            this.mapModel = mapModel;
        }

        /**
         * Update the path of the selected image into the model when it has been
         * selected on the view
         *
         * @param e the update document event which is captured
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            File sourceImage;
            try {
                String imagePath = e.getDocument().getText(0, e.getDocument().getLength());
                sourceImage = new File(imagePath);
                if (sourceImage.getPath().equals("No file selected.")) {
                    return;
                }
                BufferedImage backgroundImage = ImageIO.read(sourceImage);

                Dimension buttonDim = mapPanel.getButtonDimension();
                mapModel.setImage(backgroundImage, buttonDim);
                mapModel.setImagePath(imagePath);

            } catch (BadLocationException ex) {
                Logger.getLogger(MapEditorView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MapEditorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

    }

    /**
     * getter for WarnCheckBoxListener
     *
     * @return a WarnCheckBoxListener
     */
    public WarnCheckBoxListener getWarnCheckBoxListener() {
        return new WarnCheckBoxListener(this.editedMap);
    }

    /**
     * getter for WrapCheckBoxListener
     *
     * @return a WrapCheckBoxListener
     */
    public WrapCheckBoxListener getWrapCheckBoxListener() {
        return new WrapCheckBoxListener(this.editedMap);
    }

    /**
     * getter for ScrollBoxListener
     *
     * @return a ScrollBoxListener
     */
    public ScrollBoxListener getScrollBoxListener() {
        return new ScrollBoxListener(this.editedMap);
    }

    /**
     * getter for AuthorTextFieldListener
     *
     * @return a AuthorTextFieldListener
     */
    public AuthorTextFieldListener getAuthorTextFieldListener() {
        return new AuthorTextFieldListener(this.editedMap);
    }

    /**
     * Listener of the warn checkbox, one of the parameters of the map
     */
    public class WarnCheckBoxListener implements ItemListener {

        /**
         * Model to be edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param newMap the new map which is constructed
         */
        public WarnCheckBoxListener(MapModel newMap) {
            this.newMap = newMap;
        }

        /**
         * Called when the state of the box has changed
         *
         * @param e the event captured
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            JCheckBox box = (JCheckBox) e.getItem();
            this.newMap.setWarnConfig(box.isSelected());
        }
    }

    /**
     * Listener of the wrap checkbox, one of the parameters of the map
     */
    public class WrapCheckBoxListener implements ItemListener {

        /**
         * Map being edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param newMap the new map constructed
         */
        public WrapCheckBoxListener(MapModel newMap) {
            this.newMap = newMap;
        }

        /**
         * Called when the state of the box has changed
         *
         * @param e the event captured
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            JCheckBox box = (JCheckBox) e.getItem();
            this.newMap.setWrapConfig(box.isSelected());
        }
    }

    /**
     * Listener to change the scroll style of the map
     */
    public class ScrollBoxListener implements ItemListener {

        /**
         * Map being edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param newMap the new map which is constructed
         */
        public ScrollBoxListener(MapModel newMap) {
            this.newMap = newMap;
        }

        /**
         * Called when a new item has been selected
         *
         * @param e the state changing which is updated
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            this.newMap.setScrollConfig((String) e.getItem());
        }
    }

    /**
     * Listener on the text field of the parameters panel which enables to enter
     * a new author name.
     */
    public class AuthorTextFieldListener implements DocumentListener {

        /**
         * Map being edited
         */
        public MapModel newMap;

        /**
         * Constructor
         *
         * @param newMap the new map which is constructed
         */
        public AuthorTextFieldListener(MapModel newMap) {
            this.newMap = newMap;
        }

        /**
         * Updates the model when a new character has been inserted
         *
         * @param e the update which is captured
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        /**
         * Updates the model when a character has been removed
         *
         * @param e the remove event which is captured
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        /**
         * Updates the model when there have been changes in the document
         *
         * @param e the update document event which is captured
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            update(e);
        }

        /**
         * Updates the model when the text has been updated.
         *
         * @param e the event to update
         */
        private void update(DocumentEvent e) {
            String text = null;
            try {
                text = e.getDocument().getText(0, e.getDocument().getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(MapEditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.newMap.setAuthorConfig(text);
        }

    }
}
