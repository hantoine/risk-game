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
 *
 * @author timot
 */
public class MapConfigPanel extends JPanel {

    protected JTextField authorField;
    protected JCheckBox isWarnBox;
    protected JCheckBox isWrapBox;
    protected JComboBox scrollTypeBox;

    public MapConfigPanel(MapEditorController controler, MapConfig configModel) {
        this.setLayout(new FlowLayout());

        init(controler, configModel);
    }

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

    public void setView(MapConfig mapConfig) {
        authorField.setText(mapConfig.getAuthor());
        isWarnBox.setSelected(mapConfig.isWarn());
        isWrapBox.setSelected(mapConfig.isWrap());
        scrollTypeBox.setSelectedItem(mapConfig.getScroll());
    }

}
