package com.hackathon.tests;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.hackathon.driver.DriverSetup;
import com.hackathon.functions.ExcelUtil;
import com.hackathon.functions.ScreenShot;
import com.hackathon.pages.JustDialFreeListingPage;
import com.hackathon.pages.JustDialHomePage;
import com.hackathon.reporter.ExtentReporterUtil;

public class FreeListingPageTest {

	private WebDriver driver;
	private JustDialHomePage justDialHomePage;
	private JustDialFreeListingPage justDialFreeListingPage;

	@DataProvider(name="freeListingData")
	public String[][] getListingData() {
		String[][] s = new String[1][6];
		
		// Read data from excel sheet
		ExcelUtil excelReader = new ExcelUtil("datasheets/InvalidData.xlsx");
		XSSFSheet sheet1 = excelReader.workbook.getSheetAt(0);
		XSSFRow row = sheet1.getRow(0);
		
		for(int i=0; i<6; i++) {
			s[0][i] = String.valueOf(row.getCell(i));
		}
		return s;
	}
	
	
	
	@BeforeTest
	public void init() {
		driver = DriverSetup.getDriver();
		justDialHomePage = new JustDialHomePage(driver);
		justDialFreeListingPage = new JustDialFreeListingPage(driver);
	}
	
	
	
	@Test(priority = 11)
	public void OpenFreeListingPage() {
		ExtentReporterUtil.logReport("OpenFreeListingPage", "Open The Free Listing Page");
		justDialHomePage.clickOnFreeListing();
	}
	
	
	
	@Test(priority = 12, dataProvider = "freeListingData")
	public void SubmitFreeListingWithInvalidMobileNumber(String salutation, String fname, String lname, String service, String location, String mobile) {
		ExtentReporterUtil.logReport("SubmitFreeListingWithInvalidMobileNumber", "Submit should not be possible with invalid Mobile Number");

		System.out.println("\nFilling form with data");
		System.out.println("Company Name: " + service);
		System.out.println("Name: " + salutation + " " + fname + " " + lname);
		System.out.println("Location: " + location);
		System.out.println("Phone number: " + mobile);	
		
		// Input fields
		justDialFreeListingPage.setCompanyName(service);
		justDialFreeListingPage.setCity(location);
		justDialFreeListingPage.setSalutation(salutation);
		justDialFreeListingPage.setFirstName(fname);
		justDialFreeListingPage.setLastName(lname);
		justDialFreeListingPage.setPhoneNumber(mobile);
		
		// Submit
		justDialFreeListingPage.submitFreeListing();
		
		// Validate error message
		String errorMsg = justDialFreeListingPage.getError();
		System.out.println("\nError message: " + errorMsg);
		Assert.assertNotEquals(errorMsg, "", "Error is blank");
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
