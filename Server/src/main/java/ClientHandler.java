import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int count;
    private String nickName;

    static ArrayList<ClientHandler> clients = new ArrayList<>();

    // constructor
    public ClientHandler(Socket socket, PrintWriter out, BufferedReader in, int count) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.count = count;
        this.nickName = null;
    }

    @Override
    public void run() {
        String msg;
        try {
            while (true) {
                try {
                    nickName = in.readLine();
                    msg = this + " connect! " + " All clients = " + clients.size();
                    sendMsgToAllClients(msg);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            msg = in.readLine();
            while (msg != null) {
                msg = this + " replied: " + msg;
                sendMsgToAllClients(msg);
                msg = in.readLine();
                if (msg.equals("exit")) {
                    msg = this + " disconnect! " + "All clients = " + (clients.size() - 1);
                    sendMsgToAllClients(msg);
                    break;
                }
            }
            // closing resources
            out.close();
            in.close();
            socket.close();
            Main.logger.info("Close socket Client N" + count + ": " + socket);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            removeClient(this);
        }
    }

    public void sendMsgToAllClients(String msg) {
        for (ClientHandler client : clients) {
            client.out.println(msg);
        }
        Main.logger.info(msg);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    @Override
    public String toString() {
        return "Client №" + count + " [nickName:" + nickName + "]";
    }
}

