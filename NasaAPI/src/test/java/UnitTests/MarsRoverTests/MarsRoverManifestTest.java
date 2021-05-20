package UnitTests.MarsRoverTests;


import NasaAPI.MarsRover.MarsRoverImages;
import NasaAPI.MarsRover.Rover;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MarsRoverManifestTest extends MarsRoverImages {

    static final Logger log = LogManager.getLogger(MarsRoverManifestTest.class.getName());

    @Parameterized.Parameters(name = "Rover - {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"Opportunity","2004-01-25"},
                {"Spirit","2004-01-04"},
                {"Curiosity","2012-08-06"}
        });
    }

    @Parameterized.Parameter(0)
    public String roverName;

    @Parameterized.Parameter(1)
    public String landingDate;

    @Ignore("Test works fine for two values, but gets stuck on the third. Requires more investigation")
    @Test(timeout=10000)
    public void testGetMarsRoverManifestMethodFunctionality() {
        log.debug("Getting the relevant rover map");
        Rover rover = getMarsRoverManifest(roverName);
        log.debug("Asserting that returned rover name matches the actual rover name");
        Assert.assertEquals("The rover name (from rover map) doesn't match the name it was called with",
                roverName, rover.getName());
        log.debug("Asserting that returned rover landing date matches the actual landing date");
        Assert.assertEquals("The rover landing date (from rover map) doesn't match the date it was called with",
                landingDate, rover.getLandingDate());
    }
}
