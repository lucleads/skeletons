package com.skeleton.skeletons.feature.shared.steps;

import com.skeleton.skeletons.feature.shared.config.CucumberHttpClient;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class ApiStepDefinitions {

    @Autowired
    private CucumberHttpClient cucumberHttpClient;
    private ResponseEntity<?> response;
    private String requestBody = null;
    private Optional<LinkedMultiValueMap<String, String>> queryParams = Optional.empty();
    private String token;

    @Given("I am an authenticated user")
    public void i_am_an_authenticated_user() {
        this.token = Base64.getEncoder()
                .encodeToString("cucumberuser:password".getBytes());
    }

    @Given("I am authenticated as an invalid user")
    public void i_am_authenticated_as_an_invalid_user() {
        this.token = Base64.getEncoder()
                .encodeToString("cucumberuserinvalid:password".getBytes());
    }

    @Given("I have the body")
    public void i_have_the_body(JsonNode requestBody) {
        this.requestBody = requestBody.toString();
    }

    @Given("I have the query parameters")
    public void i_have_the_query_parameters(DataTable queryParameters) {
        Map<String, String> map = queryParameters.asMap();
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for (Entry<String, String> parameter : map.entrySet()) {
            multiValueMap.add(parameter.getKey(), parameter.getValue());
        }

        this.queryParams = Optional.of(multiValueMap);
    }

    @When("I send a {string} request to {string}")
    public void i_send_a_request_to(String method, String path) {
        this.response = this.cucumberHttpClient.doRequest(method, path, this.requestBody,
                this.queryParams, this.token);
    }

    @Then("the response status code is {int}")
    public void the_response_status_code_is(int expectedStatusCode) {
        assertEquals(expectedStatusCode, this.response.getStatusCodeValue());
    }

    @Then("the response content is:")
    public void the_response_content_is(JsonNode expectedResponseBody) {
        assertEquals(expectedResponseBody.toString(), StringEscapeUtils.unescapeJava(
                new JSONObject((HashMap) this.response.getBody()).toString()));
    }

    @Then("the response contains at least one server")
    public void the_response_contains_at_least_one_server() throws JSONException {
        assertNotEquals("[]",
                new JSONObject((HashMap) this.response.getBody()).get("servers").toString());
    }

    @DocStringType(contentType = "json")
    public JsonNode json(String docString) throws JsonProcessingException {
        return new ObjectMapper().readTree(docString);
    }
}

