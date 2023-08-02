package com.hackathon.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.hackathon.functions.ScreenShot;
import com.hackathon.functions.UserFunctions;
import com.hackathon.model.Service;
import com.hackathon.reporter.ExtentReporterUtil;

public class JustDialSearchResultPage {
	private JavascriptExecutor jse;
	
	
	// Sort by top rated button
	@FindBy(xpath="//*[contains(text(),'Top Rated')]")
	WebElement topRatedSortingButton;
	
	// Search Result Title Text Elements
	@FindBy(xpath="//*[contains(@class, 'results_thumb_icon')]/parent::*")
	List<WebElement> serviceNames;
	
	// Search Result Phone Number Elements
	@FindBy(xpath="//*[contains(@class, 'callcontent')]")
	List<WebElement> phoneNumberSpans;
	
	// Search Result Hidden Phone Number Elements
	@FindBy(xpath="//*[@class='button_flare']")
	List<WebElement> hiddenPhoneNumberSpans;
	
	@FindBy(css=".linksarea a")
	List<WebElement> submenuLinks;
	
	/* 
	 * Methods for the JustDialSearchResultPage 
	 */
	
	public JustDialSearchResultPage(WebDriver driver) {
		this.jse = (JavascriptExecutor) driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}
	
	
	public void sortSearchResultByRating() {
		topRatedSortingButton.click();
	}
	
	
	public List<Service> getTopFiveServiceDetails() {
		List<Service> list = new ArrayList<>(5);
		int hiddenPhoneIndex = 0;
		
		jse.executeScript("window.scrollBy(0,150)");
		for (int i = 0; i < 5; i++) {			
			String name = serviceNames.get(i).getText();
			String phoneNumber = getPhoneNumber(i, hiddenPhoneIndex++);
						
			// Add the service to the list
			list.add(new Service(name, phoneNumber));
			
			// Take screenshot
			ExtentReporterUtil.attachScreenShot(ScreenShot.takeSnapShot("/services/"+(i+1)+".png"), "Service Result " + (i+1));
			
			// Scroll down the page to enable Lazy Loading
			jse.executeScript("window.scrollBy(0,325)");
			UserFunctions.sleep(500);
		}		
		return list;
	}
	
	
	
	public String getPhoneNumber(int index, int hiddenIndex) {
		WebElement phoneNumberSpan = phoneNumberSpans.get(index);
		String phoneNumber = null;
		// Check if phone number is displayed ( Marked with class callNowAnchor )
		if (phoneNumberSpan.getAttribute("class").contains("callNowAnchor")) { 
			phoneNumber = phoneNumberSpan.getText();
		} 
		// Else click on show numbers to display and then store phone number
		else {
			hiddenPhoneNumberSpans.get(hiddenIndex).click();
			UserFunctions.sleep(2000);
			phoneNumber = phoneNumberSpan.getText();
		}
		return phoneNumber;
	}
	
	
	
	public List<String> getAllTheSubmenuItemsRelatedToGym() { 
		jse.executeScript("window.scrollBy(0, 550)");
		if (submenuLinks.size() == 0) 
			throw new RuntimeException("No Submenu Items Loaded");
		return
		submenuLinks.stream().filter(e->e.getText().contains("Gyms")).map(e->e.getText()).collect(Collectors.toList());
	}

}
