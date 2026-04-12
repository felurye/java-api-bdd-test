package dev.serverest.utils;

import io.cucumber.java.Before;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;

public class BaseAPI {

    @Before(order = 1)
    public void setUp() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        baseURI = props.getProperty("base.url");
    }
}
