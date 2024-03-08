package testCases;

import util.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateOneAccount extends GenerateBearerToken {

	String baseURI;
	String updateOneAccountEndPoint;
	String updateAccountBodyFilePath;
	String readAllAccountEndPoint;
	String firstAccountId;
	String readOneAccountEndPoint;
	

	public UpdateOneAccount() {

		baseURI = ConfigReader.getProperty("baseURI");
		updateOneAccountEndPoint = ConfigReader.getProperty("updateOneAccountEndPoint");
		updateAccountBodyFilePath = "src/main/java/data/updateAccountBody2.json";
		readAllAccountEndPoint = ConfigReader.getProperty("readAllAccountEndPoint");
		readOneAccountEndPoint = ConfigReader.getProperty("readOneAccountEndPoint");
		

	}

	@Test(priority=1)
	public void updateOneAccount() {

		// given : All Input Details -> (baseURI, Headers, BasicAuth/Bearer Token, Payload/body,
		// QueryParameters)
		// when : Submit api requests -> httpMethod(endpointReource) HttpMethods -POST,
		// DELETE, GET, PUT, PATCH
		// then: validate response -> (status code, Headers, responseTime, Payload/Body)

		// base uri - https://qa.codefios.com/api
		// base uri resource - https://qa.codefios.com/api/user/login
		// Header - "Content-Type" = "application/json"
		//"Authorization" = "Bearer + bearerToken"
		// status code = 200
		// Response -
		// {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyIiwidXNlcm5hbWUiOiJhZG1pbiIsIkFQSV9USU1FIjoxNzA1NTk4ODQzfQ.5VYXjbxxJfwZ7QLRBjnsYZx--Bx6tm6lxARxPKUcJ-8",
		// "status":true,"message":
		// "Login success!",
		// "token_expire_time":86400}

		
		//To store response from .extract().response();than use as needed
		Response response =

				given()
				      .baseUri("baseURI")
				      .header("Content-Type", "application/json")
				      .header("Authorization","Bearer " + bearerToken)
				      .body(new File(updateOneAccountEndPoint))
					  .log().all().

				when()
				      .put(updateOneAccountEndPoint).
				      
				then()
				      .log().all()
				      .extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(statusCode, 201, "Status code not matching");

		String responseHeaderContentType = response.getHeader("Content-Type");
		System.out.println("Response Header Content Type:" + responseHeaderContentType);
		Assert.assertEquals(responseHeaderContentType, "application/json", "Content type not matching");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body:  " + responseBody);
		
		//since the above response is in string, just to fetch the access token only 
		JsonPath jp = new JsonPath(responseBody);
		
		String message = jp.getString("message");
		System.out.println("Message" + message);
		Assert.assertEquals(message, "Account updated Successfully.");
		
	}
	
	@Test (priority=2)
	public void readOneAccount() {
		
		File expectedResponseBody = new File(updateAccountBodyFilePath);
		JsonPath jp2 = new JsonPath(expectedResponseBody);
		
		String expectedAccountId = jp2.getString("account_Id");
		System.out.println("Expected Account Name" + expectedAccountId);
		
		String expectedAccountName = jp2.getString("account_name");
		System.out.println("Expected Account Name" + expectedAccountName);
		
		
		String expectedAccountDescription = jp2.getString("description");
		System.out.println("Expected Account Name" + expectedAccountDescription);
		
		
		String expectedAccountBalance = jp2.getString("balance");
		System.out.println("Expected Account Name" + expectedAccountBalance);
		
		String expectedAccountNumber = jp2.getString("Account_number");
		System.out.println("Expected Account Name" + expectedAccountNumber);
		
		
		String expectedAccountPerson = jp2.getString("actual_person");
		System.out.println("Expected Account Name" + expectedAccountPerson);

		// given : All Input Details -> (baseURI, Headers, BasicAuth/Bearer Token, Payload/body,
		// QueryParameters)
		// when : Submit api requests -> httpMethod(endpointReource) HttpMethods -POST,
		// DELETE, GET, PUT, PATCH
		// then: validate response -> (status code, Headers, responseTime, Payload/Body)

		// base uri - https://qa.codefios.com/api
		// base uri resource - https://qa.codefios.com/api/user/login
		// Header - "Content-Type" = "application/json"
		//"Authorization" = "Bearer + bearerToken"
		// status code = 200
		// Response -
		// {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyIiwidXNlcm5hbWUiOiJhZG1pbiIsIkFQSV9USU1FIjoxNzA1NTk4ODQzfQ.5VYXjbxxJfwZ7QLRBjnsYZx--Bx6tm6lxARxPKUcJ-8",
		// "status":true,"message":
		// "Login success!",
		// "token_expire_time":86400}

		
		//To store response from .extract().response();than use as needed
		Response response =

				given()
				      .baseUri("baseURI")
				      .header("Content-Type", "application/json")
				      .auth().preemptive().basic("demo1@codefios.com", "abc123")
				      .queryParam("account_id", "expectedAccountId")
					  .log().all().

				when()
				      .get(readOneAccountEndPoint).
				      
				then()
				      .log().all()
				      .extract().response();

		
		String actualResponseBody = response.getBody().asString();
		System.out.println("Response Body:  " + actualResponseBody);
		
		//since the above response is in string, just to fetch the access token only 
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String actualAccountName = jp.getString("account_name");
		System.out.println("Actual Account Name" + actualAccountName);
		
		
		String actualAccountDescription = jp.getString("description");
		System.out.println("Actual Account Name" + actualAccountDescription);
		
		
		String actualAccountBalance = jp.getString("balance");
		System.out.println("Actual Account Name" + actualAccountBalance);
		
		String actualAccountNumber = jp.getString("Account_number");
		System.out.println("Actual Account Name" + actualAccountNumber);
		
		
		String actualAccountPerson = jp.getString("actual_person");
		System.out.println("Actual Account Name" + actualAccountPerson);
		
		
		
		Assert.assertEquals(actualAccountName, expectedAccountName);
		Assert.assertEquals(actualAccountDescription, expectedAccountDescription);
		Assert.assertEquals(actualAccountBalance, expectedAccountBalance);
		Assert.assertEquals(actualAccountNumber, expectedAccountNumber);
		Assert.assertEquals(actualAccountPerson, expectedAccountPerson);
}
}