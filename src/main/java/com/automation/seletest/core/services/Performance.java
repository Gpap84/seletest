package com.automation.seletest.core.services;

import java.io.FileOutputStream;
import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
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
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Performance {

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
    public Proxy proxy(ProxyServer server) throws Exception{
        Proxy proxy = server.seleniumProxy();
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
    public void writePerformanceData(String path, Har harFile) throws IOException{
        FileOutputStream fos = new FileOutputStream(path);
        harFile.writeTo(fos);
    }

    /**
     * Stops proxy server
     * @param server
     * @return
     * @throws Exception
     */
    public Performance stopServer(ProxyServer server) throws Exception{
        server.stop();
        return this;
    }

    /**
     * Creates new har
     * @param name
     * @return
     * @throws Exception
     */
    public Performance newHar(String name) throws Exception{
        server.newHar(name);
        return this;
    }
}
