package main;

import java.io.*;

public class Sjavac {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println(2);
            System.err.println("Error: Invalid arguments count. Usage: java SJavac <file_path>");
            return;
        }
        String filePath = args[0];

        if (!filePath.endsWith(".sjava")) {
            System.out.println(2);
            System.err.println("Error: File must end with .sjava");
            return;
        }
        try {
            new CompilerFlow().compile(args[0]);
            System.out.println(0);
        }
        catch (IOException e) {
            System.out.println(2);
            System.err.println(e.getMessage());
        }
        catch (IllegalCodeException e) {
            System.out.println(1);
            System.err.println("IO Error: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println(2);
            e.printStackTrace();
        }
    }
}