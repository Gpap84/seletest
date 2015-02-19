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
package com.automation.seletest.core.jmx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

@Component
@Slf4j
public class MemoryThreadDumper {

	/**
	 * It dumps the Thread stacks
	 * @throws java.io.IOException
	 */
	public void dumpStacks() {

		String dumps = "stacks.dump";

		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threadInfos = mxBean.getThreadInfo(mxBean.getAllThreadIds(), 0);
		Map<Long, ThreadInfo> threadInfoMap = new HashMap<Long, ThreadInfo>();
		for (ThreadInfo threadInfo : threadInfos) {
			threadInfoMap.put(threadInfo.getThreadId(), threadInfo);
		}

		File dumpFile = new File(new File(Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory()).getParent()+dumps);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(dumpFile));
			this.dumpTraces(mxBean, threadInfoMap, writer);
			log.warn("Stacks dumped to: " + dumps);

		} catch (IOException e) {
			throw new IllegalStateException("An exception occurred while writing the thread dump");
		} finally {
			IOUtils.closeQuietly(writer);
		}

	}

	private void dumpTraces(ThreadMXBean mxBean,
			Map<Long, ThreadInfo> threadInfoMap, Writer writer)
			throws IOException {
		Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
		writer.write("Dump of "+stacks.size()+" thread at "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z").format(new Date(System.currentTimeMillis())) + "\n\n");
		for (Map.Entry<Thread, StackTraceElement[]> entry : stacks.entrySet()) {
			Thread thread = entry.getKey();
			writer.write("\""+thread.getName()+"\" prio="+thread.getPriority()+" tid="+thread.getId()+"\n");
			ThreadInfo threadInfo = threadInfoMap.get(thread.getId());
			if (threadInfo != null) {
				writer.write(" native=" + threadInfo.isInNative()
						+ ", suspended=" + threadInfo.isSuspended()
						+ ", block=" + threadInfo.getBlockedCount() + ", wait="
						+ threadInfo.getWaitedCount() + "\n");
				writer.write(" lock=" + threadInfo.getLockName()
						+ " owned by " + threadInfo.getLockOwnerName() + " ("
						+ threadInfo.getLockOwnerId() + "), cpu="
						+ mxBean.getThreadCpuTime(threadInfo.getThreadId())
						/ 1000000L + ", user="
						+ mxBean.getThreadUserTime(threadInfo.getThreadId())
						/ 1000000L + "\n");
			}
			for (StackTraceElement element : entry.getValue()) {
				writer.write("        ");
				writer.write(element.toString());
				writer.write("\n");
			}
			writer.write("\n");
		}
	}
}
