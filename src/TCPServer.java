import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class TCPServer {
    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("network.peroperties");
        properties.load(file);

        int serverPort = Integer.getInteger((String) properties.get("SERVER_PORT"));
        ServerSocket serverSocket = null;
        Socket client = null;

        Forca forca = new Forca("coisa boa");

        try {
            serverSocket = new ServerSocket(serverPort); // Create a server socket
            client = new Socket(); // Socket to connect to this server

            System.out.println("Server started on port \nWaiting for client...");

            while (true) {
                // wait for client
                client = serverSocket.accept();
                System.out.println("Client connected!" + client);

                // thread to deal with client
                ClientHandler handler = new ClientHandler(client, forca);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ClientHandler implements Runnable {
        private Socket client;
        private Forca forca;

        public ClientHandler(Socket client, Forca forca) {
            this.client = client;
            this.forca = forca;
        }

        @Override
        public void run() {
            try {
                // Open flow of communication
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); // Read from the client
                PrintStream out = new PrintStream(client.getOutputStream()); // Write to the client

                String rules = "Rules:\n- Only send 1 letter per turn\n";
                String sendForca = rules + "Let's start the game!\nWord: " + forca.getWord();

                out.println(sendForca);

                String inputLine;

                while ((inputLine = in.readLine())!= null) {
                    String guess = forca.guess(inputLine.charAt(0));

                    while (guess.equals("You already guessed that letter!")) {
                        out.println("You already guessed that letter!");
                        inputLine = in.readLine();
                        guess = forca.guess(inputLine.charAt(0));
                    }

                    out.println(guess);

                    if (guess.startsWith("You guessesd")) client.close();
                    System.out.println("Client " + client + " guessed the word!");
                }
            } catch (IOException e) {
                try {
                    client.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
