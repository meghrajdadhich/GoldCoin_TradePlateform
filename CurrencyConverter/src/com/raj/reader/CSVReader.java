package com.raj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.raj.Exception.CurrencyExcpetion;

public abstract class CSVReader {
	String fileName;
	public CSVReader(String pfileName){
		fileName = pfileName;
	}
	public abstract HashMap<String, String[]> readRateData() throws CurrencyExcpetion, IOException;

	
	public String[] getDataHeader() throws CurrencyExcpetion, IOException {
		int max = 0;
		File f1 = new File(fileName);
		String headerNames[]=null;
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(f1));
			String line="";
			if(br.readLine() != null ) {
				line = br.readLine();
				headerNames = line.split(",");
			}
		}catch(IOException e){
			throw new CurrencyExcpetion("Sorry we cant read file on location");
		}catch(Exception e){
			throw new CurrencyExcpetion(e.getMessage());
		}finally{
			br.close();
		}
		return headerNames;
	}
}
