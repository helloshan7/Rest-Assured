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

public class ReadAllAccounts extends GenerateBearerToken{

	String baseURI;
	String readAllAccountEndPoint;
	String firstAccountId;
	

	public ReadAllAccounts() {

		baseURI = ConfigReader.getProperty("baseURI");
		readAllAccountEndPoint = ConfigReader.getProperty("readAllAccountEndPoint");
		

	}

	@Test
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

		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status code not matching");

		String responseHeaderContentType = response.getHeader("Content-Type");
		System.out.println("Response Header Content Type:" + responseHeaderContentType);
		Assert.assertEquals(responseHeaderContentType, "application/json", "Content type not matching");

		long responseTimeInMilliSeconds = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time In MilliSeconds" + responseTimeInMilliSeconds);

		if (responseTimeInMilliSeconds < 2000) {

			System.out.println("Response time within range");

		} else {
			System.out.println("Response time out of range");
		}
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:  " + responseBody);
		
		//since the above response is in string, just to fetch the access token only 
		JsonPath jp = new JsonPath(responseBody);
		
		firstAccountId = jp.getString("record[0].account_id");
		System.out.println("First Account ID" + firstAccountId);
		
		if( firstAccountId !=null) {
			
		System.out.println("First Account Id is not NULL");
		}else {
			System.out.println("First Account Id is NULL");
		}
		
		

	}

}
