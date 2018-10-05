/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.editor.ContinentListPanel;
import com.risk.views.editor.CountryButton2;
import com.risk.views.editor.MapEditorView;
import com.risk.models.editor.EditableMapModel;
import com.risk.views.editor.MapView;
import com.risk.views.editor.Tools;
import com.risk.models.ContinentModel;
import com.risk.models.MapFileManagement;
import com.risk.models.MapModel;
import com.risk.models.TerritoryModel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author timot
 */
public class MapEditorController {

    public EditableMapModel newMap;

    public EditableMapModel getNewMap() {
        return newMap;
    }

    public MapEditorController(EditableMapModel mapModel) {
        newMap = mapModel;
    }

    /**
     * Getters
     *
     * @return
     */
    public MapMouseController getMapMouseListener() {
        return new MapMouseController(newMap);
    }

    public ButtonMouseController getCountryMouseListener() {
        return new ButtonMouseController(newMap);
    }

    public AddContinentButtonListener getAddContinentButtonListener() {
        return new AddContinentButtonListener(newMap);
    }

    public ContinentMouseListener getContinentMouseListener() {
        return new ContinentMouseListener(newMap);
    }

    public selectBackImgListener getSelectBackImgListener(MapView mapPanel, MapEditorView editorPanel) {
        return new selectBackImgListener(mapPanel, editorPanel, this.newMap);
    }

    /**
     * Class to handle clicks on the "add" button listener to add continents
     */
    public class AddContinentButtonListener implements ActionListener {

        protected EditableMapModel newMap;

        public AddContinentButtonListener(EditableMapModel mapModel) {
            newMap = mapModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean success = newMap.addContinent();
            if (success == false) {
                JButton buttonClicked = (JButton) e.getSource();
                ContinentListPanel clickedPanel = (ContinentListPanel) buttonClicked.getParent();
                clickedPanel.showError("The maximum number of continents to be created has been reached.");
            }
        }
    }

    /**
     * Class to handle mouse events on continents objects
     */
    public class ContinentMouseListener implements MouseListener {

        public EditableMapModel newMap;

