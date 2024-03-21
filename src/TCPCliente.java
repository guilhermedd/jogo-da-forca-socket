import java.io.*;
import java.util.Properties;
import java.net.Socket;
import java.util.Scanner;

public class TCPCliente {
    public static void main(String[] args) throws IOException {
        int serverPort = 0;
        String serverAddress = null;
        try {
            Properties properties = new Properties();
            FileInputStream file = new FileInputStream("network.peroperties");
            properties.load(file);



            System.out.println(properties.getProperty("SERVER_PORT"));

            serverPort = Integer.getInteger(properties.getProperty("SERVER_PORT"));
            serverAddress = properties.getProperty("SERVER_ADDRESS");
        } catch (Exception e) {
            System.out.println(e);
        }

        Scanner scanner = new Scanner(System.in);
        Socket server = null;

        try {
            server = new Socket(serverAddress, serverPort);

            // Talk to server
            BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter out = new PrintWriter(server.getOutputStream());

            String response = null;


            while (true) {
                while(response == null) {
                    response = reader.readLine();
                }
                System.out.println(response);
                Character letter;

                if (response.startsWith("You guessesd")) {
                    System.out.println(response);
                    server.close();
                    break;
                }

                while (response.startsWith("You already guessed that letter!")) {
                    letter = scanner.nextLine().charAt(0);
                    out.println(letter);
                    response = reader.readLine();
                }

                letter = scanner.nextLine().charAt(0);
                out.println(letter);
            }

        } catch (IOException e) {
            server.close();
        }

    }
}
