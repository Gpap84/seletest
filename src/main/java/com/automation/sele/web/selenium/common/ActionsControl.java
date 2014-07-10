package com.automation.sele.web.selenium.common;

import org.openqa.selenium.interactions.Actions;

/**
 * Interface for all the methods used by WebDriver for interacting with UI
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public interface ActionsControl {

    <T> WebInteraction pressTo(ActionsType type, T element);

    <T> WebInteraction typeTo(InsertType type, T element, String text);

    Actions getActionsBuilder();

    enum ActionsType{PRESS,CLICK,ACTIONS};

    enum InsertType{INPUT,KEYS};
}
