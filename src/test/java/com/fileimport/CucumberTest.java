package com.fileimport;

import cucumber.api.CucumberOptions;


import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "com.fileimport.bdd.stepdefs")
public class CucumberTest {
}
