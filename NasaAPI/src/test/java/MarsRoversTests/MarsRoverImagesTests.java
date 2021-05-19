package MarsRoversTests;

import NasaAPI.Mars.MarsRoverImages;
import NasaAPI.Mars.Rover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

public class MarsRoverImagesTests extends MarsRoverImages {

    static final Logger log = LogManager.getLogger(MarsRoverImagesTests.class.getName());

    private final String THROWN_EXCEPTION_ERROR_MESSAGE = "The thrown exception message doesn't match intended one";

    @Mock
    Rover mockRover = mock(Rover.class);

    @Test
    public void throwsIllegalArgumentExceptionIfRoverIsWrong() {
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
}
