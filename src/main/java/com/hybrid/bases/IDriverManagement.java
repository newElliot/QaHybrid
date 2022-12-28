package com.hybrid.bases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public interface IDriverManagement {
	/**
	 * Get Chrome driver
	 * @param ChromeOptions option
	 * @return WebDriver driver
	 * @throws Exception
	 */
	WebDriver getChromeDriver(ChromeOptions options) throws Exception;
	
	/**
	 * Get Firefox driver
	 * @param FirefoxOptions option
	 * @return WebDriver driver
	 * @throws Exception
	 */
	WebDriver getFireFoxDriver(FirefoxOptions option) throws Exception;
	
	/**
	 * Get Edge driver
	 * @param EdgeOptions option
	 * @return WebDriver driver
	 * @throws Exception
	 */
	WebDriver getEdgeDriver(EdgeOptions option) throws Exception;
}
