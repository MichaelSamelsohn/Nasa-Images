package UnitTests.TerminalTests;

import CMD.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.junit.Assert.*;

public class TerminalTests extends Terminal {

    static final Logger log = LogManager.getLogger(TerminalTests.class.getName());

    private final String THROWN_EXCEPTION_ERROR_MESSAGE = "The thrown exception message doesn't match intended one";

    @Rule
    public final TestRule globalTimeout = Timeout.seconds(1);

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void throwsIllegalArgumentExceptionIfCommandIsBad() {
        log.debug("Asserting that a RuntimeException is thrown when using bad commands");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> { runCMD(new String[]{"noSuchCommand"});
                });

        log.debug("Asserting that the thrown exception message is as expected");
        assertEquals(THROWN_EXCEPTION_ERROR_MESSAGE,
                "One or more of the provided commands does not exist", exception.getMessage());
    }

    @Test
    public void mkdirCommandWorksCorrectly() {
        String tempFolderPath = temporaryFolder.getRoot().getAbsolutePath();
        log.debug("path of the temporary folder is - {}", tempFolderPath);

        String[] commands = {
                "cd " + tempFolderPath,
                "mkdir testFile.txt"};
        runCMD(commands);

        File testFile = new File(tempFolderPath + "/testFile.txt");
        log.debug("Path of the supposed created test file is - {}", testFile);

        log.debug("Asserting that test file exists in the designated file path");
        Assert.assertTrue("Test file wasn't created", testFile.exists());
    }
}
