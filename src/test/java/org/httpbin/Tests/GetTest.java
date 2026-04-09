package org.httpbin.Tests;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.http.HttpStatus;
import org.httpbin.Utils.BaseAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetTest extends BaseAPI {

	@Test
	@DisplayName("Validar URL Req")
	public void validarURLReq() {

		Response res = 
		given()
			.contentType(ContentType.JSON)
		.when()
			.get("/get");
		
		res.then()
			.log().all()
			.statusCode(HttpStatus.SC_OK);

		assertEquals("https://httpbin.org/get", res.jsonPath().getString("url"));
	}
}
