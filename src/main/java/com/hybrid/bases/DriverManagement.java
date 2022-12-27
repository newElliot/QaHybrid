package com.hybrid.bases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.manager.SeleniumManager;

import com.hybrid.utils.Assertions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManagement extends Assertions {
	private static final Logger logger = LogManager.getLogger();
	private static final int MAX_ATTEMPTS = 5;
	private WebDriver driver;
	public DriverManagement(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getChromeDriver(boolean isDisableExtension) throws Exception {
		int attempts = 0;
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		if (isDisableExtension) {
			chromeOptions.addArguments("disable-extensions");
		}
		do {
			verifyNull(driver, "        Driver is already initialized");
			try {
				logger.info("    Starting init chrome driver");
				driver = new ChromeDriver(chromeOptions); 
			} catch (Exception e) {
				logger.error("        Failed while trying to init chrome driver. Attempts: " + attempts, e);
			}
		} while (driver == null && attempts++ < MAX_ATTEMPTS);
		
		return driver;
	}
	
	public WebDriver getFireFoxDriver() throws Exception {
		int attempts = 0;
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		do {
			verifyNull(driver, "        Driver is already initialized");
			try {
				logger.info("    Starting init firefox driver");
				driver = new FirefoxDriver(firefoxOptions); 
			} catch (Exception e) {
				logger.error("        Failed while trying to init firefox driver. Attempts: " + attempts, e);
			}
		} while (driver == null && attempts++ < MAX_ATTEMPTS);
		
		return driver;
	}
	
	public WebDriver getEdgeDriver() throws Exception {
		int attempts = 0;
		EdgeOptions options = new EdgeOptions();
		do {
			verifyNull(driver, "        Driver is already initialized");
			try {
				logger.info("    Starting init edge driver");
				driver = new EdgeDriver(options); 
			} catch (Exception e) {
				logger.error("        Failed while trying to init edge driver. Attempts: " + attempts, e);
			}
		} while (driver == null && attempts++ < MAX_ATTEMPTS);
		
		return driver;
	}
	
}
