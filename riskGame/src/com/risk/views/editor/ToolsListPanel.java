/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.views.editor.MapEditorView.ToolButtonListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;

/**
 * Panel that lists the tools that can be used while editing a map and highlight
 * the current tool.
 *
 * @author timot
 */
public class ToolsListPanel extends CustomListPanel {

    /**
     * Constructor.
     *
     * @param width
     * @param height
     * @param toolsList list of the tools that can be used into the editor.
     * @param buttonListener listener to attach to the buttons of the tools.
     */
    public ToolsListPanel(Integer width, Integer height, String[] toolsList, ToolButtonListener buttonListener) {
        super(width, height);

        for (String toolName : toolsList) {
            JButton newTool = new JButton(toolName);

            if (toolName.equals(toolsList[0])) {
                newTool.setBackground(Color.cyan);
            }

            newTool.addActionListener(buttonListener);
            newTool.addActionListener(new selectButtonListener(this));
            this.addElement(newTool, toolName);
        }
    }

    /**
     * Listener on the buttons that update the buttons' colours when the
     * selection changes.
     */
    protected class selectButtonListener implements ActionListener {

        /**
         * container of the buttons
         */
        private final ToolsListPanel panel;

        /**
         * Constructor
         *
         * @param panel
         */
        private selectButtonListener(ToolsListPanel panel) {
            this.panel = panel;
        }

        /**
         * Listener
         *
         * @param e event that will be catched.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton target = (JButton) e.getSource();
            HashMap<String, Component> currentmap = this.panel.items;

            //update the colours of the buttons
            for (HashMap.Entry<String, Component> buttonEntry : currentmap.entrySet()) {

                //retrieve button
                JButton button = (JButton) buttonEntry.getValue();

                //update colour
                if (target.getText().equals(button.getText())) {
                    button.setBackground(Color.cyan);
                } else {
                    button.setBackground(Color.white);
                }
            }
        }
    }
}
