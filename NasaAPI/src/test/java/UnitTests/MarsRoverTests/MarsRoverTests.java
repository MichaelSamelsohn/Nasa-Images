package UnitTests.MarsRoverTests;

import NasaAPI.Common;
import NasaAPI.MarsRover.MarsRoverImages;
import NasaAPI.MarsRover.Rover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MarsRoverTests extends MarsRoverImages {

    static final Logger log = LogManager.getLogger(MarsRoverTests.class.getName());

    private final String ROVER_MANIFEST_JSON = System.getProperty("user.dir") + "/src/test/resources/CuriosityRoverManifest.json";

    private final String THROWN_EXCEPTION_ERROR_MESSAGE = "The thrown exception message doesn't match intended one";
    private final String CHECK_EARTH_DATE_ERROR_MESSAGE = "The returned value wasn't processed properly";
    private final String ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE = "The returned value doesn't match the information in the JSON file";

    @Mock
    Rover mockRover = mock(Rover.class);

    @Mock
    Common mockCommon = mock(Common.class);

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void throwsIllegalArgumentExceptionIfRoverNameIsWrong() {
        log.debug("Asserting that an IllegalArgumentException is thrown when using a bad rover name");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> { getMarsRoverManifest("NON_EXISTING_ROVER_NAME");
                });

        log.debug("Asserting that the thrown exception message is as expected");
        assertEquals(THROWN_EXCEPTION_ERROR_MESSAGE,
                "Bad rover name. The acceptable choices are - Curiosity, Opportunity and Spirit",
                exception.getMessage());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfDateIsOfWrongType() {
        log.debug("Asserting that an IllegalArgumentException is thrown when using a bad type for selected date");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> { getMarsRoverImagesUrl(mockRover, true); // Date is given as type boolean.
                });

        log.debug("Asserting that the thrown exception message is as expected");
        assertEquals(THROWN_EXCEPTION_ERROR_MESSAGE,
                "Wrong type of date given." +
                        "Should be either integer (for Mars sol) or string (for Earth date)",
                exception.getMessage());
    }

    @Test
    public void checkEarthDateWithDifferentValues() {
        // Dates (Strings) for testing.
        final String MIN_DATE = "2000-01-01";
        final String MAX_DATE = "2020-12-31";
        final String MIDDLE_DATE = "2010-06-15";

        // Dates (Integers) for testing.
        final int MIN_DATE_INT = 20000101;
        final int MAX_DATE_INT = 20201231;
        final int MIDDLE_DATE_INT = 20100615;

        log.debug("Asserting that correct result returns for a date lower than minimum one");
        when(mockCommon.validateNumberInInterval(MIN_DATE_INT,MAX_DATE_INT,19900610)).thenReturn(MIN_DATE_INT);
        Assert.assertEquals(CHECK_EARTH_DATE_ERROR_MESSAGE,
                MIN_DATE,
                checkEarthDate(MIN_DATE, MAX_DATE, "1990-06-10"));

        log.debug("Asserting that correct result returns for a date higher than maximum one");
        when(mockCommon.validateNumberInInterval(MIN_DATE_INT,MAX_DATE_INT,20300920)).thenReturn(MAX_DATE_INT);
        Assert.assertEquals(CHECK_EARTH_DATE_ERROR_MESSAGE,
                MAX_DATE,
                checkEarthDate(MIN_DATE, MAX_DATE, "2030-09-20"));

        log.debug("Asserting that correct result returns for a date in the interval between min & max dates");
        when(mockCommon.validateNumberInInterval(MIN_DATE_INT,MAX_DATE_INT,MIDDLE_DATE_INT)).thenReturn(MIDDLE_DATE_INT);
        Assert.assertEquals(CHECK_EARTH_DATE_ERROR_MESSAGE,
                MIDDLE_DATE,
                checkEarthDate(MIN_DATE, MAX_DATE, MIDDLE_DATE));
    }

    @Test
    public void marsRoverConstructorExtractsCorrectInformation() {
        // TODO: Add logs.
        Rover rover = null;
        try {
            JSONObject roverManifest =
                    new JSONObject(readFile(ROVER_MANIFEST_JSON, Charset.defaultCharset()));
            rover = new Rover("Curiosity" , roverManifest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert rover != null;
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "Curiosity", rover.getName());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "2012-08-06", rover.getLandingDate());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                "2021-05-18", rover.getMaxDate());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                3122, rover.getMaxSol());
        Assert.assertTrue(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE, rover.isActive());
        Assert.assertEquals(ROVER_CONSTRUCTOR_MISMATCH_ERROR_MESSAGE,
                494220, rover.getTotalPhotos());
    }
}
