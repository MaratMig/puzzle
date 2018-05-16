import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "src\\main\\resources\\inputFile.txt";
        PazzleGame game = new PazzleGame(fileName);
        game.startParser();
//        File file = new File("src\\main\\resources\\inputFile.txt");
//        ArrayList<String> lines = new ArrayList<String>();
//        ArrayList<String[]> pazzelPiecesInput = new ArrayList<String[]>();
//        int numOfLines;
//        String temp;
//        Scanner sc = null;
//
//        try {
//            sc = new Scanner(file);
//            while (sc.hasNextLine()) {
//                String line = sc.nextLine();
//                if (line.length() == 0) continue;
//                lines.add(line);
//            }
//
//            Iterator<String> it = lines.iterator();
//            String[] parts = it.next().split("=", 2);
//            String part1 = parts[0].trim();
//            numOfLines = Integer.parseInt(parts[1].trim());
//
//            System.out.println(part1 + "^^" + numOfLines);
//
//            while(it.hasNext()){
//                temp = it.next().trim();
//                if(temp.charAt(0) != '#' ){
//                    pazzelPiecesInput.add(temp.split("\\s+", 2));
//                }
//            }
//            Iterator<String[]> ot = pazzelPiecesInput.iterator();
//            while(ot.hasNext()){
//                String[] tamp = ot.next();
//                System.out.println(tamp[0]);
//                System.out.println(tamp[1]);
//            }
//
//        }
//        finally {
//            sc.close();
//        }
    }
}
