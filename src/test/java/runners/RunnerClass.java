package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        //glue is where we find implementations for gherkin steps
        //we provide the path of package to get all the step definitions
        glue = "steps",
        //if true it will quickly    scan all gherkin steps whether thStey are implemented or not
        //if false it will execute our code
        dryRun = false,
        //it means the console output for cucumber test is having irrelevant information
        //when we set it to true, it simply removes all the irrelevant info
        monochrome = true,
        tags = "@smoke",
        //html report will be generated under target folder
        plugin = {"html:target/cucumber.html", "pretty", "json:target/cucumber.json",
        "rerun:target/failed.txt"}
)

public class RunnerClass {

}
