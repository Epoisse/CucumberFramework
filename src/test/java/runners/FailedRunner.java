package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/failed.txt",
        //glue is where we find implementations for gherkin steps
        //we provide the path of package to get all the step definitions
        glue = "steps",
        //if true it will quickly    scan all gherkin steps whether they are implemented or not
        //if false it will execute our code
        dryRun = false,
        //it means the console output for cucumber test is having irrelevant information
        //when we set it to true, it simply removes all the irrelevant info
        monochrome = true,
        //tags = "@sprint12",
        //html report will be generated under target folder
        plugin = {"pretty"}
)

// Second chance for failed classes


public class FailedRunner {

}
