import UnitTests.ApodTests.ApodTests;
import UnitTests.CommonTests.CommonTests;
import UnitTests.MarsRoverTests.MarsRoverParametrizedTests;
import UnitTests.MarsRoverTests.MarsRoverTests;
import UnitTests.TerminalTests.TerminalTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApodTests.class,
        CommonTests.class,
        MarsRoverParametrizedTests.class,
        MarsRoverTests.class,
        TerminalTests.class
})

public class UnitTests {
    // the class remains empty,
    // used only as a holder for the above annotations
}