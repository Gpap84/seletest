package com.automation.seletest.core.selenium.common;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.services.actions.AbstractBase;

@Component
public class ActionsBuilder<T> extends AbstractBase.ActionsBase<Object>{



    @Override
    public ActionsBuilder<T> mouseOver(Object locator) {
        actionsBuilder().moveToElement(findElement(locator)).perform();
        return this;
    }

    @Override
    public ActionsBuilder<T> mouseUp(Keys key) {
        actionsBuilder().keyUp(key).perform();
        return this;
    }

    @Override
    public ActionsBuilder<T> mouseDown(Keys key) {
        actionsBuilder().keyDown(key).perform();
        return this;
    }

    @Override
    public ActionsBuilder<T> mouseDown(Object locator, Keys key) {
        actionsBuilder().keyDown(findElement(locator),key).perform();
        return this;
    }

    @Override
    public ActionsBuilder<T> mouseUp(Object locator, Keys key) {
        actionsBuilder().keyUp(findElement(locator),key).perform();
        return this;
    }

    @Override
    public ActionsBuilder<T> clickAndHold(Object locator) {
        actionsBuilder().clickAndHold(findElement(locator)).perform();
        return this;
    }

    /**
     * Returns WebElement based on arguments
     * @param locator
     * @return
     */
    private WebElement findElement(Object locator){
        if(locator instanceof WebElement){
            return (WebElement) locator;
        } else if(locator instanceof String){
            WebElement element=super.actionsController().findElement((String)locator);
            return element;
        }
        return null;
    }

    @Override
    public ActionsBuilder<T> click(Object locator) {
        actionsBuilder().click(findElement(locator)).perform();
        return this;
    }


}
