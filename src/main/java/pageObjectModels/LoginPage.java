package pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import excelUtilities.ExcelUtilities;
import exceptions.NoRowFoundException;
import exceptions.ObjectLengthNotCorrectException;
import helperUtility.EncryptDecrypt;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;

import webElementUtilities.WebElementUtlities;

/**
 * Login Page
 * 
 * @author Niraj.Tiwari
 */
public class LoginPage extends BasePage{

	public static final Logger logger = LogManager.getLogger(LoginPage.class);

	public LoginPage(WebDriver rdriver) {
		super(rdriver);
	}

	private By loginForm = By.xpath("//div[@id='login_left' and @class='inputForm']");
	private By usernameBy = By.xpath("//input[@id='Username']");
	private By passwordBy = By.xpath("//input[@id='Password']");
	private By loginBtnBy = By.xpath("//span[@id='login_on_login_page']");
	private By headerRibbonTextVisible = By.xpath("//div[@id='sign_in_fail' and @style='display: block;']//p");
	//private By headerRibbonTextInvisible = By.xpath("//div[@id='sign_in_fail' and @style='display: none;']//p");

	/**
	 * Get User Credential
	 * @param userType
	 * @return
	 */
	public HashMap<String, String> getUserCredential(String loginType) {

		String path = System.getProperty("driverFilePath");
		ExcelUtilities excelUtil = new ExcelUtilities(path);
		HashMap<String, String> map = new HashMap<String, String>();

		// Get Sheet Object
		Sheet sheetObject = excelUtil.getSheetObject("LoginTestData");

		String[][] searchData={{"LoginType",loginType}};
		ArrayList<HashMap<String, String>> rowData = null;
		try {
			rowData = ExcelUtilities.getAllRowsData(sheetObject,searchData);
		} catch (NoRowFoundException | ObjectLengthNotCorrectException e) {
			e.printStackTrace();
		}

		// Get the Username & Password and map encrypted details  		
		map.put("Username", EncryptDecrypt.encryptString(rowData.get(0).get("Username")));
		map.put("Password", EncryptDecrypt.encryptString(rowData.get(0).get("Password")));
		return map;
	}

	/**
	 * Login To Pluralsight application
	 * @param <T>
	 * @param loginPage
	 * @param userType
	 * @return
	 */
	public SearchPage loginToPluralsightApplication(String loginType) {
		// Get User Credential
		HashMap<String, String> usercredential = getUserCredential(loginType);

		//Login pluralsight application
		SearchPage searchpage = pluralsightApplicationLogin( usercredential.get("Username"), usercredential.get("Password"));
		logger.info("Successfully Login To Pluralsight Application");
		return searchpage;
	}

	/**
	 * Wait For Login Form To Be Visible
	 */
	public void waitForLoginFormToBeVisible() {
		// Wait till the login page is visible
		WebElementUtlities.explicitWaitForElementToBeVisible(driver,loginForm);
	}

	/**
	 * Pluralsight Application Login
	 * @param userName
	 * @param password
	 * @return
	 */
	public SearchPage pluralsightApplicationLogin(String username, String password) { //<T extends BasePage>T

		//Decrpyt & Enter Username 
		enterUsername(EncryptDecrypt.decryptString(username));

		//Decrpyt & Enter Password
		enterPassword(EncryptDecrypt.decryptString(password));

		// Click on Login
		clickLogin();

		if(!waitForAlertPopup(driver)) {
			throw new NoAlertPresentException("No Success login alert found");			
		}
		if(!getAlertText(driver).equalsIgnoreCase("Successfully logged in Puralsight application")) {
			throw new NoAlertPresentException("Alert text not equal");
		}
		handleAlertOnFinish(driver);
		return new SearchPage(driver);
	}

	/**
	 * Check Negative Login Scenarios
	 * @param userType
	 * @param loginType
	 */
	public void checkNegativeLoginScenarios(String username, String password) {

		//Login pluralsight application
		pluralsightApplicationNegativeLoginScenarios(username, password);
	}

	/**
	 * Pluralsight Application Negative Login Scenarios
	 * @param username
	 * @param password
	 */
	public void pluralsightApplicationNegativeLoginScenarios(String username, String password) {

		refreshPage();

		// Wait till the login page is visible
		WebElementUtlities.explicitWaitForElementToBeVisible(driver,loginForm);

		customWaitInSec(1);

		// Enter User name
		enterUsername(username);

		// Enter Password
		enterPassword(password);

		// Click on Login
		clickLogin();
	}

	/**
	 * Get Login Error Text
	 * @return
	 */
	public String getLoginErrorText() {
		//Header ribbon text
		String ribbonText = getHeaderRibbonText();

		//wait till ribbon is invisible
		checkHeaderRibbonIsInvisible();

		return ribbonText;
	}

	/**
	 * check Header Ribbon Is Visible
	 * @return boolean
	 */
	public boolean checkHeaderRibbonIsVisible() {
		boolean flag = false;
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, headerRibbonTextVisible, 15);
		if(!WebElementUtlities.isElementVisible(driver, headerRibbonTextVisible)) {
			throw new ElementNotInteractableException("Header message is not visible");	
		}
		flag=true;		
		return flag;
	}

	/**
	 * get Header Ribbon Text
	 * @return String
	 */
	public String getHeaderRibbonText() {
		checkHeaderRibbonIsVisible();		
		return WebElementUtlities.getText(driver, driver.findElement(headerRibbonTextVisible));
	}

	/**
	 * Check Header Ribbon Is Invisible
	 * @return
	 */
	public boolean checkHeaderRibbonIsInvisible() {
		boolean flag = false;
		if(getHeaderRibbonText().equalsIgnoreCase("Invalid user name or password")) {
			flag=true;
			WebElementUtlities.explicitWaitForElementToBeInVisible(driver, headerRibbonTextVisible, 10);
		}
		return flag;
	}

	/**
	 * Set Username
	 * @param userName
	 */
	public void enterUsername(String userName) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, usernameBy, 10);
		WebElementUtlities.clearText(driver, driver.findElement(usernameBy));
		WebElementUtlities.setText(driver,driver.findElement(usernameBy),userName);
	}

	/**
	 * set Password
	 * @param password
	 */
	public void enterPassword(String pwd) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, passwordBy, 10);
		WebElementUtlities.clearText(driver, driver.findElement(passwordBy));
		WebElementUtlities.setText(driver, driver.findElement(passwordBy), pwd);
	}

	/**
	 * Click on Login
	 */
	public void clickLogin() {
		WebElementUtlities.click(driver, driver.findElement(loginBtnBy));
	}

}
