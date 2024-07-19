package io.ljunggren.dhl.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DhlProperties {

    private static Properties properties = new Properties();
    
    private static final String API_TRACKING_URL = "api.tracking.url";

    public DhlProperties(DhlEnvironment environment) {
        Map<DhlEnvironment, String> fileMap = generateFileMap();
        setProperties(fileMap.get(environment));
    }
    
    private static Map<DhlEnvironment, String> generateFileMap() {
        Map<DhlEnvironment, String> fileMap = new HashMap<DhlEnvironment, String>();
        fileMap.put(DhlEnvironment.SANDBOX, "/properties/sandbox/dhl.properties");
        fileMap.put(DhlEnvironment.PRODUCTION, "/properties/production/dhl.properties");
        return fileMap;
    }
    
    private static void setProperties(String file) {
        try {
            InputStream is = DhlProperties.class.getResourceAsStream(file);
            properties.load(is);
            is.close();
        } catch(IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }
    
    public String getTrackingUrl() {
        return properties.getProperty(API_TRACKING_URL);
    }
    
}
