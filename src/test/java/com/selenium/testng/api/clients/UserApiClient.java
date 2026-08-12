package com.selenium.testng.api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserApiClient {
	
	private static final Logger log = LoggerFactory.getLogger(UserApiClient.class);

	public Response getUser(int id) {
		
		log.info("========== API REQUEST ==========");
		log.info("GET /users/{}", id);
		
		Response response = given()
		            .log().all() // Request logging as sysout to console
		        .when()
		            .get("/users/" + id)
		        .then()
		            .log().all() // Response logging as sysout to console
		            .extract().response();	
		
		log.info("Status : {}", response.statusCode());
		log.info("Response :\n{}", response.asPrettyString());
		log.info("================================");
		return response;
	}
}
