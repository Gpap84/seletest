/**
 *
 */
package com.automation.seletest.core.selenium.webAPI;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.Reporter;

/**
 * WebActionsBase class
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public abstract class ActionsBase implements ActionsController<Object>{

    /**
     * Log the screenshot of a failed log entry (error(...)) to
     * the logs
     * @param file
     *            file must exist
     */
    protected void reportScreenshot(File file) {
        Reporter.log("<a href=\"" + relativePath(file, Reporter.getCurrentTestResult().getTestContext()) + "\"><p><img width=\"878\" src=\"" + relativePath(file, Reporter.getCurrentTestResult().getTestContext()) + "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br />");
        log.warn("Screenshot captured, path is {}", file.getAbsolutePath());
    }

    /**
     * make a filename within the reportng folders so we can use relative paths.
     * @return the file
     * @throws IOException
     */
    protected File createScreenshotFile() throws IOException {
        if (Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory() != null) {
            File outputDir = new File(new File(Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory()).getParent(), "screenshots");
            FileUtils.forceMkdir(outputDir);
            return new File(outputDir, "screenshot-" + System.nanoTime() + ".png");
        }
        return new File("screenshot-" + System.nanoTime() + ".png");
    }

    /**
     * Make relative path (different OS)
     * @param file
     * @param ct
     * @return
     */
    protected String relativePath(File file, ITestContext ct) {
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
