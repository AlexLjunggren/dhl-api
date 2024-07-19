package io.ljunggren.dhl.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.ljunggren.dhl.api.response.Error;
import io.ljunggren.dhl.api.response.TrackingResponse;
import io.ljunggren.jsonUtils.JsonUtils;

public class DhlApi {

    private String apiKey;
    private DhlProperties properties;
    
    public DhlApi(DhlEnvironment environment, String apiKey) {
        this.properties = new DhlProperties(environment);
        this.apiKey = apiKey;
    }
    
    public TrackingResponse track(String... trackingNumbers) throws IOException {
        return track(getHttpClient(), trackingNumbers);
    }
    
    // package public for unit testing
    public TrackingResponse track(CloseableHttpClient httpClient, String... trackingNumbers) throws IOException {
        Header[] headers = new Header[] {
                new BasicHeader("content-type", "application/json"),
                new BasicHeader("DHL-API-Key", apiKey)
        };
        try {
            URI uri = new URIBuilder(properties.getTrackingUrl()).addParameter("trackingNumber", String.join(",", trackingNumbers)).build();
            HttpGet get = new HttpGet(uri);
            get.setHeaders(headers);
            CloseableHttpResponse response = httpClient.execute(get);
            int responseCode = response.getStatusLine().getStatusCode();
            String json = getResult(response);
            if (200 == responseCode) {
                return parse(json, TrackingResponse.class);
            }
            if (429 == responseCode) {
                createErrorResponse(responseCode, "Rate limit: Too many requests.");
            }
            return createErrorResponse(responseCode, json);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return null;
    }
    
    private TrackingResponse createErrorResponse(int responseCode, String message) {
        TrackingResponse trackingResponse = new TrackingResponse();
        Error errorResponse = new Error(responseCode, message);
        trackingResponse.setError(errorResponse);
        return trackingResponse;
    }
    
    private static String getResult(HttpResponse response) throws UnsupportedOperationException, IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = new String();
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
    }
    
    private <T> T parse(String json, Class<T> clazz) throws Exception {
        try {
            return JsonUtils.jsonToObject(json, clazz);
        } catch (JsonProcessingException e) {
            throw new Exception("Unable to parse response");
        }
    }
}
