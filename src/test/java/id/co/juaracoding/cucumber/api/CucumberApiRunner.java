package id.co.juaracoding.cucumber.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = "id.co.juaracoding.cucumber.api",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api/cucumber.html",
                "json:target/cucumber-reports/api/cucumber.json"
        }
)
public class CucumberApiRunner extends AbstractTestNGCucumberTests {
}