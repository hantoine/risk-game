/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.main;

import com.risk.controllers.GameControllerTest;
import com.risk.controllers.MapEditorControllerTest;
import com.risk.controllers.MenuListenerTest;
import com.risk.models.AttackMoveTest;
import com.risk.models.MapFileManagementTest;
import com.risk.models.MapModelTest;
import com.risk.models.PlayerModelTest;
import com.risk.models.RiskModelTest;
import com.risk.models.TournamentModelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite for the group of test Classes
 *
 * @author Nellybett
 */
@RunWith(Suite.class)
@SuiteClasses({
    MapFileManagementTest.class,
    MapEditorControllerTest.class,
    MenuListenerTest.class,
    PlayerModelTest.class,
    GameControllerTest.class,
    AttackMoveTest.class,
    MapModelTest.class,
    RiskModelTest.class,
    TournamentModelTest.class
})

/**
 * TestSuite class
 */
public class TestSuiteAll {
}
