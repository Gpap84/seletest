package com.automation.sele.web.spring;

import lombok.extern.slf4j.Slf4j;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * This class serves as the Base Class for Test Preparation
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@ActiveProfiles(profiles={"dev,qa"})
@ContextConfiguration({"classpath*:META-INF/spring/*-context.xml" })
public class AutomationUITestBase extends AbstractTestNGSpringContextTests {

    @BeforeSuite(alwaysRun = true)
    @BeforeClass(alwaysRun = true)
    @BeforeTest(alwaysRun = true)
    @Override
    protected void springTestContextPrepareTestInstance() throws Exception {
    	prepareTest();
    }

    
    @BeforeSuite(alwaysRun = true)
    protected void suiteSettings() throws Exception {
        
    }

    @BeforeTest(alwaysRun = true)
    protected void beforeTest() throws Exception {
       
    }

    @BeforeClass(alwaysRun = true)
    protected void beforeClass() throws Exception {
        

    }

    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod() throws Exception {

    }

    @AfterClass(alwaysRun = true)
    protected void cleanContextOnClass() throws Exception {
        
    }

    @AfterTest(alwaysRun = true)
    public void cleanContextOnTest() throws Exception {
      
    }

    @AfterMethod(alwaysRun = true)
    public void cleanContextOnMethod() throws Exception {


    }

 
    @AfterSuite(alwaysRun = true)
    protected void cleanSuite() throws Exception {
       
    }
    
    private void initializaSession(){
    	
    }

 
    private void prepareTest(){
        try {
            if (applicationContext == null) {
                super.springTestContextPrepareTestInstance();

            }
        } catch (Exception e1) {
            log.error("Error during initializing spring container "+e1);
        }
    }
}
