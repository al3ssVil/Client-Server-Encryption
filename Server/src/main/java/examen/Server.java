package examen;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server
{
    public static void main(String[] args)
    {
        try (ServerSocket serverSocket = new ServerSocket(1234))
        {
            System.out.println("Server pornit...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true))
            {

                int q = 7, a = 3;
                // Generare aleatorie a cheii secrete (XA) pentru server
                Random random = new Random();
                int XA = random.nextInt(6) + 1;
                System.out.println("Cheia secreta aleatoare generata pentru Server (XA): " + XA);

                int YA = DiffieHellman.modExp(a, XA, q);
                out.println(YA);

                String ybLine = in.readLine();
                if (ybLine == null)
                {
                    System.out.println("Clientul s-a inchis inainte de a trimite YB.");
                    return;
                }

                int YB = Integer.parseInt(ybLine);
                int K = DiffieHellman.generateSharedKey(YB, XA, q);
                System.out.println("Cheia secreta comuna (K) generata de Server: " + K);

                while (true)
                {
                    String optionStr = in.readLine();
                    if (optionStr == null || optionStr.equalsIgnoreCase("exit"))
                    {
                        System.out.println("Clientul a închis conexiunea.");
                        break;
                    }

                    int option;
                    try
                    {
                        option = Integer.parseInt(optionStr);
                    } catch (NumberFormatException e)
                    {
                        System.out.println("Optiune invalida.");
                        continue;
                    }

                    String keyLine = null;
                    if (option == 2)
                    {
                        keyLine = in.readLine();
                        if (keyLine == null) break;
                    }

                    String encryptedMessage = in.readLine();
                    if (encryptedMessage == null) break;

                    String decryptedMessage = "";
                    if (option == 1)
                    {
                        decryptedMessage = CaesarCrypto.decrypt(encryptedMessage, K);
                    } else if (option == 2)
                    {
                        decryptedMessage = TranspositionCipher.decrypt(encryptedMessage, keyLine);
                    }

                    System.out.println("Mesaj criptat primit: " + encryptedMessage);
                    System.out.println("Mesaj decriptat: " + decryptedMessage);
                }

            } catch (IOException e)
            {
                System.out.println("Eroare la comunicare cu clientul: " + e.getMessage());
            }

        } catch (IOException e)
        {
            System.out.println("Eroare la pornirea serverului: " + e.getMessage());
        }

        System.out.println("Serverul s-a inchis.");
    }
}
