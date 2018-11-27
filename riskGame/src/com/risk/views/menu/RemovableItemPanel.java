/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Removable item for MapPathListPanel and StrategyListPanel in tournament mode
 *
 * @author hantoine
 * @param <T> type of removable item
 */
public class RemovableItemPanel<T> extends JPanel {

    /**
     * Strategy name or map name
     */
    private final JLabel name;
    /**
     * Remove button
     */
    private final JButton removeButton;
    /**
     * Removable item
     */
    private T item;

    /**
     * Constructor
     *
     * @param item removable item
     */
    public RemovableItemPanel(T item) {
        name = new JLabel(item.toString());
        removeButton = new JButton("-");

        GroupLayout gl = new GroupLayout(this);
        this.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createParallelGroup()
                .addComponent(name)
                .addComponent(removeButton)
        );
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(removeButton)
                .addComponent(name)
        );
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Getter of removable item
     *
     * @return Removable item
     */
    public T getItem() {
        return item;
    }

    /**
     * Set listener for remove button
     *
     * @param listener RemovableItemPanelListener
     */
    public void setListener(RemovableItemPanelListener<T> listener) {
        removeButton.addActionListener(al -> {
            listener.removed();
        });
    }

    /**
     * Interface for listener for removable item
     *
     * @param <T> type of removable item
     */
    public static interface RemovableItemPanelListener<T> {

        /**
         * Remove the item
         */
        public void removed();
    }
}
