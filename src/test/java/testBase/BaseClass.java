package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {

public static WebDriver driver;
public Logger logger;
public Properties p;

@BeforeClass(groups= {"Sanity","Master","Regression"})
@Parameters({"os","browser"})
public void setup(String os,String br) throws IOException
{
logger=LogManager.getLogger(this.getClass());

FileReader file=new FileReader(".//src//test//resources//config.properties");
p=new Properties();
p.load(file);

if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
{
	DesiredCapabilities capabilities=new DesiredCapabilities();
	String huburl="http://192.168.0.104:4444/wd/hub";
	
	switch(os.toLowerCase())
	{
	case "windows": capabilities.setPlatform(Platform.WIN11);break;
	case "mac": capabilities.setPlatform(Platform.MAC);break;
	default: System.out.println("Invalid platfrom");return;
	}
	
	switch(br.toLowerCase())
	{
	case "chrome": capabilities.setBrowserName("chrome");break;
	case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
	default:System.out.println("Invalid browser");return;
	}
	
	driver=new RemoteWebDriver(new URL(huburl),capabilities);
	
}

if(p.getProperty("execution_env").equalsIgnoreCase("local"))
{
	
switch(br.toLowerCase())
{
case "chrome":driver=new ChromeDriver();break;
case "firefox":driver=new FirefoxDriver();break;
case "edge":driver=new EdgeDriver();break;
default:System.out.println("Invalid browser");return;
}

}

driver.manage().deleteAllCookies();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

driver.get(p.getProperty("appURL2"));
driver.manage().window().maximize();
	
}

@AfterClass(groups= {"Sanity","Master","Regression"})
public void tearDown()
{
	driver.quit();
}


public String randomString()
{
	String generatedString=RandomStringUtils.randomAlphabetic(5);
	return generatedString;
}

public String randomNumber()
{
	String generatedString=RandomStringUtils.randomAlphanumeric(10);
	return generatedString;
}


public String randomAlphaNumeric()
{
	String str=RandomStringUtils.randomAlphabetic(3);
	String num=RandomStringUtils.randomAlphanumeric(3);
	return (str+num);
}

public String captureScreen(String tname) throws IOException
{
	String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	
	TakesScreenshot takesScreenshot=(TakesScreenshot)driver;
	File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
	
	String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
	File targetFile=new File(targetFilePath);
	
	sourceFile.renameTo(targetFile);
	
	return targetFilePath;
	
}




}
