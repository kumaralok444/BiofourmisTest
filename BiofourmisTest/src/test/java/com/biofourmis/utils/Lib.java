package com.biofourmis.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Lib {
	public static String resourceLocation = System.getProperty("user.dir").toString().replace("\\", "\\\\")
			+ "\\src\\test\\resources\\";
	public static WebElement GetObject(WebDriver driver, String objName) throws Exception, NoSuchElementException {
		String identifier;
		String description;
		WebElement element = null;
		By searchBy = null;
		if (objName.equalsIgnoreCase(null) || objName.trim().equalsIgnoreCase("")) {
			throw new Exception("Object description must be provided");
		} else {
			identifier = objName.split("~")[0].trim();
			description = objName.split("~")[1].trim();
			if (identifier.equals("xpath")) {
				searchBy = By.xpath(description);
			} else if (identifier.equals("name")) {
				searchBy = By.name(description);
			} else if (identifier.equals("id")) {
				searchBy = By.id(description);
			} else if (identifier.equals("css")) {
				searchBy = By.cssSelector(description);
			} else if (identifier.equals("tagname")) {
				searchBy = By.tagName(description);
			} else if (identifier.equals("linktext")) {
				searchBy = By.linkText(description);
			} else if (identifier.equals("partiallinktext")) {
				searchBy = By.partialLinkText(description);
			} else if (identifier.equals("classname")) {
				searchBy = By.className(description);
			} else {
				throw new Exception("Object description/search type is valid");
			}
			try {
				element = driver.findElement(searchBy);
			} catch (NoSuchElementException e) {
				System.out.println("I am in no such exception catch block ");
				element = driver.findElement(searchBy);
			}

		}
		return element;
	}
	public static WebElement GetObject(WebElement ele, String objName) throws Exception, NoSuchElementException {
		String identifier;
		String description;
		WebElement element = null;
		By searchBy = null;
		if (objName.equalsIgnoreCase(null) || objName.trim().equalsIgnoreCase("")) {
			throw new Exception("Object description must be provided");
		} else {
			identifier = objName.split("~")[0].trim();
			description = objName.split("~")[1].trim();
			if (identifier.equals("xpath")) {
				searchBy = By.xpath(description);
			} else if (identifier.equals("name")) {
				searchBy = By.name(description);
			} else if (identifier.equals("id")) {
				searchBy = By.id(description);
			} else if (identifier.equals("css")) {
				searchBy = By.cssSelector(description);
			} else if (identifier.equals("tagname")) {
				searchBy = By.tagName(description);
			} else if (identifier.equals("linktext")) {
				searchBy = By.linkText(description);
			} else if (identifier.equals("partiallinktext")) {
				searchBy = By.partialLinkText(description);
			} else if (identifier.equals("classname")) {
				searchBy = By.className(description);
			} else {
				throw new Exception("Object description/search type is valid");
			}
			//checkPresenceOfElementLocatedByFluentWait(ele, identifier, description);
			//System.out.println("I am done with  Wait ");
			try {
				element = ele.findElement(searchBy);
			} catch (NoSuchElementException e) {
				System.out.println("I am in no such exception catch block ");
				//checkPresenceOfElement(ele, identifier, description);
				element = ele.findElement(searchBy);
			}

		}
		return element;
	}

	public static List<WebElement> GetObjects(WebDriver driver, String objName)
			throws Exception, NoSuchElementException {
		String identifier;
		String description;
		List<WebElement> elements = null; 
		By searchBy = null;
		if (objName.equals(null) || objName.trim().equals("")) {
			throw new Exception("Object description must be provided");
		} else {
			identifier = objName.split("~")[0].trim();
			description = objName.split("~")[1].trim();
			if (identifier.equals("xpath")) {
				searchBy = By.xpath(description);
			} else if (identifier.equals("name")) {
				searchBy = By.name(description);
			} else if (identifier.equals("id")) {
				searchBy = By.id(description);
			} else if (identifier.equals("css")) {
				searchBy = By.cssSelector(description);
			} else if (identifier.equals("tagname")) {
				searchBy = By.tagName(description);
			} else if (identifier.equals("linktext")) {
				searchBy = By.linkText(description);
			} else if (identifier.equals("partiallinktext")) {
				searchBy = By.partialLinkText(description);
			} else if (identifier.equals("classname")) {
				searchBy = By.className(description);
			} else {
				throw new Exception("Object description/search type is valid");
			}
			elements = driver.findElements(searchBy);
			return elements;
		}
	}
	
	public static List<WebElement> GetObjects(WebElement ele, String objName)
			throws Exception, NoSuchElementException {
		
		String identifier;
		String description;
		List<WebElement> elements = null; // This line was changed
		By searchBy = null;
		if (objName.equals(null) || objName.trim().equals("")) {
			throw new Exception("Object description must be provided");
		} else {
			identifier = objName.split("~")[0].trim();
			description = objName.split("~")[1].trim();
			if (identifier.equals("xpath")) {
				searchBy = By.xpath(description);
			} else if (identifier.equals("name")) {
				searchBy = By.name(description);
			} else if (identifier.equals("id")) {
				searchBy = By.id(description);
			} else if (identifier.equals("css")) {
				searchBy = By.cssSelector(description);
			} else if (identifier.equals("tagname")) {
				searchBy = By.tagName(description);
			} else if (identifier.equals("linktext")) {
				searchBy = By.linkText(description);
			} else if (identifier.equals("partiallinktext")) {
				searchBy = By.partialLinkText(description);
			} else if (identifier.equals("classname")) {
				searchBy = By.className(description);
			} else {
				throw new Exception("Object description/search type is valid");
			}
			
			elements = ele.findElements(searchBy); 
			return elements; 
		}
	}
	
	public static void pageFullyLoaded(WebDriver driver) {
		new WebDriverWait(driver, 90).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}
	
	public static HashMap<String, String> GetDataFromExcel(String sFile, String sQuery) throws Exception {
		sQuery = sQuery.replace("[", "");
		sQuery = sQuery.replace("$", "");
		sQuery = sQuery.replace("]", "");
		Fillo fillo = new Fillo();
		Recordset rs = null;
		sFile = resourceLocation + "\\" + sFile;
		System.out.println(sFile);
		HashMap<String, String> results = new HashMap<String, String>();
		String columnNames = "";
		int columnCount = 0;
		int rowCount = 0;
		if (isInvalid(sQuery)) {
			throw new Exception("Invalid query");
		}
		com.codoid.products.fillo.Connection connObj = fillo.getConnection(sFile);
		if (null != connObj) {
			try{
			rs = connObj.executeQuery(sQuery);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			String cellValue = new String("");
			ArrayList<String> columnList = rs.getFieldNames();
			columnCount = columnList.size();
			rowCount = 0;
			while (rs.next()) {
				rowCount++;
				columnNames = "";
				for (int i = 0; i < columnCount; i++) {
					String columnName = columnList.get(i);
					if (columnNames == "") {
						columnNames = columnName;
					} else {
						columnNames = columnNames + ";" + columnName;
					}
					cellValue = (String) rs.getField(columnName);
					if (cellValue == null)
						cellValue = "";
					results.put(columnName + rowCount, cellValue);
				}
			}
		}
		if (rs != null)
			rs.close();
		if (null != connObj)
			connObj.close();
		results.put("RowCount", rowCount + "");
		results.put("ColumnCount", columnCount + "");
		results.put("ColumnNames", columnNames);
		return results;
	}
	public static boolean isInvalid(String sInput) throws Exception {
		boolean isInvalid = true;
		if (sInput != null && sInput.trim().length() > 0)
			isInvalid = false;
		return isInvalid;
	}
	public static By StringtoBy(String objName) throws Exception {

        String identifier;
        String description;
        By searchBy = null;

               identifier = objName.split("~")[0].trim();
               description = objName.split("~")[1].trim();
               if (identifier.equals("xpath")) {
                     searchBy = By.xpath(description);
               } else if (identifier.equals("name")) {
                     searchBy = By.name(description);
               } else if (identifier.equals("id")) {
                     searchBy = By.id(description);
               } else if (identifier.equals("css")) {
                     searchBy = By.cssSelector(description);
               } else if (identifier.equals("tagname")) {
                     searchBy = By.tagName(description);
               } else if (identifier.equals("linktext")) {
                     searchBy = By.linkText(description);
               } else if (identifier.equals("partiallinktext")) {
                     searchBy = By.partialLinkText(description);
               } else if (identifier.equals("classname")) {
                     searchBy = By.className(description);
               } else {
                     throw new Exception("Object description/search type is valid");
               }
        
        return searchBy;   
 }
}
