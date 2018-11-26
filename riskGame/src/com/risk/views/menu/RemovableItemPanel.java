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
 *
 * @author hantoine
 * @param <T>
 */
public class RemovableItemPanel<T> extends JPanel {

    private final JLabel name;
    private final JButton removeButton;
    private T item;

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

    public T getItem() {
        return item;
    }

    public void setListener(RemovableItemPanelListener<T> listener) {
        removeButton.addActionListener(al -> {
            listener.removed();
        });
    }

    public static interface RemovableItemPanelListener<T> {

        public void removed();
    }
}
