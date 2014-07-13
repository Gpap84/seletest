/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;

import java.io.File;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.Reporter;

import lombok.extern.slf4j.Slf4j;

/**
 * WebActionsBase class
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public abstract class WebActionsBase implements WebActionsController<Object>{
	
	/**
	 * Log the screenshot of a failed log entry (error(...)) to
	 * the logs
	 *
	 * @param file
	 *            file must exist
	 */
	protected void reportLogScreenshot(File file, ITestContext ct) {
		Reporter.log("<a href=\"" + relativePath(file, ct) + "\"><p align=\"left\">Error screenshot at " + new Date() + "</p>");
		Reporter.log("<p><img width=\"878\" src=\"" + relativePath(file, ct) + "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br />");
		log.warn("Screenshot has been generated, path is {}", file.getAbsolutePath());

	}
	
	@Override
	public void sleep(long milliseconds) {
		try {
			log.info("About to sleep for " + milliseconds + " msecs");
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * make a filename within the reportng folders so we can use relative paths.
	 *
	 * @return the file
	 */
	protected File createScreenshotFile(ITestContext ct) {
		if (ct.getSuite().getOutputDirectory() != null) {
			File outputDir = new File(new File(ct.getSuite().getOutputDirectory()).getParent(), "screenshots");
			if(!outputDir.exists()){
				outputDir.mkdirs();
			}
			return new File(outputDir, "screenshot-" + System.nanoTime() + ".png");
		}
		return new File("screenshot-" + System.nanoTime() + ".png");
	}

	/**
	 * Make relative path (different OS)
	 *
	 * @param file
	 *            the file instance
	 * @return the file path
	 */
	private String relativePath(File file, ITestContext ct) {
		String outputDir = new File(ct.getSuite().getOutputDirectory()).getParent();
		if (outputDir != null) {
			String absolute = file.getAbsolutePath();
			int beginIndex = absolute.indexOf(outputDir) + outputDir.length();
			String relative = absolute.substring(beginIndex);
			return "."+relative.replace('\\', '/');
		}
		return file.getPath();
	}
}
