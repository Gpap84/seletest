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
package com.automation.seletest.core.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EhCacheEventListener class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 */
public class EhCacheEventListener implements CacheEventListener {

	/**Logger for EhCacheEventListener.class*/
	private static final Logger CACHE_EVENT_LISTENER = LoggerFactory.getLogger(EhCacheEventListener.class);

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		CACHE_EVENT_LISTENER.debug("Cache element removed ----> "+element.getObjectKey());
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		CACHE_EVENT_LISTENER.debug("Cache element put ----> "+element.getObjectKey());
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		CACHE_EVENT_LISTENER.debug("Cache element updated ----> "+element.getObjectKey());
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		CACHE_EVENT_LISTENER.debug("Cache element expired ----> "+element.getObjectKey());
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		CACHE_EVENT_LISTENER.debug("Cache element evicted ----> "+element.getObjectKey());
	}

	@Override
	public void notifyRemoveAll(final Ehcache ehcache) {
		CACHE_EVENT_LISTENER.debug("All elements removed from cache ----> "+ehcache.getName());
	}

	@Override
	public void dispose() {

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Singleton instance");
	}

}