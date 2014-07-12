package com.automation.sele.web.spring;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.automation.sele.web.services.Constants;

/**
 * This class serves as the Base Class for Test Preparation
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@Configuration
@ContextConfiguration({"classpath*:META-INF/spring/*-context.xml" })
@PropertySources({ @PropertySource({ "BrowserSettings/browser.properties" })})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AutomationUITestBase extends AbstractTestNGSpringContextTests {

	@BeforeSuite(alwaysRun = true)
	@BeforeClass(alwaysRun = true)
	@BeforeTest(alwaysRun = true)
	@Override
	protected void springTestContextPrepareTestInstance() throws Exception {
		setActiveProfile(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(Constants.BROWSERTYPE.get()));
		prepareTest();
	}


	@BeforeSuite(alwaysRun = true)
	protected void suiteSettings(ITestContext ctx) throws Exception {

	}

	@BeforeTest(alwaysRun = true)
	protected void beforeTest(ITestContext ctx) throws Exception {

	}

	@BeforeClass(alwaysRun = true)
	protected void beforeClass(ITestContext ctx) throws Exception {

	}

	@BeforeMethod(alwaysRun = true)
	protected void beforeMethod(ITestContext ctx) throws Exception {

	}

	@AfterClass(alwaysRun = true)
	protected void cleanSessionOnClass() throws Exception {

	}

	@AfterTest(alwaysRun = true)
	public void cleanSessionOnTest() throws Exception {

	}

	@AfterMethod(alwaysRun = true)
	public void cleanSessionOnMethod() throws Exception {


	}

	@AfterSuite(alwaysRun = true)
	protected void cleanSuite() throws Exception {

	}

//	private void initializeSession(){
//
//	}
	
	private void setActiveProfile(String profile){
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,profile);
	}


	private void prepareTest() throws Exception{
		try {
			if (applicationContext == null) {
				super.springTestContextPrepareTestInstance();
			}
		} catch (Exception e1) {
			log.error("Error during initializing spring container "+e1);
			throw e1;
		}
	}
}
