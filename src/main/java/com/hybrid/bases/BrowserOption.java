package com.hybrid.bases;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserOption implements IBrowserOption {

	@Override
	public ChromeOptions getChromeOptions(boolean isDisableExtension) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		if (isDisableExtension) {
			chromeOptions.addArguments("disable-extensions");
		}
		return chromeOptions;
	}

	@Override
	public FirefoxOptions getFirefoxOption() {
		// TODO Auto-generated method stub
		return new FirefoxOptions();
	}

	@Override
	public EdgeOptions getEdgeOption() {
		// TODO Auto-generated method stub
		return new EdgeOptions();
	}

}
