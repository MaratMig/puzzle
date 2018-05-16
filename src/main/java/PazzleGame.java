import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PazzleGame {
    String fileName;
    Parser parser;
    ArrayList<String[]> pazzelPiecesInput = new ArrayList<String[]>();

    public PazzleGame(String fileName) {
        this.fileName = fileName;
        parser = new Parser(fileName);
    }

    public void startParser() throws IOException {
        pazzelPiecesInput = parser.parse();
        Iterator<String[]> ot = pazzelPiecesInput.iterator();
        while(ot.hasNext()){
            String[] tamp = ot.next();
            System.out.println(tamp[0]);
            System.out.println(tamp[1]);
        }
    }

}
