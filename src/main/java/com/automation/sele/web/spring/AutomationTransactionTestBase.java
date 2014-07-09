package com.automation.sele.web.spring;

import java.lang.reflect.Method;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * This class is used as a base class for DB testing
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@TransactionConfiguration(defaultRollback = false)
@ActiveProfiles(profiles={"dev,qa"})
@ContextConfiguration({"classpath*:META-INF/spring/*-context.xml" })
public class AutomationTransactionTestBase extends AbstractTransactionalTestNGSpringContextTests {

	@Override
	@BeforeMethod(alwaysRun = true)
	protected synchronized void springTestContextBeforeTestMethod(Method testMethod) throws Exception {
		super.springTestContextBeforeTestMethod(testMethod);
	}

	@Override
	@AfterMethod(alwaysRun = true)
	protected synchronized void springTestContextAfterTestMethod(Method testMethod) throws Exception {
		super.springTestContextAfterTestMethod(testMethod);
	}
}
