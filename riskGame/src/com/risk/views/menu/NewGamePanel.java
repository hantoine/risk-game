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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author timot
 */
public class NewGamePanel extends JPanel {

    private JPanel playersPanel;
    private JPanel mapSelector;
    private JButton play;
    private JTextField selectFileTextField;

    public NewGamePanel(RiskModel riskModel, MenuListener menuAction) {

        this.setLayout(new BorderLayout());

        //map selector
        this.mapSelector = new JPanel();
        this.mapSelector.setSize(4000, 50);
        this.selectFileTextField = new JTextField("");
        Dimension d= new Dimension(100, 20);
        this.selectFileTextField.setPreferredSize(d);
        JButton selectFileButton = new JButton("Select a Map");
        
        
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser;
                fileChooser = new JFileChooser();

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    getSelectFileTextField().setText(fileName);
                } else {
                    getSelectFileTextField().setText("");
                }
            }
        });

        this.mapSelector.setLayout(new FlowLayout());
        this.mapSelector.add(selectFileButton);
        this.mapSelector.add(selectFileTextField);
        this.mapSelector.add(selectFileTextField);

        //players panel
        playersPanel = new PlayerListPanel(riskModel, menuAction);

        //start game button
        this.play = new JButton("PLAY");
        play.addMouseListener(menuAction);

        //add content to panel
        this.add(BorderLayout.PAGE_START, mapSelector);
        this.add(BorderLayout.CENTER, playersPanel);
        this.add(BorderLayout.PAGE_END, play);
    }

    /**
     * @return the playersPanel
     */
    public JPanel getPlayersPanel() {
        return playersPanel;
    }

    /**
     * @param playersPanel the playersPanel to set
     */
    public void setPlayersPanel(JPanel playersPanel) {
        this.playersPanel = playersPanel;
    }

    /**
     * @return the mapSelector
     */
    public JPanel getMapSelector() {
        return mapSelector;
    }

    /**
     * @param mapSelector the mapSelector to set
     */
    public void setMapSelector(JPanel mapSelector) {
        this.mapSelector = mapSelector;
    }

    /**
     * @return the play
     */
    public JButton getPlay() {
        return play;
    }

    /**
     * @param play the play to set
     */
    public void setPlay(JButton play) {
        this.play = play;
    }

    /**
     * @return the selectFileTextField
     */
    public JTextField getSelectFileTextField() {
        return selectFileTextField;
    }

    /**
     * @param selectFileTextField the selectFileTextField to set
     */
    public void setSelectFileTextField(JTextField selectFileTextField) {
        this.selectFileTextField = selectFileTextField;
    }

}
