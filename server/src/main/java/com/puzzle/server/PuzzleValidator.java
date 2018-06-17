package com.puzzle.server;

import com.puzzle.utils.entities.Corner;
import com.puzzle.utils.entities.Piece;
import com.puzzle.utils.utils.ErrorBuilder;
import com.puzzle.utils.utils.ErrorCollection;
import com.puzzle.utils.utils.ErrorTypeEnum;
import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.util.Combinations;

import java.util.*;

public class PuzzleValidator {

    private List<Piece> pieces;
    private ErrorCollection errorCollection = new ErrorCollection();;

    public PuzzleValidator(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<String> getErrorCollection() {
        return errorCollection.getErrors();
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

    public Set<Integer> getPosibleNumRows() {

        int numOfPieces = pieces.size();
        Set<Integer> possibleNumberOfLines = new HashSet<>();

        possibleNumberOfLines.add(1);
        possibleNumberOfLines.add(pieces.size());

        List<Integer> factors = new ArrayList<>();
        factors.addAll(Primes.primeFactors(numOfPieces));

        for(int i=1; i<factors.size();i++){
            Combinations combinations = new Combinations(factors.size(), i);
            Iterator<int[]> iterator = combinations.iterator();
            while (iterator.hasNext()){
                int[] combination = iterator.next();
                int multiple = Arrays.stream(combination).map(k->factors.get(k)).reduce(1, (f1, f2) -> f1*f2);
                possibleNumberOfLines.add(multiple);
            }
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
            }
            if (piece.getTop() == 0 && piece.getRight() == 0) {
                isTRCornerExist = true;
            }
            if (piece.getRight() == 0 && piece.getBottom() == 0) {
                isBRCornerExist = true;
            }
            if (piece.getLeft() == 0 && piece.getBottom() == 0) {
                isBLCornerExist = true;
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
            errorCollection.addError(error.getError());
            result = false;
        }
        if (!isValidNumberOfStraitSides() || getValidNumOfRows().isEmpty()){
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.WRONG_NUM_STRAIGHTS);
            errorCollection.addError(error.getError());
            result = false;
        }
        if(!isSumOfRightAndLeftSidesZero()){
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.SUM_LR_NOT_ZERO);
            errorCollection.addError(error.getError());
            result = false;
        }

        if(!isSumOfTopAndBottomSidesZero()){
            ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.SUM_TB_NOT_ZERO);
            errorCollection.addError(error.getError());
            result = false;
        }

        List<Corner> missingCorners = findMissingCorners();
        if (!missingCorners.isEmpty()) {
            for (Corner corner : missingCorners) {
                ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.MISSING_CORNER, corner.name());
                errorCollection.addError(error.getError());
            }
            result = false;
        }
        return result;
        }
}
