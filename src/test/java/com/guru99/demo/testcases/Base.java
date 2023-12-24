package com.guru99.demo.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class Base {

	public static WebDriver driver;

	@Parameters({ "browser", "url" })
	@BeforeMethod
	public void Setup(String browser, String url) throws IOException {
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.navigate().to(url);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.navigate().to(url);
			driver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
			driver.navigate().to(url);
			driver.manage().window().maximize();

		} else {
			System.out.println("Script supported for Chrome, Firefox and edge drivers");
		}

	}

	@AfterMethod
	public void TearDown() {
		driver.quit();
	}

	@DataProvider(name = "credentials")

	public Object[][] credentialsDataProvider() throws EncryptedDocumentException, IOException {
		String excelFilePath = ".\\src\\test\\resources\\Login-Test_Data.xlsx";

		List<String[]> credList = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(excelFilePath);

				Workbook workbook = WorkbookFactory.create(fis)) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell usernamecell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				Cell passwordCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String username = usernamecell.getStringCellValue();
				String password = passwordCell.getStringCellValue();
				credList.add(new String[] { username, password });
			}

		}
		Object[][] credentialsArray = new Object[credList.size()][];
		for (int i = 0; i < credList.size(); i++) {
			credentialsArray[i] = credList.get(i);
		}

		return credentialsArray;
	}

	public void screenshot() throws IOException {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = currentDateTime.format(formatter);
		formattedDateTime = formattedDateTime.replace(":","_");
		File trg = new File("C:\\Users\\potnu\\eclipse-workspace\\com.guru99.demo\\target\\ScreenShots\\screenshot_"+formattedDateTime+".png");
		FileUtils.copyFile(src, trg);

	}

}
