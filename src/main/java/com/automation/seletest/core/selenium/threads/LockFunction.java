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

package com.automation.seletest.core.selenium.threads;

import lombok.extern.slf4j.Slf4j;

/**
 * This class will be used for locking/ unlocking functions from being called from multiple threads
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class LockFunction{

	 boolean isLocked = false;
	  Thread  lockedBy = null;
	  int     lockedCount = 0;

	  public synchronized void lock()
	  throws InterruptedException{
	    Thread callingThread = Thread.currentThread();
	    long startTime = System.currentTimeMillis();
	    while(isLocked && lockedBy != callingThread){
	    	log.info("Wait called!!! "+Thread.currentThread());
	      wait(10000);
	      long endTime = System.currentTimeMillis();
	      if (endTime - startTime > 30000) {
	    	  //notify();
	    	  log.warn("Thread was not notified!!!");
	    	  break;
	      }
	    }
	    isLocked = true;
	    lockedCount++;
	    lockedBy = callingThread;
	  }


	  public synchronized void unlock(){
	    if(Thread.currentThread() == this.lockedBy){
	      lockedCount--;

	      if(lockedCount == 0){
	        isLocked = false;
	        notify();
	        log.info("Unlock the thread: "+Thread.currentThread());
	      }
	    }
	  }
	}