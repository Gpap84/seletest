package com.automation.seletest.core.listeners;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;


/**
 * The event Listener
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class EventListener implements ApplicationListener<Event.MessageEvent> {

	@Override
	public void onApplicationEvent(Event.MessageEvent event) {
		log.info("Event occured: "+event.getMessage());
	}

}