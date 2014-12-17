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
package com.automation.seletest.core.spring;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.testNG.assertions.SoftAssert;

/**
 * ExceptionHandlingAsyncTaskExecutor class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@Slf4j
@SuppressWarnings({"rawtypes","unchecked","hiding"})
public class ExceptionHandlingAsyncTaskExecutor<T> implements AsyncTaskExecutor {

	@Autowired
	ThreadPoolTaskExecutor threadPoolExecutor;

	/**The AsyncTaskExecutor*/
	private final AsyncTaskExecutor executor;

	public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
		this.executor = executor;
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.task.TaskExecutor#execute(java.lang.Runnable)
	 */
	@Override
	public void execute(Runnable task) {
		executor.execute(createWrappedRunnable(task));
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.task.AsyncTaskExecutor#execute(java.lang.Runnable, long)
	 */
	@Override
	public void execute(Runnable task, long startTimeout) {
		executor.execute(createWrappedRunnable(task), startTimeout);
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.task.AsyncTaskExecutor#submit(java.lang.Runnable)
	 */
	@Override
	public Future<?> submit(Runnable task) {
		return executor.submit(createWrappedRunnable(task));
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.task.AsyncTaskExecutor#submit(java.util.concurrent.Callable)
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		Future<T> futureTask=null;
		try {
			futureTask = executor.submit(createCallable(task));
			if(!((SessionContext.getSession().getAssertion()).getAssertion() instanceof SoftAssert)){
				futureTask.get();
				log.debug("Assertion finished: {} , proceed to the next one if exists!!!", task);
			} else {
				SessionContext.getSession().getVerifications().add(futureTask);
			}
			log.debug("Future task submitted {}", task.toString());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception during executing future task {}, stop submitting any further tasks: ",e.getMessage());
			throw new RejectedExecutionException("Reject any other verification task due to hard assertion error ----> "+e.getMessage());

		}
		return futureTask;
	}

	/**
	 * Create callable task
	 * @param task
	 * @return Callable
	 */
	private Callable createCallable(final Callable task) {
		return new Callable() {
			@Override
			public T call() throws Exception {
				try {
					log.debug("Thread Id {} for thread: ", Thread.currentThread().getId(),Thread.currentThread().getName());
					return (T) task.call();
				} catch (Exception ex) {
					handle(ex);
					throw ex;
				}
			}
		};
	}


	/**
	 * Create runnable task
	 * @param task
	 * @return Runnable
	 */
	private Runnable createWrappedRunnable(final Runnable task) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch (Exception ex) {
					handle(ex);
				}
			}
		};
	}

	/**
	 * Handle exception for asynchronous execution method
	 * @param ex
	 * @throws Exception
	 */
	private void handle(Exception ex){
		log.error("Error during @Async execution: {}", ex);
	}
}