package com.eac.kalah.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
@Slf4j
public class JsonHelper {

    public String toJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(final String message, final Class<T> clazz) {
        try {
            return getObjectMapper().readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper getObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setTimeZone(TimeZone.getDefault());

        return mapper;
    }
}