package com.foreign.exchange.rate.stepdefinitions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.log4j.xml.DOMConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import com.aventstack.extentreports.Status;
import com.cucumber.listener.Reporter;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinition {

	public static Logger logger = Logger.getLogger(StepDefinition.class.getName());
	public static String apiEndPointUri;
	public static String testName;
	public static String CONTENT_TYPE;
	public static String STATUS_CODE;
	public static String FILE_PATH;
	public static String REQUESTBODY;
	public static String RESPONSEBODY;
	public static Response response;
	
	
	@Given("^Set EndPointURL as \"([^\"]*)\"$")	
	public void set_EndPointURL(String arg1) throws Throwable {
		String apiHostName = "https://api.ratesapi.io";
		apiEndPointUri = String.format("%s%s", apiHostName, arg1);
		Reporter.addStepLog(Status.PASS + " :: Cucumber Hostname URL is :: " + apiEndPointUri);
		logger.info("Cucumber Hostname URL is :: " + apiEndPointUri);
		System.out.println("host url is: "+apiEndPointUri);
	}

	@When("^Set the header content type as \"([^\"]*)\"$")
	public void set_the_header_content_type(String arg1) throws Throwable {
		if (arg1 != null && !arg1.isEmpty()) {
			CONTENT_TYPE = arg1;
			Reporter.addStepLog(Status.PASS + " :: content type is :: " + CONTENT_TYPE);
			logger.info("Content type is :: " + CONTENT_TYPE);
		} else {
			Reporter.addStepLog(Status.FAIL + " :: content type cannot be null or empty!");
			logger.info("Content type cannot be null or empty!");
		}
	}

	@When("^Hit the API with request method is \"([^\"]*)\"$")
	public void hit_the_API_with_request_method(String arg1) throws Throwable {
		RestAssured.baseURI = apiEndPointUri;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", CONTENT_TYPE);
		if (arg1.equalsIgnoreCase("GET")) {
			response = request.get();
		}
		STATUS_CODE = String.valueOf(response.getStatusCode());
		RESPONSEBODY = response.getBody().asString();
		Reporter.addStepLog(Status.PASS + " :: Request successfully processed");
		Reporter.addStepLog("Response is :: " + RESPONSEBODY);
	}
	
	@Then("^Verify the status code is \"([^\"]*)\"$")
	public void verify_the_status_code(String arg1) throws Throwable {
		if (arg1.equals(String.valueOf(STATUS_CODE))) {
			Assert.assertEquals(STATUS_CODE, arg1);
			Reporter.addStepLog(Status.PASS + " :: Status Code is :: " + STATUS_CODE);
			logger.info("Status Code is :: " + STATUS_CODE);
		} else {
			Assert.assertEquals(STATUS_CODE, arg1);
			Reporter.addStepLog(Status.FAIL + " :: Status Code is :: " + STATUS_CODE);
			logger.info("Status Code is not as expected :: " + STATUS_CODE);
		}
	}

	@And("^Verify the response value of \"([^\"]*)\" is \"([^\"]*)\"$")
	public void verify_Response_Value(String responseKey, String value) throws Throwable {
		JSONParser parser = new JSONParser();
		JSONObject responseObject = (JSONObject) parser.parse(RESPONSEBODY);
		Object key = (Object) responseObject.get(responseKey);
		compareResponseValues(String.valueOf(value), String.valueOf(key), responseKey);
	}

	private void compareResponseValues(String expected, String actual, String responseKey) {
		Reporter.addStepLog("Actual Value is  ::" + actual);
		Reporter.addStepLog("Expected Value is  ::" + expected);
		logger.info("Actual Value is  ::" + actual);
		logger.info("Expected Value is  ::" + expected);
		if (expected.equals(actual)) {
			Assert.assertEquals(actual, expected);
			Reporter.addStepLog(Status.PASS + " " + responseKey + " : Expected value : " + expected
					+ " mathches with Actual Value : " + actual);
		} else {
			Reporter.addStepLog(Status.FAIL + " " + responseKey + " : Expected value : " + expected
					+ " do not matches with Actual Value : " + actual);
			Assert.assertEquals(actual, expected);
		}
	}

	@And("^Verify the \"([^\"]*)\" in response matches with current date$")
	public void verify_Response_Matches_With_Current_Date(String responseKey) throws Throwable {
		
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DATE, -1);
		
		JSONParser parser = new JSONParser();
		JSONObject responseObject = (JSONObject) parser.parse(RESPONSEBODY);
		Object key = (Object) responseObject.get(responseKey);
		compareResponseValues(String.valueOf(dateFormat.format(cal.getTime())), String.valueOf(key), responseKey);
		
	}

}
