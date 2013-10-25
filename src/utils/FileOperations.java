package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileOperations {

	
	public static Map<String, String> createEmailToPasskeyMap(String filename) throws IOException {
		
		Map<String, String> emailToPasskeyMap = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String inputLine;
		
		while(true) {
			
			inputLine = reader.readLine();
			
			if(inputLine==null || inputLine.equals(""))
				break;
			
			//Assuming the file contains data of the format "<emailID> <passkey>"
			String[] values = inputLine.split(" ");
			emailToPasskeyMap.put(values[0].trim(), values[1].trim());
		}
		
		reader.close();
		return emailToPasskeyMap;
	}
	
	public static List<String> createCandidateList(String filename) throws IOException {
	
		List<String> candidateList = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
	
		String inputLine;
		
		while(true) {
			inputLine = reader.readLine();
			
			if(inputLine==null || inputLine.equals(""))
				break;
		
			//Assuming the file contains one candidate per line
			candidateList.add(inputLine.trim());
		}
		
		reader.close();
		return candidateList;
	}
}
