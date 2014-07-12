package com.automation.sele.web.testNG.assertions;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import com.automation.sele.web.services.LoggingService;

/**
 * This class used for executing soft assertions
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class SoftAssert extends Assertion {

	@Autowired
	LoggingService log;
	
    // LinkedHashMap to preserve the order
    private final Map<AssertionError, IAssert> m_errors = Maps.newLinkedHashMap();


    @Override
    public void executeAssert(IAssert a) {
        try {
            a.doAssert();
            log.info("[EXPECTED]:"+a.getExpected()+" [ACTUAL]:"+ a.getActual()+"***** ----> VERIFICATION: "+a.getMessage());
        } catch(AssertionError ex) {
        	log.error("*****[EXPECTED]:"+a.getExpected()+" [ACTUAL]:"+ a.getActual()+"***** ----> VERIFICATION: "+a.getMessage()+ "------StackTrace:\\n"+findLineExceptionOccured(ex));
            onAssertFailure(a, ex);
            m_errors.put(ex, a);
        }
    }

    public void assertAll() {
        if (! m_errors.isEmpty()) {
            StringBuilder sb = new StringBuilder("The following asserts failed:\n");
            boolean first = true;
            for (Map.Entry<AssertionError, IAssert> ae : m_errors.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(ae.getKey());
            }
            //set the test as failed
            Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
            org.testng.Reporter.setCurrentTestResult(Reporter.getCurrentTestResult());
            m_errors.clear();//clear the soft assertion HashMap
        }
    }

    private String findLineExceptionOccured(AssertionError ex){
        StringBuilder sb = new StringBuilder("");

        for(StackTraceElement line:ex.getStackTrace()){
            if(line.getClassName().startsWith("com.automation.sele.web")) {
                sb.append("Class: "+line.getClassName()+ " method: "+line.getMethodName()+" line: "+line.getLineNumber()+"\\n");
            }
        }
        return sb.toString();

    }
}
