/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;


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
        //modelRisk.setBoard("maps"+File.separator+"Old Yorkshire.map");
        MapListener countryListener= new MapListener(modelRisk);
        MenuListener menuListener=new MenuListener(modelRisk,viewRisk,countryListener);
        viewRisk.initialMenu(modelRisk, menuListener);
        //viewRisk.initialMap(modelRisk, countryListener);
        this.viewRisk.setVisible(true);

    }

}
