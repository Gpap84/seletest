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
public class HttpClient {

    @Autowired
    RestTemplate template;

    /**
     * Get request RestFul Web Service
     * @param <T>
     * @param ip
     * @param account
     * @return response from web service
     */
    public <T> T getRequest(String uri, Class<T> responseType, T arguments){
        return template.getForObject(uri,responseType, arguments);
    }




}
