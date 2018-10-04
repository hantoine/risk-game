/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import com.risk.mapeditor.MapEditorPanel.ToolButtonListener;
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
            this.addElement(newTool, toolName);
        }
    }
}
