package com.automation.seletest.core.listeners;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;


/**
 * This class serves as for publishing events to Spring IoC
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class EventPublisher implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    @Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Publish a message event
     * @param message
     */
    public void publishMessageEvent(String message) {
        this.publisher.publishEvent(new Event.MessageEvent(this, message, Thread.currentThread()));
    }

    /**
     * Publish event for initializing web session
     * @param message
     */
    public void publishWebInitEvent(String message, String hostUrl, boolean performance) {
        this.publisher.publishEvent(new Event.WebInitEvent(this, message, hostUrl, performance));
    }

    /**
     * Publish event for initializing mobile session
     * @param message
     */
    public void publishMobileInitEvent(String message) {
        this.publisher.publishEvent(new Event.MobileInitEvent(this,message));
    }




}
