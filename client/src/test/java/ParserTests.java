import com.puzzle.client.fileHandlers.Parser;
import com.puzzle.common.entities.Piece;
import com.puzzle.common.utils.ErrorTypeEnum;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {

    private static final String resourceDir = ("src/test/resources/filesToParse/");

    @Test
    public void dashAfterPieceDetails() throws IOException {
        File file = new File(resourceDir + "dashAfterPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNotNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()==0);
        assertTrue(parseList.size()==4);
    }

    @Test
    public void elementsNumberEqualsMissing() throws IOException {
        File file = new File(resourceDir + "elementsNumberEqualsMissing.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_FILE_HEADER_FORMAT.getValue())){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void emptyLines() throws IOException {
        File file = new File(resourceDir + "emptyLines.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNotNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()==0);
        assertTrue(parseList.size()==4);

    }

    @Test
    public void incorectPieceDetails() throws IOException {
        File file = new File(resourceDir + "incorectPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_FORMAT.getValue().substring(0,8))){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void lessElements() throws IOException {
        File file = new File(resourceDir + "lessElements.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_NUM_OF_PIECES.getValue().substring(0,8))){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void minusCharAppearTwice() throws IOException {
        File file = new File(resourceDir + "minusCharAppearTwice.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_FORMAT.getValue().substring(0,8))){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void missingElementsNum() throws IOException {
        File file = new File(resourceDir + "missingElementsNum.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_FILE_HEADER_FORMAT.getValue())){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void missingElementsNumString() throws IOException {
        File file = new File(resourceDir + "missingElementsNumString.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_FILE_HEADER_FORMAT.getValue())){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void missingHeader() throws IOException {
        File file = new File(resourceDir + "missingHeader.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_FILE_HEADER_FORMAT.getValue())){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void missingId() throws IOException {
        File file = new File(resourceDir + "missingId.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_FORMAT_GENERIC.getValue())){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void missingPieceDetails() throws IOException {
        File file = new File(resourceDir + "missingPieceDetails.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_FORMAT_GENERIC.getValue())){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void moreElements() throws IOException {
        File file = new File(resourceDir + "moreElements.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_ID.getValue().substring(0,8))){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void sameId() throws IOException {
        File file = new File(resourceDir + "sameId.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.MISSING_ELEMENTS.getValue().substring(0,8))){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }

    @Test
    public void whiteSpaces() throws IOException {
        File file = new File(resourceDir + "whiteSpaces.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size()!=0);
        boolean contains=false;
        for (String str :inputValidationErrors){
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_FORMAT.getValue().substring(0,8))){
                contains=true;
                break;
            }
        }
        assertTrue(contains);

    }


    @Test
    public void moreElementThenLines() throws IOException {
        File file = new File(resourceDir + "moreElementThenLines.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size() != 0);
        boolean contains = false;
        for (String str : inputValidationErrors) {
            if (str.contains(ErrorTypeEnum.WRONG_ELEMENT_ID.getValue().substring(0, 8))) {
                contains = true;
                break;
            }
        }
        assertTrue(contains);
    }

    @Test
    public void missingElements() throws IOException {
        File file = new File(resourceDir + "missingElements.txt");
        Parser parse = new Parser(file.toPath());
        ArrayList<Piece> parseList = parse.parse();
        assertNull(parseList);
        ArrayList<String> inputValidationErrors = parse.getInputValidationErrors();
        assertTrue(inputValidationErrors.size() != 0);
        boolean contains = false;
        for (String str : inputValidationErrors) {
            if (str.contains(ErrorTypeEnum.MISSING_ELEMENTS.getValue().substring(0, 8))) {
                contains = true;
                break;
            }
        }
        assertTrue(contains);
    }


}



