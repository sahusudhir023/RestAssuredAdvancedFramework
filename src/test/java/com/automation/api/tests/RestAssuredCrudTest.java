package com.automation.api.tests;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.api.payloads.PostPayLoad;
import com.automation.api.utilities.ExtentReportManager;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredCrudTest {
	
	int globalPostId = 5;
	
	RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    ResponseSpecification createResponseSpec;
	
	@BeforeClass
	public void setUp() {
//		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		
//		requestSpec = new RequestSpecBuilder()
//				            .setBaseUri("https://jsonplaceholder.typicode.com")
//				            .setContentType("application/json")
//				            .build();
		
		try {
			
			PrintStream logFile = new PrintStream(new FileOutputStream("logging.txt"));
			
			String baseURIFromConfig = com.automation.api.utilities.ConfigReader.getBaseUrl();
			
			requestSpec = new RequestSpecBuilder()
					            .setBaseUri(baseURIFromConfig)
					            .setContentType("application/json")
					            .addFilter(RequestLoggingFilter.logRequestTo(logFile))
					            .addFilter(ResponseLoggingFilter.logResponseTo(logFile))
					            .build();
			
		} catch (Exception e) {
			System.out.println("Log file created time issues : " + e.getMessage());
		}
		
		responseSpec = new ResponseSpecBuilder()
				             .expectStatusCode(200)
				             .build();
		
		createResponseSpec = new ResponseSpecBuilder()
				                   .expectStatusCode(201)
				                   .build();
		
	}
	
	@DataProvider(name = "postDataProvider")
	public Object[][] getPostData(){
		
		return new Object[][] {
			{"Sudhir Automation Hero", "Learning Data Driven Testing", 11},
			{"Sameer RestAssured Guide", "Mastering API Automation", 22},
			{"Java Advanced concept", "Deep dive to into Frameworks", 33}
		};
	}
	
	@Test(priority = 1)
	public void validateGetSinglePost() {
		
		
		Response res =	RestAssured.given()
						                   .spec(requestSpec)
						                   .pathParam("postId", globalPostId)
						               .when()
						                   .get("/posts/{postId}")
						               .then()
						                   .spec(responseSpec)
						                   .body("id", Matchers.equalTo(globalPostId))
						                   .body("userId", Matchers.notNullValue())
						                   .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		               	
		System.out.println("Successfully Validated GET single POST");	
	}
	
	@Test(priority = 2)
	public void createPostUsingMap() {
		
		HashMap<String, Object> myData = new HashMap<String, Object>();
		
		myData.put("title", "Sudhir Pro Automator");
		myData.put("body", "RestAssured framework learning live");
		myData.put("userId", 77);
		
		Response res = RestAssured.given()
						              .spec(requestSpec)
						              .body(myData)
						          .when()
						              .post("/posts")
						          .then()
						              .spec(createResponseSpec)
						              .body("title", Matchers.equalTo("Sudhir Pro Automator"))
						              .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		              	
		System.out.println("Successfully Created POST using HashMap");
	}
	
	@Test(priority = 3)
	public void extractDataAndAssert() {
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .pathParam("postId", globalPostId)
				                  .when()
				                      .get("/posts/{postId}")
				                  .then()
				                      .spec(responseSpec)
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		
		String actualTitle = res.path("title");
		int actualId = res.path("id");
//		
//		String actualTitle = RestAssured.given()
//				                            .spec(requestSpec)
//				                            .pathParam("postId", globalPostId)
//				                        .when()
//				                            .get("/posts/{postId}")
//				                        .then()
//				                            .spec(responseSpec)
//				                            .extract().path("title");
//		
//		int actualId = RestAssured.given()
//				                         .spec(requestSpec)
//				                         .pathParam("postId", globalPostId)
//				                     .when()
//				                         .get("/posts/{postId}")
//				                     .then()
//				                         .spec(responseSpec)
//				                         .extract().path("id");
//		
		Assert.assertEquals(actualId, globalPostId);
		Assert.assertNotNull(actualTitle);
		
		System.out.println("Successfully Extracted DATA and ASSERT");
	}
	
	
	@Test(priority = 4)
	public void testQueryParameter() {
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .queryParam("userId", 1)
				                  .when()
				                      .get("/posts")
				                  .then()
				                      .spec(responseSpec)
				                      .extract().response();
		
//		String firstTitle = RestAssured.given()
//				                           .spec(requestSpec)
//				                           .queryParam("userId", 1)
//				                        .when()
//				                           .get("/posts")
//				                        .then()
//				                           .spec(responseSpec)
//				                           .extract().path("[0].title");
		ExtentReportManager.logResponseToReport(res);
		
		String firstTitle = res.path("[0].title");
		
		Assert.assertNotNull(firstTitle);
		
		System.out.println("Query Parameter Using and get to first title : " + firstTitle);
		System.out.println("Successfully Tested Query Parameter");
	}
	
	
	@Test(priority = 5)
	public void updatePostUsingPut() {
		
		HashMap<String, Object> updateData = new HashMap<String, Object>();
		
		updateData.put("title", "Sudhir update title");
		updateData.put("body", "Framework update successfully");
		updateData.put("userId", 77);
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .pathParam("postId", globalPostId)
				                      .body(updateData)
				                  .when()
				                      .put("/posts/{postId}")
				                  .then()
				                      .spec(responseSpec)
				                      .body("title", Matchers.equalTo("Sudhir update title"))
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		
//		RestAssured.given()
//		               .spec(requestSpec)
//		               .pathParam("postId", globalPostId)
//		               .body(updateData)
//		           .when()
//		               .put("/posts/{postId}")
//		           .then()
//		               .spec(responseSpec)
//		               .body("title", Matchers.equalTo("Sudhir update title"));
		              
		System.out.println("Successfully Updated POST using PUT");
	}
	
	
	@Test(priority = 6)
	public void deletePost() {
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .pathParam("postId", globalPostId)
				                  .when()
				                      .delete("/posts/{postId}")
				                  .then()
				                      .spec(responseSpec)
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		
//		RestAssured.given()
//		               .spec(requestSpec)
//		               .pathParam("postId", globalPostId)
//		           .when()
//		               .delete("/posts/{postId}")
//		           .then()
//		               .spec(responseSpec);
		             
		System.out.println("Successfully DELETED Post");
	}
	
	
	@Test(priority = 7)
	public void createPostUsingPOJO() {
		
		PostPayLoad pojoData = new PostPayLoad("Sudhir POJO King", "Learning advanced payload creation", 99);
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .body(pojoData)
				                  .when()
				                      .post("/posts")
				                  .then()
				                      .spec(createResponseSpec)
				                      .body("title", Matchers.equalTo("Sudhir POJO King"))
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
//		RestAssured.given()
//		               .spec(requestSpec)
//		               .body(pojoData)
//		           .when()
//		               .post("/posts")
//		           .then()
//		               .spec(createResponseSpec)
//		               .body("title", Matchers.equalTo("Sudhir POJO King"));
		              	
		System.out.println("Successfully Created POST using POJO");
	}
	
	@Test(priority = 8)
	public void getPostAndConvertToObject() {
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .pathParam("postId", globalPostId)
				                  .when()
				                      .get("posts/{postId}")
				                  .then()
				                      .spec(responseSpec)
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		
		PostPayLoad responseObj = res.as(PostPayLoad.class);
		
//		PostPayLoad responseObj = RestAssured.given()
//				                                 .spec(requestSpec)
//				                                 .pathParam("postId", globalPostId)
//				                             .when()
//				                                 .get("/posts/{postId}")
//				                             .then()
//				                                 .spec(responseSpec)
//				                                 .extract().as(PostPayLoad.class);
		
		System.out.println("----POJO De-serializtion Result----");
		
		System.out.println("Fetched Title via POJO : " + responseObj.getTitle());
		System.out.println("Fetched Body vis POJO : " + responseObj.getBody());
		System.out.println("Fetched UserId via POJO : " + responseObj.getUserId());
		System.out.println("Fetched Id via POJO : " + responseObj.getId());
		
		Assert.assertEquals(responseObj.getId(), globalPostId);
		Assert.assertNotNull(responseObj.getTitle());
		
		System.out.println("Successfully GET POST and Converted to Object");
	}
	
	@Test(priority = 9)
	public void validateUsingJsonPath() {
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .pathParam("postId", globalPostId)
				                  .when()
				                      .get("posts/{postId}")
				                  .then()
				                      .spec(responseSpec)
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		
		String jsonResponse = res.asString();
		
//		String jsonResponse = RestAssured.given()
//				                             .spec(requestSpec)
//				                             .pathParam("postId", globalPostId)
//				                         .when()
//				                             .get("/posts/{postId}")
//				                         .then()
//				                             .spec(responseSpec)
//				                             .extract().asString();
		
		JsonPath jp = new JsonPath(jsonResponse);
		
		String title = jp.getString("title");
		String body = jp.getString("body");
		int userId = jp.getInt("userId");
		int iD = jp.getInt("id");
		
		System.out.println("-----Extracting via JsonPath-----");
		
		System.out.println("Extracted Title via JsonPath : " + title);
		System.out.println("Extracted Body via JsonPath : " + body);
		System.out.println("Extracted UserId via JsonPath : " + userId);
		System.out.println("Extracted Id via JsonPath : " + iD);
		
		Assert.assertEquals(iD, globalPostId);
		Assert.assertEquals(userId, 1);
		
		System.out.println("Successfully Validated using JsonPath");	
	}
	
	
	@Test(priority = 10, dataProvider = "postDataProvider")
	public void createPostDataDriven(String title, String body, int userId) {
		
		PostPayLoad pojoData = new PostPayLoad(title, body, userId);
		
		Response res = RestAssured.given()
				                      .spec(requestSpec)
				                      .body(pojoData)
				                  .when()
				                      .post("/posts")
				                  .then()
				                      .spec(createResponseSpec)
				                      .body("title", Matchers.equalTo(title))
				                      .body("body", Matchers.equalTo(body))
				                      .extract().response();
		
		ExtentReportManager.logResponseToReport(res);
		
//		RestAssured.given()
//		               .spec(requestSpec)
//		               .body(pojoData)
//		           .when()
//		               .post("/posts")
//		           .then()
//		               .spec(createResponseSpec)
//		               .body("title", Matchers.equalTo(title))
//		               .body("body", Matchers.equalTo(body));
		
		System.out.println("Successfully Created Data Driven POST for Title : " + title);
	}

}
