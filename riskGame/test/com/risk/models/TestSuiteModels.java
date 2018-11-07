/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for the group of test Classes
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    MapFileManagementTest.class,
    PlayerModelTest.class,
    AttackMoveTest.class,
    MapModelTest.class
})

public class TestSuiteModels {
}