        public ContinentMouseListener(EditableMapModel mapModel) {
            newMap = mapModel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            //get object we clicked on (jlabel)
            Object sourceObj = e.getSource();
            String className = sourceObj.getClass().getName();

            //if JLabel then process
            if (className != "javax.swing.JLabel") {
                return;
            }

            JLabel clickedLabel = (JLabel) sourceObj;
            ContinentListPanel clickedPanel = (ContinentListPanel) clickedLabel.getParent();

            if (SwingUtilities.isRightMouseButton(e)) {
                String continentName = ((JLabel) sourceObj).getText();
                boolean success = this.newMap.removeContinent(continentName);
                if (!success) {
                    clickedPanel.showError("Any map needs at least one continent.");
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                //save original name of continent
                String formerName = clickedLabel.getText();

                ContinentModel continentToModify = newMap.getGraphContinents().get(formerName);
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

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public void loadMapFromFile(String path) {
        MapModel map = new MapModel();
        MapFileManagement.createBoard(path, map);

        ArrayList<TerritoryModel> territories = new ArrayList<>(this.newMap.getGraphTerritories().values());
        territories.stream().forEach((t) -> {
            this.newMap.removeTerritory(t.getName());
        });

        ArrayList<ContinentModel> continents = new ArrayList<>(this.newMap.getGraphContinents().values());
        continents.stream().forEach((c) -> {
            this.newMap.removeContinent(c.getName());
        });

        this.newMap.setImage(map.getImage(), new Dimension(200, 50));

        map.getGraphContinents().values().stream().forEach((c) -> {
            this.newMap.addContinent();
            Map<String, String> updateContinentData = new HashMap<>();

            updateContinentData.put("name", "Continent0");
            updateContinentData.put("newName", c.getName());
            updateContinentData.put("bonusScore", Integer.toString(c.getBonusScore()));
            this.newMap.updateContinent(updateContinentData);
        });

        map.getGraphTerritories().values().stream().forEach((t) -> {
            this.newMap.addTerritory(t.getPositionX(), t.getPositionY());

            Map<String, String> updateTerritoryData = new HashMap<>();

            updateTerritoryData.put("name", "Territory0");
            updateTerritoryData.put("newName", t.getName());
            updateTerritoryData.put("continent", t.getContinentName());

            this.newMap.updateTerritory(updateTerritoryData);
        });

        map.getGraphTerritories().values().stream().forEach((t) -> {
            t.getAdj().stream().forEach((ta) -> {
                this.newMap.addLink(t.getName(), ta.getName());
            });
        });

        this.newMap.setAuthorConfig(map.getConfigurationInfo().getAuthor());
        this.newMap.setScrollConfig(map.getConfigurationInfo().getScroll());
        this.newMap.setWarnConfig(map.getConfigurationInfo().isWarn());
        this.newMap.setWrapConfig(map.getConfigurationInfo().isWrap());
        this.newMap.setImagePath(map.getConfigurationInfo().getImagePath());
    }

    public void saveMapToFile(String path) {
        MapFileManagement.generateBoardFile(path, this.newMap.getInternalMap());
    }

    /**
     * Listener for the entire map panel (to add new elements on it)
     */
    protected class MapMouseController implements MouseListener {

        public EditableMapModel newMap;

        public MapMouseController(EditableMapModel mapModel) {
            newMap = mapModel;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

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
     * Listeners for buttons
     */
    protected class ButtonMouseController implements MouseListener {

        public EditableMapModel newMap;

        public ButtonMouseController(EditableMapModel mapModel) {
            newMap = mapModel;
        }

        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {

                CountryButton2 targetButton = (CountryButton2) e.getSource();
                String className = targetButton.getClass().getName();

                MapView clickedPanel = (MapView) targetButton.getParent();
                Tools toolName = clickedPanel.getCurrentTool();
                String territoryName = targetButton.getName();
                String neighbourName;

                switch (toolName) {
                    case CREATE:
                        break;

                    case EDIT:
                        String[] continentList = newMap.getContinentList();
                        TerritoryModel territoryModel = newMap.getTerritoryByName(territoryName);
                        String continentName = territoryModel.getContinentName();

                        //get information from the user
                        JPanel mapPanel = (JPanel) targetButton.getParent();
                        Map<String, String> data = ((MapView) mapPanel).modifyTerritory(continentList, territoryName, continentName);

                        //if succeeded update the model's data
                        if (!data.isEmpty()) {
                            data.put("name", territoryName);
                            this.newMap.updateTerritory(data);
                        }
                        break;

                    case LINK:
                        String[] territoryList = newMap.getPotentialNeighbours(territoryName);

                        if (territoryList.length == 0) {
                            clickedPanel.showError("No potential neighbour available.");
                            return;
                        }

                        neighbourName = clickedPanel.createLink(territoryList, territoryName);

                        if (!"".equals(neighbourName) && neighbourName != null) {
                            newMap.addLink(territoryName, neighbourName);
                        }
                        break;

                    case UNLINK:
                        //get list of neighbours' names
                        LinkedList<TerritoryModel> neighbourList = newMap.getTerritoryByName(territoryName).getAdj();

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
                            newMap.removeLink(territoryName, neighbourName);
                        }
                        break;
                }
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                Object sourceObj = e.getSource();
                String className = sourceObj.getClass().getName();
                if (className == "com.risk.mapeditor.CountryButton2") {
                    String countryName = ((CountryButton2) sourceObj).getName();
                    this.newMap.removeTerritory(countryName);
                }
            }
        }
    }

    public class selectBackImgListener implements DocumentListener {

        protected MapView mapPanel;
        protected MapEditorView editorPanel;
        public EditableMapModel mapModel;

        public selectBackImgListener(MapView mapPanel, MapEditorView editorPanel, EditableMapModel mapModel) {
            this.mapPanel = mapPanel;
            this.editorPanel = editorPanel;
            this.mapModel = mapModel;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            File sourceImage;
            try {
                sourceImage = new File(e.getDocument().getText(0, e.getDocument().getLength()));
                if (sourceImage.getPath().equals("No file selected.")) {
                    return;
                }
                BufferedImage backgroundImage = ImageIO.read(sourceImage);

                Dimension buttonDim = mapPanel.getButtonDimension();
                mapModel.setImage(backgroundImage, buttonDim);

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

    public WarnCheckBoxListener getWarnCheckBoxListener() {
        return new WarnCheckBoxListener(this.newMap);
    }

    public WrapCheckBoxListener getWrapCheckBoxListener() {
        return new WrapCheckBoxListener(this.newMap);
    }

    public ScrollBoxListener getScrollBoxListener() {
        return new ScrollBoxListener(this.newMap);
    }

    public AuthorTextFieldListener getAuthorTextFieldListener() {
        return new AuthorTextFieldListener(this.newMap);
    }

    public class WarnCheckBoxListener implements ItemListener {

        public EditableMapModel newMap;

        public WarnCheckBoxListener(EditableMapModel newMap) {
            this.newMap = newMap;
        }

        public void itemStateChanged(ItemEvent e) {
            JCheckBox box = (JCheckBox) e.getItem();
            this.newMap.setWarnConfig(box.isSelected());
        }
    }

    public class WrapCheckBoxListener implements ItemListener {

        public EditableMapModel newMap;

        public WrapCheckBoxListener(EditableMapModel newMap) {
            this.newMap = newMap;
        }

        public void itemStateChanged(ItemEvent e) {
            JCheckBox box = (JCheckBox) e.getItem();
            this.newMap.setWrapConfig(box.isSelected());
        }
    }

    public class ScrollBoxListener implements ItemListener {

        public EditableMapModel newMap;

        public ScrollBoxListener(EditableMapModel newMap) {
            this.newMap = newMap;
        }

        public void itemStateChanged(ItemEvent e) {
            this.newMap.setScrollConfig((String) e.getItem());
        }
    }

    public class AuthorTextFieldListener implements DocumentListener {

        public EditableMapModel newMap;

        public AuthorTextFieldListener(EditableMapModel newMap) {
            this.newMap = newMap;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            update(e);
        }

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
