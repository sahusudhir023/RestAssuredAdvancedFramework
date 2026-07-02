package com.automation.api.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.response.Response;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public static ExtentTest test;
	
	public void onStart(ITestContext testContext) {
		
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/ExtentReport.html");
		
		sparkReporter.config().setDocumentTitle("RestAssured API AUtomation Report");
		sparkReporter.config().setReportName("JsonPlaceholder API Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "JsonPlaceholder API");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User name", System.getProperty("user.name"));
		
		extent.setSystemInfo("Environment", ConfigReader.getProperty("environment"));
		extent.setSystemInfo("User", ConfigReader.getProperty("user"));
	}
	
	
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getName());
	}
	
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Case PASSED is : " + result.getName());
	}
	
	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, "Test Case FAILED is : " + result.getName());
		test.log(Status.FAIL, "Test Case FAILED due to : " + result.getThrowable().getMessage());
	}
	
	public void onTestSKipped(ITestResult result) {
		test.log(Status.SKIP, "Test Case SKIPPED is : " + result.getName());
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
	
	public static void logResponseToReport(Response response) {
		
		if(test != null) {
			String prettyJson = response.getBody().asPrettyString();
			
			test.info("<b>Status Code</b>" + response.getStatusCode());
			test.info("<b>Response JSON : </b><pre>" + prettyJson +"</pre>");
		}
	}
	
	
}
