package com.selenium.testng.api.tests;

import org.testng.annotations.Test;

import com.selenium.testng.api.base.BaseApiTest;
import com.selenium.testng.api.clients.UserApiClient;
import com.selenium.testng.utils.AssertionUtils;

import io.restassured.response.Response;

public class UserApiTest extends BaseApiTest {
	
    private final UserApiClient userApi = new UserApiClient();

	@Test(groups = { "api-smoke" })
	public void verifyUserExists() {

		Response response = userApi.getUser(2);

		AssertionUtils.assertStatusCode(response, 
										200, 
										"Verified status code is 200");

		AssertionUtils.assertJsonPathEquals(response, 
											"data.first_name", 
											"Janet", 
											"Verified first name is Janet");

		AssertionUtils.assertResponseTimeLessThan(response, 
													2000, 
													"Verified response time is less than 2 seconds");

		AssertionUtils.assertJsonPathNotNull(response, 
												"data.email", 
												"Verified email is present");

	}
	
	@Test(groups = { "api-smoke" })
	public void verifyUserWithSoftAssertions() {

	    Response response = userApi.getUser(2);

		// 🔵 Soft validations start
		AssertionUtils.softAssertStatusCode(response, 
											200, 
											"Verify status code is 200");

		// Page validation
		AssertionUtils.softAssertJsonPathEquals(response, 
												"data.id", 
												"2", 
												"Verify page number is 2");

		// Total users validation
		AssertionUtils.softAssertTrue(response.jsonPath().get("data") != null, 
										"Verify users list is not empty");

		// 🔥 Extract multiple values from response
		String firstUserFirstName = response.jsonPath().getString("data.first_name");
		String firstUserEmail = response.jsonPath().getString("data.email");

		AssertionUtils.softAssertEquals(firstUserFirstName, 
										"Janet", 
										"Verify first user's first name");
		AssertionUtils.softAssertTrue(firstUserEmail.contains("@reqres.in"), 
										"Verify email domain is correct");

		// Second user validation (different data set)
		String secondUserLastName = response.jsonPath().getString("data.last_name");

		AssertionUtils.softAssertNotEquals(secondUserLastName, 
											"", 
											"Verify second user's last name is not empty. It's " + secondUserLastName);

		// 🔴 Final step (VERY IMPORTANT)
		AssertionUtils.assertAll();
	}
	
}