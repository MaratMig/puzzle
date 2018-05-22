import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PuzzleGame {
    String fileName;
    Parser parser;


    public PuzzleGame(String fileName) {
        this.fileName = fileName;
        parser = new Parser(fileName);
    }

    public ArrayList<String[]> startParser() throws IOException {

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

    private ArrayList<Piece> convertLinestoPieces(Map<Integer, int[]> parsedPieces){
        ArrayList<Piece> puzzlePieces = new ArrayList<>();

        for(Map.Entry<Integer,int[]> entry : parsedPieces.entrySet()){
            Piece piece = new Piece(entry.getKey(), entry.getValue());
            puzzlePieces.add(piece);
        }
        return puzzlePieces;
    }

    private void validateBeforeSolver(){};

    private void solver(){};





}
