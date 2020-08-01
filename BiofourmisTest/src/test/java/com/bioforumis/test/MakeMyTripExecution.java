package com.bioforumis.test;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.biofourmis.pages.MakeMyTrip;
import com.biofourmis.utils.Lib;
import com.biofourmis.utils.Reportsam;

public class MakeMyTripExecution extends Reportsam {
	WebDriver driver;
	String dataFiles="TestData.xlsx";
	@Parameters ("BrowserType")
	@BeforeTest
	public void invokeBrowser(String browser) {
		String sUserDir = System.getProperty("user.dir").toString();
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",sUserDir + "\\drivers\\chromedriver.exe"); 
			driver=new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")){
			System.out.println("Firfox browser");
			System.setProperty("webdriver.gecko.driver",sUserDir + "\\drivers\\geckodriver.exe"); 
			driver=new FirefoxDriver();
			}
		else { 
			System.err.println("Enter Valid browser name in testng xml file, Valid values are:-chrome,firefox");
			System.exit(1); 
			} 
		startReport("MakeMyTrip");
		driver.manage().window().maximize();
		
	}
	
	@Test(enabled=true)
	public void scenario2() throws Exception {
		startTestcase("TestCaseNo:Scenario2", "Description: Make My Trip validating Filters for Indigo and NonStop ","Web Browser");
		driver.get("https://www.makemytrip.com/");
		Lib.pageFullyLoaded(driver);
		
		reportStep(driver, "MakeMyTrip Page open", "Pass",true);
		try {
		HashMap<String, String> myResults = new HashMap<String, String>();
		HashMap<String, String> dataSet = new HashMap<String, String>();
		myResults = Lib.GetDataFromExcel(dataFiles,"SELECT FromPlace,ToPlace,DepartureDate FROM MakeMyTrip where TestID='TC01'");
		dataSet.put("From", myResults.get("FROMPLACE1").trim());
		dataSet.put("To", myResults.get("TOPLACE1").trim());
		dataSet.put("DepartureDate", myResults.get("DEPARTUREDATE1").trim());
		MakeMyTrip mmt=new MakeMyTrip();
		mmt.closePopup(driver);
		String[] status=mmt.searchFlight(driver, dataSet);
		if(status[0].equalsIgnoreCase("Pass")) {
			mmt.filter(driver);
		}
		}
		catch(Exception e) {
			
		}
		finally {
			endTestcase();
		}
		
	}
	
	@Test
	public void scenario3() throws Exception {
		startTestcase("TestCaseNo:Scenario3", "Description: validating Filters for deals and searching flight ","Web Browser");
		driver.get("https://www.makemytrip.com/");
		Lib.pageFullyLoaded(driver);
		reportStep(driver, "MakeMyTrip Page open", "Pass",true);
		try {
		HashMap<String, String> myResults = new HashMap<String, String>();
		HashMap<String, String> dataSet = new HashMap<String, String>();
		myResults = Lib.GetDataFromExcel(dataFiles,"SELECT FromPlace,ToPlace,DepartureDate FROM MakeMyTrip where TestID='TC02'");
		dataSet.put("From", myResults.get("FROMPLACE1").trim());
		dataSet.put("To", myResults.get("TOPLACE1").trim());
		dataSet.put("DepartureDate", myResults.get("DEPARTUREDATE1").trim());
		MakeMyTrip mmt=new MakeMyTrip();
		//mmt.closePopup(driver);
		driver=mmt.goToDealsPage(driver);
		System.out.println(driver.getTitle());
		mmt.searchFlightInDealsPage(driver, dataSet);
		}
		catch(Exception e) {
			
		}
		finally {
			endTestcase();
		}
		
	}
	
	@AfterTest
	public void afterTest() {
		endReport();
		driver.quit();
	}
}
