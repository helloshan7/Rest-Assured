package util;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties;

	static {

		properties = new Properties();
		try {

			FileInputStream input = new FileInputStream("src/main/java/util/config.properties");
			properties.load(input);
		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static String getProperty(String key) {

		return properties.getProperty(key);

	}
}

//		properties = new Properties();
//		
//		try {
//			FileInputStream input = new FileInputStream("src\\main\\java\\util\\ConfigReader.java");
//			properties.load(input);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
