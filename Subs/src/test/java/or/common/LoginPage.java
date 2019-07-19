package or.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.configData_Util.Constant;
import com.configData_Util.STATUS;
import com.customReporting.CustomReporter;
import com.driverManager.DriverFactory;
import com.seleniumExceptionHandling.SeleniumMethods;
import com.xlUtil.DataTable;

public class LoginPage {

	private SeleniumMethods com;
	
	@FindBy(linkText="Login")
	private WebElement link_Login;

	@FindBy(id="firstname")
	private WebElement text_UserName;

	@FindBy(id="lastname")
	private WebElement text_Password;

	@FindBy(id="login")
	private WebElement button_Login;

	@FindBy(xpath="//a[contains(. , 'Log Out')]")
	private WebElement button_Logout;

	public static String title="User Login";
	public LoginPage(){
		com=new SeleniumMethods();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public void logout(){
		com.click(button_Logout,"Logout Link");
		
		if(com.verifyPageTitle("Subs For You")){
			CustomReporter.report_ExitCurrentNode(STATUS.PASS, "Logout succeed");
		}else{
			CustomReporter.report_ExitCurrentNode(STATUS.FAIL, "Logout failed");
		}
	}
	
	public boolean logoutThenPerformLogin(String type){
		return performLogin(type);
	}

	public boolean performLogin(String type) {
		
		
		CustomReporter.report(STATUS.INFO, "Login Process Start for user type: "+"<br/><b style='font-size: small;'>"+type+"</b>");
		
		com.click(link_Login);
		
		boolean bool=false;
		SeleniumMethods com=new SeleniumMethods();
		if(com.verifyPageTitle(title,true)){
			DataTable DataTable= new DataTable(Constant.getTestDataFilePath(), Constant.getEnvironmentInfoSheet());
			int rowCount=DataTable.getRowCount();
			int credentialsRow=-1;
			for(int row=1;row<rowCount;row++){
				String userType=DataTable.getValue( row, "user type");
				if(type.equalsIgnoreCase(userType)){
					credentialsRow=row;
					break;
				}
			}
			
			if (credentialsRow!=-1) {
				String userName=DataTable.getValue(credentialsRow,"username");
				String password=DataTable.getValue(credentialsRow,"password");
				bool=sendUserPassAndClickLogin(type,userName, password);
			} else{
				CustomReporter.report(STATUS.FAIL, "Passsed user type '"+type+"' is not present in the test data sheet "+Constant.getEnvironmentInfoSheet());
			}
		}
		
		if (!bool) {
			//Assert.fail("Login Failed");
		}
		
		return bool;
	}
	
	public static synchronized boolean sendUserPassAndClickLogin(WebElement text_UserName,WebElement text_Password,WebElement button_Login, String type,String user, String pass){
		SeleniumMethods com = new SeleniumMethods();
		com.sendKeys(text_UserName,user);
		com.sendKeys(text_Password,pass);
		com.click(button_Login);
		com.wait(10);
		return com.verifyPageTitle("Order Lunches");

	}

	public boolean sendUserPassAndClickLogin(String type,String user, String pass){
		return sendUserPassAndClickLogin(text_UserName, text_Password, button_Login, type, user, pass);
	}


}
