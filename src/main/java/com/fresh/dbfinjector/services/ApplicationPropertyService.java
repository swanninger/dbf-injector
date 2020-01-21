package com.fresh.dbfinjector.services;

public interface ApplicationPropertyService {

    boolean setProperty(String path, String property, String value);

    boolean setProperty(String property, String value);
}
