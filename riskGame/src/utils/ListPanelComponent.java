/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author timot
 */
public class ListPanelComponent extends JPanel {
    private JPanel container;
    private LinkedList<JPanel> items;
    private JButton addButton;

    public ListPanelComponent(Integer panelWidth, Integer panelHeight) {
        this.setSize(panelWidth, panelHeight);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.container = new JPanel();
        this.items = new LinkedList<>();
        this.addButton = new JButton("+");
        
        this.container.add(addButton);
        this.add(container);
    }
    
    public void addElement() {    
        revalidate();
        repaint();
    }
    
    public void removeElement(){
        revalidate();
        repaint();
    }
}