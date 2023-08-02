package com.hackathon.tests;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.hackathon.driver.DriverSetup;
import com.hackathon.functions.ExcelUtil;
import com.hackathon.functions.ScreenShot;
import com.hackathon.functions.UserFunctions;
import com.hackathon.model.Service;
import com.hackathon.pages.JustDialHomePage;
import com.hackathon.pages.JustDialSearchResultPage;
import com.hackathon.reporter.ExtentReporterUtil;

@Test
public class IdentifyCarWashServices {

	private WebDriver driver;
	private JustDialHomePage justDialHomePage;
	private JustDialSearchResultPage justDialSearchResultPage;

	@BeforeTest
	public void init() {
		driver = DriverSetup.getDriver();
		justDialHomePage = new JustDialHomePage(driver);
		justDialSearchResultPage = new JustDialSearchResultPage(driver);
	}
	
	

	@Test(priority = 3)
	@Parameters({ "location" })
	public void SelectLocation(@Optional("Kolkata") String location) {
		ExtentReporterUtil.logReport("SelectLocation", "Location Successfully selected as " + location);
		justDialHomePage.selectLocationAs(location);
	}

	
	
	@Test(priority = 4)
	public void SearchForCarWashServices() {
		ExtentReporterUtil.logReport("SearchForCarWashServices", "Searching for Car wash services at selected location");
		justDialHomePage.searchFor("Car Washing Services");
	}
	
	

	@Test(priority = 5)
	public void SortSearchResultByRating() {
		ExtentReporterUtil.logReport("SortSearchResultByRating", "Click on the Top Rated button");
		justDialSearchResultPage.sortSearchResultByRating();
	}
	
	

	@Test(priority = 6)
	@Parameters({ "location" })
	public void IdentifyTopFiveCarWashingService(@Optional("Kolkata") String location) {
		ExtentReporterUtil.logReport("IdentifyTopFiveCarWashingService", "Display and store top 5 search results");
		List<Service> services = justDialSearchResultPage.getTopFiveServiceDetails();
	
		// Print and Save to Excel
		// Create XSSFWorkbook
		ExcelUtil excelWriter = new ExcelUtil();
		XSSFSheet sheet = excelWriter.workbook.createSheet("Top Car Washing Services");
		
		System.out.println("\nTop 5 Car Washing Services in " + location);
		for (int i = 0; i < 5; i++) {
			String name = services.get(i).getServiceName();
			String phoneNumber = services.get(i).getPhoneNumber();
			System.out.println("Car Washing Service: " + name + "  Phone Number: " + phoneNumber);
			
			// Create an excel sheet row && store service name to a new cell
			XSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(name);
			row.createCell(1).setCellValue(phoneNumber);
		}
		// Write Sheet data to file
		excelWriter.writeToExcel(UserFunctions.getReportBasePath() + "/TopFiveCarWashingServices.xlsx");
		System.out.println("Recorded results stored in: " + UserFunctions.getReportBasePath() + "/sheets/topFiveCarWashingServices.xlsx");
	}
	
	
	
	@Test(priority = 7) 
	@Parameters({"baseUrl"})
	public void NavigateBackToHomePage(String baseUrl) {
		ExtentReporterUtil.logReport("NavigateBackToHomePage", "Load JustDial Home Page Successfully");

		driver.manage().deleteAllCookies();
		driver.navigate().back();
		
		UserFunctions.sleep(5000);
		driver.manage().deleteAllCookies();
		Assert.assertTrue(driver.getTitle().contains("Justdial")); // Check whether page title contains JustDial
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
