import com.puzzle.fileHandlers.Parser;
import com.puzzle.Piece;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ParserTests {

private static final String resourceDir= ("src/test/resources/filesToParse/");

    @Test
    public void dashAfterPieceDetails() throws IOException {
        Parser parse = new Parser(resourceDir + "dashAfterPieceDetails.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.contains("errror");
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void elementsNumberEqualsMissing() throws IOException {
        Parser parse = new Parser(resourceDir + "elementsNumberEqualsMissing.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void emptyLines() throws IOException {
        Parser parse = new Parser(resourceDir + "emptyLines.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void incorectPieceDetails() throws IOException {
        Parser parse = new Parser(resourceDir + "incorectPieceDetails.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void lessElements() throws IOException {
        Parser parse = new Parser(resourceDir + "lessElements.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void minusCharAppearTwice() throws IOException {
        Parser parse = new Parser(resourceDir + "minusCharAppearTwice.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingElementsNum() throws IOException {
        Parser parse = new Parser(resourceDir + "missingElementsNum.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingElementsNumString() throws IOException {
        Parser parse = new Parser(resourceDir + "missingElementsNumString.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingHeader() throws IOException {
        Parser parse = new Parser(resourceDir + "missingHeader.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingId() throws IOException {
        Parser parse = new Parser(resourceDir + "missingId.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void missingPieceDetails() throws IOException {
        Parser parse = new Parser(resourceDir + "missingPieceDetails.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void moreElements() throws IOException {
        Parser parse = new Parser(resourceDir + "moreElements.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void sameId() throws IOException {
        Parser parse = new Parser(resourceDir + "sameId.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }

    @Test
    public void whiteSpaces() throws IOException {
        Parser parse = new Parser(resourceDir + "whiteSpaces.txt");
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        inputValidationErrors.get(0).equals("");

    }




}



