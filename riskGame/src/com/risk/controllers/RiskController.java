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
        this.viewRisk.inicializePanels(modelRisk);
        this.viewRisk.setVisible(true);
    }
    
}
