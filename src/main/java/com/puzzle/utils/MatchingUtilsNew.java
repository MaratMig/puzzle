package com.puzzle.utils;

import com.puzzle.entities.Piece;
import com.puzzle.entities.PuzzleShape;

import java.util.List;
import java.util.stream.Collectors;

public class MatchingUtilsNew {

    public static List<PuzzleShape> getAllBothTR_TL(List<PuzzleShape> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0 && p.getRight()==0).collect(Collectors.toList());
    }

    public static List<PuzzleShape> getAllBothTR_BR(List<PuzzleShape> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0 && p.getBottom()==0).collect(Collectors.toList());
    }

    public static List<PuzzleShape> getAllMatchingForTL(List<PuzzleShape> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0).collect(Collectors.toList());
    }

    public static boolean isPlaceNotOnEdges(int place, int numOfPieces, int col) {
        return !isBLcorner(place, numOfPieces, col) && !isTRcorner(place, col)
                && !isBRcorner(place, numOfPieces) && !isBottomEdge(place, numOfPieces, col)
                && !isTopEdge(place, col) && !isLeftEdge(place, numOfPieces, col)
                && !isRightEdge(place, numOfPieces, col);
    }

    public static boolean isBottomEdge(int place, int numOfPieces, int col) {
        return (place>=numOfPieces - col) && !isBLcorner(place, numOfPieces, col) && !isBRcorner(place, numOfPieces);
    }

    public static boolean isTopEdge(int place, int col) {
        return place>0 && place < col-1;
    }

    public static boolean isLeftEdge(int place, int numOfPieces, int col){
        return place!=0 && (place % col==0) && !isBLcorner(place, numOfPieces, col);
    }

    public static boolean isRightEdge(int place, int numOfPieces, int col){
        return !isTRcorner(place, col) && (place % col==col -1) && !isBRcorner(place, numOfPieces);
    }

    public static boolean isBLcorner(int place, int numOfPieces, int col) {
        return place == numOfPieces - col;
    }

    public static boolean isTRcorner(int place, int col) {
        return place==col - 1;
    }

    public static boolean isBRcorner(int place, int numOfPieces) {
        return place==numOfPieces-1;
    }

    public static List<PuzzleShape> findTRCorners(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream()
                .filter(p -> (p.getTop() == 0 && p.getRight() == 0 && (p.getLeft() + board[place - 1].getRight()) == 0))
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findBRCorners(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getRight() == 0 &&
                        p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findBLCorners(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getLeft() == 0 &&
                        p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForTopEdge(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getTop() == 0 && p.getLeft() + board[place - 1].getRight() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForBottomEdge(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForLeftEdge(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() == 0 && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForRightEdge(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0 && p.getRight() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForInternalPiece(List<PuzzleShape> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForInternalPiece(List<PuzzleShape> freeToUse, PuzzleShape[] alreadyPlaced, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() + alreadyPlaced[place - 1].getRight() == 0
                        && p.getTop() + alreadyPlaced[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findBRCorners(List<PuzzleShape> freeToUse, PuzzleShape[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getRight() == 0 &&
                        p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForBottomEdge(List<PuzzleShape> freeToUse, PuzzleShape[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<PuzzleShape> findMatchForRightEdge(List<PuzzleShape> freeToUse, PuzzleShape[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0 && p.getRight() == 0)
                .collect(Collectors.toList());
    }

}
