package pageObjectModels;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import webElementUtilities.WebElementUtlities;

public class SearchPage  extends BasePage{

	public static final Logger logger = LogManager.getLogger(SearchPage.class);
	
	public SearchPage(WebDriver rdriver) {
		super(rdriver);
	}

	//Filters option
	private By filtersTab = By.xpath("//div[@id='Filters']");	

	//Courses tab
	private By coursesTab = By.xpath("//div[@id='courses_tabs']");

	private By clearActiveAllTabs = By.xpath("//a[text()='All']//parent::li[contains(@class,'ui-tabs-active')]");
	private By clearAllTabs = By.xpath("//a[text()='All']//parent::li[not(contains(@class,'ui-tabs-active'))]");
	
	//private By activeCourseTab = By.xpath("//a[text()='Courses']//parent::li[contains(@class,'ui-tabs-active')]");
	private By courseTabBlockVisible = By.xpath("//div[@id='tabs']//div[@id='tabs-2' and @style='display: block;']");
	//private By courseTabBlockInvisible = By.xpath("//div[@id='tabs']//div[@id='tabs-2' and @style='display: none;']");


	/**
	 * Search Page Filter Tags
	 * @author Niraj.Tiwari
	 */
	public enum SearchPageFilterTags{
		SkillLevels("Skill Levels"),Authors("Authors"),Roles("Roles"),Certifications("Certifications");

		private String searchPageFilterTags; 

		SearchPageFilterTags(String searchPageFilterTags) {
			this.searchPageFilterTags = searchPageFilterTags;
		}

		public String getSearchPageFilterTagName() {
			return searchPageFilterTags;
		}
	}

	/**
	 * Search Page NavBar List Tabs
	 * @author Niraj.Tiwari
	 */
	public enum SearchPageNavBarListTabs{
		All("All"),Courses("Courses"),Blog("Blog"),Resources("Resources"),Authors("Authors");

		private String searchPageNavBarListTab; 

		SearchPageNavBarListTabs(String searchPageNavBarListTab) {
			this.searchPageNavBarListTab = searchPageNavBarListTab;
		}

		public String getSearchPageNavBarListTabName() {
			return searchPageNavBarListTab;
		}
	}

	//---------------------------Filter tabs methods------------------------------------

