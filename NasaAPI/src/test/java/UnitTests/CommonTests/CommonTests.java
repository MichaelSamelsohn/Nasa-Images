package UnitTests.CommonTests;

import NasaAPI.Common;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class CommonTests extends Common {

    static final Logger log = LogManager.getLogger(CommonTests.class.getName());

    private final String VALIDATE_NUMBER_IN_INTERVAL_ERROR_MESSAGE = "The returned value wasn't processed properly";
    private final String NUMBER_OF_IMAGES_TO_DOWNLOAD_ERROR_MESSAGE = "The returned value wasn't processed properly";

    private final String[] STRINGS = new String[] {
            "THIS",
            "IS",
            "A",
            "TEST"
    };

    private static final String[] URL = new String[] {
            "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00136/opgs/edr/fcam/FLA_409555441EDR_F0051858FHAZ00311M_.JPG",
            "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00136/opgs/edr/rcam/RRA_409555466EDR_F0051858RHAZ00309M_.JPG",
            "http://mars.jpl.nasa.gov/msl-raw-images/msss/00136/mcam/0136ML0821000000I1_DXXX.jpg",
            "http://mars.jpl.nasa.gov/msl-raw-images/msss/00136/mcam/0136MR0827008000E1_DXXX.jpg"
    };

    @Test
    public void numberOfImagesToDownloadForDifferentValues() {
        Assert.assertEquals(NUMBER_OF_IMAGES_TO_DOWNLOAD_ERROR_MESSAGE, 2, numberOfImagesToDownload(2));
        Assert.assertEquals(NUMBER_OF_IMAGES_TO_DOWNLOAD_ERROR_MESSAGE, 1, numberOfImagesToDownload(0));
    }

    @Test
    public void correctFormattingOfStringsWithWhiteSpaceSeparator() {
        log.debug("Asserting that the formatting is performed correctly");
        Assert.assertEquals("The formatting didn't work as expected",
                "THIS IS A TEST", formatStringWithSeparator(STRINGS, " "));
    }

    @Test
    public void correctFormattingOfStringsWithNullSeparator() {
        log.debug("Asserting that the formatting is performed correctly");
        Assert.assertEquals("The formatting didn't work as expected",
                "THIS IS A TEST", formatStringWithSeparator(STRINGS, null));
    }

    @Test
    public void validateNumberInIntervalForDifferentValues() {
        Assert.assertEquals(VALIDATE_NUMBER_IN_INTERVAL_ERROR_MESSAGE, 10, validateNumberInInterval(0, 10, 20));
        Assert.assertEquals(VALIDATE_NUMBER_IN_INTERVAL_ERROR_MESSAGE, 0, validateNumberInInterval(0, 10, -20));
        Assert.assertEquals(VALIDATE_NUMBER_IN_INTERVAL_ERROR_MESSAGE, 5, validateNumberInInterval(0, 10, 5));
    }
}
