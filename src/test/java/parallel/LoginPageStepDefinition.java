package parallel;

import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.Status;

import driverfactory.DriverFactory;
import helperTestUtility.ReportLogs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageStepDefinition {

	public static final Logger logger = LogManager.getLogger(LoginPageStepDefinition.class);
	LoginPage loginpage = new LoginPage(DriverFactory.getInstance().getDriver());
	SearchPage searchpage;

	@Given("User launches the Pluralsight Clone Application using link {string}")
	public void user_launches_the_pluralsight_clone_application_using_link(String url) {
		DriverFactory.getInstance().getDriver().get(url);
	}

	@Given("Login form should be visible on launch application URL")
	public void login_form_should_be_visible_on_launch_application_url() {
		loginpage.waitForLoginFormToBeVisible();
		ReportLogs.addLog(Status.INFO, "Login Form is Visible");
	}

	@When("User login as {string}")
	public void user_login_as(String authority) {
		searchpage = loginpage.loginToPluralsightApplication(authority);
		ReportLogs.addLog(Status.INFO, "User Logged in as "+authority);
	}

	@Then("Verify Search page has displayed after login and verify search box text as {string}")
	public void verify_search_page_has_displayed_after_login_and_verify_search_box_text_as(String expSearchText) {
		String searchText = loginpage.getSearchPlaceholderText();
		//Assert.assertFalse(true);
		assertEquals(searchText, expSearchText,"Failed to assert Search Text of Search Box placeholder");
	}

	@Then("Logout from Pluralsight clone application")
	public void logout_from_pluralsight_clone_application() {
		loginpage = loginpage.logoutFromPluralsightApplication();
		ReportLogs.addLogWithMarkUp(Status.INFO, "Logged out from Pluralsight Application");
		ReportLogs.addLogWithScreenshot(Status.INFO, "Logged out from Pluralsight Application");
	}

	@When("User should enter credentials as {string} and {string}")
	public void user_should_enter_credentials_as_and(String username, String password) {
        loginpage.checkNegativeLoginScenarios(username, password);
	}

	@Then("Verify login error message as {string}")
	public void verify_login_error_message_as(String errorMessage) {
		String loginErrorMsg = loginpage.getLoginErrorText();
		//Assert.assertFalse(true);
		assertEquals(loginErrorMsg, errorMessage, "Failed to assert Login Error message for nagative scenario");
	}

}
