package com.puzzle.server;

import com.puzzle.common.entities.Piece;
import com.puzzle.common.entities.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleSolver {

    List<Piece> pieces;
    Integer numOfLines;
    Piece[] result;
    Shape[] resultEnlarged;
    private SolveHelper solveHelper;

    public PuzzleSolver(List<Piece> pieceList, Integer numOfLines) {
        this.pieces = pieceList;
        this.result = new Piece[pieces.size()];
        this.numOfLines = numOfLines;
        solveHelper = new SolveHelper(pieces);
    }

    private void initResultEnlarged(){
        resultEnlarged = solveHelper.createPuzzleEnlargedByStraitPieces(numOfLines);
    }

    public boolean tryToSolvePuzzleRectangle(){
        if(isSolutionInShapesExists(numOfLines)){
            createResultFromSolutionInShapes(resultEnlarged);
            return true;
        } else {
            return false;
        }
    }

    public Piece[] getResult() {
        return result;
    }

    private void createResultFromSolutionInShapes(Shape[] solutionInShapes){

        Map<Shape, List<Piece>> existingShapesToPieces = solveHelper.getExistingShapesToIds();

        int columns = pieces.size() / numOfLines + 1;

        int resultIndex=0;
        for(int i =0; i<resultEnlarged.length; i++){
            if(i>columns && i%columns!=0){ //avoid added strait pieces
                List<Piece> matchingPieces = existingShapesToPieces.get(solutionInShapes[i]);
                result[resultIndex] = matchingPieces.get(0);
                matchingPieces.remove(result[resultIndex]);
                resultIndex++;
            }
        }
    }

    public boolean isSolutionInShapesExists(int numOfLines) {

        initResultEnlarged();

        int newNumOfLines = numOfLines+1;
        int bigPuzzleSize = resultEnlarged.length;
        int col = bigPuzzleSize / newNumOfLines;

        //This map will be updated during finding solution process;
        //Taken Shape will be removed (while step forward) / added back(while step backward)
        Map<String, Map<Shape, Long>> indexOfShapes = solveHelper.createIndexOfShapesByLeftTopSides();

        //Map of matching for place Shapes - will be used during "step backward" in case there is no possible option to continue
        Map<Integer, List<Shape>> placeToMatchingShapes = new HashMap<>();

        //Starting place is a second piece in the second line (since puzzle was enlarged by strait pieces)
        int place = col + 1;

        //First list of matching Shapes
        List<Shape> matchingForCurrent = solveHelper.findMatchingShape(place, col, resultEnlarged, indexOfShapes);

        while (place <= bigPuzzleSize - 1 || solveHelper.isNoAnyMatches(indexOfShapes)) {
            while (place <= bigPuzzleSize - 1 && !matchingForCurrent.isEmpty()) {

                if(!matchingForCurrent.isEmpty()) {

                    //It's better to take a shape with max occurrence from all possible options;
                    //It should decrease a chance for missing such shape at the next steps.
                    Shape currentShape = solveHelper.findShapeWithMaxOccurrenceInIndex(matchingForCurrent, indexOfShapes);

                    //currently used Shape should be removed from possible options
                    matchingForCurrent.remove(currentShape);
                    solveHelper.removeFromIndex(currentShape, indexOfShapes);
                    resultEnlarged[place] = currentShape;

                    placeToMatchingShapes.put(place, matchingForCurrent);
                    place=solveHelper.nextPlace(place, col, bigPuzzleSize);
                }
                if(place<bigPuzzleSize) {//no need to find a match after last iteration
                    matchingForCurrent = solveHelper.findMatchingShape(place, col, resultEnlarged, indexOfShapes);
                }
            }
            if (place >= bigPuzzleSize) {
                return true; //Puzzle is solved in Shapes
            } else { //Step backward: return previously selected shape to index for future use; get other options for this place
                while (matchingForCurrent.isEmpty() && place>col+1) {
                        place = solveHelper.previousPlace(place, col);
                        solveHelper.returnPreviousChoiceToIndex(resultEnlarged[place], indexOfShapes);
                        resultEnlarged[place] = null;
                        matchingForCurrent = placeToMatchingShapes.get(place);
                }
                if (place==col + 1 && matchingForCurrent.isEmpty()) {//Step backward returned till first selected shape and no any other option
                    return false;
                }
            }
        }
        return false;
    }
}
