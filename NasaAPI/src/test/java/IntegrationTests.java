import IntegrationTests.CommonTests.CommonTests;
import IntegrationTests.EpicTests.EpicTests;
import IntegrationTests.MarsRoverTests.MarsRoverTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CommonTests.class,
        EpicTests.class,
        MarsRoverTests.class
})

public class IntegrationTests {
}
