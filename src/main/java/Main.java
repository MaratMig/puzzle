import org.apache.commons.math3.primes.Primes;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws Exception {
        String fileName = "src\\main\\resources\\inputFile.txt";
        PuzzleGame game = new PuzzleGame(fileName);
        game.startGame();
    }
}
