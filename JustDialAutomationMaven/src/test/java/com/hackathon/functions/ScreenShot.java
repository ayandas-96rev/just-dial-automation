package com.hackathon.functions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.hackathon.driver.DriverSetup;


public class ScreenShot {
	
	public static int FailedStatus = 0;
	public static int SuccessStatus = 1;
	
	public static String takeSnapShot(String filename) {
		WebDriver webdriver = DriverSetup.getDriver();

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        String destPathName = UserFunctions.getReportBasePath() + "/screenshots/" + filename;
        File DestFile=new File(destPathName);

        //Copy file at destination
        try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return destPathName;
    }
	
	public static String takeTestCaseScreenShot(ITestResult result, int status) {
		String pathName = null;
		if(status == 0) {
			String testMethodName = result.getMethod().getMethodName();
			String testErrorName = result.getThrowable().getClass().getName();
			pathName = "errors/" + testMethodName + "_Throws_" + testErrorName + ".png";
		} else {
			String testMethodName = result.getMethod().getMethodName();
			pathName = testMethodName + "_SUCCESS.png";
		}
		//take screenshot
		return ScreenShot.takeSnapShot(pathName);
	}
}
