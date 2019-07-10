package tests;

import org.testng.annotations.Test;

import com.configData_Util.STATUS;
import com.customReporting.CustomReporter;
import com.seleniumExceptionHandling.SeleniumMethods;

import or.common.LoginPage;
import or.common.OrderLunches;
import or.common.Profiles;

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
		
		Profiles profiles = new Profiles();
		profiles.updateBasicDetails();
		com.wait(10);
		
	}
	



}
