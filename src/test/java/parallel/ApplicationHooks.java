package parallel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.*;

import driverfactory.DriverFactory;
import helperUtility.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApplicationHooks {
	
	public static final Logger logger = LogManager.getLogger(ApplicationHooks.class); 
	
	private DriverFactory driverFactory;
	public static WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	static LocalDateTime currentDateTime = LocalDateTime.now();  

	static DateTimeFormatter dateMonthYearFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");  
	public static String dateMonthYear = currentDateTime.format(dateMonthYearFormat).toString(); 

	static DateTimeFormatter twentyFourHourFormat = DateTimeFormatter.ofPattern("k-mm-ss a ");
	public static String twentyFourHour = currentDateTime.format(twentyFourHourFormat).toString();
	
	static DateTimeFormatter twelveHourFormat = DateTimeFormatter.ofPattern("hh-mm-ss a ");
	public static String twelveHour = currentDateTime.format(twelveHourFormat).toString();
	
	static DateTimeFormatter monthYearFormat = DateTimeFormatter.ofPattern("MMM YYYY");  
	public static String monthYear = currentDateTime.format(monthYearFormat).toString(); 
	
	static DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("YYYY");  
	public static String year = currentDateTime.format(yearFormat).toString(); 

	@Before(order=0)
	public void getProperty() {
		logger.info("In Before - Current Time : "+twelveHour);
		configReader = new ConfigReader();
		prop=configReader.init_prop();
	}
 
	@Before(order=1)
	public void launchBrowser(Scenario scenario) {
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver=driverFactory.init_driver(browserName);
	}

//	@Before(order=2)
//	public void getTestContext() {
//		logger.info("In Before step");
//	}
	
	@After(order=0)
	public void quitBrowser() {
		logger.info("In After");
		DriverFactory.closeDriver();
		//driver.quit();
	}

	@After(order=1)
	public void tearDown(Scenario scenario) {
		if(scenario.isFailed()) {
			try {
				String screenshotName = scenario.getName().replaceAll(" ", "_");
				ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL,screenshotName +" "+twelveHour,MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshotAsBase64(driver)).build());
				ExtentCucumberAdapter.addTestStepLog("Screenshot is attached");
				captureScreenshotAsFile(screenshotName,driver);
			} catch (Throwable e) {
				e.printStackTrace();
			}		
		}
	}
	
	/**
	 * Get Screenshot As Base64
	 * @param driver
	 * @return String
	 */
	public String getScreenshotAsBase64(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
		
	/**
	 * Capture Screenshot As File
	 * @param testMethodName
	 * @param driver
	 * @return String
	 */
	public String captureScreenshotAsFile( String testMethodName, WebDriver driver){
		File srcfile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String captureScreenshotPath = System.getProperty("user.dir") + "\\Screenshots\\"+year+"\\"+monthYear+"\\"+ dateMonthYear+"\\"+twentyFourHour+ testMethodName+" "+".png";
		try {
			FileUtils.copyFile(srcfile, new File(captureScreenshotPath));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return captureScreenshotPath;
	}
		
	/**
	 * Capture Screenshot As Base64
	 * @param testMethodName
	 * @param driver
	 * @return String
	 * @throws Throwable
	 * @throws Throwable
	 */
	public String captureScreenshotAsBase64(String testMethodName,  WebDriver driver){
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String captureScreenshotBase64Path = System.getProperty("user.dir") + "\\Screenshots\\"+year+"\\"+monthYear+"\\"+ dateMonthYear+"\\"+twentyFourHour+testMethodName+" "+".png";
		byte[] imageBytes = null;
		try {
			FileUtils.copyFile(source, new File(captureScreenshotBase64Path));
			imageBytes = IOUtils.toByteArray(new FileInputStream (captureScreenshotBase64Path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(imageBytes);
	}

}
