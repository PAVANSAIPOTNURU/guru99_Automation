package com.guru99.demo.testcases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginSection extends Base{
	
	@Test(dataProvider = "credentials")
	public void login(String userid, String pswd) throws IOException 
	{
		driver.findElement(By.name("uid")).sendKeys(userid);
		driver.findElement(By.name("password")).sendKeys(pswd);
		driver.findElement(By.name("btnLogin")).click();
		try
		{
		Alert alert = driver.switchTo().alert();
		String al = alert.getText();
		Assert.assertEquals("User or Password is not valid", alert.getText());
		if(al.equals("User or Password is not valid"))
		{
			alert.accept();
		}
		}
		catch(NoAlertPresentException e)
		{
			String manager_element = driver.findElement(By.xpath("//tr[@class = \"heading3\"]//td")).getText();
			List<String> manager_id = new ArrayList<>(Arrays.asList(manager_element.split(" ")));
			Assert.assertEquals(manager_id.get(3), userid);
			screenshot();
			
		}	
	

	}

	
	
	
	
	
	
	
	
	
}
