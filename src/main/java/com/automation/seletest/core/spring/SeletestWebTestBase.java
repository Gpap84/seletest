package com.automation.seletest.core.spring;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.automation.seletest.core.listeners.EventPublisher;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.CoreProperties;
import com.automation.seletest.core.testNG.TestNG;

/**
 * This class serves as the Base Class for Web Test Preparation
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@ContextConfiguration({"classpath*:META-INF/spring/*-context.xml" })
@EnableAspectJAutoProxy(proxyTargetClass=true)
public abstract class SeletestWebTestBase extends AbstractTestNGSpringContextTests {

	@Autowired
	TestNG testNG;

	@BeforeSuite(alwaysRun = true)
	@BeforeClass(alwaysRun = true)
	@BeforeTest(alwaysRun = true)
	@Override
	protected void springTestContextPrepareTestInstance() throws Exception {
		prepareTest();
	}

	@BeforeSuite(alwaysRun = true)
	protected void suiteSettings(ITestContext ctx) throws Exception {

	}

	@BeforeTest(alwaysRun = true)
	protected void beforeTest(
			ITestContext ctx) throws Exception {
		if(ctx.getCurrentXmlTest().getParallel().compareTo("false")==0||
				ctx.getCurrentXmlTest().getParallel().compareTo("tests")==0){

			log.debug("*****************************************");
			log.debug("**** Parallel level is: <<{}>>***********", ctx.getCurrentXmlTest().getParallel());
			log.debug("*****************************************");

			/*****Define initialization phase*/
			initializeSession(ctx);
		}
	}

	@BeforeClass(alwaysRun = true)
	protected void beforeClass(
			ITestContext ctx) throws Exception {
		if(ctx.getCurrentXmlTest().getParallel().compareTo("classes")==0){

			log.debug("******************************************************************");
			log.debug("**** Initialize session upon parallel level: <<\"{}\">>***********", ctx.getCurrentXmlTest().getParallel());
			log.debug("******************************************************************");

			/*****Define initialization phase*/
			initializeSession(ctx);
		}
	}

	@BeforeMethod(alwaysRun = true)
	protected void beforeMethod(
			ITestContext ctx) throws Exception {
		if(ctx.getCurrentXmlTest().getParallel().compareTo("methods")==0){

			log.debug("*********************************************************************");
			log.debug("**** Initialize session upon parallel level <<\"{}\">>***************", ctx.getCurrentXmlTest().getParallel());
			log.debug("*********************************************************************");

			/*****Define initialization phase*/
			initializeSession(ctx);
		}
	}

	@AfterClass(alwaysRun = true)
	protected void cleanSessionOnClass(ITestContext ctx) throws Exception {
		if(ctx.getCurrentXmlTest().getParallel().compareTo("classes")==0){
			log.debug("************* Clean session after test class ************************");
			SessionContext.cleanSession();
		}
	}

	@AfterTest(alwaysRun = true)
	public void cleanSessionOnTest(ITestContext ctx) throws Exception {
		if(ctx.getCurrentXmlTest().getParallel().compareTo("false")==0||
				ctx.getCurrentXmlTest().getParallel().compareTo("tests")==0){
			log.debug("************* Clean session after test ***************************");
			SessionContext.cleanSession();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void cleanSessionOnMethod(ITestContext ctx) throws Exception {
		if(ctx.getCurrentXmlTest().getParallel().compareTo("methods")==0){
			log.debug("************* Clean session after test method *********************");
			SessionContext.cleanSession();
		}
	}

	@AfterSuite(alwaysRun = true)
	protected void cleanSuite() throws Exception {

	}

	/**Prepare initialization*/
	private void initializeSession(ITestContext ctx){
		EventPublisher publisher = applicationContext.getBean(EventPublisher.class);
		if(!testNG.getParameterXML(ctx, CoreProperties.PROFILEDRIVER.get()).startsWith("appium")){
			publisher.publishWebInitEvent("New Web session initialized!!!", testNG.getParameterXML(ctx, "hostURL"));
		} else {
			publisher.publishMobileInitEvent("New Appium session initialized!!!");
		}

	}

	/**Starts the Spring container*/
	private void prepareTest() throws Exception{
		try {
			if (applicationContext == null) {
				super.springTestContextPrepareTestInstance();
			}
		} catch (Exception e1) {
			log.error("Error during initializing spring container "+e1.getCause());
			throw e1;
		}
	}


}
