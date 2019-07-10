package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;

import com.seleniumExceptionHandling.CustomExceptionHandler;

public class ConnectionManager {
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://stg.subsforyou.com/adminer.php/unlimite_subsforu_staging", "unlimite_subStag","PQ&$27RWDndh");

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery("select * from Login");

			/*
			 * rs.last();
			 * 
			 * System.out.println(rs.getRow());
			 */

			int count = 1;
			while (rs.next())
				System.out.println(count++ + " : " + rs.getString(1));

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private ConnectionManager() {
	}

	static Connection con = null;

	/**
	 * Most Important! - Run this mthod when your test does not want to get any data from db 
	 * It will close the connection object, also will set null to {@link ResultSet} object  
	 * Precondition	 	: Make sure you have ConnectionManager.createConnection() method have executed
	 * Postcondition	: will close the connection object, also will set null to {@link ResultSet} object
	 */
	public static synchronized void closeConnection() {
		if (con != null) {
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				new CustomExceptionHandler(e);
			}
		}
	}

	/**
	 * It will initialise the connection object with UAT db so that we can fire query  
	 * Precondition	 	: Just make sure your username and password is functional 
	 * Postcondition	: Will init the connection object for firing up the queries
	 */
	public static synchronized void createConnection() {
		if (con == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");

				con = DriverManager.getConnection("jdbc:oracle:thin:@10.184.40.120:1521:UATNGC", "DEV_SR", "K667GMMMQQ");

			} catch (Exception e) {
				new CustomExceptionHandler(e);
				Assert.fail(e.getMessage());
			}
		}
	}

	/**
	 * It will fire the passed queryString on db and initialise the result set object, also will 
	 * 	@return {@link ResultSet} object if you want to create your code
	 * Precondition	 	: Call ConnectionManager.createConnection(); method prior to run this 
	 * Postcondition	: Will init the result set object for further use.
	 */
	public static synchronized ResultSet executeQuery(String queryString) {
		ResultSet rs=null;
		if (con == null) {
			createConnection();
		}

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(queryString.trim());
		} catch (Exception e) {
			new CustomExceptionHandler(e);
		}
		return rs;
	}

	/**
	 * It will print the result of query, in console so that you can copy it in
	 * excel for further analysis
	 * Precondition	 	: Call ConnectionManager.createConnection(); and ConnectionManager.executeQuery(); methods prior to run this 
	 * Postcondition	: Will print the content of result set object on console
	 * 
	 */
	public static synchronized void printAllText(ResultSet rs) {
		if (rs != null) {
			try {
				int count = 1;
				ResultSetMetaData metaData = rs.getMetaData();
				int columnsNumber = metaData.getColumnCount();
				
				String columnHeaders="";
				for (int i = 1; i <= columnsNumber; i++) {
					columnHeaders=columnHeaders+ "	" + metaData.getColumnLabel(i);	
				}
				System.out.println( "0" + columnHeaders);
				
				while (rs.next()){
					String rowWiseData="";
					for (int i = 1; i <= columnsNumber; i++) {
						rowWiseData=rowWiseData+ "	" + rs.getString(i);	
					}
					rowWiseData=count++ + rowWiseData;
					System.out.println(rowWiseData);
				}
			} catch (Exception e) {
				new CustomExceptionHandler(e);
			}
		}

	}

	/**
	 * Usage : ConnectionManager.getCellText("FLUCTUATION_PERCENT", rs, "PERIOD_START_DATE==6/1/2018 0:0:0","CURR_TO==AUD");
	 * returns the data present in 
	 * 
	 * 
	 * */
	public static synchronized String getCellText(String resultColName,ResultSet rs,String... commaSeparatedColumnNameAndValue) {
		String result=null;
		if (rs != null) {
			try {
				rs.beforeFirst();
				while (rs.next()){
					
					//Matching whether row has all matching column data
					boolean[] flagArr=new boolean[commaSeparatedColumnNameAndValue.length];
					for (int i=0;i<commaSeparatedColumnNameAndValue.length;i++) {
						String searchColNameVal=commaSeparatedColumnNameAndValue[i]; 
						String expectedVal=searchColNameVal.split("==")[1];
						String colName=searchColNameVal.split("==")[0];
						String actualVal=rs.getString(colName);
						if (expectedVal.equalsIgnoreCase(actualVal)) {
							flagArr[i]=true;
						}
					}
					
					//getting the required column data
					boolean mayRun=true;
					for (int i = 0; i < flagArr.length; i++) {
						if(!flagArr[i]){
							mayRun=false;
						}
					}
					if(mayRun){
						result=rs.getString(resultColName);
						break;
					}
				}
			} catch (Exception e) {
				new CustomExceptionHandler(e);
			}
		}
		return result;
	}


}