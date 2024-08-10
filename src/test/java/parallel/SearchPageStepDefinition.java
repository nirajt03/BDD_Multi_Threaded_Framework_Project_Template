package parallel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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
import pageObjectModels.SearchPage.SearchPageFilterTags;
import pageObjectModels.SearchPage.SearchPageNavBarListTabs;

public class SearchPageStepDefinition {
	public static final Logger logger = LogManager.getLogger(SearchPageStepDefinition.class);
	SearchPage searchPage = new SearchPage(DriverFactory.getInstance().getDriver());
	CoursePage coursePage;

	@Given("Search required course in search box as {string}")
	public void search_required_course_in_search_box(String courseName) {
		searchPage.searchRequiredCourseInSearchBox(courseName);
		ReportLogs.addLog(Status.INFO,"Searched Java Course in search box");
	}
	
	@Given("Click on Course tab in Nav Bar List")
	public void click_on_course_tab_in_nav_bar_list() {
		searchPage.clearAllTabs();
		ReportLogs.addLog(Status.INFO,"Cleared All tabs");
		searchPage.clickCourseTabDetails();
		ReportLogs.addLogWithScreenshot(Status.INFO,"Verified Search Java Course");
	}
	
	@When("Select required course from filtered course list and Move to Course page as {string}")
	public void select_required_course_from_filtered_course_list_and_Move_to_Course_page(String courseName) {
		coursePage = searchPage.moveToCoursePage(courseName);
		ReportLogs.addLogWithScreenshot(Status.INFO,"Successfully moved to Course Page");

	}

	@Then("Verify selected filter tabs options")
	public void verify_selected_filter_tabs_options() {
		searchPage.selectRequiredFilterTab(SearchPageFilterTags.SkillLevels);		
		boolean isSkillLevelDivVisible = searchPage.checkSelectedFilterHeaderActive(SearchPageFilterTags.SkillLevels.getSearchPageFilterTagName());
		Assert.assertTrue(isSkillLevelDivVisible, "Failed to assert Skill Level filter options");
		List<String> listOfSkillLevelFilters = searchPage.getListOfSelectedFilterOptions(SearchPageFilterTags.SkillLevels);
		assertThat(listOfSkillLevelFilters)
			.hasSize(3)
			.contains("Advanced","Beginner","Intermediate");
		Assert.assertTrue(searchPage.closeSelectedFilterActiveDiv(SearchPageFilterTags.SkillLevels.getSearchPageFilterTagName()), "Failed to close Skill Level filter options");
		ReportLogs.addLogWithScreenshot(Status.INFO,"Verified Skills Levels Filter Functionality");

		searchPage.selectRequiredFilterTab(SearchPageFilterTags.Roles);		
		boolean isRolesDivVisible = searchPage.checkSelectedFilterHeaderActive(SearchPageFilterTags.Roles.getSearchPageFilterTagName());
		Assert.assertTrue(isRolesDivVisible, "Failed to assert Skill Level filter options");
		List<String> listOfRolesFilters = searchPage.getListOfSelectedFilterOptions(SearchPageFilterTags.Roles);
		assertThat(listOfRolesFilters)
			.hasSize(6)
			.contains("Business Professional","Creative Professional","Data Professional","IT Ops","Information & Cyber Security","Software Development");
		Assert.assertTrue(searchPage.closeSelectedFilterActiveDiv(SearchPageFilterTags.Roles.getSearchPageFilterTagName()), "Failed to close Roles filter options");
		ReportLogs.addLogWithScreenshot(Status.INFO,"Verified Roles Filter Functionality");

	}

	@Then("Verify filtered courses list details")
	public void verify_filtered_courses_list_details() {
		searchPage.clearAllTabs();

		searchPage.clickCourseTabDetails();
		List<String> listOfCourseDetails = searchPage.getCoursesListDetails();		
		assertThat(listOfCourseDetails)
			.hasSize(24)
			.containsOnlyOnce("Java Fundamentals: The Java Language")
			.doesNotContain("Python");
		ReportLogs.addLogWithScreenshot(Status.INFO,"Verified Click on Course Functionality");
	}

	@Then("Validate other tabs details in nav bar list")
	public void validate_other_tabs_details_in_nav_bar_list() {		
		List<SearchPageNavBarListTabs> listOfTabOptions = new ArrayList<SearchPage.SearchPageNavBarListTabs>();
		listOfTabOptions.add(SearchPageNavBarListTabs.Blog);
		listOfTabOptions.add(SearchPageNavBarListTabs.Resources);
		listOfTabOptions.add(SearchPageNavBarListTabs.Authors);
		
		List<String> listOfTabDetails = searchPage.validateOtherTabsDetails(listOfTabOptions);
		assertThat(listOfTabDetails)
			.hasSize(3)
			.contains("Blog posts","Resources","Authors");
		ReportLogs.addLogWithScreenshot(Status.INFO,"Verified Tab details");

	}

}
