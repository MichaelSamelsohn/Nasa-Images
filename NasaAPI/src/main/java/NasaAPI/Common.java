package NasaAPI;

import CMD.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Common extends Terminal{

    static final Logger log = LogManager.getLogger(Common.class.getName());

    public int numberOfImagesToDownload(int numOfImages) {
        if (numOfImages > 0) {
            return  numOfImages;
        } else {
            log.warn("Will use default value of photos to collect - 1, since provided number is lower than 1");
            return 1;
        }
    }

    public void downloadImages(@NotNull String[] url, String downloadCommand, String imageFileNamePrefix,
                                      String imageFileType, String imagePath) {
        log.debug("Number of URLs provided is - {}", url.length);
        log.debug("Provided download command is - {}", downloadCommand);
        log.debug("Provided image file name prefix is - {}", imageFileNamePrefix);
        log.debug("Provided image file type is - {}", imageFileType);
        log.debug("Provided image path is - {}", imagePath);

        for (int i = 0; i < url.length; i++) {
            if (url[i] != null) {
                log.debug("The URL is - {}", url[i]);
                String[] commands = {
                        "cd " + imagePath,
                        downloadCommand + " " + imageFileNamePrefix + i + imageFileType + " " + url[i]};
                runCMD(commands);
            } else {  // The API returned less urls than requested (probably due to lack of other photos).
                log.warn("The amount of photos provided is less than requested");
                break;
            }
        }
    }

    public String formatStringWithSeparator(String[] strings, String separator) {
        log.debug("Selected separator is - {}", separator);
        log.debug("Number of strings provided is - {}", strings.length);

        if (separator == null) {
            log.warn("No separator was chosen. Will use default white space separator");
            return formatStringWithSeparator(strings, " ");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            log.debug("Current string is - {}", strings[i]);
            if (i != strings.length - 1) {
                stringBuilder.append(strings[i]).append(separator);
            } else {
                stringBuilder.append(strings[i]);
            }
        }
        String postFormatting = stringBuilder.toString();
        log.debug("Post formatting string is - {}", postFormatting);

        return postFormatting;
    }

    public int validateNumberInInterval(int min, int max, int value) {
        // If value is <min, then make it min.
        // If value is >max, then make it max.
        // If value is between min and max, then it won't be affected and returned as is.
        return Math.min(Math.max(value, min), max);
    }
}
