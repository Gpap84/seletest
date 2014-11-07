package com.automation.seletest.core.services.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * HttpClient class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
@SuppressWarnings("unchecked")
public class HttpClient {

    @Autowired
    RestTemplate template;

    /**
     * Get request
     * @param <T>
     * @param ip
     * @param account
     * @return response from web server
     */
    public <T> T getRequest(String uri, Class<?> responseType, Object... arguments){
        return (T) template.getForObject(uri,responseType, arguments);
    }

    /**
     * Post request
     * @param uri
     * @param request
     * @param responseType
     * @param arguments
     * @return response from web server
     */
    public <T> T postRequest(String uri, Object request, Class<?> responseType, Object... arguments){
        return (T) template.postForObject(uri, request, responseType, arguments);
    }




}
