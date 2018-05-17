import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PuzzleGame {
    String fileName;
    Parser parser;


    public PuzzleGame(String fileName) {
        this.fileName = fileName;
        parser = new Parser(fileName);
    }

    private ArrayList<String[]> startParser() throws IOException {

        ArrayList<String[]>  puzzelPiecesInput = parser.parse();

        // print for debug purpose only
        Iterator<String[]> ot = puzzelPiecesInput.iterator();
        while(ot.hasNext()){
            String[] tamp = ot.next();
            System.out.println(tamp[0]);
            System.out.println(tamp[1]);
        }

        return puzzelPiecesInput;
    }

    private void convertLinestoPieces(){};

    private void validateBeforeSolver(){};

    private void solver(){};





}
