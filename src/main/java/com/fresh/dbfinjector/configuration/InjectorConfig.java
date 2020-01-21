package com.fresh.dbfinjector.configuration;

import com.fresh.dbfinjector.services.ApplicationPropertyService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@ConfigurationProperties(prefix = "injector")
@Component
@Getter
@Setter
public class InjectorConfig {
    private final ApplicationPropertyService applicationPropertyService;

    private LocalDateTime lastChecked;
    private String newDataPath;

    public InjectorConfig(ApplicationPropertyService applicationPropertyService) {
        this.applicationPropertyService = applicationPropertyService;
    }

    public void setLastChecked(LocalDateTime newLastChecked) {
        applicationPropertyService.setProperty("injector.lastChecked", newLastChecked.toString());
        this.lastChecked = newLastChecked;
    }
}
