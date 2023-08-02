package com.hackathon.driver;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {
	
	private static WebDriver driver;
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static void setDriver(WebDriver driver) {
		DriverSetup.driver = driver;
	}

	/* to get the Chrome WebDriver */
	public static WebDriver getChromeDriver() {
		
		// Set driver executable in system property
		WebDriverManager.chromedriver().setup();
		
		// Create ChromeOptions object
        ChromeOptions options = new ChromeOptions();

        // Disable location permission
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-geolocation");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		return driver;
	}

	/* to get the Edge WebDriver */
	public static WebDriver getEdgeDriver() {
		
		// Set driver executable in system property
		WebDriverManager.edgedriver().setup();
		
		// Create ChromeOptions object
        EdgeOptions options = new EdgeOptions();

        // Disable location permission
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-geolocation");
		
		driver = new EdgeDriver(options);
		driver.manage().window().maximize();
		return driver;
	}
	
	public static WebDriver getUserSpecifiedDriver() {
		System.out.println("Enter 1 to select Chrome");
		System.out.println("Enter 2 to select Edge");
		System.out.println("Select Browser: ");
		Scanner sc = new Scanner(System.in);

		switch (sc.nextInt()) {
		case 1:
			driver = DriverSetup.getChromeDriver();
			System.out.println("Chrome Driver started successfully.");
			break;
		case 2:
			driver = DriverSetup.getEdgeDriver();
			System.out.println("Edge Driver started successfully.");
			break;
		}
		sc.close();
		return driver;
	}
	
}

