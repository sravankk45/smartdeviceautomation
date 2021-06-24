# smart device automation 


## Automation test regression suite for smart device rest api

### Before each test runs below steps performed(smartdevice.tests.prepareDevice method) to avoid issues when connecting device(like connecting to already connected device)

* Disconnect the device
* Connect the device

### The below is test is implemented with steps

* Check list of devices (smartdevice.tests.listBulbsTest)
	* get the devices list
	* verify with expected ips
* Check device connection(smartdevice.tests.deviceConnectTest)
	* disonnects device if it is already connected
	* connects the device
	* verify  the response 
* Check device connection when it is already connected(smartdevice.tests.deviceConnectInvalidTest)
	* try to connect to device when it is already connected
	* validate the response
* Check the state of connected device(smartdevice.tests.stateConnectedTest)
	* get the state of device
	* verify if it has all data of device
* Check the state of disconnected connected device(smartdevice.tests.stateDisconnectedTest)
	* disconnect the device
	* get the state of device
	* verify the response
* Check the brightness of connected device(smartdevice.tests.brightnessConnectedTest)
	* set the brightness of the connected device
	* verify the response by getting the state
* Check the brightness  of disconnected connected device(smartdevice.tests.brightnessDisconnectedTest)
	* disconnect the device
	* try to set the brightness of the disconnected device
	* verify the response
* Check the brightness  with invalid brightness value(smartdevice.tests.invalidBrightnessTest)
	* try to set the brightness with invalid value other than 0-10
	* verify the response
* Check the color of connected device(smartdevice.tests.colorConnectedTest)
	* set the color of the connected device
	* verify the response by getting the state
* Check the color of disconnected connected device(smartdevice.tests.colorDisconnectedTest)
	* disconnect the device
	* try to set the color of the disconnected device
	* verify the response
* Check the color with invalid brightness value(smartdevice.tests.invalidcolorTest)
	* try to set the brightness with invalid color value 
	* verify the response
* Check the name of connected device(smartdevice.tests.nameConnectedTest)
	* set the name of the connected device
	* verify the response by getting the state
* Check the name of disconnected connected device(smartdevice.tests.nameDisconnectedTest)
	* disconnect the device
	* try to set the name to the disconnected device
	* verify the response



### Tools and Technologies used

* Java (1.8 or higher)
* TestNG
* RestAssured
* Maven
* Extent Report

* Followed Pojo object pattern to represent device data for serialization and deserialization of the request and responses

### Configuration/setup to be done to run the project

* The below details are configured in config.properies file(available in smartdeviceautomation\src\main\java\smartdevice\config\config.properties)
	* baseURI for samrt device api
* TestBase class (smartdevice.base) is base class for all tests and it provides configurations like baseURI and base directory of project to tests
* The test classe smartdevice.tests.BulbTests has all tests to be performed on api.
* DevicePojo class (device.pojo package) represents Device request and response data to be used for validations


### To Run Tests
* rightclick on testng.xml file and run as testng suite
* after tests run, open report under test-output/TestReport.html to see interactive HTML report on test execution status.
* for testng report, open test-output/index.html




