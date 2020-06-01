package utils;

import org.junit.Assert;
import org.mortbay.log.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {


    public static void waitFor(int i) {
        try {
            Thread.sleep(i * 1000);

        } catch (InterruptedException e) {
        }
    }

    public static void waitForElementShown(WebDriver driver, By by) {

        Log.info("Started waiting for the Element Visible happen...");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 90);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        Log.info("Waiting over...");
    }

    public static void waitForTextIsNotPresentInElement(WebDriver driver, By by, String text) {
        Log.info("Started waiting for the text to be shown...");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 90);
        int i = 0;
        while (driver.findElement(by).getText().contains(text) && i < 90) {
            VUtils.waitFor(1);
            i++;
            Log.info("Waiting for 1 sec... for text to be gone: " + text);
        }
//        webDriverWait.until(ExpectedConditions.(driver.findElement(by),text));
        Log.info("Waiting over...");
    }

    public static void waitForElement(WebElement defaultElement) {
        for (int i = 0; i <= 5; i++) {
            try {

                if (defaultElement.isDisplayed())
                    return;
                else {
                    VUtils.refresh();
                    Log.info("refreshing page....");
                }
            } catch (NoSuchElementException e) {
                Log.info("Waiting for 1 sec ....");
                waitFor(1);
            }
        }
        Log.info("Waited for an element : " + defaultElement);
    }

    public static void waitForElementContainsText(WebElement defaultElement, String text) {
        for (int i = 0; i <= (Integer.parseInt(PropertiesLoader.getProperty("MAX_TIME_OUT"))); i++) {
            try {

                if (defaultElement.getText().contains(text))
                    return;
                waitFor(1);
                Log.info("Waiting for 1 sec ....");

            } catch (NoSuchElementException e) {
                Log.info("Waiting for 1 sec ....");
                waitFor(1);
            }
        }
    }

    public static void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), 120);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
        Log.info("Waited for page to load.");
    }

    public static WebDriverWait webDriverWait(int threshold) {
        return
                (new WebDriverWait(Driver.getCurrentDriver(), threshold));
    }
}
