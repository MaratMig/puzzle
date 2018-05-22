import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

public class ParserTests {

    private static final String resourceDir= ("src/test/resources/filesToParse");
    public static String[][] provideData(List<String> fileNamesFromFolder, String filepath) {

        String[][] arObject = new String[fileNamesFromFolder.size()][];
        int index = 0;
        for (String obj : fileNamesFromFolder) {
            arObject[index++] = new String[] { filepath, obj };
        }
        return arObject;
    }

    public static String[][] fileList(String diir) {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(resourceDir))) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
        }
        return provideData(fileNames,resourceDir);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithSimpleMethodSource(String[][] str) {

        System.out.println(str[0][0]);

    }

    static Stream<Arguments> stringProvider() {
        return Stream.of(
                Arguments.of(fileList(resourceDir)));
    }
}



