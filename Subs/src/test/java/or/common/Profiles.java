package or.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.configData_Util.STATUS;
import com.customReporting.CustomReporter;
import com.driverManager.DriverFactory;
import com.seleniumExceptionHandling.SeleniumMethods;

public class Profiles {

	@FindBy(xpath="//form[@id='profile_form']//input[@name = 'name']")
	private WebElement firstName;
	
	@FindBy(xpath="//form[@id='profile_form']//input[@name = 'last_name']")
	private WebElement lastName;
	
	@FindBy(xpath="//form[@id='profile_form']//input[@name = 'unit']")
	private WebElement yourLocation;
	
	@FindBy(xpath="//input[@value = 'Update details']")
	private WebElement update_button;
		
   public void updateBasicDetails() {
	   CustomReporter.report(STATUS.PASS, "Updaing basic details");
		
	   SeleniumMethods com = new SeleniumMethods();
	   com.sendKeys(firstName, "Tester");
	   com.sendKeys(lastName, "CSS");
	   com.sendKeys(yourLocation, "456");
	   com.click(update_button);
}
   
   public Profiles() {
		
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	
}
