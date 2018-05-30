import java.io.IOException;
import java.util.*;

public class PuzzleGame {
    String fileName;


    public PuzzleGame(String fileName) {
        this.fileName = fileName;
    }

    public void startGame() throws Exception {
        ArrayList<String> stringsList = startParser();
        boolean validateParserOutputResult = validateParserOutput(stringsList);
        List<Piece> pieceList = new ArrayList<>();
        if (validateParserOutputResult){
            pieceList = convertStringListToPieces(stringsList);
        }
        else {throw new Exception("validateParserOutputResult failed"); }

        boolean isPuzzleCanBeSolved = validateBeforeSolver(pieceList);

        if (isPuzzleCanBeSolved){
            Set<Integer> boardSize = ValidationUtils.getPosibleNumRows(pieceList);

            boolean solutionFound = false;
            for(Integer numOfLines : boardSize){
                PuzzleBoard puzzleBoard = new PuzzleBoard(pieceList, numOfLines);
                if(puzzleBoard.tryToSolvePuzzleRectangle())
                {
                    Piece[] solutions = puzzleBoard.getResult();
                    System.out.println(String.format("solution for %s lines", numOfLines));
                    printPuzzle(solutions,numOfLines);
                    solutionFound = true;
                    break;
                }
            }
            if(!solutionFound){
                System.out.println("There is no solution for current puzzle.");
            }
        }
        else {throw new Exception("validateBeforeSolver failed");}

    }

    public static void printPuzzle(Piece[] pieces, int numOfLines){
        int col = pieces.length / numOfLines;
            for (int i = 0; i < pieces.length; i++) {
                System.out.print(pieces[i].toString() + " ");
                if ((i % col == col - 1 && col!=1) || col==pieces.length) {
                    System.out.println();
                }
            }
    }

        private List<Piece> convertStringListToPieces(ArrayList<String> stringsList) {
        List<Piece> listPieces = new ArrayList<>();
            for (String strPieces : stringsList){
                List<Integer> nums = new ArrayList<>();
                int counter = 1;
                for (int i=1 ; i <= strPieces.length() ; i++) {
                    if (strPieces.length() >= 5 && !(nums.size()==4)) {
                        if (String.valueOf(strPieces.charAt(strPieces.length() - i - 1)).contains("-")) {
                            StringBuilder s = new StringBuilder().append(strPieces.charAt(strPieces.length() - i - 1)).append(strPieces.charAt(strPieces.length() - i));
                            nums.add(Integer.parseInt(s.toString()));
                            i++;
                        } else {
                            nums.add(Character.getNumericValue(strPieces.charAt(strPieces.length() - i)));
                        }
                        counter++;
                    }
                    else  {
                        nums.add(Integer.parseInt(strPieces.substring(0,strPieces.length()-i+1)));
                        break;
                    }
                }
                int[] sidesData = new int[4];
                sidesData[3] = nums.get(0);
                sidesData[2] = nums.get(1);
                sidesData[1] = nums.get(2);
                sidesData[0] = nums.get(3);
                Piece piece = new Piece(nums.get(4), sidesData);
                listPieces.add(piece);
            }

        return listPieces;
    }

    private boolean validateParserOutput(ArrayList<String> stringsList) {
    return true;
    }

    private ArrayList<String> startParser() throws IOException {
        Parser parser = new Parser(fileName);
        ArrayList<String>  puzzelPiecesInput = parser.parse();

//        // print for debug purpose only
//        Iterator<String[]> ot = puzzelPiecesInput.iterator();
//        while(ot.hasNext()){
//            String[] tamp = ot.next();
//            System.out.println(tamp[0]);
//            System.out.println(tamp[1]);
//        }

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

    private boolean validateBeforeSolver(List<Piece>  pieces){
        return ValidationUtils.isPuzzleValid(pieces);
    }
}
