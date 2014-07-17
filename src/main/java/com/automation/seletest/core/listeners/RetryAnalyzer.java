package com.automation.seletest.core.listeners;

import org.openqa.selenium.TimeoutException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;


public class RetryAnalyzer implements IRetryAnalyzer{

    private int count = 0;

    private int maxCount = 1;

    public RetryAnalyzer() {
        setCount(maxCount);
    }

    @Override
    public boolean retry(ITestResult result) {

        if ((!result.isSuccess() && !(result.getThrowable() instanceof TimeoutException))) {
            if (count < maxCount) {
                count++;
                Reporter.log("<font color=\"#FF00FF\"/>"+Thread.currentThread().getName() + "Error in "
                        + result.getName() + " with status "
                        + result.getStatus() + " Retrying " + count + " times</font><br>");
                return true;
            }

        }
        return false;

    }

    public void setCount(int count) {
        maxCount = count;
    }


}