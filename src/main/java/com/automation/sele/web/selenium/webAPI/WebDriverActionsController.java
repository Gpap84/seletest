/**
 *
 */
package com.automation.sele.web.selenium.webAPI;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.automation.sele.web.aspectJ.RetryIfFails;
import com.automation.sele.web.selenium.threads.SessionContext;
import com.automation.sele.web.services.actions.FluentWait;
import com.automation.sele.web.services.actions.WaitExpected;

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
    WaitExpected waitFor;
    
    @Autowired
    FluentWait fluentwaitFor;

    /**The webDriver object*/
    @Getter @Setter
    WebDriver driver;

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
    @RetryIfFails(retryCount=1)
    public WebDriverActionsController<T> clickTo(Object locator, long timeout) {
        waitFor.waitForElementToBeClickable(timeout, locator).click();
        return this;
    }

    @Override
    @RetryIfFails(retryCount=1)
    public WebDriverActionsController<T> enterTo(String locator, String text, long timeout) {
        waitFor.waitForElementPresence(timeout, locator).sendKeys(text);
        return this;
    }
    
	@Override
	@RetryIfFails(retryCount=1)
	public WebDriverActionsController<T> changeStyle(String attribute, String locator, String attributevalue) {
        jsExec.executeScript("arguments[0].style."+attribute+"=arguments[1]",waitFor.waitForElementVisibility(SessionContext.getSession().getWaitForElement(), locator),attributevalue);
		return this;
	}
	
	@Override
	public void takeScreenShot() throws IOException{
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (scrFile != null) {
            File file = createScreenshotFile();
            FileUtils.copyFile(scrFile, file);
            reportLogScreenshot(file);
        }
    }
	
	@Override
	public void takeScreenShotOfElement(String locator) throws IOException {
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);
		WebElement element=waitFor.waitForElementVisibility(SessionContext.getSession().getWaitForElement(), locator);
		Point point = element.getLocation();
		int eleWidth = element.getSize().getWidth();
		int eleHeight = element.getSize().getHeight();
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth,
		    eleHeight);
		ImageIO.write(eleScreenshot, "png", screenshot);
		FileUtils.copyFile(screenshot, createScreenshotFile());
	}
}
