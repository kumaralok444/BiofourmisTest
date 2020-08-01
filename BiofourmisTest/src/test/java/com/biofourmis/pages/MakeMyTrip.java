package com.biofourmis.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.biofourmis.utils.Lib;
import com.biofourmis.utils.OREP;
import com.biofourmis.utils.Reportsam;

public class MakeMyTrip extends Reportsam{
	
	
	public String[] searchFlight(WebDriver driver,HashMap<String, String> dataSet) throws Exception {
		WebDriverWait wait=new WebDriverWait(driver,60);
		String[] status=new String[2];
		Actions ac=new Actions(driver);
		//From City
		WebElement we=wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.MMTElements.fromCity)));
		ac.moveToElement(we).click().build().perform();
		we.sendKeys(dataSet.get("From"));
		Thread.sleep(3000);
		we=wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.MMTElements.firstElementInDropDown)));
		ac.moveToElement(we).click().build().perform();
		
		//To City
		we=Lib.GetObject(driver, OREP.MMTElements.toCity);
		we.sendKeys(dataSet.get("To"));
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.MMTElements.firstElementInDropDown))).click();
		reportStep(driver, "From Airport and To Airport has been entered", "Pass",true);
		we.sendKeys(Keys.TAB);
		
		//Departuredate
		driver.findElement(By.xpath("//div[@class='dateInnerCell']//p[text()='"+dataSet.get("DepartureDate")+"']")).click();
		
		//Search button click
		Lib.GetObject(driver, OREP.MMTElements.searchBtn).click();
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.MMTElements.deptCity)));
			reportStep(driver, "Search flight Success", "Pass",true);
			status[0]="Pass";
		}
		catch(Exception e){
			reportStep(driver, "Search flight not Success", "Fail",true);
			status[0]="Fail";
			status[1]="Search flight operation not success";
		}
		return status;
	}
	
	public void closePopup(WebDriver driver) {
		try {
			WebDriverWait wait=new WebDriverWait(driver,60);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Lib.StringtoBy(OREP.MMTElements.notificationFrame)));
			reportStep(driver, "Notification pop up displayed and closing it", "Pass",true);
			Lib.GetObject(driver, OREP.MMTElements.closeIconPopup).click();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void filter(WebDriver driver) throws NoSuchElementException, Exception {
		
		//Price filter
		try {
			Lib.GetObject(driver, OREP.MMTElements.priceSortDesc);
			reportStep(driver, "Price is already in descending order", "Pass",true);
		}
		catch(NoSuchElementException e) {
			try {
				Lib.GetObject(driver, OREP.MMTElements.priceSortAsc).click();
				reportStep(driver, "Clicked for pricing in descending order", "Pass",true);
			} catch (NoSuchElementException e1) {
				e1.printStackTrace();
				reportStep(driver, e1.getMessage(), "Fail",true);
			} catch (Exception e1) {
				e1.printStackTrace();
				reportStep(driver, e1.getMessage(), "Fail",true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportStep(driver, e.getMessage(), "Fail",true);
		}
		//Indigo check box
		Lib.GetObject(driver, OREP.MMTElements.indiGoCheckBox).click();
		reportStep(driver, "Indigo Check box clicked", "Pass",true);
		
		//No stop Checkbox
		Lib.GetObject(driver, OREP.MMTElements.noStopCheckbox).click();
		reportStep(driver, "Nonstop Check box clicked", "Pass",true);
		List<WebElement> flights=Lib.GetObjects(driver, OREP.MMTElements.filterRecord);
		reportStep(driver, "Number of Flight  is:- "+flights.size(), "Pass",true);
		
		System.out.println("Number of Flight is:- "+flights.size());
	}
	
	
	public WebDriver goToDealsPage(WebDriver driver) throws NoSuchElementException, Exception {
		WebDriverWait wait=new WebDriverWait(driver,60);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.MMTElements.loginCreateAccount))).click();
		WebElement we=Lib.GetObject(driver, OREP.MMTElements.moreItem);
		Actions act=new Actions(driver);
		act.moveToElement(we).build().perform();
		reportStep(driver, "Hover more Item, clicking Deals link", "Pass",true);
		Lib.GetObject(driver, OREP.MMTElements.deallink).click();
		Lib.pageFullyLoaded(driver);
		reportStep(driver, "Clicked On Deals page link", "Pass",true);
		Set<String> ids=driver.getWindowHandles();
		for(String id:ids) {
			driver.switchTo().window(id);
		}
		return driver;
	}
	
	public void searchFlightInDealsPage(WebDriver driver,HashMap<String,String> dataSet) throws Exception {
		WebDriverWait wait=new WebDriverWait(driver,60);
		Actions ac=new Actions(driver);
		//From City
		WebElement we=wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.dealpage.fromCity)));
		we.sendKeys(dataSet.get("From"));
		Thread.sleep(3000);
		
		//toCity
		we=Lib.GetObject(driver, OREP.dealpage.toCity);
		we.sendKeys(dataSet.get("To"));
		Thread.sleep(3000);
		we.sendKeys(Keys.TAB);
		
		driver.findElement(By.xpath("//a[text()='"+dataSet.get("DepartureDate")+"']")).click();
		reportStep(driver, "From Airport, To Airport and Date has been entered", "Pass",true);
		
		Lib.GetObject(driver, OREP.dealpage.searchBtn).click();
		reportStep(driver, "Clicked on search button", "Pass",true);
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Lib.StringtoBy(OREP.MMTElements.deptCity)));
			reportStep(driver, "Search flight Success", "Pass",true);
			Lib.pageFullyLoaded(driver);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			List<WebElement> flights=Lib.GetObjects(driver, OREP.MMTElements.filterRecord);
			reportStep(driver, "Number of Flight  is:- "+flights.size(), "Pass",true);
			
			System.out.println("Number of Flight is:- "+flights.size());
			
		}
		catch(Exception e){
			reportStep(driver, "Search flight not Success", "Fail",true);
			
		}
	}
}
