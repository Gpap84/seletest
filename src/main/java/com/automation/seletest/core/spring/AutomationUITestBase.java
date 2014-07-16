package com.automation.seletest.core.spring;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.Initialization;

/**
 * This class serves as the Base Class for Web Test Preparation
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@ContextConfiguration({"classpath*:META-INF/spring/*-context.xml" })
public abstract class AutomationUITestBase extends AbstractTestNGSpringContextTests {

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

    @Parameters({"hostURL","testType"})
    @BeforeTest(alwaysRun = true)
    protected void beforeTest(
            ITestContext ctx,
            @Optional String hostURL,
            String type) throws Exception {

        if(ctx.getCurrentXmlTest().getParallel().compareTo("false")==0||
                ctx.getCurrentXmlTest().getParallel().compareTo("tests")==0){

            log.debug("*****************************************");
            log.debug("**** Parallel level is: {}***************", ctx.getCurrentXmlTest().getParallel());
            log.debug("*****************************************");
            initializeSession(hostURL,type);
        }

    }

    @Parameters({"hostURL","testType"})
    @BeforeClass(alwaysRun = true)
    protected void beforeClass(
            ITestContext ctx,
            @Optional String hostURL,
            String type) throws Exception {

        if(ctx.getCurrentXmlTest().getParallel().compareTo("classes")==0){

            log.debug("*****************************************");
            log.debug("**** Parallel level is: {}***********", ctx.getCurrentXmlTest().getParallel());
            log.debug("*****************************************");
            initializeSession(hostURL,type);
        }
    }

    @Parameters({"hostURL","testType"})
    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod(
            ITestContext ctx,
            @Optional String hostURL,
            String type) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("methods")==0){

            log.debug("*****************************************");
            log.debug("**** Parallel level is: {}***************", ctx.getCurrentXmlTest().getParallel());
            log.debug("*****************************************");
            initializeSession(hostURL,type);
        }
    }

    @AfterClass(alwaysRun = true)
    protected void cleanSessionOnClass(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("classes")==0){
            log.debug("************* Clean session after test class *********************");
            SessionContext.destroyThread();
        }
    }

    @AfterTest(alwaysRun = true)
    public void cleanSessionOnTest(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("false")==0||
                ctx.getCurrentXmlTest().getParallel().compareTo("tests")==0){
            log.debug("************* Clean session after test ***************************");
            SessionContext.destroyThread();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void cleanSessionOnMethod(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("methods")==0){
            log.debug("************* Clean session after test method *********************");
            SessionContext.destroyThread();
        }
    }

    @AfterSuite(alwaysRun = true)
    protected void cleanSuite() throws Exception {

    }

    /**Prepare initialization*/
    private void initializeSession(String hostURL, String mode) throws BeansException, Exception{
        Map<String, Object> controls=Initialization.getControllers(mode);
        SessionContext.setSessionProperties(controls);
        SessionContext.getSession().getActionscontroller().getTargetHost(hostURL);
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
