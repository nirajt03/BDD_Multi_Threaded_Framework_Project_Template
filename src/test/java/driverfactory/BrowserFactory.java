package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserFactory {

	/**
	 * Create Browser Instance
	 * @param browser
	 * @return
	 */
	public WebDriver createBrowserInstance(String browser) {

		WebDriver driver = null;
		if (browser.equalsIgnoreCase("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("--incognito");
			options.addArguments("--remote-allow-origins=*");	
			//options.setBinary("126");
			//options.setBrowserVersion("126");

			//Map<String, Object> prefs = new HashMap<>();
			// enable/disable mic or camera permissions
			//prefs.put("profile.default_content_setting_values.media_stream_mic", 2);
			//value "1" is used for allowing the option, "2" -- for blocking.
			//prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
			//options.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("Firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-private");
			driver = new FirefoxDriver(options);
		} else if (browser.equalsIgnoreCase("Edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("-private");
			driver = new EdgeDriver(options);
		} else {
			System.out.println("Please pass the correct browser value. Browser passed was : " + browser);
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;
	}
}
