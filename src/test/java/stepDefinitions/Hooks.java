package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import utils.Driver;
import utils.PropertiesLoader;
import java.net.MalformedURLException;

public class Hooks {

    long time1;
    public static Scenario scen;

    @Before(value = "@InitializePage", order = -1)
    public void start(Scenario scenario) throws MalformedURLException {
        time1 = System.nanoTime();
        if (!Driver.getKeepSession().equals("true")) {
            Driver.getDriverInstance();
            Driver.getCurrentDriver().navigate().to(PropertiesLoader.getProperty("ui_url"));
        }
        scen = scenario;
    }
}
