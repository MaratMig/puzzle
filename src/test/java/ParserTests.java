import com.puzzle.Piece;
import com.puzzle.fileHandlers.Parser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ParserTests {

    private static final String resourceDir = ("src/test/resources/filesToParse/");

    @Test
    public void dashAfterPieceDetails() throws IOException {
        File file = new File(resourceDir + "dashAfterPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.contains("errror");
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void elementsNumberEqualsMissing() throws IOException {
        File file = new File(resourceDir + "elementsNumberEqualsMissing.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void emptyLines() throws IOException {
        File file = new File(resourceDir + "emptyLines.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void incorectPieceDetails() throws IOException {
        File file = new File(resourceDir + "incorectPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void lessElements() throws IOException {
        File file = new File(resourceDir + "lessElements.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void minusCharAppearTwice() throws IOException {
        File file = new File(resourceDir + "minusCharAppearTwice.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingElementsNum() throws IOException {
        File file = new File(resourceDir + "dashAfterPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingElementsNumString() throws IOException {
        File file = new File(resourceDir + "missingElementsNumString.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingHeader() throws IOException {
        File file = new File(resourceDir + "missingHeader.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingId() throws IOException {
        File file = new File(resourceDir + "missingId.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingPieceDetails() throws IOException {
        File file = new File(resourceDir + "missingPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void moreElements() throws IOException {
        File file = new File(resourceDir + "moreElements.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void sameId() throws IOException {
        File file = new File(resourceDir + "sameId.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void whiteSpaces() throws IOException {
        File file = new File(resourceDir + "whiteSpaces.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }


}



