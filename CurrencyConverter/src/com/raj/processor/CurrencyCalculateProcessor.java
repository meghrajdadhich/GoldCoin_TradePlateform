package com.raj.processor;

import java.util.HashMap;

import com.raj.reader.CSVReader;
import com.raj.reader.RateFileReader;

public class CurrencyCalculateProcessor {
	String rateFile;
	String inputFile;
	public CurrencyCalculateProcessor(String prateFile, String pinputFile){
		inputFile=pinputFile;
		rateFile=prateFile;
	}

	HashMap<String, String[]> currencyData = new HashMap<String, String[]>();
	public void loadRecord() {
		CSVReader reader = new RateFileReader(rateFile);
		currencyData=reader.readRateData();
		
	}

	public void writeOutpurFile(String outputFile) {
		// TODO Auto-generated method stub
		
	}
	
}
