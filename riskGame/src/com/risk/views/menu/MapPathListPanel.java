/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.models.MapPath;
import com.risk.models.TournamentModel;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Panel that contains multiple panels with players information
 *
 * @author Nellybett
 */
public class MapPathListPanel extends JPanel {

    JButton addButton;
    JPanel strategiesList;
    MapPathListPanelListener listener;

    public MapPathListPanel() {

        addButton = new JButton("Add Map");
        strategiesList = new JPanel();
        this.strategiesList.setLayout(
                new BoxLayout(strategiesList, BoxLayout.Y_AXIS));

        GroupLayout gl = new GroupLayout(this);
        this.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(addButton)
                .addComponent(strategiesList)
        );
        gl.setHorizontalGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(addButton)
                .addComponent(strategiesList)
        );
    }

    public void updateView(TournamentModel tm) {
        this.strategiesList.removeAll();

        tm.getMapsPaths().forEach((s) -> {
            RemovableItemPanel<MapPath> newStrategy
                    = new RemovableItemPanel<>(s);
            newStrategy.setListener(() -> {
                listener.mapRemoved(s);
            });
            this.strategiesList.add(newStrategy);
        });

        this.addButton.setEnabled(tm.getMapsPaths().size() != 5);

        this.revalidate();
        this.repaint();
    }

    public void setListener(MapPathListPanelListener listener) {
        this.addButton.addActionListener((ActionEvent ae) -> {
            JFileChooser fileChooser;
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Conquest Map file", "map");
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File("." + File.separator + "maps"));

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                listener.mapAdded(new MapPath(fileName));
            }
        });

        this.listener = listener;
    }

    public static interface MapPathListPanelListener {

        void mapAdded(MapPath mapPath);

        void mapRemoved(MapPath mapPath);
    }

}
