/**
 * 
 */
package com.automation.sele.web.listeners;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.ApplicationEvent;

/**
 * Event class for custom events
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class Event extends ApplicationEvent{
	private static final long serialVersionUID = -5308299518665062983L;

	public Event(Object source) {
		super(source);}

	/**
	 * Class for events with a message
	 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
	 *
	 */
	public static class MessageEvent extends Event {
		@Getter @Setter private String message;
		@Getter @Setter private Thread thread;

		private static final long serialVersionUID = -5308299518665062983L;

		public MessageEvent(Object source, String msg, Thread thread) {
			super(source);
			this.message=msg;
			this.thread=thread;
		}
	}

}
