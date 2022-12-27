package com.hybrid.bases;

import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.hybrid.utils.Assertions;

public class BaseTest extends Assertions {
	protected static final Logger logger = LogManager.getLogger();
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected String methodName;
	
	private DriverManagement driverManager;
	private static int defaultTimeout = 45;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"browser", "timeout", "isDisableExtension"})
	public void setUp(String browser, int timeout, @Optional boolean isDisableExtension) throws Exception {
		if(StringUtils.isEmpty(browser)) {
			logger.info("!!! Please input the browser");
			return;
		}
		driverManager = new DriverManagement(driver);
		
		switch(browser) {
		case "Chrome":
			driver = driverManager.getChromeDriver(true);
			break;
			
		case "Firefox":
			driver = driverManager.getFireFoxDriver();
			break;
			
		case "Edge":
			driver = driverManager.getEdgeDriver();
			break;
			
		default:
			logger.info("!!! Browser is not support on this version. Please select another.");
			break;
		}
		
		verifyNotNull(driver, "        Unable to initial " + browser + " driver");
		
		if (timeout <= 0) {
			timeout = defaultTimeout;
		}
		Duration duration = Duration.ofSeconds(timeout);
		wait = new WebDriverWait(driver, duration);
		driver.manage().timeouts().implicitlyWait(duration);
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			// No big deal
		}
		
		logger.info("===== Starting test script: " + methodName  + ", browser: " + browser + " =====");
		
	}
	
}
