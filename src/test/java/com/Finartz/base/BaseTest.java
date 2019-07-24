package com.Finartz.base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseTest {


  protected static WebDriver driver;
  protected static WebDriverWait webDriverWait;

  @BeforeScenario
  public void setUp(){
    String baseUrl = "http://thedemosite.co.uk/login.php";

    DesiredCapabilities capabilities = DesiredCapabilities.chrome();

      System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver");
      driver = new ChromeDriver();

    webDriverWait = new WebDriverWait(driver, 45, 150);
    driver.get(baseUrl);
  }

  @AfterScenario
  public void tearDown(){
    driver.quit();
  }


}
