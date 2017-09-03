package com.bssuite.cucumber.stepdefs;

import com.bssuite.ProductServiceApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ProductServiceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
