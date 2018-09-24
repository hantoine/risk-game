/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.menu.MenuView;
import com.risk.views.menu.StartMenuView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

/**
 *
 * @author Nellybett
 */
public class RiskController implements ActionListener{

    RiskView viewRisk;
    RiskModel modelRisk;
    MenuListener menuListener;
    MapListener countryListener;
    
    public RiskController(RiskModel riskModel,RiskView riskView) {
        this.modelRisk=riskModel;
        this.viewRisk = riskView;
        this.viewRisk.setRiskController(this);
        this.countryListener = new MapListener(modelRisk);
        this.menuListener = new MenuListener(modelRisk, viewRisk, this); 
        viewRisk.initialMenu(modelRisk, menuListener);
        viewRisk.addMenuBar();
        viewRisk.initStagePanel();
        viewRisk.setVisible(true);
        //viewRisk.initialMap(modelRisk, countryListener);
        

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("hola llegue");
        JComponent c = (JComponent) e.getSource();
        
        if (c != null && c instanceof JMenuItem) {
            JMenuItem source = (JMenuItem) c;
            System.out.println(source.getText());
            if(source.getText().equals("New Game")){
                StartMenuView aux1=new StartMenuView(modelRisk, menuListener);
                
                MenuView aux=new MenuView(aux1,viewRisk, "New Game");
                this.viewRisk.setMenuPanel(aux);
                aux.add(aux1);
                aux.setSize(300,500);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                aux.setLocation(dimension.width / 2 - 300 / 2, dimension.height / 2 - 500 / 2);
                aux.setVisible(true);
            }
        }
    }

    void initGame() {
        
        this.viewRisk.initialMap(modelRisk, countryListener);
    }
}