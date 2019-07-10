package com.db;

import java.sql.ResultSet;

import com.configData_Util.Constant;
import com.configData_Util.Util;

public class ConnectionTester {
	public static void main(String[] args) {
		String query=ContentReader.readLineByLineJava8(Constant.getDbQueriesFolderPath()+"hktAlertsCSVemailData.sql");
		
		
		 /*rs.last();
		 
		 System.out.println(rs.getRow());*/
		 
		DBManager db = new DBManager();
		ResultSet rs=db.executeQuery(query);
		db.printAllText(rs);
		
		String[] trafPer={"0618","0718","0818","0918","1018","1118"};
		System.out.println();

		for (String string : trafPer) {
			String formatted=Util.convertToString("m/d/yyyy",Util.convertToDate("mmyy", string));
			System.out.println(formatted);
			System.out.println(db.getCellText("FLUCTUATION_PERCENT", rs, "PERIOD_START_DATE=="+formatted+" 0:0:0","CURR_TO==AUD"));
		}
		
		
		
		//String data_0618_AUD = ConnectionManager.getCellText("")
		
		db.closeConnection();
		
	}
}
