/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * Window of showing the log
 *
 * @author hantoine
 */
public class LogViewer extends JFrame {

    /**
     * Log text
     */
    JTextArea logTextArea;

    /**
     * Constructor
     *
     * @param logs List of logs
     */
    public LogViewer(List<String> logs) {
        this.setResizable(false);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setColumns(120);
        logTextArea.setRows(10);
        logTextArea.setText(logs.stream().collect(Collectors.joining("\n")));

        JScrollPane scrollableActions = new JScrollPane(logTextArea);
        scrollableActions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableActions.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        GroupLayout gl = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(scrollableActions)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(scrollableActions)
        );

        this.setSize(this.getPreferredSize().width, 600);
        this.centerWindow();
        this.setTitle("Tournament Logs");
        this.setVisible(true);
    }

    /**
     * Set log window in the center
     */
    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }
}
