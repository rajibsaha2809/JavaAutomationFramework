package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import utils.Driver;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "stepDefinitions", format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class CommonRunner {
    @AfterClass
    public static void tearDownClass() {
        try {
            Driver.closeAll();
        } catch (Exception e) {
            // no action required
        }
    }
}