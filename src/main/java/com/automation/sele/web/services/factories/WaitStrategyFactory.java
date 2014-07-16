package com.automation.sele.web.services.factories;

import com.automation.sele.web.services.actions.ActionsSync;

/**
 * WaitStrategyFactory
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public interface WaitStrategyFactory {

    /**Gets the strategy for waiting for conditions*/
    ActionsSync getWaitStrategy(String strategyName);
}

