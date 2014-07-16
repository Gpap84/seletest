/**
 *
 */
package com.automation.seletest.core.selenium.webAPI;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.automation.seletest.core.aspectJ.RetryFailure;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.factories.WaitStrategyFactory;

/**
 * This class contains the implementation of webDriver API
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@SuppressWarnings({"hiding","unchecked"})
@Component
@Scope("prototype")
public class WebDriverActionsController<T> extends ActionsBase{

	@Autowired
	WaitStrategyFactory waitStrategy;

	/**The webDriver object*/
	@Getter @Setter
	WebDriver driver;

	@Getter @Setter
	RemoteWebDriver remoteDriver;

	@Getter @Setter
	JavascriptExecutor jsExec;

	@Override
	public void getTargetHost(String url) {
		driver.get(url);
	}

	@Override
	public <T extends WebDriver> T getDriverInstance() {
		return (T) getDriver();
	}

	@Override
	public void quit(CloseSession type) {
		switch (type) {
		case QUIT:
			driver.quit();
			break;
		case CLOSE:
			driver.close();
			break;
		default:
			driver.quit();
			break;
		}
	}

	@Override
	@RetryFailure(retryCount=1)
	public WebDriverActionsController<T> clickTo(String locator, long timeout) {
		waitStrategy.
		getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementToBeClickable(locator, timeout).
		click();
		return this;
	}

	@Override
	@RetryFailure(retryCount=1)
	public WebDriverActionsController<T> enterTo(String locator, String text, long timeout) {
		waitStrategy.
		getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementPresence(locator, timeout).sendKeys(text);
		return this;
	}

	@Override
	public WebDriverActionsController<T> changeStyle(String attribute, String locator, String attributevalue) {
		jsExec.executeScript("arguments[0].style."+attribute+"=arguments[1]",waitStrategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementVisibility(locator, SessionContext.getSession().getWaitUntil()),attributevalue);	
		return this;
	}

	@Override
	public void takeScreenShot() throws IOException{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File file = createScreenshotFile();
		FileUtils.copyFile(scrFile, file);
		reportScreenshot(file);
	}

	@Override
	public void takeScreenShotOfElement(String locator) throws IOException {
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);
		WebElement element=waitStrategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementVisibility(locator, SessionContext.getSession().getWaitUntil());
		Point point = element.getLocation();
		int eleWidth = element.getSize().getWidth();
		int eleHeight = element.getSize().getHeight();
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", screenshot);
		File file = createScreenshotFile();
		FileUtils.copyFile(screenshot, file);
		reportScreenshot(file);
	}
}
