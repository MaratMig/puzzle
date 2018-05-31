import java.io.*;

public class OutputFile {

    private static final String fileName = "outputFile.txt";
    private static final String path = "src\\main\\resources\\";





    public static void writeResultToFile(String str) throws IOException {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path + fileName);
            try (OutputStreamWriter writer = new OutputStreamWriter(fos)) {
                writer.write(str);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't create file");
        } finally {
            fos.close();
        }


    }



}
