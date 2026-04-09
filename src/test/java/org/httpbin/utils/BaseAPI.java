package org.httpbin.utils;

import io.cucumber.java.Before;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;

public class BaseAPI {

    @Before
    public void setUp() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        baseURI = props.getProperty("base.url");
    }
}
