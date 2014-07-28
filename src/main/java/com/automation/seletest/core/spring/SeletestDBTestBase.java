package com.automation.seletest.core.spring;

import java.lang.reflect.Method;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * This class is used as a base class for DB testing
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Transactional
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration({"classpath*:META-INF/spring/db*-context.xml" })
public abstract class SeletestDBTestBase extends AbstractTransactionalTestNGSpringContextTests {

    @BeforeMethod(alwaysRun = true)
    @AfterMethod(alwaysRun = true)
    @Override
    protected synchronized void springTestContextBeforeTestMethod(Method testMethod) throws Exception {
        super.springTestContextBeforeTestMethod(testMethod);
    }

    @BeforeTransaction
    public void beforeTransaction(){
        //TODO
    }

    @AfterTransaction
    public void afterTransaction(){
        //TODO
    }

}
