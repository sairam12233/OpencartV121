package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class)	
public void verify_loginDDT(String email,String password,String exp)
{
	logger.info("**********Starting TC003_LoginDDT Test**********");
	
	try
	{

	HomePage hp=new HomePage(driver);
	hp.clickMyAccount();
	hp.clickLogin();
	
	LoginPage lp=new LoginPage(driver);
	lp.setUsername(email);
	lp.setPassword(password);
	lp.clickLogin();
	
	MyAccountPage myacc=new MyAccountPage(driver);
	boolean targetPage=myacc.isMyAccountPageExists();
	
	if(exp.equalsIgnoreCase("Valid"))
	{
		if(targetPage==true)
		{
			myacc.clickLogout();
			Assert.assertTrue(true);
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	if(exp.equalsIgnoreCase("Invalid"))
	{
		if(targetPage==true)
		{
			myacc.clickLogout();
			Assert.assertTrue(false);
		}
		else
		{
			Assert.assertTrue(true);
		}
	}
	}
	catch(Exception e)
	{
		Assert.fail("An Exception occured"+e.getMessage());
	}
	
	logger.info("******* Finished TC003_LoginDDT Test***********");
}
	
	
	
	
}
