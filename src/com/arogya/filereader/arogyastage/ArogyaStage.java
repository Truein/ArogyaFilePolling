package com.arogya.filereader.arogyastage;

import java.sql.Timestamp;

public class ArogyaStage {

	private String Customer_Id;
	private Timestamp  File_Timestamp; 
	private String File_Location;
	private char Processed;
	private char Transmitted;
	private String errorMessage;

	public ArogyaStage(){}

	
	
	
	public ArogyaStage(String customer_Id, Timestamp file_Timestamp, String file_Location, char processed,
			char transmitted, String errorMessage) {
		super();
		Customer_Id = customer_Id;
		File_Timestamp = file_Timestamp;
		File_Location = file_Location;
		Processed = processed;
		Transmitted = transmitted;
		this.errorMessage = errorMessage;
	}




	public String getCustomer_Id() {
		return Customer_Id;
	}

	public void setCustomer_Id(String customer_Id) {
		Customer_Id = customer_Id;
	}

	public Timestamp getFile_Timestamp() {
		return File_Timestamp;
	}

	public void setFile_Timestamp(Timestamp file_Timestamp) {
		File_Timestamp = file_Timestamp;
	}

	public char getProcessed() {
		return Processed;
	}

	public void setProcessed(char processedflag) {
		Processed = processedflag;
	}

	public char getTransmitted() {
		return Transmitted;
	}

	public void setTransmitted(char transmitted) {
		Transmitted = transmitted;
	}

	public String getFile_Location() {
		return File_Location;
	}

	public void setFile_Location(String file_Location) {
		File_Location = file_Location;
	}




	public String getErrorMessage() {
		return errorMessage;
	}




	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	
}
