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

package com.automation.seletest.core.services.utilities;

import java.io.FileOutputStream;
import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.Proxy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Performance class
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Service("performanceService")
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PerformanceUtils {

    @Getter @Setter ProxyServer server;
    @Getter @Setter Proxy proxy;
    @Getter @Setter Har har;

    /**
     * Starts the proxy server
     * @param port
     * @return
     * @throws Exception
     */
    public ProxyServer proxyServer(int port) throws Exception{
        ProxyServer server = new ProxyServer(port);
        server.start();
        server.setCaptureHeaders(true);
        server.setCaptureContent(true);
        this.server=server;
        return server;
    }

    /**
     * get the Selenium proxy object
     * @param server
     * @return
     * @throws Exception
     */
    public Proxy proxy(int port) throws Exception{
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("localhost:"+port+"");
        return proxy;
    }

    /**
     * Gets performance data
     * @param server
     * @return
     */
    public Har getPerformanceData(ProxyServer server){
        Har har = server.getHar();
        this.har=har;
        return har;
    }

    /**
     * writes performance data to file in har format
     * @param path
     * @param harFile
     * @throws IOException
     */
    public void writePerformanceData(String path, Har harFile){
        try{
            FileOutputStream fos = new FileOutputStream(path);
            harFile.writeTo(fos);}
        catch(Exception ex){
            log.error("Cannot write to external file: "+ex.getLocalizedMessage());
        }
    }

    /**
     * Stops proxy server
     * @param server
     * @return
     * @throws Exception
     */
    public PerformanceUtils stopServer(ProxyServer server){
        try {
            server.stop();
        } catch (Exception e) {
           log.error("Server cannot be stopped!!"+e);
        }
        return this;
    }

    /**
     * Creates new har
     * @param name
     * @return
     * @throws Exception
     */
    public PerformanceUtils newHar(String name) throws Exception{
        server.newHar(name);
        return this;
    }
}