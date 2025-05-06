package examen;

public class CaesarCrypto {
    public static String encrypt(String text, int key)
    {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray())
        {
            if (Character.isUpperCase(c))
            {
                result.append((char) ((c - 'A' + key) % 26 + 'A'));
            } else if (Character.isLowerCase(c))
            {
                result.append((char) ((c - 'a' + key) % 26 + 'a'));
            } else
            {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int key)
    {
        return encrypt(text, 26 - (key % 26));
    }
}
