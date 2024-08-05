package helperTestUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import driverfactory.DriverFactory;


public class ReportLogs {

	public static final Logger logger = LogManager.getLogger(ReportLogs.class);

    /**
     * Add Log.
     * @param status The log status.
     * @param message The log message.
     */
    public static void addLog(Status status, String message) {
        ExtentCucumberAdapter.getCurrentStep().log(status, "Log message: "+message);
        logger.info("Log Message: " + status + " - " + message);
    }

    /**
     * Add Log With MarkUp (Colored Background).
     * @param status The log status.
     * @param message The log message.
     */
    public static void addLogWithMarkUp(Status status, String message) {
        ExtentColor color = getColorForStatus(status);
        ExtentCucumberAdapter.getCurrentStep().log(status, MarkupHelper.createLabel("Log message: "+message, color));
        logger.info("Log Message: " + status + " - " + message);
    }

    /**
     * Add Log With Error.
     * @param status The log status.
     * @param throwable The throwable error.
     */
    public static void addLogWithError(Status status, Throwable throwable) {
        ExtentCucumberAdapter.getCurrentStep().log(status, throwable);
        logger.info("Log Message: " + status + " : ", throwable);
    }

    /**
     * Add Log With Screenshot.
     * @param status The log status.
     * @param message The log message.
     */
    public static void addLogWithScreenshot(Status status, String message) {
        try {
            String screenshot = getBase64Image();
            ExtentCucumberAdapter.getCurrentStep().log(status,"Log message: "+ message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
            ExtentCucumberAdapter.addTestStepLog("Screenshot is attached");
        } catch (WebDriverException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }

    /**
     * Add Log With Error And Screenshot.
     * @param status The log status.
     * @param throwable The throwable error.
     */
    public static void addLogWithErrorAndScreenshot(Status status, Throwable throwable) {
        try {
            String screenshot = getBase64Image();
            ExtentCucumberAdapter.getCurrentStep().log(status, throwable, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
            ExtentCucumberAdapter.addTestStepLog("Screenshot is attached");
        } catch (WebDriverException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }

    /**
     * Add Log For String Comparison.
     * @param actual The actual string value.
     * @param expected The expected string value.
     * @param message The log message.
     */
    public static void addLogForStringComparison(String actual, String expected, String message) {
        if (actual.contains(expected)) {
            addLogWithMarkUp(Status.PASS, message + ": actual - <b><i>" + actual + "</i></b> & expected - <b><i>" + expected + "</i></b>");
        } else {
            addLogWithMarkUp(Status.FAIL, message + ": actual - <b><i>" + actual + "</i></b> & expected - <b><i>" + expected + "</i></b>");
        }
    }

    /**
     * Get Base64 Image: Captures screenshot as a base64 string.
     * @return String
     * @throws WebDriverException
     */
    private static String getBase64Image() throws WebDriverException {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Get color based on Status.
     * @param status The log status.
     * @return ExtentColor
     */
    private static ExtentColor getColorForStatus(Status status) {
        switch (status) {
            case PASS: return ExtentColor.GREEN;
            case FAIL: return ExtentColor.RED;
            case INFO: return ExtentColor.BLUE;
            case WARNING: return ExtentColor.YELLOW;
            case SKIP: return ExtentColor.ORANGE;
            default: return ExtentColor.GREY;
        }
    }
	    
}
