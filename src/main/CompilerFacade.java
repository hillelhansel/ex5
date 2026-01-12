package main;

import scope.Global;
import syntax.CodeCleaner;
import syntax.Line;
import syntax.SyntaxValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompilerFacade {
    private final CodeCleaner cleaner;
    private final SyntaxValidator syntaxValidator;

    public CompilerFacade() {
        this.cleaner = new CodeCleaner();
        this.syntaxValidator = new SyntaxValidator();
    }

    public void compile(BufferedReader reader) throws IOException, IllegalCodeException {
        ArrayList<Line> lines = cleaner.cleanCode(reader);

        syntaxValidator.validateCode(lines);

        new Global(null, lines);
    }
}