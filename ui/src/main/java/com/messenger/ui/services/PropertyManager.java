package com.messenger.ui.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyManager.class);
    private final static String NETWORK_PROPERTY_FILE_NAME = "ui/src/main/resources/network.properties";

    private static final Properties property = new Properties();

    private static boolean isLocalFlag;

    static {
        try (InputStream fis = new FileInputStream(NETWORK_PROPERTY_FILE_NAME)) {
            property.load(fis);
        } catch (IOException e) {
            LOG.debug("Error: Property file not found!");
        }
    }

    public static String getProperty(String propertyName) {
        if(isLocalFlag) {
            if (property.containsKey(propertyName + "-local")){
                return property.getProperty(propertyName + "-local");
            }
        }
        return property.getProperty(propertyName);
    }

    public static void setIsLocalFlag(Boolean flag) {
        isLocalFlag = flag;
        LOG.info("isLocalFlag is set to {}",isLocalFlag);
    }
}
