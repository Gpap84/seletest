/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
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

package com.automation.seletest.core.jmx.mbeans;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;



/**
     * MBean which allows clients to change or retrieve the logging level for a
     * Log4j Logger at runtime
     */
    @Component
    @Slf4j
    @ManagedResource(objectName = LoggerConfigurator.MBEAN_NAME, description = "Allows clients to set the Log4j Logger level at runtime")
    public class LoggerConfigurator {

        public static final String MBEAN_NAME = "seletest.mbeans:type=config,name=LoggingConfiguration";

        @ManagedOperation(description = "Returns the Logger LEVEL for the given logger name")
        @ManagedOperationParameters({ @ManagedOperationParameter(description = "The Logger Name", name = "loggerName"), })
        public String getLoggerLevel(String loggerName) {
            Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
            Level loggerLevel = logger.getLevel();
            return loggerLevel == null ? "The logger " + loggerName + " has not level" : loggerLevel.toString();
        }

        @ManagedOperation(description = "Set Logger Level")
        @ManagedOperationParameters({
                @ManagedOperationParameter(description = "The Logger Name", name = "loggerName"),
                @ManagedOperationParameter(description = "The Level to which the Logger must be set", name = "loggerLevel") })
        public void setLoggerLevel(String loggerName, String loggerLevel) {
            Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
            logger.setLevel(Level.INFO);
            Logger loggerNew = (Logger) LoggerFactory.getLogger(loggerName);
            loggerNew.setLevel(Level.toLevel(loggerLevel, Level.INFO));
            log.info("Set logger " + loggerName + " to level "+ loggerNew.getLevel());
        }
    }
