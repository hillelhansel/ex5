package CodeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeCleaner {
    public ArrayList<Line> cleanCode(BufferedReader reader) throws SyntaxException, IOException {
        ArrayList<Line> cleanedCode = new ArrayList<>();
        String regex = "^(\\{)|\\/(\\*{1,2})";
        Pattern pattern = Pattern.compile(regex);

        String line;
        int lineIndex = 0;
        while((line = reader.readLine()) != null){
            lineIndex++;
            int commentIndex = line.indexOf("//");
            if(commentIndex > 0){
                throw new SyntaxException(lineIndex + "invalid comment syntax");
            }

            line = line.trim();
            if(line.startsWith("//") || line.isEmpty()){
                continue;
            }

            Matcher matcher = pattern.matcher(line);
            if(matcher.lookingAt()){
                throw new SyntaxException(lineIndex + "invalid comment syntax");
            }
            Line newLine = new Line(line, lineIndex, null);
            cleanedCode.add(newLine);
        }
        return cleanedCode;
    }
}
