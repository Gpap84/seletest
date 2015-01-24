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

import com.automation.seletest.core.jmx.MemoryWarningService;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ManagedResource(objectName = MemoryWarningServiceConfigurator.MBEAN_NAME,description = "Allows clients to set the memory threshold")
public class MemoryWarningServiceConfigurator {

	public static final String MBEAN_NAME = "seletest.mbeans:type=config,name=MemoryWarningServiceConfiguration";

	@ManagedOperation(description = "Sets the memory threshold for the memory warning system")
	@ManagedOperationParameters({ @ManagedOperationParameter(description = "The memory threshold", name = "memoryThreshold"), })
	public void setMemoryThreshold(double memoryThreshold) {
		MemoryWarningService memoryWarningService = (MemoryWarningService) ApplicationContextProvider.getApplicationContext().getBean("memoryWarningService");
		memoryWarningService.setPercentageUsageThreshold(memoryThreshold);
		log.info("Memory threshold set to " + memoryThreshold);
	}


}
