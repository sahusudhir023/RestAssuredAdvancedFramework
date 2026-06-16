package com.automation.api.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties;

	static {
		try {

			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/config.properties");
			
			properties = new Properties();
			properties.load(fis);
			
			fis.close();

		} catch (Exception e) {
			System.out.println("Config.properties file not upload : " + e.getMessage());
		}
	}
	
	public static String getBaseUrl() {
		return properties.getProperty("baseUrl");
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
