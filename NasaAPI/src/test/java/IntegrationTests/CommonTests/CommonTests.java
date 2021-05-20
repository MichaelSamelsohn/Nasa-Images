package IntegrationTests.CommonTests;

import NasaAPI.Common;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Arrays;

public class CommonTests extends Common {

    static final Logger log = LogManager.getLogger(CommonTests.class.getName());

    private final String VALIDATE_NUMBER_IN_INTERVAL_ERROR_MESSAGE = "The returned value wasn't processed properly";

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final String[] URL = new String[] {
            "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00136/opgs/edr/fcam/FLA_409555441EDR_F0051858FHAZ00311M_.JPG",
            "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00136/opgs/edr/rcam/RRA_409555466EDR_F0051858RHAZ00309M_.JPG",
            "http://mars.jpl.nasa.gov/msl-raw-images/msss/00136/mcam/0136ML0821000000I1_DXXX.jpg",
            "http://mars.jpl.nasa.gov/msl-raw-images/msss/00136/mcam/0136MR0827008000E1_DXXX.jpg"
    };

    @Test
    public void imagesAreDownloadedWithCorrectFormatAndPath() {
        String tempFolderPath = temporaryFolder.getRoot().getAbsolutePath();
        log.debug("path of the temporary folder is - {}", tempFolderPath);

        downloadImages(URL, "wget -O", "MARS_", ".JPG",
                tempFolderPath);
        String[] fileList = temporaryFolder.getRoot().list();
        assert fileList != null;

        log.debug("Asserting that temporary directory contains the exact amount of files as images that were downloaded");
        Assert.assertEquals("Not all images were downloaded", URL.length, fileList.length);

        log.debug("Asserting that format and naming conventions are according to the specified parameters");
        for(int i = 0; i < URL.length; i++) {
            String imageFileNameAndFormat = "MARS_" + i + ".JPG";
            log.debug("Current file tested is - {}", imageFileNameAndFormat);
            Assert.assertTrue("Format or naming convention isn't correct for one of the image files",
                    Arrays.asList(fileList).contains(imageFileNameAndFormat));
        }
    }
}
