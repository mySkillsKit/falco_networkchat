import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        final String FILE_LOG = "fileClient.log";
        final String SETTINGS = "settings.txt";
        final int PORT;
        String host = "localhost";


        setLogger(FILE_LOG);

        logger.info("Start Client Chat application");

        // getting port
        PORT = readFile(SETTINGS);
        logger.info("Read file settings.txt: Port = " + PORT);

        // establish the connection
        Socket socket = new Socket(host, PORT);
        logger.info("Client socket = " + socket);
        Client client = new Client(socket);
        client.start();
    }

    static void setLogger(String FILE_LOG) {
        try {
            // This block configure the logger with handler and formatter
            FileHandler fileHandler = new FileHandler(FILE_LOG, true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int readFile(String SETTINGS) {
        Optional<String> port = null;
        try {
            port = Files.lines(Paths.get(SETTINGS)).findFirst();
        } catch (IOException e) {
            logger.info("Check File " + SETTINGS);
            e.printStackTrace();
        }
        return Integer.parseInt(port.get());
    }

}