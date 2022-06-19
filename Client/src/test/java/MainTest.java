import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @BeforeEach
    void init() {

        System.out.println("test Client Chat application started " + getClass());

    }

    @Test
    void testSetLogger() {
        //given:
        final String FILE_LOG = "file.log";

        // when:
        //create "file.log"
        Main.setLogger(FILE_LOG);

        // then:
        assertTrue(isDeleteFile(FILE_LOG));

    }

    boolean isDeleteFile(String FILE_LOG) {
        File file = new File(FILE_LOG);
        //to try delete FILE_LOG
        if (file.delete()) return true;
        return false;
    }

    @Test
    void TestReadFile() {
        //given:
        final String SETTINGS = "/Users/avas/IdeaProjects/falco_networkchat/settings.txt";
        final int PORT;
        int expected = 8700;

        // when:
        PORT = Main.readFile(SETTINGS);

        // then:
        assertEquals(expected, PORT);
    }

}
