package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by icule on 22/02/17.
 */
public class Executor {
    public static String executeCommand(String command) throws IOException, InterruptedException {
        String[] argList = command.split(" ");
        ProcessBuilder builder = new ProcessBuilder(argList);
        Process executed = builder.start();

        executed.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(executed.getInputStream()));
        String res = "";
        String temp;
        while((temp = reader.readLine()) != null){
            res += temp + "\n";
        }
        return res;
    }
}
