package com.biofourmis.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reportsam {
	public static  ExtentTest test;
	public static  ExtentTest parentTest;
	public static ExtentTest childTest;
	public static ExtentReports report;
	public String TestcaseName, TestDescription, Category, Author;
	public static String sharepathlocation = System.getProperty("user.dir")+ "/test-output/Results";
	
	public ExtentReports startReport(String filename) {
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
		String path=sharepathlocation+"\\"+timeStamp;
		File opdir=new File(path);
		if(!opdir.exists()){
			try{
				opdir.mkdir();
			}
			catch(Exception e){
				System.out.println("Unable to create output directory");
			}
		}
		timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
		report= new ExtentReports(path+"\\"+filename+timeStamp+".html", true);
		report.loadConfig(new File(System.getProperty("user.dir").toString().replace("\\", "\\\\")+ "\\config\\extent-config.xml"));
		return report;
	}

	public ExtentTest startTestcase(String TestcaseName, String TestcaseDescription,String categories ) {
		test= report.startTest(TestcaseName, TestcaseDescription).assignCategory(categories);
		return test;
	}

	public static void reportStep(WebDriver driver, String desc,String status, boolean bsnap){
		long snapNumber = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		if(bsnap && !status.equalsIgnoreCase("INFO")) {
			
			try {
				TakesScreenshot schot = ((TakesScreenshot)driver);
				FileUtils.copyFile(schot.getScreenshotAs(OutputType.FILE),new File(sharepathlocation+"\\snaps\\" +snapNumber+ ".jpg"));
			} catch (WebDriverException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//desc = desc + test.addScreenCapture(System.getProperty("user.dir").toString().replace("\\", "\\\\")+ "\\snaps\\" +snapNumber+".jpg");
			desc = desc + test.addScreenCapture(sharepathlocation+"\\snaps\\" +snapNumber+".jpg");
		}
			if(status.equalsIgnoreCase("PASS")) {
				test.log(LogStatus.PASS, desc);
			}else if(status.equalsIgnoreCase("FAIL")) {
				test.log(LogStatus.FAIL, desc);
			}else if(status.equalsIgnoreCase("WARN")) {
				test.log(LogStatus.WARNING, desc);
			}else if(status.equalsIgnoreCase("INFO")) {
				test.log(LogStatus.INFO, desc);
			}
		
	}

	public static void reportStep(WebDriver driver,String desc,String status) throws Exception {
		reportStep(driver,desc,status, true);
	}

	public void endTestcase() {
		report.endTest(test);
	}

	public void endReport() {
		report.flush();
	}



}
