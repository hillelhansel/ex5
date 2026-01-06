package main;

import CodeParser.CodeCleaner;
import CodeParser.Line;
import CodeParser.SyntaxValidator;
import Scopes.Global;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CompilerFlow {
    private final CodeCleaner cleaner = new CodeCleaner();
    private final SyntaxValidator syntaxValidator = new SyntaxValidator();

    public void compile(String filePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArrayList<Line> lines = cleaner.cleanCode(reader);

        syntaxValidator.validateCode(lines);
        Global global = new Global(null, lines);

    }
}
