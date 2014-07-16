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