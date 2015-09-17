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
     * GET http request to Rest API
     * @param uri The uri to send GET request
     * @param responseType The class of the response type
     * @param arguments Object... for arguments to send GET request
     * @param <T>
     * @return T
     */
    public <T> T getHTTPRequest(String uri, Class<?> responseType, Object... arguments){
        return (T) template.getForObject(uri,responseType, arguments);
    }

    /**
     * POST http reqest to Rest API
         * @param uri  The uri to send POST request
     * @param request The object request
     * @param responseType The class of the response type
     * @param arguments Object... for arguments to send POST request
     * @param <T>
     * @return T
     */
    public <T> T postHTTPRequest(String uri, Object request, Class<?> responseType, Object... arguments){
        return (T) template.postForObject(uri, request, responseType, arguments);
    }




}
