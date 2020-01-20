package com.fresh.dbfinjector.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * This class helps Spring properly convert Strings in properties to LocalDateTime format
 */

@Component
@ConfigurationPropertiesBinding
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String s) {
        if (s == null) {
            return null;
        }
        return LocalDateTime.parse(s);
    }
}