	/**
	 * select Required Filter Tab
	 * @param filterTag
	 */
	public void selectRequiredFilterTab(SearchPageFilterTags filterTag) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, filtersTab, 10);
		String filterTagValue = filterTag.getSearchPageFilterTagName();
		By filterTagDiv = By.xpath("//div[text()='"+filterTagValue+"']");
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, filterTagDiv, 10);
		WebElementUtlities.click(driver, driver.findElement(filterTagDiv));
	}

	/**
	 * check Selected Filter Header Active
	 * @param filterTagName
	 * @return
	 */
	public boolean checkSelectedFilterHeaderActive(String filterTagName) {
		By activeFilterHeader = By.xpath("//div[.='"+filterTagName+"']//parent::h3[contains(@class,'accordion-header-active')]");
		if(!WebElementUtlities.isElementVisible(driver, activeFilterHeader)) {
			throw new ElementNotInteractableException("Element not found");
		}
		return true;
	}

	/**
	 * get List Of Selected Filter Options
	 * @param filterTag
	 * @return
	 */
	public List<String> getListOfSelectedFilterOptions(SearchPageFilterTags filterTag) {
		customWaitInSec(2);
		new ArrayList<String>();
		//selectRequiredFilterTab(filterTag);
		String filterTagValue = filterTag.getSearchPageFilterTagName();
		checkSelectedFilterHeaderActive(filterTagValue);		
		By filterTagDiv = By.xpath("//div[text()='"+filterTagValue+"']//ancestor::h3[contains(@class,'ui-accordion-header-active')]//following-sibling::div[contains(@class,'ui-accordion-content-active')]//span");
		List<WebElement> listOfSelectedFilterBy = driver.findElements(filterTagDiv);
		return WebElementUtlities.getElementsText(driver, listOfSelectedFilterBy);
	}

	/**
	 * close Selected Filter Active Div
	 * @param filterTagName
	 * @return
	 */
	public boolean closeSelectedFilterActiveDiv(String filterTagName) {
		checkSelectedFilterHeaderActive(filterTagName);
		By activeFilterHeader = By.xpath("//div[.='"+filterTagName+"']//parent::h3[contains(@class,'accordion-header-active')]");
		WebElementUtlities.click(driver, driver.findElement(activeFilterHeader));
		By collapsedFilterHeader = By.xpath("//div[.='"+filterTagName+"']//parent::h3[contains(@class,'accordion-header-collapsed')]");
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, collapsedFilterHeader, 10);
		if(!WebElementUtlities.isElementVisible(driver, collapsedFilterHeader)) {
			throw new ElementNotInteractableException("Element not found");
		}
		return true;
	}

	//---------------------------Nav Bar tabs methods------------------------------------

	/**
	 * select Required Tab In Nav Bar
	 * @param requiredTab
	 */
	public void selectRequiredTabInNavBar(SearchPageNavBarListTabs requiredTab) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, coursesTab, 10);
		String navBartabValue = requiredTab.getSearchPageNavBarListTabName();
		By navBarTabDiv = By.xpath("//div[@id='courses_tabs']//a[text()='"+navBartabValue+"']");
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, navBarTabDiv, 10);
		WebElementUtlities.click(driver, driver.findElement(navBarTabDiv));
	}

	/**
	 * click Course Tab Details
	 */
	public void clickCourseTabDetails() {
		selectRequiredTabInNavBar(SearchPageNavBarListTabs.Courses);
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, courseTabBlockVisible, 10);
	}

	/**
	 * get Courses List Details Count
	 * @param xpath
	 * @return
	 */
	public int getCoursesListDetailsCount(By xpath) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, xpath, 10);
		List<WebElement> listOfXpathWE = driver.findElements(xpath);
		return WebElementUtlities.getElementsCount(driver, listOfXpathWE);
	}

	/**
	 * get Courses List Details
	 * @return
	 */
	public List<String> getCoursesListDetails() {
		List<String> listOfCourseDetails = new ArrayList<String>();
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, courseTabBlockVisible, 10);
		By coursesList = By.xpath("(//div[contains(@class,'search-results-rows')]//div[contains(@class,'columns')])");
		int totalCourses = getCoursesListDetailsCount(coursesList);
		for (int i = 1; i < totalCourses; i++) {
			By courseTitleBy = By.xpath("(//div[contains(@class,'columns')]//div[contains(@class,'title')])["+i+"]");
			WebElementUtlities.moveToElement(driver, driver.findElement(courseTitleBy));
			String courseTitle = WebElementUtlities.getText(driver, driver.findElement(courseTitleBy));
		    listOfCourseDetails.add(courseTitle);			
		}
		return listOfCourseDetails;
	}

	/**
	 * validate Other Tabs Details
	 * @param navBarTabs
	 * @return
	 */
	public List<String> validateOtherTabsDetails(List<SearchPageNavBarListTabs> navBarTabs) {
		List<String> listOfTabDetails = new ArrayList<String>();
		String detailsText = null;
		for (SearchPageNavBarListTabs tab : navBarTabs) {
			clearAllTabs();
			selectRequiredTabInNavBar(tab);
			By tabDetailstext = By.xpath("//div[contains(@id,'tabs') and @style='display: block;']//p");
			detailsText = WebElementUtlities.getText(driver, driver.findElement(tabDetailstext));
			listOfTabDetails.add(detailsText);
		}	
		return listOfTabDetails;
	}

	/**
	 * clear All Tabs
	 */
	public void clearAllTabs() {		
		if(WebElementUtlities.isElementVisible(driver, clearActiveAllTabs)) {
			WebElementUtlities.explicitWaitForElementToBeVisible(driver, clearActiveAllTabs, 10);
			WebElementUtlities.moveToElement(driver, driver.findElement(clearActiveAllTabs));
			WebElementUtlities.click(driver, driver.findElement(clearActiveAllTabs));
		}else {
			WebElementUtlities.explicitWaitForElementToBeVisible(driver, clearAllTabs, 10);
			WebElementUtlities.moveToElement(driver, driver.findElement(clearAllTabs));
			WebElementUtlities.click(driver, driver.findElement(clearAllTabs));
		}
	}
	
	/**
	 * click On Required Course
	 * @param courseName
	 */
	public void clickOnRequiredCourse(String courseName) {
		By courseTitleBy = By.xpath("(//div[contains(@class,'columns')]//div[contains(@class,'title')]//a[text()='"+courseName+"'])");
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, courseTitleBy, 10);
		WebElementUtlities.click(driver, driver.findElement(courseTitleBy));
	}
	
	/**
	 * move To Course Page
	 * @param courseName
	 * @return
	 */
	public CoursePage moveToCoursePage(String courseName) {
		clickOnRequiredCourse(courseName);
		return new CoursePage(driver);
	}
	
	
}
