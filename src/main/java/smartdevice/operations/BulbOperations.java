package smartdevice.operations;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class BulbOperations{
	
	public Response getDevices() {
		
		return given()
				  .when()
				  .get("/devices").then()
				  .extract().response();
	}
	
	public Response connectDevice(String ip) {
		
		return given()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.formParam("ip",ip)
				.when()
				.post("/connect").then()
				.extract().response();
	}
	
	public Response getState() {
		
		return given()
				  .header("content-type","application/json")
				  .when()
				  .get("/state").then()
				  .extract().response();
	}
	
	public Response setBrightness(int brightness) {
		
		return given()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.formParam("brightness",brightness)
				.when()
				.post("/brightness").then()
				.extract().response();
	}
	
	public Response setColor(String color) {
		
		return given()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.formParam("color", color)
				.when()
				.post("/color").then()
				.extract().response();
	}
	
	public Response setName(String name) {
		
		return given()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.formParam("name", name)
				.when()
				.post("/name").then()
				.extract().response();
	}
	
	public Response disconnectDevice() {
		
		return given()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.when()
				.post("/disconnect").then()
				.extract().response();
	}
	

}
