import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ParserTests {

private static final String resourceDir= ("src/test/resources/filesToParse/");

    @Test
    public void dashAfterPieceDetails() throws IOException {
        Parser parse = new Parser(resourceDir + "dashAfterPieceDetails.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void elementsNumberEqualsMissing() throws IOException {
        Parser parse = new Parser(resourceDir + "elementsNumberEqualsMissing.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void emptyLines() throws IOException {
        Parser parse = new Parser(resourceDir + "emptyLines.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void incorectPieceDetails() throws IOException {
        Parser parse = new Parser(resourceDir + "incorectPieceDetails.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void lessElements() throws IOException {
        Parser parse = new Parser(resourceDir + "lessElements.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void minusCharAppearTwice() throws IOException {
        Parser parse = new Parser(resourceDir + "minusCharAppearTwice.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingElementsNum() throws IOException {
        Parser parse = new Parser(resourceDir + "missingElementsNum.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingElementsNumString() throws IOException {
        Parser parse = new Parser(resourceDir + "missingElementsNumString.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingHeader() throws IOException {
        Parser parse = new Parser(resourceDir + "missingHeader.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingId() throws IOException {
        Parser parse = new Parser(resourceDir + "missingId.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void missingPieceDetails() throws IOException {
        Parser parse = new Parser(resourceDir + "missingPieceDetails.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void moreElements() throws IOException {
        Parser parse = new Parser(resourceDir + "moreElements.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void sameId() throws IOException {
        Parser parse = new Parser(resourceDir + "sameId.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }

    @Test
    public void whiteSpaces() throws IOException {
        Parser parse = new Parser(resourceDir + "whiteSpaces.txt");
        ArrayList<String[]> parseList = parse.parse();
        assertTrue(parseList.size() > 0);

    }




}



