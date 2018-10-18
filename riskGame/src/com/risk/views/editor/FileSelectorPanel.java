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

    /**
     * Button to launch the file chooser.
     */
    protected JButton selectFileButton;

    /**
     * Text field that displays the path to the selected file.
     */
    protected JTextField textField;

    /**
     * Label to inform the user.
     */
    protected JLabel label;

    /**
     * Selection panel which contains all the above elements.
     */
    protected JPanel selectionPanel;

    /**
     * Constructor method.
     *
     * @param width the width of the panel
     * @param height the height of the panel
     * @param exts Available extensions of the files that can be selected.
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
     * @param exts Available extensions of the files that can be selected.
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

    /**
     * Getter of the JTextfield that contains the path to the selected file.
     *
     * @return a JTextfield that contains the path to the selected file.
     */
    public JTextField getTextField() {
        return this.textField;
    }

    /**
     * Update the size of the button.
     *
     * @param width width of the textfield
     * @param height height of the textfield
     */
    public void setTextFieldSize(int width, int height) {
        Dimension d = new Dimension(width, height);
        this.textField.setPreferredSize(d);
    }

    /**
     * Update message being displayed in the textfield.
     *
     * @param message the message which is sent to the text field
     */
    public void setTextFieldMessage(String message) {
        this.textField.setText(message);
    }

    /**
     * Update message being displayed on the button.
     *
     * @param message the message which is sent to the text field
     */
    public void setButtonMessage(String message) {
        this.selectFileButton.setText(message);
    }

    /**
     * Set the message displayed by the label and make it visible if it is not
     * already
     *
     * @param message message shown in the label
     */
    public void setLabel(String message) {
        this.label.setText(message);
        if (!this.label.isVisible()) {
            this.label.setVisible(true);
        }
    }
}
