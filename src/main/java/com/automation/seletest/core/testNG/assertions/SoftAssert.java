/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.automation.seletest.core.testNG.assertions;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import com.automation.seletest.core.services.Logging;

/**
 * This class used for executing soft assertions
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class SoftAssert extends Assertion {

	@Autowired
	Logging log;

    // LinkedHashMap to preserve the order
    private final Map<AssertionError, IAssert> m_errors = Maps.newLinkedHashMap();

    @Override
    public void executeAssert(IAssert a) {
        try {
            a.doAssert();
            log.info("[EXPECTED]:"+a.getExpected()+" [ACTUAL]:"+ a.getActual()+"***** ----> VERIFICATION: "+a.getMessage(),"background-color:green; color:black; margin-left:20px;");
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

    /**
     * Display stackTrace to HTML report
     * @param ex
     * @return
     */
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
