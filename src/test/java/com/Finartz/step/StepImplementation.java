package com.Finartz.step;
import com.Finartz.base.BaseTest;
import com.Finartz.helper.ElementHelper;
import com.Finartz.helper.StoreHelper;
import com.Finartz.model.ElementInfo;
import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import static io.restassured.RestAssured.given;


public class StepImplementation extends BaseTest {


    private WebElement findElement(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 1);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }


    private void clickElement(WebElement element) {
        element.click();
    }


    private void hoverElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }






    @Step({"Wait <value> seconds",
            "<int> saniye bekle"})
    public void waitBySeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step({"Wait <value> milliseconds",
            "<long> milisaniye bekle"})
    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();


        }
    }



    @Step({"Click to element <key>",
            "Elementine tıkla <key>"})
    public void clickElement(String key) {
        // if (!key.equals("")) {
        WebElement element = findElement(key);
        hoverElement(element);
        waitByMilliSeconds(500);
        clickElement(element);
        //}
    }
    @Step("<url> Servis bilgisinin 200 kontrolünü yap")
    public void GetURLResponseCode(String url) {
        Response response = given()
                .get(url)
                .then()
                .statusCode(200)
                .extract().response();


        System.out.println("Dönen Response Değeri: " + response.statusCode());
        int statusCode = response.statusCode();

        Assert.assertTrue("StatusCode 200 degil.", statusCode == 200);

    }

    @Step("<url> Servis bilgisinin 200 kontrolünü yap ve HTML kodunu yazdir")
    public void GetURLResponseHTML(String url) {
        Response response = given()
                .get(url)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Dönen Response Değeri: " + response.asString());
        int statusCode = response.statusCode();

        Assert.assertTrue("StatusCode 200 degil.", statusCode == 200);
    }




    @Step("Sifre Degismisse")
    public void sifreDegismisse() {
        if (driver.getPageSource().contains("**Failed Login**"))
        {
            do {
                driver.findElement(By.cssSelector("a:nth-child(6)")).click();
                waitBySeconds(2);
                driver.findElement(By.cssSelector("input[name='username']")).sendKeys("testuser");
                driver.findElement(By.cssSelector("input[name='password']")).sendKeys("testpass");
                driver.findElement(By.cssSelector("input[type='button']")).click();
                driver.findElement(By.cssSelector("a:nth-child(7)")).click();
                waitBySeconds(2);

                driver.findElement(By.cssSelector("input[name='username']")).sendKeys("testuser");
                driver.findElement(By.cssSelector("input[name='password']")).sendKeys("testpass");
                driver.findElement(By.cssSelector("input[type='button']")).click();
            }while (driver.getPageSource().contains("**Failed Login**"));

        }else {
            System.out.println("Basarılı bir şekilde giriş yapıldı..");
        }

    }

    @Step({"Write value <text> to element <key>",
            "<text> textini elemente yaz <key>"})
    public void sendKeys(String text, String key) {
        if (!key.equals("")) {
            findElement(key).sendKeys(text);
        }
    }



    @Step("<key> elementin <attribute> degeri <text> degerini iceriyor mu")
    public void printAttributeKey(String key, String attribute, String text) {


        WebElement element = findElement(key);
        System.out.println("elementin attribute Degeri: "+ element.getAttribute(attribute));
        Assert.assertTrue(element.getAttribute(attribute).contains(text));

    }


    @Step("Sayfa Basligi <text> degerini iceriyor mu")
    public void pageTitle(String text) {
        String pageTitle =driver.getTitle();
        System.out.println("Sayfanın Title Değeri: "+ pageTitle);
        Assert.assertTrue(pageTitle.contains(text));
    }


    @Step({"Check if element <key> contains text <expectedText>",
            "<key> elementi <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String key, String expectedText) {
        Boolean containsText = findElement(key).getText().contains(expectedText);
        System.out.println("Elementin Text değeri: " + findElement(key).getText());
        Assert.assertTrue("Expected text is not contained", containsText);

    }


}
