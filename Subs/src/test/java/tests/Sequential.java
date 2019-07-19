package tests;

import org.testng.annotations.Test;

import com.configData_Util.Constant;
import com.configData_Util.STATUS;
import com.customReporting.CustomReporter;
import com.seleniumExceptionHandling.SeleniumMethods;
import com.xlUtil.DataTable;

import or.common.LoginPage;
import or.ordernow.OrderLunches;
import or.profiles.Profile;

public class Sequential {

	@Test(priority = 1, description = "Profile update")
	public void Profile_update() {
		LoginPage log = new LoginPage();
		
		
		log.performLogin("ST");
		
		CustomReporter.createNode("Updateing details");
		
		CustomReporter.report(STATUS.PASS, "Clicking on profile menu");
		OrderLunches p_update = new OrderLunches();
		SeleniumMethods com = new SeleniumMethods();
		
		com.waitForElementTobe_Clickable(p_update.Profile_menu);
		com.click(p_update.Profile_menu);
		
		Profile profiles = new Profile();
		
		DataTable excel = new DataTable(Constant.getTestDataFilePath(), "profile");
		
		profiles.updateBasicDetails(excel.getValue(1, "FN"), excel.getValue(1, "LN"), excel.getValue(1, "YL"));
		com.wait(10);
		
		log.logout();
		
	}
	
	@Test(priority = 1, description = "Child Create Update Delete")
	public void child_Create_Update_Delete() {
		LoginPage log = new LoginPage();
		
		
		log.performLogin("ST");
		
		CustomReporter.createNode("Updateing details");
		
		CustomReporter.report(STATUS.PASS, "Clicking on profile menu");
		OrderLunches p_update = new OrderLunches();
		SeleniumMethods com = new SeleniumMethods();
		
		com.waitForElementTobe_Clickable(p_update.Profile_menu);
		com.click(p_update.Profile_menu);
		
		Profile profiles = new Profile();
		profiles.createChild();
		//profiles.updateChild();
		//profiles.deleteChild();
		com.wait(10);
		
		log.logout();
		
	}
	
	

	@Test(priority = 1, description = "Create Lunch Pack")
	public void orderNow() {
		LoginPage log = new LoginPage();
		
		
		log.performLogin("ST");
	
		CustomReporter.createNode("Select Child");
		
		CustomReporter.report(STATUS.PASS, "Select Child");
		OrderLunches p_update = new OrderLunches();
		SeleniumMethods com = new SeleniumMethods();
		
		p_update.createLunchOrder();
		com.wait(10);
		
		log.logout();
		 
		
	}
	

}
