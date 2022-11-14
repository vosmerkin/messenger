package com.messenger.ui.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Logger log = LoggerFactory.getLogger(PropertyManager.class);
    private final static String NETWORK_PROPERTY_FILE_NAME = "ui/src/main/resources/network.properties";

    private static final Properties property = new Properties();

    static {
        try (InputStream fis = new FileInputStream(NETWORK_PROPERTY_FILE_NAME)) {
            property.load(fis);
        } catch (IOException e) {
            log.debug("Error: Property file not found!");
        }
    }

    public static String getProperty(String propertyName) {
        return property.getProperty(propertyName);
    }

}
