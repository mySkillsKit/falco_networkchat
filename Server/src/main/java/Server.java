
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private int count;

    public Server(int port) {
        this.port = port;
    }

    public void start() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Main.logger.info("Create ServerSocket / server is listening on port " + port);

            // running infinite loop for getting
            // client request
            while (true) {
                // Accept the incoming request
                Socket socket = serverSocket.accept();
                count++;
                Main.logger.info("New client N" + count + " request received: " + socket);

                // obtain input and output streams
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(socket.getInputStream()));

                Main.logger.info("Creating a new handler for this client N" + count);
                // Create a new handler object for handling this request.
                ClientHandler client = new ClientHandler(socket, out, in, count);
                // Create a new Thread with this object and start the thread.
                new Thread(client).start();

                Main.logger.info("Adding this client N" + count + " to active client list");
                // add this client to active clients list
                ClientHandler.clients.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


