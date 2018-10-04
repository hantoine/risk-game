/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.mapeditor.MapEditorPanel.ToolButtonListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;

/**
 *
 * @author timot
 */
public class ToolsListPanel extends CustomListPanel {
    
    public ToolsListPanel(Integer width, Integer height, String[] toolsList, ToolButtonListener buttonListener) {
        super(width, height);
        
        for(String toolName:toolsList){
            JButton newTool = new JButton(toolName);
            newTool.addActionListener(buttonListener);
            newTool.addActionListener(new selectButtonListener(this));
            this.addElement(newTool, toolName);
        }
    }
    
    protected class selectButtonListener implements ActionListener{

        private final ToolsListPanel panel;

        private selectButtonListener(ToolsListPanel panel) {
            this.panel = panel;
        }
                
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton target = (JButton)e.getSource();
            
            HashMap<String, Component> currentmap = this.panel.items;
            for(HashMap.Entry<String, Component> buttonEntry: currentmap.entrySet()){
                JButton button =(JButton)buttonEntry.getValue();
                if(target.getText().equals(button.getText()))
                    button.setBackground(Color.cyan);
                else
                    button.setBackground(Color.white);
            }
        }
    }
}
