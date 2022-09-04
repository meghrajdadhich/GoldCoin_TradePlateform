package com.raj;

import com.raj.processor.CurrencyCalculateProcessor;

public class Converter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String rateFile = "currency_rate.csv";
		String inputFile = "InputData.csv";
		String outputFile = "OutputData.csv";
		CurrencyCalculateProcessor processor =new  CurrencyCalculateProcessor(rateFile, inputFile);
		processor.loadRecord();
		processor.writeOutpurFile(outputFile);
	}

}
