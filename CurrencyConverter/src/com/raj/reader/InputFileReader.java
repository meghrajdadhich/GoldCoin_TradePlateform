package com.raj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.raj.Exception.CurrencyExcpetion;

public class InputFileReader extends CSVReader{
	String fileName;
	public InputFileReader(String pfileName){
		super(pfileName);
		fileName = pfileName;
	}
	public HashMap<String, String[]> readInputData() throws CurrencyExcpetion, IOException{
		File f1 = new File(fileName);
		HashMap<String, String[]> inputList = new HashMap<String, String[]>();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(f1));
			String line="";
			if(br.readLine() != null ) {
				line = br.readLine();
				String record[] = line.split(",");
				inputList.put(record[0], record);
			}
		}catch(IOException e){
			throw new CurrencyExcpetion("Sorry we cant read file on location");
		}catch(Exception e){
			throw new CurrencyExcpetion(e.getMessage());
		}finally{
			br.close();
		}
		return inputList;
	}

}
