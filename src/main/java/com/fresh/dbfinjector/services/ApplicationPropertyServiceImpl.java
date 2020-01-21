package com.fresh.dbfinjector.services;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.Properties;

@Service
public class ApplicationPropertyServiceImpl implements ApplicationPropertyService {
    private final String DEFAULT_PATH = "application.properties";

    private void saveProperties(String path, Properties prop) {
        OutputStream output = null;
        try {
            output = new FileOutputStream(path);

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
        }
    }

    private Properties loadProperties(String path) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(path);
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
        return prop;
    }

    @Override
    public boolean setProperty(String path, String property, String value) {
        Properties prop = loadProperties(path);
        prop.setProperty(property, value);
        saveProperties(path, prop);
        return true;
    }

    @Override
    public boolean setProperty(String property, String value) {
        return setProperty(DEFAULT_PATH, property, value);
    }

}