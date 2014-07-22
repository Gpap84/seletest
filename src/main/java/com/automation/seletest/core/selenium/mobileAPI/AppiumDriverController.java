package com.automation.seletest.core.selenium.mobileAPI;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * AppiumDriverController api for Appium interaction
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Scope("prototype")
public class AppiumDriverController implements AppiumController{

    @Getter @Setter AppiumDriver appiumDriver;

    @Override
    public AppiumDriverController launchApp() {
        appiumDriver.launchApp();
        return this;
    }

    @Override
    public AppiumDriverController resetApp() {
        appiumDriver.resetApp();
        return this;
    }

    @Override
    public AppiumDriverController runAppinBackground(int sec) {
        appiumDriver.runAppInBackground(sec);
        return this;
    }

    @Override
    public AppiumDriverController closeApp() {
        appiumDriver.closeApp();
        return this;
    }



}
