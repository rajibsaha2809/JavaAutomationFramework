package utils;

import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.mortbay.log.Log;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VUtils {

    public static void captureScreen(Scenario scenario) {
        WebDriver augmentedDriver = new Augmenter().augment(Driver.getCurrentDriver());
        byte[] screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
    }

    public static void captureScreenShot(String fileName) {

        // Take screenshot and store as a file format
        File src = ((TakesScreenshot) Driver.getCurrentDriver()).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage bufferedImage;
            bufferedImage = ImageIO.read(src);

            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "png", new File(fileName.replace(" ", "_")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAllFiles(String folder, final String fileType) {
        File dir = new File(folder);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(fileType);
            }
        });
        if (files.length == 0) {
            Log.info("No file found file for this extension");
        } else {
            Log.info("Found file with count " + files.length);
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            Log.info("All the files are deleted");
        }
    }

    public static void switchToDefaultContent() {
        Log.info("***Switching to default content***");
        Driver.getCurrentDriver().switchTo().defaultContent();
    }

    public static void switchToIFrame() {
        Log.info("Switching to frame");
        switchToDefaultContent();
        VUtils.waitFor(2); // give JQuery the chance to initialise
//        (new WebDriverWait(Driver.getCurrentDriver(), 45))
//                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        List<WebElement> iframeElements = Driver.getCurrentDriver().findElements(By.tagName("iframe"));
        if (iframeElements.size() > 0) {
            Driver.getCurrentDriver().switchTo().frame(0);
        }
        VUtils.waitFor(2);
        Log.info("***Switched to iframe***");
    }

    public static void switchToIFrame(int implicitWait, int staticWait) {
        Log.info("Switching to frame");
        Driver.getCurrentDriver().manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        switchToDefaultContent();
        VUtils.waitFor(staticWait);
        Driver.getCurrentDriver().manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        List<WebElement> iframeElements = Driver.getCurrentDriver().findElements(By.tagName("iframe"));
        if (iframeElements.size() > 0) {
            Driver.getCurrentDriver().switchTo().frame(0);
        }
        Driver.getCurrentDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public static void waitFor(int i) {
        try {
            Thread.sleep(1000 * i);
        } catch (InterruptedException e) {

        }
        Log.info("***Waited for " + i + " seconds***");
    }

    public static void refresh() {
        Driver.getCurrentDriver().navigate().refresh();
    }

    public static boolean isElementPresent(By by) {
        return elementExists(by);
    }

    public static boolean elementExists(By xpath) {
        Driver.setImplicitWait(5);
        try {
            if (Driver.getCurrentDriver().findElement(xpath).isDisplayed()) {
                Driver.resetToDefaultStaticWait();
                return true;
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            Log.info("Element not found : "+xpath);
            Driver.resetToDefaultStaticWait();
            return false;
        }
        Driver.resetToDefaultStaticWait();
        return false;
    }

    public static boolean elementExists(WebElement element) {
        Driver.setImplicitWait(1);
        try {
            if (element.isDisplayed()) {
                Driver.resetToDefaultStaticWait();
                return true;
            }
        } catch (NoSuchElementException e) {
            Log.info("Element not found : " + element);
            Driver.resetToDefaultStaticWait();
            return false;
        }
        Driver.resetToDefaultStaticWait();
        return false;
    }

    public static boolean isElementEnabled(By xpath) {
        try {
            return Driver.getCurrentDriver().findElement(xpath).isEnabled();
        } catch (NoSuchElementException e) {
            Log.info("Element not found :" + xpath);
        }
        return false;
    }

    public static boolean isElementPresent(WebElement element) {
        return elementExists(element);
    }

    public static void waitForMilliseconds(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {

        }
    }

    public static WebElement findElementByText(String text) {
        WebElement element = Driver.getCurrentDriver().findElement(By.xpath("//*[text() = '" + text + "']"));
        return element;
    }

    public static WebElement findElementByPartialText(String text) {
        WebElement element = Driver.getCurrentDriver().findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
        return element;
    }

    public static Actions Actions() {
        Actions actions = new Actions(Driver.getCurrentDriver());
        return actions;
    }

    public static void deleteFiles(String folder, final String fileType) {
        File dir = new File(folder);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(fileType);
            }
        });
        if (files.length == 0) {
            Log.info("No file found file for this extension");
        } else {
            Log.info("Found file with count " + files.length);
            for (int i = 1; i < files.length; i++) {
                files[i].delete();
            }
            Log.info("All the files are deleted");
        }
    }

    public static void copyFile(File file, String fileName, String windows_boxFolder) {

        File destinationPathObject = new File(windows_boxFolder);
        File sourceFilePathObject = file;
        try {
            if ((destinationPathObject.isDirectory()) && (sourceFilePathObject.isFile()))
            //both source and destination paths are available
            {
                //creating object for File class
                File statusFileNameObject = new File(windows_boxFolder + "/" + fileName);
                if (statusFileNameObject.isFile())
                //Already file is exists in Destination path
                {
                    //deleted File
                    statusFileNameObject.delete();
                }
                //paste file from source to Destination path with fileName as value of fileName argument
                FileUtils.copyFile(sourceFilePathObject, statusFileNameObject);
                //delete sourceFile
                sourceFilePathObject.delete();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void deleteFile(String folder, final String fileName) {
        File file = new File(folder + "\\" + fileName);
        file.delete();
    }

    public static void moveToElement(WebElement ele) {
        Actions().moveToElement(ele).build().perform();
    }

    public static void clickActionOnElement(WebElement ele) {
        Actions().click(ele).build().perform();
    }

    public static void getURL(String URL) {
        Driver.getCurrentDriver().get(URL);
    }

    public static void sendKeys(String key) {
        switch (key.toLowerCase()) {
            case "esc":
                Driver.getCurrentDriver().switchTo().activeElement().sendKeys(Keys.ESCAPE);
                break;
            case "enter":
                Driver.getCurrentDriver().switchTo().activeElement().sendKeys(Keys.ENTER);
                break;
            default:
                throw new IllegalArgumentException("Incorrect key has been sent : " + key);
        }
        Log.info("Pressed " + key + " key in the keyboard.");
    }

}
