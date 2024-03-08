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

public class DeleteOneAccount extends GenerateBearerToken {

	String baseURI;
	String deleteAccountBodyFilePath;
	String deleteOneAccountEndPoint;
	String firstAccountId;
	String readOneAccountEndPoint;
	String deleteAccountId;

	public DeleteOneAccount() {

		baseURI = ConfigReader.getProperty("baseURI");
		deleteOneAccountEndPoint = ConfigReader.getProperty("deleteOneAccountEndPoint");
		readOneAccountEndPoint = ConfigReader.getProperty("readOneAccountEndPoint");
		deleteAccountId = "117";

	}

	@Test(priority = 1)
	public void deleteOneAccount() {

		Response response =

				given()
				       .baseUri("baseURI")
				       .header("Content-Type", "application/json")
					   .header("Authorization", "Bearer " + bearerToken)
					   .queryParam("account_id", deleteAccountId)
					   .log().all().

						when().delete(deleteOneAccountEndPoint).

						then().log().all().extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status code not matching");

		String responseHeaderContentType = response.getHeader("Content-Type");
		System.out.println("Response Header Content Type:" + responseHeaderContentType);
		Assert.assertEquals(responseHeaderContentType, "application/json", "Content type not matching");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body:  " + responseBody);

		// since the above response is in string, just to fetch the access token only
		JsonPath jp = new JsonPath(responseBody);

		String message = jp.getString("message");
		System.out.println("Message" + message);
		Assert.assertEquals(message, "Account Deleted Successfully.");

	}

	@Test(priority = 2)
	public void readOneAccount() {
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
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(statusCode, 404, "Status code not matching");


		String actualResponseBody = response.getBody().asString();
		System.out.println("Response Body:  " + actualResponseBody);
		
		JsonPath jp = new JsonPath(actualResponseBody);

		String actualMessage = jp.getString("message");
		System.out.println("Actual Message" + actualMessage);

		Assert.assertEquals(actualMessage, "No Record Found");
		
	}
}
