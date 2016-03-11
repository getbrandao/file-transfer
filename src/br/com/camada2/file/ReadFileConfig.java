package br.com.camada2.file;

import java.io.IOException;
import java.util.Map;

public class ReadFileConfig {
	static GetFileValues gfv = new GetFileValues();
	
	public static void viewValues() throws IOException{
		Map<String, String> valuelist = gfv.getPropValues();
		System.out.println(valuelist.get("dir"));
		System.out.println(valuelist.get("name"));
		System.out.println(valuelist.get("client"));
		System.out.println(valuelist.get("server"));
		System.out.println(valuelist.get("port"));
		

	}

}
