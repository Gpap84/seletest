package com.automation.sele.web.listeners;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;


/**The event listener
 * @param <E>*/
@Slf4j
public class EventListener implements ApplicationListener<Event.MessageEvent> {

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(Event.MessageEvent event) {
		log.info("Event occured: "+event.getMessage());
	}

}