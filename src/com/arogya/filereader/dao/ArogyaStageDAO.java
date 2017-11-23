package com.arogya.filereader.dao;

import java.sql.Connection;
import java.sql.Statement;


public class ArogyaStageDAO {
	

	private Connection connection;
	private Statement statement;
	
	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	
	public ArogyaStageDAO(){
		connection = ArogyaConnectionFactory.getConnection();
	}
	


}
