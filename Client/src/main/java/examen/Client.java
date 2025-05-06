package examen;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static void main(String[] args)
    {
        try (Socket socket = new Socket("localhost", 1234);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in))
        {

            int q = 7, a = 3;
            Random random = new Random();
            int XB = random.nextInt(6) + 1;
            System.out.println("Cheia secreta aleatoare generata pentru Client (XB): " + XB);

            int YB = DiffieHellman.modExp(a, XB, q);
            out.println(YB);

            String yaLine = in.readLine();
            if (yaLine == null)
            {
                System.out.println("Serverul a inchis conexiunea inainte sa trimita YA.");
                return;
            }

            int YA = Integer.parseInt(yaLine);
            int K = DiffieHellman.generateSharedKey(YA, XB, q);

            System.out.println("Cheia secreta comuna (K) generata de Client: " + K);

            while (true)
            {
                System.out.println("\nAlege algoritmul (sau scrie 'exit' pentru a iesi):");
                System.out.println("1 - Caesar");
                System.out.println("2 - Transpozitie");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit"))
                {
                    out.println("exit");
                    break;
                }

                int option;
                try
                {
                    option = Integer.parseInt(input);
                } catch (NumberFormatException e)
                {
                    System.out.println("Optiune invalida.");
                    continue;
                }

                out.println(option);

                System.out.print("Introdu mesajul: ");
                String message = scanner.nextLine();
                String encrypted = "";

                if (option == 1)
                {
                    encrypted = CaesarCrypto.encrypt(message, K);
                } else if (option == 2)
                {
                    String key;
                    while (true) {
                        System.out.print("Introdu cheia (doar litere, toate diferite): ");
                        key = scanner.nextLine().toLowerCase();

                        if (!key.matches("[a-zA-Z]+")) {
                            System.out.println("Cheia trebuie sa contina DOAR litere.");
                            continue;
                        }

                        boolean areAllUnique = key.chars().distinct().count() == key.length();
                        if (!areAllUnique) {
                            System.out.println("Toate literele din cheie trebuie sa fie diferite.");
                            continue;
                        }

                        break;
                    }

                    out.println(key);
                    encrypted = TranspositionCipher.encrypt(message, key);
                } else
                {
                    System.out.println("Optiune invalida.");
                    continue;
                }

                out.println(encrypted);
                System.out.println("Mesaj criptat trimis: " + encrypted);
            }

        } catch (ConnectException ce)
        {
            System.out.println("Nu se poate conecta la server: Serverul nu este pornit.");
        } catch (IOException e)
        {
            System.out.println("Eroare de comunicatie cu serverul: " + e.getMessage());
        }
        System.out.println("Clientul s-a inchis.");
    }
}
