package com.puzzle.fileHandlers;

import com.puzzle.Piece;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Parser {
    private Path fileName;
    private ArrayList<String[]> puzzelPiecesInput = new ArrayList<>();
    private ArrayList<Integer[]> puzzelPiecesInputInteger = new ArrayList<>();

    public ArrayList<String> getInputValidationErrors() {
        return inputValidationErrors;
    }

    private ArrayList<String> inputValidationErrors = new ArrayList<>();
    private ArrayList<Piece> puzzlePieces = new ArrayList<>();
    private int numOfLines;
    private String fileHeader;
    private Boolean InputValidity = false;

    public Parser(Path fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Piece> parse() {
        Scanner sc = null;
        ArrayList<String> lines = new ArrayList<>();
        String[] parts;
        File file = new File(fileName.toFile().toString());

        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() == 0) continue;
                lines.add(line);

            }
        } catch(FileNotFoundException e) {
            System.out.println("Can't open file");
        } finally {
            sc.close();
        }
        Iterator<String> it = lines.iterator();
        try{
            parts = it.next().split("=", 2);
            fileHeader = parts[0].trim();
            numOfLines = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            inputValidationErrors.add("Incorrect file header, no number of lines info");
            numOfLines = -1;
        }

        getStringPuzzlePiecesInput(it);
        checkInputValidity();
        if(InputValidity){
            return puzzlePieces;
        }else {
            return null;
        }

    }

    public Boolean checkInputValidity() {
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> wrongElementIDs = new ArrayList<>();
        ArrayList<Integer> correctElementIDs = new ArrayList<>();
        ArrayList<Integer> missingElementIDs = new ArrayList<>();

        if(numOfLines != puzzelPiecesInput.size()){
            inputValidationErrors.add("Wrong number of pieces. Expected: " + numOfLines + " got: " + puzzelPiecesInput.size());
        }

        Iterator<String[]> it = puzzelPiecesInput.iterator();

        while(it.hasNext()){
            Integer[] intTemp = checkIfAllNumbers(it.next());
            if(intTemp != null){
                ids.add(intTemp[0]);
                puzzelPiecesInputInteger.add(intTemp);
            }
        }

        for(int i = 0; i < ids.size(); i++){
            if(ids.get(i) < 1 || ids.get(i) > numOfLines){
                wrongElementIDs.add(ids.get(i));
            }else {
                correctElementIDs.add(ids.get(i));
            }
        }
        for(int i = 1; i <= numOfLines; i++){
            if(!correctElementIDs.contains(i)){
                missingElementIDs.add(i);
            }
        }

        ArrayList<Integer[]> piecesToRemove = new ArrayList<>();
        for (Integer[] pieces: puzzelPiecesInputInteger) {
            if(pieces.length != 5){
                inputValidationErrors.add("Wrong elements format (not having 4 edges):" + Arrays.toString(pieces));
                piecesToRemove.add(pieces);
            }
        }

        for (Integer[] pieces: puzzelPiecesInputInteger) {
            for(int i = 1; i < pieces.length; i++){
                if(pieces[i] > 1 || pieces[i] < -1){
                    inputValidationErrors.add("Wrong elements format (having edges which are not 0, 1, -1):" + Arrays.toString(pieces));
                    piecesToRemove.add(pieces);
                }
            }
        }

        for(int i = 0; i < piecesToRemove.size(); i++){
            puzzelPiecesInputInteger.remove(piecesToRemove.get(i));
            if(correctElementIDs.contains(piecesToRemove.get(i)[0])){
                correctElementIDs.remove(piecesToRemove.get(i)[0]);
                missingElementIDs.add(piecesToRemove.get(i)[0]);
            }
        }

        if(numOfLines == correctElementIDs.size() && inputValidationErrors.size() == 0){
            for (Integer[] pieces: puzzelPiecesInputInteger) {
                ArrayList<Integer> tempList = new ArrayList<>(Arrays.asList(pieces));
                int[] tempArr = new int[tempList.size()-1];
                for(int i = 0; i < tempArr.length; i++) {
                    tempArr[i] = tempList.get(i + 1);
                }
                puzzlePieces.add(new Piece(tempList.get(0), tempArr));
            }
            // TODO: remove this comment beofre 15/06/2018
//            for (Piece p: puzzlePieces) {
//                System.out.println(p.toString());
//            }
            InputValidity = true;
        }

//        Collections.sort(correctElementIDs);
//        System.out.println("==============");
//        System.out.println(correctElementIDs.toString());
//        System.out.println("==============");
//
//        System.out.println(correctElementIDs);
//        System.out.println(wrongElementIDs);
//        System.out.println(missingElementIDs);
//        System.out.println(inputValidationErrors);
        return InputValidity;
    }

    private void getStringPuzzlePiecesInput(Iterator<String> it) {
        String temp;

        while(it.hasNext()){
            temp = it.next().trim();
            if(temp.charAt(0) != '#' ){
                temp = temp.trim();
                temp = temp.replaceAll("\\s+"," ");
                String[] tempPieces = temp.split(" ");
                puzzelPiecesInput.add(tempPieces);
            }
        }
    }

    private Integer[] checkIfAllNumbers(String[] next) {
        Integer[] intTemp = new Integer[next.length];
        int counter = 0;
        for (String str: next) {
            Integer temp;
            try{
                temp = Integer.parseInt(str);
                intTemp[counter] = temp;
            }catch (NumberFormatException e){
                inputValidationErrors.add("NumberFormatException was thrown !" + Arrays.toString(next));
                intTemp = null;
                break;
            }
            counter++;
        }
        return intTemp;
    }
}