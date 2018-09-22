/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.map.MapPanel;
import com.risk.views.menu.MenuView;
import com.risk.views.menu.StartMenuView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author Nellybett
 */
public class BarListener implements ActionListener {
    
    RiskModel riskModel;
    MenuListener menuListener;
    RiskView riskView;
    
    public BarListener(RiskModel riskModel,RiskView riskView,MenuListener menuListener) {
        this.menuListener = menuListener;
        this.riskModel=riskModel;
        this.riskView=riskView;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent c = (JComponent) e.getSource();
        System.out.println("CLICK!!!!!!!");
        if (c != null && c instanceof JMenuItem) {
            JMenuItem source = (JMenuItem) c;
            System.out.println(source.getText());
            if(source.getText().equals("New Game")){
                StartMenuView aux1=new StartMenuView(riskModel, menuListener);
                
                MenuView aux=new MenuView(aux1,riskView, "New Game");
                this.riskView.setMenuPanel(aux);
                aux.add(aux1);
                aux.setSize(300,500);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                aux.setLocation(dimension.width / 2 - 300 / 2, dimension.height / 2 - 500 / 2);
                aux.setVisible(true);
            }
        }
    }
    
}
