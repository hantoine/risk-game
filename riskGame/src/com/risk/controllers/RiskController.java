/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;
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
        
        viewRisk.initialMap(modelRisk);
        this.viewRisk.setVisible(true);

    }

}
