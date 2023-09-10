package pageObjectModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElementUtilities.WebElementUtlities;

public class HomePage extends BasePage{

	public static final Logger logger = LogManager.getLogger(HomePage.class);
	
	public HomePage(WebDriver rdriver) {
		super(rdriver);
	}

	private By homePageHeader = By.xpath("//div[@id='content']//h1");
	private By homePageDescription = By.xpath("//div[@id='content']//p");
	
	/**
	 * Wait For Home Page To Be Visible
	 */
	public void waitForHomePageToBeVisible() {
		customWaitInSec(1);
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, homePageHeader, 15);
	}
	
	/**
	 * get Home Page Header
	 * @return
	 */
	public String getHomePageHeader() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, homePageHeader, 15);
		return WebElementUtlities.getText(driver, driver.findElement(homePageHeader));
	}
	
	/**
	 * get Home Page Description
	 * @return
	 */
	public String getHomePageDescription() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, homePageDescription, 15);
		return WebElementUtlities.getText(driver, driver.findElement(homePageDescription));
	}
	
}
