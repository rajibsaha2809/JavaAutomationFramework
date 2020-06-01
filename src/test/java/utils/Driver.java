package utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Driver {

    static final String LOCAL_SELENIUM_GRID_URL = "http://localhost:4444/wd/hub";
    static String DRIVER_PATH = null;

    static {
        try {
            DRIVER_PATH = new File(".").getCanonicalPath() + "/src/test/resources/Drivers/";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String BROWSER = System.getProperty("Browser");
    public static final String REMOTE_BROWSER = System.getProperty("Remote");
    public static int defaultStaticWait = 30;

    protected static List<WebDriver> webDriverPool = Collections.synchronizedList(new ArrayList<WebDriver>());
    protected static ThreadLocal<WebDriver> driverThread = new ThreadLocal<WebDriver>();
    protected static ThreadLocal<String> implicitWait = new ThreadLocal<String>();

    protected static ThreadLocal<String> keepSession = ThreadLocal.withInitial(() -> "false");

    public static WebDriver getDriverInstance() throws MalformedURLException {
        if (!keepSession.get().equalsIgnoreCase("true")) {
            final WebDriver webDriver = Driver.getDriver();
            webDriverPool.add(webDriver);
            driverThread.set(webDriver);
        }
        return driverThread.get();
    }

    public static WebDriver getDriver() throws MalformedURLException {

        WebDriver driver = null;

        switch (BROWSER.toLowerCase()) {
            case "chrome":
                driver = getChromeDriver(false, false);
                break;

            case "chromeheadless":
                driver = getChromeDriver(true, false);
                break;

            case "firefox":
                driver = getFirefoxDriver(false);
                break;

            default:
                driver = getChromeDriver(false, false);
                break;
        }

        if (!BROWSER.equalsIgnoreCase("api")) {
            driver.manage().timeouts().implicitlyWait(defaultStaticWait, TimeUnit.SECONDS);
            implicitWait.set(String.valueOf(defaultStaticWait));
            implicitWait.set(String.valueOf(defaultStaticWait));
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static WebDriver getChromeDriver(boolean isHeadless, boolean consoleLogs) throws MalformedURLException {
        WebDriver driver = null;
        DesiredCapabilities capability;
        LoggingPreferences logPrefs = new LoggingPreferences();

        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("headless");
            options.addArguments("disable-gpu");
            options.addArguments("disable-bundled-ppapi-flash");
            options.addArguments("--window-size=1920,2000");
        }

        if (consoleLogs) {
            logPrefs.enable(LogType.BROWSER, Level.ALL);
        }

        if (!isHeadless)
            options.addArguments("--start-maximized");

        options.addArguments("--disable-infobars");
        options.addArguments("--lang=en-GB");

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);

        // TO TURN OFF MULTIPLE DOWNLOAD WARNING
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
        chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);

        // TO TURN OFF DOWNLOAD PROMPT
        chromePrefs.put("download.prompt_for_download", "false");
        chromePrefs.put("download.default_directory", System.getProperty("user.home"));


        options.setExperimentalOption("prefs", chromePrefs);

        capability = DesiredCapabilities.chrome();
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        switch (REMOTE_BROWSER) {
            case "false":
                System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver");
                driver = new ChromeDriver(options);
                break;
            case "true":
                driver = new RemoteWebDriver(new URL(LOCAL_SELENIUM_GRID_URL), capability);

        }

        return driver;
    }

    private static WebDriver getFirefoxDriver(final boolean consoleLogs) throws MalformedURLException {
        WebDriver driver = null;

        final DesiredCapabilities firefox = DesiredCapabilities.firefox();
        final FirefoxProfile profile = new FirefoxProfile();
        final FirefoxOptions options = new FirefoxOptions();

        // profile settings
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "image/jpg, text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel," +
                        "application/x-msexcel,application/excel,application/pdf, application/csv");

        profile.setPreference("browser.download.dir", System.getProperty("user.home"));
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xlsx,application/octet-stream," +
                "application/x-msdos-program, application/x-unknown-application-octet-stream, " +
                "application/vnd.ms-powerpoint, application/excel, application/vnd.ms-publisher, " +
                "application/x-unknown-message-rfc822, application/vnd.ms-excel, application/msword, " +
                "application/x-mspublisher, application/x-tar, application/zip, " +
                "application/x-gzip,application/x-stuffit,application/vnd.ms-works, " +
                "application/powerpoint, application/rtf, application/postscript, " +
                "application/x-gtar, video/quicktime, video/x-msvideo, video/mpeg, audio/x-wav, audio/x-midi, " +
                "audio/x-aiff,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet," +
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml,text/csv");
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);

        // capabilities settings
        firefox.setPlatform(Platform.ANY);
        firefox.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        firefox.setCapability(FirefoxDriver.PROFILE, profile);

        // options settings
        options.setProfile(profile);

        if (consoleLogs) {
            firefox.setCapability("marionette", true);
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, System.getProperty("user.dir") +
                    "/BrowserConsoleLogs.txt");
        }

        switch (REMOTE_BROWSER) {
            case "false":
                profile.setPreference("marionette", true);
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty("webdriver.gecko.driver", DRIVER_PATH + "geckodriver");
                driver = new FirefoxDriver(options);
                break;

            case "true":
                driver = new RemoteWebDriver(new URL(LOCAL_SELENIUM_GRID_URL), options);
                break;

            default:
                driver = new RemoteWebDriver(new URL(LOCAL_SELENIUM_GRID_URL), options);

        }
        return driver;
    }

    public static void setImplicitWait(int implWait) {
        getCurrentDriver().manage().timeouts().implicitlyWait(implWait, TimeUnit.SECONDS);
        implicitWait.set(String.valueOf(implWait));
    }

    public static void resetToDefaultStaticWait(){
        setImplicitWait(defaultStaticWait);
    }

    public static List<WebDriver> getDriverPool() {
        return webDriverPool;
    }

    public static WebDriver getCurrentDriver() {
        return driverThread.get();
    }

    public static int getCurrentImplicitWait() {
        return Integer.parseInt(implicitWait.get());
    }

    public static String getKeepSession() {
        return keepSession.get();
    }

    public static void closeAll() {
        for (WebDriver webDriver : getDriverPool()) {
            webDriver.quit();
        }
    }

    public static void setKeepSession(String value){
        keepSession.set(value);
    }
}