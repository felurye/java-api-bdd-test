package org.httpbin.Utils;

import static io.restassured.RestAssured.baseURI;

import org.junit.jupiter.api.BeforeAll;

public class BaseAPI {

	@BeforeAll
	public static void preCondition() {
		baseURI = "https://httpbin.org";
	}

}
