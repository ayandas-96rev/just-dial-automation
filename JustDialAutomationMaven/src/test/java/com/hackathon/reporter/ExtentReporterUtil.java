package com.hackathon.reporter;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.hackathon.functions.UserFunctions;

public class ExtentReporterUtil {

	// Extent Report
	static ExtentHtmlReporter report;
	static ExtentReports extent;
	static ExtentTest test;

	public static void initReport() {
		// Setup Extent Reporter
		report = new ExtentHtmlReporter(UserFunctions.getReportBasePath() + "/EXTENT_REPORT.html");
		extent = new ExtentReports();
		extent.attachReporter(report);
	}
	
	public static void logReport(String reportName, String reportDesc) {
		test = extent.createTest(reportName, reportDesc);
	}
	
	public static void logReport(Status status, String reportName, String reportDesc) {
		test = extent.createTest(reportName, reportDesc);
		test.log(status, "Test Case failed");
	}
	
	public static void logStatus(Status status, String desc) {
		test.log(status, desc);
	}
	
	public static void logStatus(Status status, Throwable desc) {
		test.log(status, desc);
	}
	
	public static void attachScreenShot(String pathName, String title) {
		try {
			test.addScreenCaptureFromPath(pathName, title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void endReport() {
		extent.flush();
	}
	
}
