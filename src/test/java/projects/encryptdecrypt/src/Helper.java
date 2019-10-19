package projects.encryptdecrypt.src;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Helper {
    public static void writeToFile(String fileName, String msg) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.println(msg);
        } catch (IOException e) {
            System.out.println("Cannot write file: " + e.getMessage());
        }

    }

    public static String readFile(String fileName) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
        return content;
    }

    public static Params findArgs(String[] args) {
        Params params = new Params(
            "enc", "0", "", "", "", "shift"
        );
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    params.setMode(args[i+1]);
                    break;
                case "-key":
                    params.setKey(args[i+1]);
                    break;
                case "-data":
                    params.setData(args[i+1]);
                    break;
                case "-in":
                    params.setIn(args[i+1]);
                    break;
                case "-out":
                    params.setOut(args[i+1]);
                    break;
                case "-alg":
                    params.setAlg(args[i+1]);
                    break;
            }
        }
        return params;
    }

    public static void writeToTerminal(String msg) {
        System.out.println(msg);
    }
}
