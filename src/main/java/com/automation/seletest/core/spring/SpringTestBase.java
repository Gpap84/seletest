package com.automation.seletest.core.spring;

/**
 * Created by uocgp on 11/9/2015.
 */

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.automation.seletest.core.selenium.configuration.ConfigurationDriver;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * Test Base for TestNG test suites
 * Created by uocgp on 4/5/2015.
 */
@ContextConfiguration(classes=ConfigurationDriver.class)
public class SpringTestBase extends AbstractTestNGSpringContextTests {

	@BeforeSuite(alwaysRun = true)
	@BeforeClass(alwaysRun = true)
	@BeforeTest(alwaysRun = true)
	@Override
	protected void springTestContextPrepareTestInstance() throws Exception {
		Logger root = (Logger) LoggerFactory.getLogger("com.automation.seletest");
		if(System.getProperty("logging.level")!=null) {
			if (System.getProperty("logging.level").compareTo("INFO") == 0) {
				root.setLevel(Level.INFO);
			} else if (System.getProperty("logging.level").compareTo("DEBUG") == 0) {
				root.setLevel(Level.DEBUG);
			} else if (System.getProperty("logging.level").compareTo("WARN") == 0) {
				root.setLevel(Level.WARN);
			} else if (System.getProperty("logging.level").compareTo("ERROR") == 0) {
				root.setLevel(Level.ERROR);
			}
		}
		prepareTest();
	}

	/**
	 * Prepare Test loading application context
	 * @throws Exception
	 */
	private void prepareTest() throws Exception {
		if (applicationContext == null) {
			super.springTestContextPrepareTestInstance();
		}
	}

}