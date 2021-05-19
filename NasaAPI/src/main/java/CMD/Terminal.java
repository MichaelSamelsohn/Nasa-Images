package CMD;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    static final Logger log = LogManager.getLogger(Terminal.class.getName());

    public static void runCMD(@NotNull String[] commands) {
        StringBuilder stringBuilder = new StringBuilder();
        log.debug("The following commands were given for execution:");
        for (int i = 0; i < commands.length; i++) {
            log.debug(commands[i]);
            if (i != commands.length - 1) {
                stringBuilder.append(commands[i]).append(" && ");
            } else { // To avoid "&&" at the end of the commands.
                stringBuilder.append(commands[i]);
            }
        }
        String commandToExecute = stringBuilder.toString();
        log.debug("The aggregated command for execution is - {}", commandToExecute);

        ProcessBuilder builder = new ProcessBuilder("bash", "-c", commandToExecute);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            log.debug("Output of the terminal is displayed below:");
            while (true) {
                line = bufferedReader.readLine();
                log.debug(line);
                if (line == null) {
                    break;
                }
                if (line.contains("command not found")) {
                    throw new IllegalArgumentException("One or more of the provided commands does not exist");
                }
            }
        } catch (IOException e) {
            log.error("Bad commands were given", e);
        }
        log.debug("Finished running the terminal for the given commands");
    }
}
