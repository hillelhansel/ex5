package main;

import CodeParser.CodeClassifier;
import CodeParser.CodeCleaner;
import CodeParser.Line;
import Scopes.Global;
import Validation.SyntaxValidation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompilerFlow {
    private final CodeCleaner cleaner = new CodeCleaner();
    private final CodeClassifier classifier = new CodeClassifier();
    private final SyntaxValidation syntaxValidator = new SyntaxValidation();

    public void compile(String filePath) throws IOException, IllegalCodeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            ArrayList<Line> lines = cleaner.cleanCode(reader);

            classifier.classifyCode(lines);

            syntaxValidator.validateSyntax(lines);

            Global global = new Global(null, lines);


        }
    }
}
