import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PuzzleGame {
    String fileName;
    Parser parser;
    ArrayList<String[]> puzzelPiecesInput = new ArrayList<String[]>();

    public PuzzleGame(String fileName) {
        this.fileName = fileName;
        parser = new Parser(fileName);
    }

    public void startParser() throws IOException {
        puzzelPiecesInput = parser.parse();
        Iterator<String[]> ot = puzzelPiecesInput.iterator();
        while(ot.hasNext()){
            String[] tamp = ot.next();
            System.out.println(tamp[0]);
            System.out.println(tamp[1]);
        }
    }

}
