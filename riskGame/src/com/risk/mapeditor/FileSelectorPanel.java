/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Panel enabling to select a file and display the name of the selected file in a textfield.
 * @author timot
 */
public class FileSelectorPanel extends JPanel {
    private JButton selectFileButton;
    private JTextField textField;
    
    /**
     * Constructor method.
     * @param width
     * @param height 
     * @param exts : available extensions 
     */
    public FileSelectorPanel(int width, int height, FileNameExtensionFilter exts){
        //parameterize the panel
        this.setSize(width, height);
        this.setLayout(new FlowLayout());

        this.textField = new JTextField("No file selected.");
        setTextFieldSize(200,20);
        
        this.selectFileButton = new JButton("Select a file");
        this.selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser;
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(exts);
                fileChooser.setCurrentDirectory(new File("."+File.separator));
                
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    setTextFieldMessage(fileName);
                } else {
                    setTextFieldMessage("No file selected.");
                }
            }
        });

        this.add(selectFileButton);
        this.add(textField);
    }
    
    /**
     * Update the size of the button.
     * @param width
     * @param height 
     */
    public void setTextFieldSize(int width, int height){
        Dimension d= new Dimension(width, height);
        this.textField.setPreferredSize(d);
    }
    
    /**
     * Update message being displayed in the textfield.
     * @param message 
     */
    public void setTextFieldMessage(String message){
        this.textField.setText(message);
    }
    
    /**
     * Update message being displayed on the button.
     * @param message 
     */
    public void setButtonMessage(String message){
        this.selectFileButton.setText(message);
    }
}
