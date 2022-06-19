
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        // obtaining input and out streams
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Input your nickname:");
            String msg = scanner.nextLine();
            out.println(msg);
            System.out.println("Enter message:");

            while (true) {
                msg = scanner.nextLine();
                out.println(msg);
                Main.logger.info(msg);
                if ("exit".equals(msg)) break;

                //readMessage thread
                Thread readMsg = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!socket.isClosed()) {
                            try {
                                // read the message sent to this client
                                String msg = in.readLine();
                                if (msg == null) break;
                                System.out.println("SERVER: " + msg);
                                Main.logger.info(msg);
                            } catch (IOException e) {
                                break;
                            }
                        }
                    }
                });
                readMsg.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
