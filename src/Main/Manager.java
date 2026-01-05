package Main;

import java.io.*;
import java.util.ArrayList;

public class Manager {
    public static void main(String[] args) {
        String filePath = args[0];
        CodeCleaner codeCleaner = new CodeCleaner();
        CodeClassifier codeClassifier = new CodeClassifier();


        try(Reader reader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(reader)){
            ArrayList<Line> cleanCode = codeCleaner.cleanCode(bufferedReader);
            codeClassifier.classifyCode(cleanCode);

        }
        catch (IOException e){
            System.out.println(2);
            System.err.println(e.getMessage());
        }
        catch (IllegalCodeException e){
            System.out.println(1);
            System.err.println(e.getMessage());
        }
    }
}
