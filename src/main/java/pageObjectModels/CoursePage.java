package pageObjectModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElementUtilities.WebElementUtlities;

public class CoursePage extends BasePage {

	public static final Logger logger = LogManager.getLogger(CoursePage.class);
	
	public CoursePage(WebDriver rdriver) {
		super(rdriver);
	}
	
	private By titleHeader = By.xpath("//div[contains(@class,'title section')]//h1");
	private By courseDescription = By.xpath("//div[@class='text-component']");
	private By authorLink = By.xpath("//span[@class='course-hero-rating']//following-sibling::a");
	private By freeTrailBtn = By.xpath("//a[@id='free_trial']");
	private By playCourseOverviewBtn = By.xpath("//div[@id='course_overview']//a");
	
	//methods 
	/**
	 * get Course PageHeader
	 * @return
	 */
	public String getCoursePageHeader() {
		customWaitInSec(1);
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, titleHeader, 15);
		return WebElementUtlities.getText(driver, driver.findElement(titleHeader));
	}
	
	/**
	 * get Course Description
	 * @return
	 */
	public String getCourseDescription() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, courseDescription, 15);
		return WebElementUtlities.getText(driver, driver.findElement(courseDescription));
	}
	
	/** 
	 * validate Author Link Visible
	 * @return
	 */
	public boolean validateAuthorLinkVisible() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, authorLink, 10);
		return WebElementUtlities.isElementVisible(driver, authorLink);
	}
	
	/**
	 * Get Course Author Name
	 * @return
	 */
	public String getCourseAuthorName() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, authorLink, 10);
		return WebElementUtlities.getText(driver, authorLink);
	}
	
	/**
	 * get Free Trail Button Text
	 * @return
	 */
	public String getFreeTrailButtonText() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, freeTrailBtn, 15);
		return WebElementUtlities.getText(driver, driver.findElement(freeTrailBtn));
	}
	
	/**
	 * get Course Overview Button Text
	 * @return
	 */
	public String getCourseOverviewButtonText() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, playCourseOverviewBtn, 15);
		return WebElementUtlities.getText(driver, driver.findElement(playCourseOverviewBtn));
	}
	
	/**
	 * move To Search page
	 * @return
	 */
	public SearchPage moveToSearchpage() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, coursesLink, 10);
		WebElementUtlities.click(driver, driver.findElement(coursesLink));
		customWaitInSec(2);
		return new SearchPage(driver);
	}
	
}
