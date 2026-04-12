package dev.serverest.utils;

import io.restassured.response.Response;

public class ScenarioContext {

    private Response lastResponse;
    private String authToken;
    private String lastCreatedId;
    private String lastUserEmail;
    private String lastUserPassword;

    public Response getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getLastCreatedId() {
        return lastCreatedId;
    }

    public void setLastCreatedId(String lastCreatedId) {
        this.lastCreatedId = lastCreatedId;
    }

    public String getLastUserEmail() {
        return lastUserEmail;
    }

    public void setLastUserEmail(String lastUserEmail) {
        this.lastUserEmail = lastUserEmail;
    }

    public String getLastUserPassword() {
        return lastUserPassword;
    }

    public void setLastUserPassword(String lastUserPassword) {
        this.lastUserPassword = lastUserPassword;
    }
}
