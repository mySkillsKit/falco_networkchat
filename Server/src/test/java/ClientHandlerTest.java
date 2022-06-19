import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


public class ClientHandlerTest {

    String host = "localhost";
    int port = 1000;
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    {
        try {
            serverSocket = new ServerSocket(port);
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ClientHandler cut;


    @BeforeEach
    void init() {
        cut = new ClientHandler(socket, out, in, 1);
        ClientHandler.clients.add(cut);

        System.out.println("test Server Chat application started " + getClass());

    }

    @Test
    void TestRemoveClient() {

        //given:
        System.out.println(ClientHandler.clients.size()); //1
        int expected = 0;
        // when:
        cut.removeClient(cut);
        int result = ClientHandler.clients.size();

        // then:
        assertEquals(expected, result);

    }
}
