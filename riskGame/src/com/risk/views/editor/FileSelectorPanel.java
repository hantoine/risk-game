/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Panel enabling to select a file and display the name of the selected file in
 * a textfield. While you don't set a label there is no label displayed.
 *
 * @author timot
 */
public class FileSelectorPanel extends JPanel {

    protected JButton selectFileButton;
    protected JTextField textField;
    protected JLabel label;
    protected JPanel selectionPanel;

    /**
     * Constructor method.
     *
     * @param width
     * @param height
     * @param exts : available extensions
     */
    public FileSelectorPanel(int width, int height, FileNameExtensionFilter exts) {
        //parameterize the panel
        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        //create label
        this.label = new JLabel("");
        this.label.setVisible(false);

        //create panel for button and text field
        this.selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());

        //create text field
        this.textField = new JTextField("No file selected.");
        this.textField.setEditable(false);
        setTextFieldSize(200, 20);

        //create button
        this.selectFileButton = new JButton("Select a file");
        addFileChooserListener(exts);

        //add all components to the class
        this.add(BorderLayout.PAGE_START, label);
        selectionPanel.add(selectFileButton);
        selectionPanel.add(textField);
        this.add(BorderLayout.CENTER, selectionPanel);
    }

    /**
     * Add a listener to the button so that it opens a FileChooser when clicked.
     *
     * @param exts
     */
    public void addFileChooserListener(FileNameExtensionFilter exts) {
        //add an action listener so that when one click on the button it opens the file chooser
        this.selectFileButton.addActionListener((ActionEvent e) -> {
            //create a new fiel chooser
            JFileChooser fileChooser;
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(exts);
            fileChooser.setCurrentDirectory(new File("." + File.separator));

            //handle selection on file chooser
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                setTextFieldMessage(fileName);
            } else {
                setTextFieldMessage("No file selected.");
            }
        });
    }

    public JTextField getTextField() {
        return this.textField;
    }

    /**
     * Update the size of the button.
     *
     * @param width
     * @param height
     */
    public void setTextFieldSize(int width, int height) {
        Dimension d = new Dimension(width, height);
        this.textField.setPreferredSize(d);
    }

    /**
     * Update message being displayed in the textfield.
     *
     * @param message
     */
    public void setTextFieldMessage(String message) {
        this.textField.setText(message);
    }

    /**
     * Update message being displayed on the button.
     *
     * @param message
     */
    public void setButtonMessage(String message) {
        this.selectFileButton.setText(message);
    }

    /**
     * Set the message displayed by the label and make it visible if it is not
     * already
     *
     * @param message
     */
    public void setLabel(String message) {
        this.label.setText(message);
        if (!this.label.isVisible()) {
            this.label.setVisible(true);
        }
    }
}
