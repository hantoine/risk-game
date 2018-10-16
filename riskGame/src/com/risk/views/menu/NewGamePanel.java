/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.controllers.MenuListener;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * It represents the new game tab in the start menu.
 *
 * @author timot
 */
public class NewGamePanel extends JPanel {
    
    /**
     * List of the players.
     */
    private PlayerListPanel playersPanel;
    
    /**
     * Panel to select a map on which to play the game.
     */
    private JPanel mapSelector;
    
    /**
     * Button to launch the game.
     */
    private JButton play;
    
    /**
     * Button to open the map editor.
     */
    private final JButton openMapEditor;
    
    /**
     * Panel to either select or edit a new map.
     */
    JPanel mapSelectAndEdit;
    
    /**
     * Dummy text field to be a separator.
     */
    JLabel text;
    
    /**
     * Field that displays the current map being selected.
     */
    private JTextField selectFileTextField;

    /**
     * Constructor.
     *
     * @param riskModel model of the game
     * @param menuAction listener of mouse actions
     */
    public NewGamePanel(RiskModel riskModel, MenuListener menuAction) {

        this.setLayout(new BorderLayout());

        //map selector
        this.mapSelector = new JPanel();
        this.mapSelector.setSize(4000, 50);
        this.selectFileTextField = new JTextField("");
        Dimension d = new Dimension(100, 20);
        this.selectFileTextField.setPreferredSize(d);
        JButton selectFileButton = new JButton("Select a Map");

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser;
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Conquest Map file", "map");
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File("." + File.separator + "maps"));

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    getSelectFileTextField().setText(fileName);
                } else {
                    getSelectFileTextField().setText("");
                }
            }
        });
        this.openMapEditor = new JButton("Open map editor");
        this.text = new JLabel(" ");

        this.mapSelector.setLayout(new FlowLayout());
        this.mapSelector.add(selectFileButton);
        this.mapSelector.add(selectFileTextField);
        this.mapSelector.add(selectFileTextField);

        this.mapSelectAndEdit = new JPanel();
        this.mapSelectAndEdit.setLayout(new BoxLayout(mapSelectAndEdit, BoxLayout.Y_AXIS));
        this.mapSelectAndEdit.add(this.mapSelector);
        this.mapSelectAndEdit.add(this.openMapEditor);
        this.mapSelectAndEdit.add(this.text);

        //players panel
        playersPanel = new PlayerListPanel(riskModel, menuAction);

        //start game button
        this.play = new JButton("PLAY");
        play.addMouseListener(menuAction);

        //add content to panel
        this.add(BorderLayout.PAGE_START, mapSelectAndEdit);
        this.add(BorderLayout.CENTER, playersPanel);
        this.add(BorderLayout.PAGE_END, play);
    }

    /**
     * Getter of the playersPanel attribute
     *
     * @return the playersPanel
     */
    public PlayerListPanel getPlayersPanel() {
        return playersPanel;
    }

    /**
     * Setter of the playersPanel attribute
     *
     * @param playersPanel the playersPanel to set
     */
    public void setPlayersPanel(PlayerListPanel playersPanel) {
        this.playersPanel = playersPanel;
    }

    /**
     * Getter of the button to open the map editor.
     * @return return the map edistor
     */
    public JButton getOpenMapEditor() {
        return this.openMapEditor;
    }

    /**
     * Getter of the mapSelector attribute
     *
     * @return the mapSelector
     */
    public JPanel getMapSelector() {
        return mapSelector;
    }

    /**
     * Setter of the mapSelector attribute
     *
     * @param mapSelector the mapSelector to set
     */
    public void setMapSelector(JPanel mapSelector) {
        this.mapSelector = mapSelector;
    }

    /**
     * Getter of the play attribute
     *
     * @return the play
     */
    public JButton getPlay() {
        return play;
    }

    /**
     * Setter of the play attribute
     *
     * @param play the play to set
     */
    public void setPlay(JButton play) {
        this.play = play;
    }

    /**
     * Getter of the selectFileTextField attribute
     *
     * @return the selectFileTextField
     */
    public JTextField getSelectFileTextField() {
        return selectFileTextField;
    }

    /**
     * Setter of the selectFileTextField attribute
     *
     * @param selectFileTextField the selectFileTextField to set
     */
    public void setSelectFileTextField(JTextField selectFileTextField) {
        this.selectFileTextField = selectFileTextField;
    }

}
