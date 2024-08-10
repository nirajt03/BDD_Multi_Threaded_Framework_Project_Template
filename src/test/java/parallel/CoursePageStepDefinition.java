package parallel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import driverfactory.DriverFactory;
import helperTestUtility.ReportLogs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjectModels.CoursePage;
import pageObjectModels.SearchPage;

public class CoursePageStepDefinition {
	
	public static final Logger logger = LogManager.getLogger(CoursePageStepDefinition.class);
	SearchPage searchPage = new SearchPage(DriverFactory.getInstance().getDriver());
	CoursePage coursePage = new CoursePage(DriverFactory.getInstance().getDriver());	
	
	@Given("Verify selected Course header text as {string}")
	public void verify_selected_course_header_text_as(String expCourseHeaderText) {
		String courseHeaderText = coursePage.getCoursePageHeader();
		ReportLogs.addLogForStringComparison(courseHeaderText, expCourseHeaderText,"Course Header Text");
		Assert.assertEquals(courseHeaderText, expCourseHeaderText);
	}
	
	@When("Verify selected Course description test as {string}")
	public void verify_selected_course_description_test_as(String expCourseDescriptionText) {
		String courseDescriptionText = coursePage.getCourseDescription();
		ReportLogs.addLogForStringComparison(courseDescriptionText, expCourseDescriptionText,"Course Description Text");
		Assert.assertEquals(courseDescriptionText, expCourseDescriptionText);
		ReportLogs.addLogWithScreenshot(Status.INFO,"Course Page verification");
	}	
	
	@Then("Validate other course details as {string}, {string} and {string}")
	public void validate_other_course_details_as_and(String expAuthorName, String expFreeTrailButtonText, String expCourseOverviewText) {
		boolean isAuthorLinkVisible = coursePage.validateAuthorLinkVisible();
		Assert.assertTrue(isAuthorLinkVisible, "Author Link is not visible");
		
		String authorName = coursePage.getCourseAuthorName();
		ReportLogs.addLogForStringComparison(authorName, expAuthorName,"Author Name Text");
		Assert.assertEquals(authorName, expAuthorName, "Failed to assert Course Author Name");
		
		String freeTrailButtonText = coursePage.getFreeTrailButtonText();
		ReportLogs.addLogForStringComparison(freeTrailButtonText, expFreeTrailButtonText,"Free Trail Button Text");
		Assert.assertEquals(freeTrailButtonText, expFreeTrailButtonText, "Failed to assert Trail Button text");
		
		String courseOverviewText = coursePage.getCourseOverviewButtonText();
		ReportLogs.addLogForStringComparison(courseOverviewText, expCourseOverviewText,"Course Header Text");
		Assert.assertEquals(courseOverviewText, expCourseOverviewText, "Failed to assert Course Overview text");
		ReportLogs.addLogWithScreenshot(Status.INFO,"Course Overview verification");
	}

}
