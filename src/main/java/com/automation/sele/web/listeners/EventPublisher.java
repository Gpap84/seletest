package com.automation.sele.web.listeners;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import com.automation.sele.web.listeners.Event;


/**
 * This class serves as for publishing events to Spring IoC
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class EventPublisher implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    @Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(String message) {
        this.publisher.publishEvent(new Event.MessageEvent(this,message,Thread.currentThread()));
    }

   
}
