package com.puzzle;


import com.puzzle.utils.ErrorBuilder;
import com.puzzle.utils.ErrorTypeEnum;
import org.apache.commons.math3.primes.Primes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PuzzleValidator {

    private List<Piece> pieces;

    public PuzzleValidator(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Set<Integer> getValidNumOfRows() {
        Set<Integer> possibleNumOfLines = getPosibleNumRows();
        Set<Integer> numOfLinesAfterElimination = new HashSet<>();

        for (int numOfLines : possibleNumOfLines) {
            int numOfCol = pieces.size() / numOfLines;

            if (getNumOfStraitTopEdges() >= numOfCol && getNumOfStraitBottomEdges() >= numOfCol
                    && getNumOfStraitRightEdges() >= numOfLines && getNumOfStraitLeftEdges() >= numOfLines) {
                numOfLinesAfterElimination.add(numOfLines);
            }
        }
        return numOfLinesAfterElimination;
    }

    private Set<Integer> getPosibleNumRows() {

        int numOfPieces = pieces.size();
        Set<Integer> possibleNumberOfLines = new HashSet<>();

        possibleNumberOfLines.add(1);
        List<Integer> factors = new ArrayList<>();
        factors.addAll(Primes.primeFactors(numOfPieces));

        int multiple = 1;
        for (int factor : factors) {
            multiple *= factor;
            possibleNumberOfLines.add(multiple);
            possibleNumberOfLines.add(numOfPieces / multiple);
        }

        return possibleNumberOfLines;
    }


    private long getNumOfStraitRightEdges() {
        return pieces.stream().filter(p -> p.getRight() == 0).count();
    }

    private long getNumOfStraitLeftEdges() {
        return pieces.stream().filter(p -> p.getLeft() == 0).count();
    }

    private long getNumOfStraitTopEdges() {
        return pieces.stream().filter(p -> p.getTop() == 0).count();
    }

    private long getNumOfStraitBottomEdges() {
        return pieces.stream().filter(p -> p.getBottom() == 0).count();
    }

    private boolean isTotalSumZero() {
        int sum = 0;
        for (Piece piece : pieces) {
            sum += piece.getSum();
        }

        return sum == 0;
    }

    public List<Corner> findMissingCorners() {

        List<Corner> missingCorners = new ArrayList<>();

        boolean isTLCornerExist = false;
        boolean isTRCornerExist = false;
        boolean isBLCornerExist = false;
        boolean isBRCornerExist = false;

        for (Piece piece : pieces) {
            if (piece.getLeft() == 0 && piece.getTop() == 0) {
                isTLCornerExist = true;
                continue;
            }
            if (piece.getTop() == 0 && piece.getRight() == 0) {
                isTRCornerExist = true;
                continue;
            }
            if (piece.getRight() == 0 && piece.getBottom() == 0) {
                isBRCornerExist = true;
                continue;
            }
            if (piece.getLeft() == 0 && piece.getBottom() == 0) {
                isBLCornerExist = true;
                continue;
            }
        }

        if (!isTLCornerExist) {
            missingCorners.add(Corner.TL);
        }
        if (!isTRCornerExist) {
            missingCorners.add(Corner.TR);
        }
        if (!isBRCornerExist) {
            missingCorners.add(Corner.BR);
        }
        if (!isBLCornerExist) {
            missingCorners.add(Corner.BL);
        }

        return missingCorners;
    }


    //Number of top and bottom strait sides should be equal;
    //Number of left and right strait sides should be equal;

    private boolean isValidNumberOfStraitSides() {

        long numOfStraitLeft = pieces.stream().filter(p -> p.getLeft() == 0).count();
        long numOfStraitRight = pieces.stream().filter(p -> p.getRight() == 0).count();
        long numOfStraitTop = pieces.stream().filter(p -> p.getTop() == 0).count();
        long numOfStraitBottom = pieces.stream().filter(p -> p.getBottom() == 0).count();

        return (numOfStraitLeft == numOfStraitRight) && (numOfStraitTop == numOfStraitBottom);

    }


    private boolean isSumOfRightAndLeftSidesZero() {

        int sumOfRight = pieces.stream().mapToInt(Piece::getRight).sum();
        int sumOfLeft = pieces.stream().mapToInt(Piece::getLeft).sum();

        return sumOfLeft + sumOfRight == 0;
    }


    private boolean isSumOfTopAndBottomSidesZero() {

        int sumOfTop = pieces.stream().mapToInt(Piece::getTop).sum();
        int sumOfBottom = pieces.stream().mapToInt(Piece::getBottom).sum();

        return sumOfTop + sumOfBottom == 0;
    }

    public boolean isPuzzleValid() {

        boolean result = true;
        if (!isTotalSumZero()) {
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.SUM_NOT_ZERO);
            PuzzleGameManager.addException(error.getError());
//            System.out.println("Puzzle can't be solved, total sum of edges isn't zero");
            result = false;
        }
        if (!isValidNumberOfStraitSides()) {
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.WRONG_NUM_STRAIGHTS);
            PuzzleGameManager.addException(error.getError());
//            System.out.println("Puzzle can't be solved, wrong number of strait edges");
            result = false;
        }

        if(!isSumOfRightAndLeftSidesZero()){
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.WRONG_NUM_STRAIGHTS);
            PuzzleGameManager.addException(error.getError());//            System.out.println("Puzzle can't be solved, total sum of edges isn't zero");
            result = false;
        }

        if(!isSumOfTopAndBottomSidesZero()){
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.WRONG_NUM_STRAIGHTS);
            PuzzleGameManager.addException(error.getError());
//            System.out.println("Puzzle can't be solved, total sum of edges isn't zero");
            result = false;
        }

        List<Corner> missingCorners = findMissingCorners();
        if (!missingCorners.isEmpty()) {
//            PuzzleGameManager.addException("Puzzle can't be solved, there are missing corners: ");
//            System.out.println("Puzzle can't be solved, there are missing corners: ");
            for (Corner corner : missingCorners) {
                ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.MISSING_CORNER, corner.name());
                PuzzleGameManager.addException(error.getError());
//                System.out.println(corner.name());
            }
            result = false;
        }
        return result;
        }
    }
}