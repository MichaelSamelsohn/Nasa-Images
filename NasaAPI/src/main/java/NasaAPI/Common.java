package NasaAPI;

import CMD.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Common {

    static final Logger log = LogManager.getLogger(Common.class.getName());

    public static void downloadImages(@NotNull String[] url, String downloadCommand, String imageFileNamePrefix,
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
                Terminal.runCMD(commands);
            } else {  // The API returned less urls than requested (probably due to lack of other photos).
                log.warn("The amount of photos provided is less than requested");
                break;
            }
        }
    }

    public static String formatStringWithSpace(String[] strings, @NotNull String separator) {
        log.debug("Selected separator is - {}", separator);
        log.debug("Number of strings provided is - {}", strings.length);

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
}
