package main;

import CodeParser.CodeCleaner;
import CodeParser.Line;
import CodeParser.SyntaxException;
import CodeParser.SyntaxValidator;
import Scopes.Global;

import java.io.*;
import java.util.ArrayList;

public class Sjavac {
    private static final CodeCleaner cleaner = new CodeCleaner();
    private static final SyntaxValidator syntaxValidator = new SyntaxValidator();

    private void compile(BufferedReader reader) throws SyntaxException, IOException {
        ArrayList<Line> lines = cleaner.cleanCode(reader);
        syntaxValidator.validateCode(lines);
        Global global = new Global(null, lines);
    }

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

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
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