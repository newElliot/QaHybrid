package com.hybrid.bases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.hybrid.utils.Assertions;
import com.hybrid.utils.SystemCall;
import com.hybrid.utils.TimeStamp;

public class BaseTest extends Assertions {
	protected static final Logger logger = LogManager.getLogger();
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected String methodName;
	
	private static final String CHROME = "Chrome";
	private static final String FIREFOX = "Firefox";
	private static final String EDGE = "Edge";
	
	private IDriverManagement driverManager;
	private IBrowserOption browserOption;
	private static int defaultTimeout = 45;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"browser", "timeout", "isDisableExtension"})
	public void setUp(String browser, int timeout, @Optional boolean isDisableExtension) throws Exception {
		if(StringUtils.isEmpty(browser)) {
			logger.info("!!! Please input the browser");
			return;
		}
		driverManager = new DriverManagement(driver);
		browserOption = new BrowserOption();
		
		switch(browser) {
		case CHROME:
			driver = driverManager.getChromeDriver(browserOption.getChromeOptions(isDisableExtension));
			break;
			
		case FIREFOX:
			driver = driverManager.getFireFoxDriver(browserOption.getFirefoxOption());
			break;
			
		case EDGE:
			driver = driverManager.getEdgeDriver(browserOption.getEdgeOption());
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
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {
		try {
			switch (result.getStatus()) {
				case ITestResult.SUCCESS:
					logger.info("***** Passed Test Case " + result.getMethod().getMethodName() + " *****");
					break;
				case ITestResult.FAILURE:
					logger.error("***** Failed Test Case " + result.getMethod().getMethodName() + ". Capturing Screenshot " + takeScreenshot(driver, result.getMethod()) + " *****");

					break;
				case ITestResult.SKIP:
					logger.info("***** Skipped Test Case " + result.getMethod().getMethodName() + " *****");

					break;
				default:
					logger.warn("***** Unknown Test Result Status (" + result.getStatus() + ") for Test Case " + result.getMethod().getMethodName() + " *****");
			}
		} finally {
			quitWebDriver();
		}
	}
	
	private void quitWebDriver() throws IOException, InterruptedException, Exception {
		if (driver != null) {
			try {
				driver.close();
			} catch (Exception e) {
				//No big deal
			}
			try {
				driver.quit();
			} catch (Exception e) {
				// Might be a problem
				logger.error("        Error quitting WebDriver, driver=" + driver, e);
				logger.info("==== Start kill driver ====");
				if (getOperatingSystem().contains("Windows")) {
					if (driver instanceof ChromeDriver) {
						SystemCall.executeShellCommand("taskkill /F /FI \"IMAGENAME eq chromedriver*\"");
						logger.info("==== Chrome driver was killed successfully ====");
					}
					
					if (driver instanceof FirefoxDriver) {
						SystemCall.executeShellCommand("taskkill /F /FI \"IMAGENAME eq geckodriver*\"");
						logger.info("==== Firefox driver was killed successfully ====");
					}
					
					if (driver instanceof EdgeDriver) {
						SystemCall.executeShellCommand("taskkill /F /FI \"IMAGENAME eq msedgedriver*\"");
						logger.info("==== Edge driver was killed successfully ====");
					}
				}
			}
			driver = null;
		}
	}
	
	private String getOperatingSystem() {
		return System.getProperty("os.name");
	}
	
	private String takeScreenshot(WebDriver driver, ITestNGMethod testMethod) throws Exception {
		if (driver == null) {
			logger.warn("Unable to take screenshot as driver is not initialized, methodName="
					+ ((testMethod != null) ? testMethod.getMethodName() : null));
			return StringUtils.EMPTY;
		}
		byte[] content = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		File file = getScreenshotFile(testMethod.getMethodName(), ((RemoteWebDriver) driver).getSessionId());
		FileUtils.writeByteArrayToFile(file, content);
		return file.getAbsolutePath();
	}
	
	private static File getScreenshotFile(String testName, SessionId sessionId) throws Exception {
		return new File(System.getProperty("user.dir") + File.separator + "screen-shots" + File.separator
				+ getScreenshotName(testName, sessionId));
	}
	
	private static String getScreenshotName(String testName, SessionId sessionId) throws Exception {
		return testName + "_" + sessionId + "_" + TimeStamp.getTimeStamp() + ".png";
	}
	
}
