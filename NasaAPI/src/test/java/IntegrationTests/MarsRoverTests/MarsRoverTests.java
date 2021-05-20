package IntegrationTests.MarsRoverTests;

import NasaAPI.MarsRover.MarsRoverImages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class MarsRoverTests extends MarsRoverImages {

    static final Logger log = LogManager.getLogger(MarsRoverTests.class.getName());

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void defaultNumberOfImagesIsUsedWhenProvidedValueIsNegative() {
        getMarsRoverImages(
                "Curiosity",
                -10, // Negative integer.
                "2012-12-23",
                temporaryFolder.getRoot().getAbsolutePath());

        String[] fileList = temporaryFolder.getRoot().list();
        assert fileList != null;

        log.debug("Asserting that temporary directory contains the exact amount of files as images that were downloaded");
        Assert.assertEquals("Default value wasn't used", 1, fileList.length);
    }
}
