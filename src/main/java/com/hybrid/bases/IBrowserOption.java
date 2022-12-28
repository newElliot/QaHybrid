package com.hybrid.bases;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public interface IBrowserOption {
	/**
	 * Get Chrome options
	 * @param isDisableExtension
	 * @return ChromeOptions
	 */
	ChromeOptions getChromeOptions(boolean isDisableExtension);
	
	/**
	 * Get Firefox option
	 * @return
	 */
	FirefoxOptions getFirefoxOption();
	
	/**
	 * Get Edge option
	 * @return
	 */
	EdgeOptions getEdgeOption();
}
