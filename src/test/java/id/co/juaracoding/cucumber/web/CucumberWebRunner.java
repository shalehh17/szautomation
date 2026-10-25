package id.co.juaracoding.cucumber.web;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "id.co.juaracoding.cucumber.web",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/web/cucumber.html",
                "json:target/cucumber-reports/web/cucumber.json"
        }
)
public class CucumberWebRunner extends AbstractTestNGCucumberTests {
}