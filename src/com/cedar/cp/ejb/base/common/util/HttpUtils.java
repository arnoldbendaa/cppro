package com.cedar.cp.ejb.base.common.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cedar.cp.api.base.ValidationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.common.exceptions.HttpUtilException;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    
    public static HttpEntity doPost(String requestURL, HttpEntity entity) throws HttpUtilException, ValidationException {
        // Initialize HttpClient
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        int error_500_iterations = 0;
        // Initialize HttpPost
        HttpPost post = new HttpPost(requestURL);
        post.setEntity(entity);
        // Get response status
        HttpResponse response = doPost(client, post, error_500_iterations);
        int status = response.getStatusLine().getStatusCode();
        
        // repeat 10x more if error 500 occurred
        while (status != 200) {
            if (status == 500) {
                if (error_500_iterations == 10) {
                    throw new HttpUtilException("ExcelIO's Internal Error, http response status code 500 in all 10 attempts!");
                } else {
                    logger.info("HttpUtil: Error 500, attempt no " + error_500_iterations + ".");
                    System.out.println("HttpUtil: Error 500, attempt no " + error_500_iterations + ".");
                    // wait 2s for external service fix
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new HttpUtilException("Thread failed while posting!", e);
                    }
                    // Get response status
                    response = doPost(client, post, ++error_500_iterations);
                    status = response.getStatusLine().getStatusCode();
                }
            } else if (status == 415) {
                throw new HttpUtilException("File is unsupported, http response status code 415.");
            } else if (status == 400) {
                throw badRequestErrorReader(response);
            } else {
                throw new HttpUtilException("Unrecognized Error, http response status code " + status + "!");
            }
        }
        return response.getEntity();
    }

    /**
     * @param response
     * @throws HttpUtilException 
     */
    private static ValidationException badRequestErrorReader(HttpResponse response) throws HttpUtilException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Exception exception = objectMapper.readValue(response.getEntity().getContent(), Exception.class);
            ValidationException validationException = new ValidationException(exception.getMessage());
            validationException.setStackTrace(exception.getStackTrace());
            return validationException;
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new HttpUtilException("Bad request, http response status code 400 & json parse exception.");
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new HttpUtilException("Bad request, http response status code 400 & json mapping exception.");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw new HttpUtilException("Bad request, http response status code 400 & illegal state exception.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpUtilException("Bad request, http response status code 400 & IOexception.");
        }
    }

    public static HttpResponse doPost(HttpClient client, HttpPost post, int error_500_iterations) throws HttpUtilException {
        try {
            return client.execute(post);
        } catch (ClientProtocolException e) {
            throw new HttpUtilException("Error executing POST request!", e);
        } catch (IOException e) {
            throw new HttpUtilException("Error executing POST request!", e);
        } catch (Exception e) {
            throw new HttpUtilException("Error executing POST request!", e);
        }
    }

}