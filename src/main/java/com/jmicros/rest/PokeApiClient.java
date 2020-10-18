package com.jmicros.rest;

import com.jmicros.Config;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PokeApiClient {

    private static final Logger logger = LogManager.getLogger(PokeApiClient.class);

    private Config config;

    public PokeApiClient(Config config){
        this.config = config;
    }

    public void run() {
        String uri = "https://pokeapi.co/api/v2/pokemon/2";
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            logger.info(response.getProtocolVersion());              // HTTP/1.1
            logger.info(response.getStatusLine().getStatusCode());   // 200
            logger.info(response.getStatusLine().getReasonPhrase()); // OK
            logger.info(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                logger.info(result);
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

}
