package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sjavac {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(2);
            System.err.println("Error: Invalid arguments count. Usage: java Sjavac <file_path>");
            return;
        }

        String filePath = args[0];
        if (!filePath.endsWith(".sjava")) {
            System.out.println(2);
            System.err.println("Error: File must end with .sjava");
            return;
        }

        CompilerFacade compiler = new CompilerFacade();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            compiler.compile(reader);

            System.out.println(0);
        }
        catch (IllegalCodeException e) {
            System.out.println(1);
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(2);
            System.err.println("IO Error: " + e.getMessage());
        }
    }
}