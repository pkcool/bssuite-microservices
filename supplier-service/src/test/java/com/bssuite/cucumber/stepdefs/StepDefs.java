package com.bssuite.cucumber.stepdefs;

import com.bssuite.SupplierServiceApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SupplierServiceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
