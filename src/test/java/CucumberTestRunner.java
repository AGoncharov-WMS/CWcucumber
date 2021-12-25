import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps",
        plugin = {"pretty"},
        tags="@TestPositive"
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
