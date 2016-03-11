package br.com.camada2.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetFileValues {
	Map<String,String> valuelist = new HashMap<String,String>();
	InputStream is;
	Properties prop = new Properties();
	String propFileName = "config.properties";
//	String dir;
//	String name;
//	String client;
//	String server;
//	String port;
	
	public Map<String,String> getPropValues() throws IOException {
		
		try {
			is = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (is != null) {
				prop.load(is);
			} else {
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
			}
 
			valuelist.put("dir",prop.getProperty("dir"));
			valuelist.put("name",prop.getProperty("name"));
			valuelist.put("client",prop.getProperty("client"));
			valuelist.put("server",prop.getProperty("server"));
			valuelist.put("port",prop.getProperty("port"));
 
			System.out.println(valuelist);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			is.close();
		}
		return valuelist;
		
	}
	
}
