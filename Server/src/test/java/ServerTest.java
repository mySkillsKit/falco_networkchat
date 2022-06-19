
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTest {
    String host = "localhost";
    int port = 8700;
    Server cut;

    @BeforeEach
    void init() {
        cut = new Server(port);
        System.out.println("test Server Chat application started " + getClass());
    }

    @Test
    void TestStart() throws IOException {
        //given:
        try {
            ServerSocket serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // when:
        //start ServerSocket
        cut.start();

        Socket clientSocket = new Socket(host, port);
        System.out.println(clientSocket);

        // then:
        //if the clientSocket isn't close, then the serverSocket is started
        assertTrue(!clientSocket.isClosed());
        clientSocket.close();

    }

}
