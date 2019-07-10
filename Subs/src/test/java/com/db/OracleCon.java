package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
class OracleCon{
	public static void main(String args[]){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con=DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.221:1521:db01","ngcplus","ngcplus");

			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

			ResultSet rs=stmt.executeQuery("select * from fileinfo1");
			
			/*rs.last();
			
			System.out.println(rs.getRow());*/
			
			int count=1;
			while(rs.next())
				System.out.println(count+++" : "+rs.getString(1));

			con.close();

		}catch(Exception e){ System.out.println(e);}

	}
}