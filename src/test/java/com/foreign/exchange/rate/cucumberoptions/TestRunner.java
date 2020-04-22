package com.foreign.exchange.rate.cucumberoptions;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/foreign/exchange/rate/features", 
		glue = { "com.foreign.exchange.rate.stepdefinitions" }, tags= {"@RegressionTesting"}, plugin = {
				"pretty", "com.cucumber.listener.ExtentCucumberFormatter:src/test/reports/cucumber_report.html",
				"html:output/html-report" }, monochrome = true)
public class TestRunner {
	
}
