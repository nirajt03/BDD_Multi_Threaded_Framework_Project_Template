package parallel;

import static parallel.ApplicationHooks.dateMonthYear;
import static parallel.ApplicationHooks.driver;
import static parallel.ApplicationHooks.monthYear;
import static parallel.ApplicationHooks.year;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.service.ExtentService;

import excelUtilities.ExcelUtility;
import exceptions.FileDoesNotExistsException;
import exceptions.InCorrectConfigConfigParameters;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import screenRecorderUtilities.ScreenRecorderUtility;
import screenRecorderUtilities.ScreenRecorderUtility.TypeOfScreen;

@CucumberOptions(
		plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failedrerun.txt"
				},
		glue={"parallel"},
		monochrome = true,			
		features={"@target/failedrerun.txt"}
		)

public class FailedReRunner extends AbstractTestNGCucumberTests{

	public static final Logger logger = LogManager.getLogger(FailedReRunner.class);
	
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios(){
		return super.scenarios();
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		try {
			int thresholdDays = 10;
			String testClassName = getClassName();
			ScreenRecorderUtility.startRecord(TypeOfScreen.RegularScreen,testClassName);
			ScreenRecorderUtility.deleteOlderFilesAndDirectories(thresholdDays, TimeUnit.DAYS,".avi");	
			logger.info("Screen Recording Started ..!!");

			String path = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\BDDMultiThreadedFrameworkTestDriver.xlsx";
			try {
				validateInputFile(path);
			} catch (FileDoesNotExistsException e) {
				e.printStackTrace();
			}
			System.setProperty("driverFilePath", path);
			try {
				System.setProperty("url",geturl());
			} catch (InCorrectConfigConfigParameters e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}

	@BeforeClass
	public void beforeClass() {
		ExtentService.getInstance().setSystemInfo("Application", "Pluralsight");
		ExtentService.getInstance().setSystemInfo("User Name", System.getProperty("user.name"));
		ExtentService.getInstance().setSystemInfo("Environment", "QA");
		ExtentService.getInstance().setSystemInfo("URL", System.getProperty("url"));
		ExtentService.getInstance().setSystemInfo("OS", System.getProperty("os.name"));
		ExtentService.getInstance().setSystemInfo("OS Version", System.getProperty("os.version"));
		ExtentService.getInstance().setSystemInfo("OS Arch", System.getProperty("os.arch"));
	}

//	@BeforeMethod
//	public void beforeMethod() {
//		logger.info("In Before Method");
//	}
//
//	@AfterMethod
//	public void afterMethod() {
//		logger.info("In After Method");
//	}

	@AfterClass
	public void afterClass() {
		logger.info("In After Class");
		ExtentService.getInstance().setSystemInfo("Browser", getBrowser());
		ExtentService.getInstance().setSystemInfo("Browser Version", getBrowserVersion());
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		try {
			ScreenRecorderUtility.stopRecord();
			logger.info("Screen Recording Stopped ..!!");

			//Gets the latest execution report
			File latestHTMLReport = getPathForLatestReport();
			String latestHTMLReportPath = latestHTMLReport.getPath();

			//Preload report on default browser of the system
			Desktop.getDesktop().browse(new File(latestHTMLReportPath).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get Path For Latest Report
	 * @return  File 
	 */
	public File getPathForLatestReport() {
		String reportPath = System.getProperty("user.dir")+"\\Reports\\ "+year+"\\"+monthYear+"\\"+dateMonthYear+"\\";
		File latestDirectory = getLatestDir(reportPath);
		String latestDirStr = latestDirectory.getPath();
		File latestReport = getTheNewestFile(latestDirStr+"\\","html");		
		return latestReport;
	}

	/**
	 * Get The Newest File : returns latest html report
	 * @param filePath
	 * @param ext
	 * @return  File
	 */
	public File getTheNewestFile(String filePath, String ext) {
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);
		if (files.length > 0) {
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}else {
			throw new NullPointerException("No Files found in the given path");
		}
		return theNewestFile;
	}

	/**
	 * Get Latest Directory
	 * @param dirPath
	 * @return File
	 */
	public File getLatestDir(String dirPath) {		
		File latestDirectory = new File(dirPath);
		File[] listOfFiles = latestDirectory.listFiles();
		File lastModified = Arrays.stream(listOfFiles).filter(File::isDirectory).max(Comparator.comparing(File::lastModified)).orElse(null);
		return lastModified;
	}

	/**
	 * Get Class Name
	 * @return
	 */
	public String getClassName() {
		String packageName = this.getClass().getPackage().getName().trim();
		return packageName;
	}

	/**
	 * Returns Browser Name of the Test Environment
	 * @return Browser Name
	 */
	public String getBrowser() {
		Capabilities browserCap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = browserCap.getBrowserName();
		return browserName;
	}

	/**
	 * Returns Browser version of the Test Environment
	 * @return Browser Version
	 */
	public String getBrowserVersion() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String version = cap.getBrowserVersion().toString();
		return version;
	}

	/**
	 * Validate Input File
	 * @param path
	 * @throws FileDoesNotExistsException
	 */
	public static void validateInputFile(String path) throws FileDoesNotExistsException {
		File f = new File(path);
		if(!f.exists()) {
			throw new FileDoesNotExistsException("File dmdaTestDriver.xlsx not found under path: "+f.getParentFile());
		}else {
			if (f.length()==0) {
				throw new FileDoesNotExistsException("File dmdaTestDriver.xlsx found under path: "+f.getParentFile() + " is empty");
			}
		}
	}

	/**
	 * Get URL
	 * @return
	 * @throws InCorrectConfigConfigParameters
	 */
	public static String geturl() throws InCorrectConfigConfigParameters  {
		ExcelUtility xlsUtil= new ExcelUtility(System.getProperty("driverFilePath"));
		Sheet sheetObj = xlsUtil.getSheetObject("Config");
		ArrayList<ArrayList<String>> urlList = xlsUtil.getMultipleColumnDataBasedOnOneColumnValue(sheetObj,"Attribute","Env-URL","Value");
		if(urlList.size() == 0) {
			throw new InCorrectConfigConfigParameters("No url value found for url-uat");
		}
		if (urlList.size() > 1){
			throw new InCorrectConfigConfigParameters("Multiple values found for url-uat");
		}
		return urlList.get(0).get(0);
	}

}
