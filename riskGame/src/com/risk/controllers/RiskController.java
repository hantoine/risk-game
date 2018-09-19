/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.StartMenuView;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;

/**
 *
 * @author Nellybett
 */
public class RiskController {

    RiskView viewRisk;
    RiskModel modelRisk;

    public RiskController(RiskView viewRisk, RiskModel modelRisk) {
        this.modelRisk = modelRisk;
        this.viewRisk = viewRisk;
        modelRisk.setBoard("C:\\Users\\Nellybett\\Desktop\\Old Yorkshire.map");
        //modelRisk.setBoard("maps"+File.separator+"Old Yorkshire.map");
        MapListener countryListener= new MapListener(modelRisk);
        MenuListener menuListener=new MenuListener(modelRisk,viewRisk,countryListener);
        viewRisk.initialMenu(modelRisk, menuListener);
        //viewRisk.initialMap(modelRisk, countryListener);
        this.viewRisk.setVisible(true);

    }

}
