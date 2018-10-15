/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import com.risk.models.MapConfig;
import com.risk.controllers.MapEditorController;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel that allows to set the parameters of the map.
 * This panel comes into the map editor.
 * @author timot
 * @see MapConfig
 */
public class MapConfigPanel extends JPanel {

    /**
     * Field to set the author name.
     */
    protected JTextField authorField;
    
    /**
     * Checkbox for the warn parameter 
     */
    protected JCheckBox isWarnBox;
    
    /**
     * Checkbox for the wrap parameter 
     */
    protected JCheckBox isWrapBox;

    /**
     * Allow to choose the type of scrolling for the map. 
     */
    protected JComboBox scrollTypeBox;

    /**
     * Constructor.
     * @param controler Controller of the map editor that contains the listeners for the components of this panel.
     * @param configModel Model to initialize the configuration parameters.
     */
    public MapConfigPanel(MapEditorController controler, MapConfig configModel) {
        this.setLayout(new FlowLayout());
        init(controler, configModel);
    }

    /**
     * Function to initialize the components of the panel.
     * @param controler Controller of the map editor that contains the listeners for the components of this panel.
     * @param configModel Model to initialize the configuration parameters.
     */
    private void init(MapEditorController controler, MapConfig configModel) {
        //create components
        this.authorField = new JTextField(configModel.getAuthor());
        this.isWarnBox = new JCheckBox();
        this.isWarnBox.setSelected(configModel.isWarn());
        this.isWrapBox = new JCheckBox();
        this.isWrapBox.setSelected(configModel.isWrap());
        String[] scrollTypes = {"horizontal", "vertical", "none"};
        this.scrollTypeBox = new JComboBox(scrollTypes);
        this.scrollTypeBox.setSelectedItem(configModel.getScroll());

        //add listeners
        this.isWarnBox.addItemListener(controler.getWarnCheckBoxListener());
        this.isWrapBox.addItemListener(controler.getWrapCheckBoxListener());
        this.scrollTypeBox.addItemListener(controler.getScrollBoxListener());
        this.authorField.getDocument().addDocumentListener(controler.getAuthorTextFieldListener());

        createContainers();
    }

    /**
     * Function to put the elements into containers and add it to the view.
     */
    public void createContainers(){
        //create container panels 
        JPanel authorPanel = new JPanel();
        authorPanel.setLayout(new BoxLayout(authorPanel, BoxLayout.Y_AXIS));
        authorPanel.add(new JLabel("Author Name"));
        authorPanel.add(this.authorField);
        this.add(authorPanel);

        JPanel warnPanel = new JPanel();
        warnPanel.setLayout(new BoxLayout(warnPanel, BoxLayout.Y_AXIS));
        warnPanel.add(new JLabel("Warn"));
        warnPanel.add(this.isWarnBox);
        this.add(warnPanel);

        JPanel wrapPanel = new JPanel();
        wrapPanel.setLayout(new BoxLayout(wrapPanel, BoxLayout.Y_AXIS));
        wrapPanel.add(new JLabel("Wrap"));
        wrapPanel.add(this.isWrapBox);
        this.add(wrapPanel);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.add(new JLabel("Scroll"));
        scrollPanel.add(this.scrollTypeBox);
        this.add(scrollPanel);
    }
    
    /**
     * Setter for the view, using the model.
     * @param mapConfig model for the configuration of the map.
     */
    public void setView(MapConfig mapConfig) {
        authorField.setText(mapConfig.getAuthor());
        isWarnBox.setSelected(mapConfig.isWarn());
        isWrapBox.setSelected(mapConfig.isWrap());
        scrollTypeBox.setSelectedItem(mapConfig.getScroll());
    }

}
