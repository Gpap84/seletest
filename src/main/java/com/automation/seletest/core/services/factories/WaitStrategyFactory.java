package com.automation.seletest.core.services.factories;

import com.automation.seletest.core.services.actions.ActionsSync;

/**
 * WaitStrategyFactory
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public interface WaitStrategyFactory {

    /**Gets the strategy for waiting for conditions*/
    ActionsSync getWaitStrategy(String strategyName);
}

