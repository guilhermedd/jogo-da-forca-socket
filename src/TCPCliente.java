import java.io.*;
import java.util.Properties;
import java.net.Socket;
import java.util.Scanner;

public class TCPCliente {
    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("network.peroperties");
        properties.load(file);

        Scanner scanner = new Scanner(System.in);


        int serverPort = Integer.getInteger(properties.getProperty("SERVER_PORT"));
        String serverAddress = properties.getProperty("SERVER_ADDRESS");

        Socket server = null;

        try {
            server = new Socket(serverAddress, serverPort);

            // Talto to server
            BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter out = new PrintWriter(server.getOutputStream());

            String response;


            while ((response = reader.readLine())!= null) {
                System.out.println(response);
                String word;

                if (response.startsWith("You guessesd")) {
                    server.close();
                    break;
                }

                while (response.startsWith("You already guessed that letter!")) {
                    System.out.println("Type the letter: ");
                    word = scanner.nextLine();
                }

                word = scanner.nextLine();
                while (word.length() != 1) {
                    System.out.println("You must type 1 letter: ");
                    word = scanner.nextLine();
                }

                out.println(word);
            }

        } catch (IOException e) {
            server.close();
        }

    }
}
