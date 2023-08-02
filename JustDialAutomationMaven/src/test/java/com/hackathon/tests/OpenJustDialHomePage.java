package com.hackathon.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.hackathon.driver.DriverSetup;
import com.hackathon.functions.ScreenShot;
import com.hackathon.functions.UserFunctions;
import com.hackathon.pages.JustDialHomePage;
import com.hackathon.reporter.ExtentReporterUtil;

@Test
public class OpenJustDialHomePage {
	private WebDriver driver;
	private JustDialHomePage justDialHomePage;

	@BeforeSuite
	public void initSuite() {
		// Start the browser
		// Set Driver implicit timeout as predefined
		// Setup Extent Reporter
		driver = DriverSetup.getUserSpecifiedDriver();                                  
		ExtentReporterUtil.initReport();                                                
	}
	
	

	@Test(priority = 1)
	@Parameters({ "baseUrl" })
	public void OpenJustdialHomePage(String baseUrl) {
		ExtentReporterUtil.logReport("OpenJustdialHomePage", "Load JustDial Home Page Successfully");
		driver.get(baseUrl);
		driver.manage().deleteAllCookies();
		Assert.assertTrue(driver.getTitle().contains("Justdial"));
		
		// initialize PageFactory for home page
		justDialHomePage = new JustDialHomePage(driver);
	}
	
	

	@Test(priority = 2)
	public void CloseLoginPopup() {
		ExtentReporterUtil.logReport("CloseLoginPopup", "Close Login popup");
		justDialHomePage.closeLoginModal();
	}
	
	
	
	@AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
        	System.err.println("\nThrew error in TestMethod("+ result.getTestName() +"): "+ result.getThrowable().getMessage() +"\n");
            ExtentReporterUtil.logStatus(Status.FAIL,result.getThrowable());
            ExtentReporterUtil.attachScreenShot(
            		ScreenShot.takeTestCaseScreenShot(result, ScreenShot.FailedStatus),
            		"After Execution Failed");
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
        	ExtentReporterUtil.logStatus(Status.PASS, result.getTestName());
        	ExtentReporterUtil.attachScreenShot(
            		ScreenShot.takeTestCaseScreenShot(result, ScreenShot.SuccessStatus),
            		"After Execution Success");
        }
        else {
        	ExtentReporterUtil.logStatus(Status.SKIP, result.getTestName());
        }
    }
	
	

	@AfterSuite
	@SuppressWarnings("all")
	public void tearDown() {
		
		// Close Browser
		System.out.println("\nClosing Browser...");
		UserFunctions.sleep(3000);
		driver.quit();
		
		// Close Report logging
		ExtentReporterUtil.endReport();
		System.out.println("Reports saved to " + UserFunctions.getReportBasePath());
	}
}
