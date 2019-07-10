package or.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.driverManager.DriverFactory;

public class OrderLunches {

	@FindBy(partialLinkText="Profiles")
	public WebElement Profile_menu;
	
	public OrderLunches() {
		
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	
	
}
