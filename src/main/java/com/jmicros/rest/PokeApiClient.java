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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class PokeApiClient {

    private static final Logger logger = LogManager.getLogger(PokeApiClient.class);
    private static final String URI = "https://pokeapi.co/api/v2/pokemon";

    public void run() {
        /*
         * Business case:
         * By default, a list "page" will contain up to 20 resources.
         * If you would like to change this just add a 'limit' query parameter to the GET request, e.g. ?=60.
         * You can use 'offset' to move to the next page, e.g. ?limit=60&offset=60.
         */
        paginationSmokeTest();
    }

    private void paginationSmokeTest() {
        checkDefaultCase();
        checkCustomLimit(60);
        checkCustomLimit(100);
        checkCustomOffset();
    }

    private void checkDefaultCase() {
        HttpGet request = new HttpGet(URI);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String message = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(message);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    logger.info("pokemon quantity: " + jsonArray.length());
                    if(jsonArray.length() == 20){
                        logger.info("default pagination case passed");
                    }else{
                        throw new RuntimeException("default pagination case failed, length=" + jsonArray.length());
                    }
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("Cannot get data from " + URI, e);
        } catch (IOException e) {
            logger.error("Cannot read response", e);
        } catch (Exception e){
            logger.error("", e);
        }
    }

    private void checkCustomLimit(int limit) {
        HttpGet request = new HttpGet(URI + "?limit=" + limit);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String message = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(message);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    logger.info("pokemon quantity: " + jsonArray.length());
                    if(jsonArray.length() == limit){
                        logger.info("custom pagination case passed");
                    }else{
                        throw new RuntimeException("custom pagination case failed, length=" + jsonArray.length());
                    }
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("Cannot get data from " + URI, e);
        } catch (IOException e) {
            logger.error("Cannot read response", e);
        } catch (Exception e){
            logger.error("", e);
        }
    }

    private void checkCustomOffset() {
        HttpGet request = new HttpGet(URI + "?offset=20");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String message = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(message);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    JSONObject pokemon = jsonArray.getJSONObject(0);
                    String name = pokemon.getString("name");
                    if(!"spearow".equals(name)){
                        throw new RuntimeException("custom offset failed, pokemon=" + name);
                    }
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("Cannot get data from " + URI, e);
        } catch (IOException e) {
            logger.error("Cannot read response", e);
        } catch (Exception e){
            logger.error("", e);
        }
    }
}
