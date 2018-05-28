import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Parser {
    private String fileName;
    private ArrayList<String> puzzelPiecesInput = new ArrayList<String>();
    private ArrayList<String> inputValidationErrors = new ArrayList<String>();
    private int numOfLines;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> parse() throws IOException{
        File file = new File(fileName);
        ArrayList<String> lines = new ArrayList<String>();

        String temp;
        Scanner sc = null;

        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() == 0) continue;
                lines.add(line);
            }

            Iterator<String> it = lines.iterator();
            String[] parts = it.next().split("=", 2);
            String part1 = parts[0].trim();
            numOfLines = Integer.parseInt(parts[1].trim());

            System.out.println(part1 + "^^" + numOfLines);

            while(it.hasNext()){
                temp = it.next().trim();
                if(temp.charAt(0) != '#' ){
                    temp = temp.trim();
                    temp = temp.replaceAll("\\s+","");
                    puzzelPiecesInput.add(temp);
                }
            }
        }
        finally {
            sc.close();
        }
        return puzzelPiecesInput;
    }

    public Boolean checkInputValidity() {
        int[] ids = new int[puzzelPiecesInput.size()];
        int counter = 0;
        if(numOfLines != puzzelPiecesInput.size()){
            inputValidationErrors.add("Wrong number of pieces. Expected: " + numOfLines + " got: " + puzzelPiecesInput.size());
        }
        // print for debug purpose only
        Iterator<String> it = puzzelPiecesInput.iterator();
        while(it.hasNext()){
            int temp = Integer.parseInt(it.next().substring(0,1));
            ids[counter++] = temp;
        }
        System.out.println(ids[8]);

//        puzzelPiecesInput.add(Integer.parseInt(temp.substring(0,1)), temp.substring(1,temp.length()-1));
//        puzzelPiecesInput.add(temp.split("\\s+", 2));


        System.out.println(inputValidationErrors);
        return true;
    }
}
