package driverfactory;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

	//Singleton design pattern - only one instance exist ever, provide global access to that instance by creating getInstance method
	
	//private constructor so that no one else can create object of the class
	private DriverFactory() {
		
	}
	
	private static DriverFactory instance;
	
	public static DriverFactory getInstance() {
		if(instance == null) {
			instance = new DriverFactory();
		}
		return instance;
	}
	
//	synchronized public static DriverFactory getInstance() { // thread safe initialization
//		if(instance == null) {
//			try {
//				Thread.sleep(750);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			instance = new DriverFactory();
//		}
//		return instance; 
//	}
	
	//factory design pattern - define separate factory methods for creating objects & create objects by calling that methods
	public ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public void setDriver(WebDriver driverParam) {
		tlDriver.set(driverParam);
	}
	
	public void closeDriver() {
		tlDriver.get().quit();
		tlDriver.remove();
	}
}
