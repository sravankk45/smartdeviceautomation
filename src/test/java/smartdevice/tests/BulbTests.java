package smartdevice.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import smartdevice.base.TestBase;
import smartdevice.operations.BulbOperations;
import smartdevice.pojo.DevicePojo;

public class BulbTests extends TestBase{
	
	private BulbOperations bulbOperations;
	private Response response;
	private JsonPath jsonResponse;
	
	private String bulb1IP = "192.168.100.10";
	private String bulb2IP = "192.168.100.11";
	private int brightness = 5;
	private String color = "#00ff00";
	private String name = "smart bulb";
	private ObjectMapper mapper;
	
	private List<DevicePojo> device;
	
	
	/**
	 *  Gets the Base URI before test suite runs
	 */
	@BeforeSuite
	public void configure() {

		RestAssured.baseURI = getBaseUri();
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		bulbOperations = new BulbOperations();

	}
	
	/**
	 *  Creates bulb operations
	 */
	@BeforeMethod
	public void prepareDevice() {

		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
				
		//connects device
		response = bulbOperations.connectDevice(bulb1IP);
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);

	}
	
	@Test
	public void listBulbsTest(){
		
		response = bulbOperations.getDevices();
		jsonResponse = new JsonPath(response.asString());
		try {
			device = Arrays.asList(mapper.readValue(response.asString(), DevicePojo[].class));
		}
		catch(JsonMappingException e) {
			e.printStackTrace();
		}
		catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		
		for(DevicePojo device : device) {
			
			String resIp=device.getIp();
			Assert.assertTrue(resIp.equals(bulb1IP) || resIp.equals(bulb2IP));
		}
		
	}
	
	@Test
	public void deviceConnectTest(){
		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//connects device
		response = bulbOperations.connectDevice(bulb1IP);
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
			
	}
	
	
	@Test
	public void deviceConnectInvalidTest(){
		
		//try to connect device again which is already connected
		response = bulbOperations.connectDevice(bulb1IP);
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}
	
	@Test
	public void stateConnectedTest(){
		
		//get the state of device and verify
		response = bulbOperations.getState();
		jsonResponse = new JsonPath(response.asString());
		String ipResponse = jsonResponse.getString("ip");
		Assert.assertEquals(ipResponse, bulb1IP);
			
	}
	
	@Test
	public void stateDisconnectedTest(){
		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//get the state of device and verify
		response = bulbOperations.getState();
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}
	
	@Test
	public void brightnessConnectedTest(){
		
		//set the brightness of device
		response = bulbOperations.setBrightness(brightness);
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//get the state of device and verify
		response = bulbOperations.getState();
		jsonResponse = new JsonPath(response.asString());
		String ipResponse = jsonResponse.getString("ip");
		float resBrightness = jsonResponse.getFloat("brightness");
		Assert.assertEquals(ipResponse, bulb1IP);
		Assert.assertEquals(resBrightness, brightness);
		
	}
	
	@Test
	public void brightnessDisconnectedTest(){
		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//set the brightness of device
		response = bulbOperations.setBrightness(brightness);
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}
	
	@Test
	public void invalidBrightnessTest(){
		
		//set the brightness of device
		response = bulbOperations.setBrightness(19);
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}
	
	@Test
	public void colorConnectedTest(){
		
		//set the color of device
		response = bulbOperations.setColor(color);
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//get the state of device and verify
		response = bulbOperations.getState();
		jsonResponse = new JsonPath(response.asString());
		String ipResponse = jsonResponse.getString("ip");
		String resColor = jsonResponse.getString("color");
		Assert.assertEquals(ipResponse, bulb1IP);
		Assert.assertEquals(resColor, color);
		
	}
	
	@Test
	public void colorDisconnectedTest(){
		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//set the brightness of disconnected device and validates response
		response = bulbOperations.setColor(color);
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}
	
	@Test
	public void invalidcolorTest(){
		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//connects device
		response = bulbOperations.connectDevice(bulb1IP);
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//set the invalid color of device and validates response
		response = bulbOperations.setColor("01");
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}
	
	@Test
	public void nameConnectedTest(){
		
		//set the name of device
		response = bulbOperations.setName(name);
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//get the state of device and verify
		response = bulbOperations.getState();
		jsonResponse = new JsonPath(response.asString());
		String ipResponse = jsonResponse.getString("ip");
		String resName = jsonResponse.getString("name");
		Assert.assertEquals(ipResponse, bulb1IP);
		Assert.assertEquals(resName, name);
		
	}
	
	@Test
	public void nameDisconnectedTest(){
		// disconnect device if it is connected already
		response = bulbOperations.disconnectDevice();
		jsonResponse = new JsonPath(response.asString());
		boolean success = jsonResponse.getBoolean("success");
		Assert.assertTrue(success);
		
		//set the brightness of disconnected device and validates response
		response = bulbOperations.setName(name);
		jsonResponse = new JsonPath(response.asString());
		success = jsonResponse.getBoolean("success");
		Assert.assertFalse(success);
			
	}


}
