package com.messenger.ui.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private final static String NETWORK_PROPERTY_FILE_NAME = "src/main/resources/network.properties";

    private static final Properties property = new Properties();

    static {
        try (InputStream fis = new FileInputStream(NETWORK_PROPERTY_FILE_NAME)) {
            property.load(fis);
        }
        catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    public static String GetProperty(String propertyName) {
        return property.getProperty("propertyName");
    }

}
