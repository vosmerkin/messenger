package com.messenger.common.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMapper<T> {
    private static final Logger log = LoggerFactory.getLogger(JsonMapper.class);

    public static <T> String toJson(T object) {
        var ow = new ObjectMapper();
        String json = "{}";
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.debug(String.valueOf(e));
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        var om = new ObjectMapper();
        T object = null;
        try {
            object = om.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.debug(String.valueOf(e));
        }
        return object;
    }
}
