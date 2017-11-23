package com.arogya.filereader.main;


import org.apache.commons.io.FilenameUtils;

import com.arogya.filereader.arogyastage.ArogyaStage;
import com.arogya.filereader.dao.ArogyaDBUtil;
import com.arogya.filereader.dao.ArogyaStageDAO;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class ArogyaFileReader {
	
    static File directory = null; 
	static Connection dbConnection = null;
	static PreparedStatement preparedStatement1 = null;
	static PreparedStatement preparedStatement = null;
	static ResultSet rs = null;
	
	public static ArogyaStageDAO arogyaReadFiles;
	public static ArogyaStage arogyaStageRec;
	public static ArogyaDBUtil arogyaDButil;
	

	public static void main(String[] args) {
		
		arogyaReadFiles = new ArogyaStageDAO();
		arogyaStageRec = new ArogyaStage();
		
		if(args.length == 0)
	    {
	        System.out.println("Directory Location has not been provided");
	        directory = new File("/home/hduser/Downloads/yogesh_nov17/new2");
	       // System.exit(0);
	    }

		else
		{
		String directoryLoc = args[0];
		
		directory = new File(directoryLoc);

		}
	
		try {

			insertRecordIntoTable();
			
			arogyaDButil.close(rs);	
			arogyaDButil.close(preparedStatement1);
			arogyaDButil.close(dbConnection);

		} catch (SQLException e) { System.out.println(e.getMessage()); 	}

	}

	private static void insertRecordIntoTable() throws SQLException {
		
		dbConnection = arogyaReadFiles.getConnection();
		
		 String query = "select * from ArogyaStage where File_Location = ? " ;
		 	        //    + "and file_timestamp = ?";
		 
		 String insertTableSQL = "INSERT INTO ArogyaStage"
				+ "(CUSTOMER_ID, FILE_LOCATION, PROCESSED, TRANSMITTED, File_TIMESTAMP, Error_Message) VALUES"
				+ "(?,?,?,?,?, ?)";

		try {
						
			String s[] = directory.list();
			System.out.println("total files are " + s.length);

			File[] fList = directory.listFiles();
		      
		      for (File file : fList){
		      
		    	  if (!file.isDirectory()){
		    		  
		              System.out.println(file.getName());
		           
		              if( FilenameUtils.isExtension(file.getName().toUpperCase(),"LOG")) {
 
		              String fname = file.getName();
		              
		              StringTokenizer tok = new StringTokenizer(file.getName(), "_ .");
		              String custid = tok.nextToken();
		              String timestamp = tok.nextToken();
		           
		  //            System.out.println(timestamp);
		              
		              DateFormat formatter;
		              formatter = new SimpleDateFormat("yyyymmddhhmmss");
		              Date date = (Date) formatter.parse(timestamp);
		              
		              Timestamp timeStampDate = new Timestamp(date.getTime());

		    //          System.out.println(timeStampDate);
		              
		              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		      //        System.out.println(sdf.format(timeStampDate)); //this will print without ms
		              //int customer = Integer.parseInt(custid);
		              
		              preparedStatement1 = dbConnection.prepareStatement(query);
		              
		          	  String fileloc = directory+"/"+fname;
		          	  
		              System.out.println("custid " + custid);
		              arogyaStageRec.setCustomer_Id(custid);
		              //arogyaStageRec.setFile_Timestamp(sdf.format(timeStampDate));
		              arogyaStageRec.setFile_Timestamp(timeStampDate);
		              
		              preparedStatement1.setString(1, fileloc);
		              //preparedStatement1.setString(2, sdf.format(arogyaStageRec.getFile_Timestamp()));
		              
		              //ResultSet rs = preparedStatement1.executeQuery();
		              
		              rs = preparedStatement1.executeQuery();
		              //preparedStatement1.executeUpdate();
		            
		              if (!rs.next() ) {
		              
		                  char processedflag = 'Y';
		                  char transmittedflag = 'N';
		                  String errorMessage = "Unprocessed";
		                  
					      System.out.println("Inserting Record to table! " + insertTableSQL);
					      
					     
		            	  preparedStatement = dbConnection.prepareStatement(insertTableSQL);
		            	 
		            	  String fileloc1 = directory+"/"+fname;
		            	  
		            	  arogyaStageRec = new ArogyaStage(custid,timeStampDate , 
		            			               fileloc1, processedflag,transmittedflag,errorMessage );
		            	  
		            	  preparedStatement.setString(1, arogyaStageRec.getCustomer_Id());
		            	  preparedStatement.setString(2, arogyaStageRec.getFile_Location());
		            	  preparedStatement.setString(3, String.valueOf(arogyaStageRec.getProcessed()));
		            	  preparedStatement.setString(4, String.valueOf(arogyaStageRec.getTransmitted()));
		            	  preparedStatement.setString(5, sdf.format(arogyaStageRec.getFile_Timestamp()));
		            	  preparedStatement.setString(6, arogyaStageRec.getErrorMessage());
			
			
		            	  //preparedStatement.setTimestamp(4, getCurrentTimeStamp());

		            	  // execute insert SQL stetement
		            	  preparedStatement.executeUpdate();
			

		            	  System.out.println("Record is inserted into table!");
			       
			      
		              }
		              else {
		            	  System.out.println("Record is already present  into DBUSER table!");
		              }
		            
		             } 
		          }
		      }

		} catch (SQLException | ParseException e) {

			System.out.println(e.getMessage());

		} 
	}

	
}