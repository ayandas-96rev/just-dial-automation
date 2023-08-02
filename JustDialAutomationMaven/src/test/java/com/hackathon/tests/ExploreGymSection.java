package com.hackathon.tests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.hackathon.driver.DriverSetup;
import com.hackathon.functions.ScreenShot;
import com.hackathon.functions.UserFunctions;
import com.hackathon.pages.JustDialHomePage;
import com.hackathon.pages.JustDialSearchResultPage;
import com.hackathon.reporter.ExtentReporterUtil;

@Test
public class ExploreGymSection {
	private WebDriver driver;
	private JustDialHomePage justDialHomePage;
	private JustDialSearchResultPage justDialSearchResultPage;
	
	
	@BeforeTest
	public void init() {
		driver = DriverSetup.getDriver();
		justDialHomePage = new JustDialHomePage(driver);
		justDialSearchResultPage = new JustDialSearchResultPage(driver);
	}
	
	
	
	@Test(priority = 8)
	public void OpenGymSection() {
		ExtentReporterUtil.logReport("OpenGymSection", "Load Gym Page Successfully");
		justDialHomePage.clickOnGymLink();
	}
	
	
	
	@Test(priority = 9)
	public void ListAndStoreAllSubmenuItems() {
		ExtentReporterUtil.logReport("ListAndStoreAllSubmenuItems", "Display and store all the related submenu items in Gym section");
		List<String> popularModelNames = justDialSearchResultPage.getAllTheSubmenuItemsRelatedToGym();
		
		System.out.println("\nAll Submenu Items:");
		popularModelNames.stream().forEach(item -> System.out.println(item));
	}
	
	
	
	@Test(priority = 10) 
	@Parameters({"baseUrl"})
	public void NavigateBackToHomePage(String baseUrl) {
		ExtentReporterUtil.logReport("NavigateBackToHomePage", "Load JustDial Home Page Successfully");

		driver.manage().deleteAllCookies();
		driver.navigate().back();
		
		UserFunctions.sleep(5000);
		driver.manage().deleteAllCookies();
		
		Assert.assertTrue(driver.getTitle().contains("Justdial"));
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

}
