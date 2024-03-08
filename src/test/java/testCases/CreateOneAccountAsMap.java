package testCases;

import util.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;



import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateOneAccountAsMap extends GenerateBearerToken {

	String baseURI;
	String createOneAccountEndPoint;
	String createAccountBodyFilePath;
	String readAllAccountEndPoint;
	String firstAccountId;
	String readOneAccountEndPoint;
	//Types of Map - HashMap, TreeMap, LinkedHashMap - k=key, v=value
	Map<String,String> createPayloadMap;

	public CreateOneAccountAsMap() {

		baseURI = ConfigReader.getProperty("baseURI");
		createOneAccountEndPoint = ConfigReader.getProperty("createOneAccountEndPoint");
		//createAccountBodyFilePath = "src/main/java/data/createAccountBody.json";
		readAllAccountEndPoint = ConfigReader.getProperty("readAllAccountEndPoint");
		readOneAccountEndPoint = ConfigReader.getProperty("readOneAccountEndPoint");
		createPayloadMap = new HashMap<String,String>();
		

	}
	
	public Map<String, String> createAccountBodyMap(){
		
		createPayloadMap.put("account name", "TechFios Account 222");
		createPayloadMap.put("description", "Test Description 2");
		createPayloadMap.put("balance", "200.33");
		createPayloadMap.put("account number", "123456789");
		createPayloadMap.put("contact person", "Myself");
		return createPayloadMap;
		
		
	}

	@Test(priority=1)
	public void createOneAccount() {

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
				      .body(createAccountBodyMap())
					  .log().all().

				when()
				      .post(createOneAccountEndPoint).
				      
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
		Assert.assertEquals(message, "Account Created Successfully");
		
	}
	@Test(priority=2)
	public void readAllAccount() {

		// given : All Input Details -> (baseURI, Headers, Authorization, Payload/body,
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
					  .log().all().

				when()
				      .get(readAllAccountEndPoint).
				      
				then()
				      .log().all()
				      .extract().response();
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:  " + responseBody);
		
		//since the above response is in string, just to fetch the access token only 
		JsonPath jp = new JsonPath(responseBody);
		
		firstAccountId = jp.getString("record[0].account_id");
		System.out.println("First Account ID" + firstAccountId);
		
}

	@Test (priority=3)
	public void readOneAccount() {

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
				      .queryParam("account_id", "firstAccountId")
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
		
		//File expectedResponseBody = new File(createAccountBodyFilePath);
		//JsonPath jp2 = new JsonPath(expectedResponseBody);
		
		String expectedAccountName = createAccountBodyMap().get("account_name");
		System.out.println("Expected Account Name" + expectedAccountName);
		
		
		String expectedAccountDescription = createAccountBodyMap().get("description");
		System.out.println("Expected Account Name" + actualAccountDescription);
		
		
		String expectedAccountBalance = createAccountBodyMap().get("balance");
		System.out.println("Expected Account Name" + expectedAccountBalance);
		
		String expectedAccountNumber = createAccountBodyMap().get("account_number");
		System.out.println("Expected Account Name" + expectedAccountNumber);
		
		
		String expectedAccountPerson = createAccountBodyMap().get("contact_person");
		System.out.println("Expected Account Name" + expectedAccountPerson);
		
		Assert.assertEquals(actualAccountName, expectedAccountName);
		Assert.assertEquals(actualAccountDescription, expectedAccountDescription);
		Assert.assertEquals(actualAccountBalance, expectedAccountBalance);
		Assert.assertEquals(actualAccountNumber, expectedAccountNumber);
		Assert.assertEquals(actualAccountPerson, expectedAccountPerson);
}
}