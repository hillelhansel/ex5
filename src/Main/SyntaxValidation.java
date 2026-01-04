package Main;

public class SyntaxValidation {
    public boolean validateSynatx(String line){
        LineClassification lineClassification = new LineClassification();
        LineType type = lineClassification.classifyLine(line);
    }

    private boolean isValid(String line, LineType lineType){
        switch (lineType){
            case

        }
    }
}
