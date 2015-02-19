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

package com.automation.seletest.core.listeners.beanUtils;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.ApplicationEvent;
import org.testng.ITestContext;

import com.automation.seletest.core.services.annotations.SeleniumTest;

/**
 * Event class for custom events
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class Events extends ApplicationEvent{
    private static final long serialVersionUID = -5308299518665062983L;

    public Events(Object source) {
        super(source);
    }

    /**
     * Class for events regarding initialization of web or mobile session
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public static class InitializationEvent extends Events {
        /**General Message for event*/
        @Getter @Setter private String message;

        /**Host url to run tests*/
        @Getter @Setter private String hostUrl;

        /**If performance metrics enabled*/
        @Getter @Setter private boolean performance;

        /**TestContext interface*/
        @Getter @Setter private ITestContext testcontext;

        private static final long serialVersionUID = -5308299518665062983L;

        /**
         * Initialize event
         * @param source Object source
         * @param msg String message
         * @param hostUrl String url
         * @param performance boolean peformance
         * @param context ITestContext context
         */
        public InitializationEvent(
                Object source,
                String msg,
                String hostUrl,
                boolean performance,
                ITestContext context) {
            super(source);
            this.message=msg;
            this.hostUrl=hostUrl;
            this.performance=performance;
            this.testcontext=context;
        }
    }
    
    /**
     * Class for events regarding TestNG configuration
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public static class TestNGEvent extends Events {

		public TestNGEvent(
				Object source,
				SeleniumTest selenium,
				String msg) {
			super(source);
			this.test=selenium;
			this.message=msg;
		}

		private static final long serialVersionUID = 1L;
		  
		/**SeleniuTest interface*/
        @Getter @Setter private SeleniumTest test;
        
        /**General Message for event*/
        @Getter @Setter private String message;
        
        
    }

}
