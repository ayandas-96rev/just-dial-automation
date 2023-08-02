package com.hackathon.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

import com.hackathon.functions.UserFunctions;

public class JustDialHomePage {
	
	private WebDriver driver;
	private JavascriptExecutor jse;
	/*
	 * Locate elements in home page of JustDial
	 * Use @FindBy annotation
	 */
	
	// Login pop-up close button
	@FindBy(css=".jd_modal_close.jdicon")
	WebElement popupCloseButton;
	
	// Location drop-down
	@FindBy(css=".input_location_box>input")
	WebElement locationDropdown;
	
	// First location suggestion
	@FindBy(id="react-autowhatever-city-auto-suggest--item-1")
	WebElement firstLocationSuggestion;
	
	// Search input element
	@FindBy(id="main-auto")
	WebElement searchBar;
	
	// Gym Link
	@FindBy(xpath="//div[.=\"GYM\"]/parent::a")
	WebElement gymLink;
	
	// Free Listing Link
	@FindBy(linkText="Free Listing")
	WebElement freeListingLink;
	
	/* 
	 * Methods for the JustDialHomePage 
	 */
	
	public JustDialHomePage(WebDriver driver) {
		this.driver = driver;
		this.jse = (JavascriptExecutor) driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}
	
	
	
	public void closeLoginModal() {
		popupCloseButton.click();
	}
	
	
	
	public void selectLocationAs(String location) {
		// Click on the location input field and Input location
		locationDropdown.click();
		locationDropdown.sendKeys(location);
		
		UserFunctions.sleep(3000);

		// Click on the first auto suggest element
		firstLocationSuggestion.click();

		System.out.println(locationDropdown.getText());
		Assert.assertTrue(locationDropdown.getAttribute("value").toLowerCase().contains(location.toLowerCase()),
				"Expected [" + location + "] but got [" + locationDropdown.getAttribute("value") + "]" );
	}
	
	
	
	public void searchFor(String service) {
		// Input on the search box and search
		searchBar.sendKeys(service);
		searchBar.sendKeys(Keys.ENTER);

		UserFunctions.sleep(2000);
		while (!jse.executeScript("return document.readyState").equals("complete")) {
			UserFunctions.sleep(300);
		}

		Assert.assertTrue(driver.getTitle().contains(service), 
				"Expected [" + service + "] but got [" + driver.getTitle() + "]" );
	}
	
	
	
	public void clickOnGymLink() {
		driver.get(gymLink.getAttribute("href"));
		Assert.assertTrue(driver.getTitle().contains("Gyms"));
	}
	
	
	public void clickOnFreeListing() {
		freeListingLink.click();
		
		while (!jse.executeScript("return document.readyState").equals("complete")) {
			UserFunctions.sleep(2000);
		}
		System.out.println("\nPage loaded: " + driver.getTitle());
		System.out.println("Page Url: " + driver.getCurrentUrl());
	}
	
}
