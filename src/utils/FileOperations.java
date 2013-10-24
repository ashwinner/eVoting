package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileOperations {

	
	public static Map<String, String> createEmailToPasskeyMap(String filename) throws IOException {
		Map<String, String> emailToPasskeyMap = new HashMap<String, String>();
		File inputFile = new File(filename);
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		String line;
		while((line=in.readLine())!=null) {
			//Assuming the file contains data of the format "<emailID> <passkey>"
			String[] values = line.split(" ");
			emailToPasskeyMap.put(values[0].trim(), values[1].trim());
		}
		in.close();
		return emailToPasskeyMap;
	}
}