package com.automation.seletest.core.selenium.common;

import org.openqa.selenium.Keys;

public interface BuildActions<T> {

    /**
     * Clicks in the middle of the given element. Equivalent to: Actions.moveToElement(onElement).click()
     * @param locator
     * @return
     */
    T click(Object locator);

    /**
     * Moves the mouse to the middle of the element
     * @param locator
     * @return
     */
    T mouseOver(Object locator);

    /**
     * Performs a modifier key release.
     * Releasing a non-depressed modifier key will yield undefined behaviour.
     * @param key
     * @return
     */
    T mouseUp(Keys key);

    /**
     * Performs a modifier key press.
     * Does not release the modifier key - subsequent interactions may assume it's kept pressed.
     * Note that the modifier key is never released implicitly - either keyUp(theKey) or sendKeys(Keys.NULL)
     *  must be called to release the modifier.
     * @param key
     * @return
     */
    T mouseDown(Keys key);

    /**
     * Move to element first and then perform mouseDown
     * @param locator
     * @param key
     * @return
     */
    T mouseDown(Object locator, Keys key);

    /**
     * Move to element and then perform mouseUp
     * @param locator
     * @param key
     * @return
     */
    T mouseUp(Object locator, Keys key);

    /**
     * Clicks (without releasing) in the middle of the given element. This is equivalent to: Actions.moveToElement(onElement).clickAndHold()
     * @param locator
     * @return
     */
    T clickAndHold(Object locator);


}
