import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "src\\main\\resources\\inputFile.txt";
        PuzzleGame game = new PuzzleGame(fileName);
        game.startParser();
    }
}
