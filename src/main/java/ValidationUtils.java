import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.primes.Primes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidationUtils {

    public static Set<Integer> getPosibleNumRows(List<Piece> pieces) {
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

    private static boolean isTotalSumZero(List<Piece> pieces) {
        int sum = 0;
        for (Piece piece : pieces) {
            sum += piece.getSum();
        }
        return sum == 0;
    }

    private static List<Corner> findMissingCorners(List<Piece> pieces) {
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
    private static boolean isValidNumberOfStraitSides(List<Piece> pieces) {
        long numOfStraitLeft = pieces.stream().filter(p -> p.getLeft() == 0).count();
        long numOfStraitRight = pieces.stream().filter(p -> p.getRight() == 0).count();

        long numOfStraitTop = pieces.stream().filter(p -> p.getTop() == 0).count();
        long numOfStraitBottom = pieces.stream().filter(p -> p.getBottom() == 0).count();

        return (numOfStraitLeft == numOfStraitRight) && (numOfStraitTop == numOfStraitBottom);
    }

    public static boolean isPuzzleValid(List<Piece> pieces) {

        boolean result = true;
        if (!isTotalSumZero(pieces)) {
            System.out.println("Puzzle can't be solved, total sum of edges isn't zero");
            result = false;
        }
        if (!isValidNumberOfStraitSides(pieces)) {
            System.out.println("Puzzle can't be solved, wrong number of strait edges");
            result = false;
        }
        List<Corner> missingCorners = findMissingCorners(pieces);
        if (!missingCorners.isEmpty()) {
            System.out.println("Puzzle can't be solved, there are missing corners: ");
            for (Corner corner : missingCorners) {
                System.out.println(corner.name());
            }
            result = false;
        }
        return result;
    }
}