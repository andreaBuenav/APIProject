package com.globant.test.api.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//Initial options for cucumber
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.test.api.stepDefinitions",
        tags ="@active and @smoke",
        plugin = {"pretty:target/cucumber/cucumber.txt",
                "html:target/cucumber/report",
                "json:target/cucumber.json"
        }
)
public class TestRunner {

}
