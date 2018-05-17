import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Parser {
    String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String[]> parse() throws IOException{
        File file = new File(fileName);
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<String[]> puzzelPiecesInput = new ArrayList<String[]>();
        int numOfLines;
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
                    puzzelPiecesInput.add(temp.split("\\s+", 2));
                }
            }
        }
        finally {
            sc.close();
        }
        return puzzelPiecesInput;
    }
}
