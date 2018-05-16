import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src\\main\\resources\\inputFile.txt");
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
            int part2 = Integer.parseInt(parts[1].trim());

            System.out.println(part1 + "^^" + part2);

            while(it.hasNext()){
                temp = it.next().trim();
                if(temp.charAt(0) != '#' ){
                    String[] localParts = temp.split("\\s+", 2);
                    System.out.println(localParts[0] + "##" + localParts[1]);
                }
            }
        }
        finally {
            sc.close();
        }
    }
}
