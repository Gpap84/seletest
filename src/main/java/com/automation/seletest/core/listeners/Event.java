/*
This file is part of the Seletest by Giannis Papadakis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Giannis Papadakis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.automation.seletest.core.listeners;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.ApplicationEvent;
import org.testng.ITestContext;

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

    /**
     * Class for events regarding initialization of web session
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public static class WebInitEvent extends Event {
        @Getter @Setter private String message;
        @Getter @Setter private String hostUrl;
        @Getter @Setter private boolean performance;
        @Getter @Setter private ITestContext testcontext;

        private static final long serialVersionUID = -5308299518665062983L;

        public WebInitEvent(Object source, String msg, String hostUrl, boolean performance, ITestContext context) {
            super(source);
            this.message=msg;
            this.hostUrl=hostUrl;
            this.performance=performance;
            this.testcontext=context;
        }
    }

    /**
     * Class for events regarding initialization of mobile session
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public static class MobileInitEvent extends Event {
        @Getter @Setter private String message;
        @Getter @Setter private ITestContext testcontext;

        private static final long serialVersionUID = -5308299518665062983L;

        public MobileInitEvent(Object source, String msg, ITestContext context) {
            super(source);
            this.message=msg;
            this.testcontext=context;
        }
    }

}
