package smartdevice.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *  This class is base class to provide configuration details and driver and base directory
 */
public class TestBase{
	
	private static Properties properties;
	protected String baseDir=System.getProperty("user.dir");
	
	public TestBase() {
		
		try {
			properties=new Properties();
			FileInputStream fileInputStream=new FileInputStream(baseDir+"/src/main/java/smartdevice/config/config.properties");
			properties.load(fileInputStream);
		}
		catch(IOException e) {
		e.printStackTrace();
		}
		
	}
	
	public String getBaseUri() {
		String baseURI=properties.getProperty("baseURI");
		return baseURI;
	}
	

	
}