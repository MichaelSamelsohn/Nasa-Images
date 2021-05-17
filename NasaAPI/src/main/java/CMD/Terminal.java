package CMD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
    public static void runCMD(String[] commands) {
        String commandToExecute = "";
        for (int i = 0; i < commands.length; i++) {
            if (i != commands.length - 1) {
                commandToExecute += commands[i] + " && ";
            } else { // To avoid "&&" at the end of the commands.
                commandToExecute += commands[i];
            }
        }

        ProcessBuilder builder = new ProcessBuilder("bash", "-c", commandToExecute);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
