package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{

	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		try
		{
		logger.info("******Starting TC_001 Account Registration Test*******");
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		
		logger.info("clicked on Myaccount Link");
		
		hp.clickRegister();
		
		logger.info("clicked on Register Link");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastname(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String password=randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected Message");
		
		String confmsg=regpage.getConfirmationMsg();
		
		Assert.assertEquals(confmsg, "Your Account Has Been Created!","Confirmation message mismatch");
		
		logger.info("Test passed");
		
		}
		catch(Exception e)
		{
			logger.error("Test Failed: "+e.getMessage());
			Assert.fail("Test Failed: "+e.getMessage());
		}
		finally
		{
		logger.info("*****Finished TC_001 AccountRegistrationTest******");
		}
	}
	
	
	
}
