package com.fresh.alohainjector.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

@ConfigurationProperties(prefix = "injector")
@Component
@Getter
public class InjectorConfig {
    private LocalDateTime lastChecked;

    public void setLastChecked(LocalDateTime newLastChecked){
        Properties prop = new Properties();
        InputStream input = null;
        OutputStream output = null;

        try {

            input = new FileInputStream("application.properties");

            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            output = new FileOutputStream("application.properties");

            prop.setProperty("injector.lastChecked", newLastChecked.toString());

            prop.store(output,null);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.lastChecked = newLastChecked;
        }
    }
}
