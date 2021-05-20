package IntegrationTests.EpicTests;

import NasaAPI.EPIC.EPIC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EpicTests extends EPIC {
    static final Logger log = LogManager.getLogger(EpicTests.class.getName());

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void defaultNumberOfImagesIsUsedWhenProvidedValueIsNegative() {
        getEpicImages(
                -10, // Negative integer.
                temporaryFolder.getRoot().getAbsolutePath());

        String[] fileList = temporaryFolder.getRoot().list();
        assert fileList != null;

        log.debug("Asserting that temporary directory contains the exact amount of files as images that were downloaded");
        Assert.assertEquals("Default value wasn't used", 1, fileList.length);
    }
}
