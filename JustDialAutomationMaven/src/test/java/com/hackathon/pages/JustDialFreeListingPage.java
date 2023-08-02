package com.hackathon.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.hackathon.functions.UserFunctions;

public class JustDialFreeListingPage {
	private WebDriver driver;
	private JavascriptExecutor jse;
	
	/*
	 * Locate elements in free listing page of JustDial
	 * Use @FindBy annotation
	 */
	
	// Company input element
	@FindBy(id="fcom")
	WebElement companyInput;
	
	// City input element
	@FindBy(id="flcity")
	WebElement cityInput;
	
	// Salutation input element
	@FindBy(css=".fsec>.drop>span.slct")
	WebElement salDropDown;
	
	// Salutation options
	@FindBy(css="#salopt>li")
	List<WebElement> salOptions;
	
	// First Name input
	@FindBy(name="fname")
	WebElement firstNameInput;
	
	// Last Name input
	@FindBy(name="lname")
	WebElement lastNameInput;
	
	// Mobile Number input
	@FindBy(id="fmb0")
	WebElement mobileNumberInput;
	
	// error field
	@FindBy(id="fmb0Err")
	WebElement error;
	
	// Submit button
	@FindBy(css="button.bbtn.subbtn")
	WebElement submitButton;
	
	/* 
	 * Methods for the JustDialHomePage 
	 */
	
	public JustDialFreeListingPage(WebDriver driver) {
		this.driver = driver;
		this.jse = (JavascriptExecutor) driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}
	
	
	public void setCompanyName(String name) {
		driver.findElement(By.id("fcom")).sendKeys(name);
	}
	
	
	public void setCity(String city) {
		// Input Location
		driver.findElement(By.id("flcity")).clear();
		driver.findElement(By.id("flcity")).sendKeys(city);
	}
	
	
	public void setSalutation(String sal) {
		// Select Salutation from drop-down
		salDropDown.click();
		jse.executeScript("document.querySelector('#salopt').style.display='block'");
		if(sal.equals("Mr"))
			salOptions.get(0).click();
		else if(sal.equals("Mrs"))
			salOptions.get(1).click();
		else
			salOptions.get(2).click();
	}
	
	
	public void setFirstName(String fname) {
		// Input Firstname
		firstNameInput.sendKeys(fname);
	}
	
	
	public void setLastName(String lname) {
		// Input Lastname
		lastNameInput.sendKeys(lname);
	}
	
	
	public void setPhoneNumber(String mobile) {
		// Input mobile number
		mobileNumberInput.sendKeys(mobile);
	}
	
	public void submitFreeListing() {
		// Submit
		driver.manage().deleteAllCookies();
		submitButton.click();
	}
	
	public String getError() {
		UserFunctions.sleep(300);
		return error.getText();
	}
}
