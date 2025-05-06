package examen;

import java.util.Arrays;
import java.util.Comparator;

public class TranspositionCipher
{
    public static String encrypt(String message, String key)
    {
        int col = key.length();
        int row = (int) Math.ceil((double) message.length() / col);
        char[][] matrix = new char[row][col];

        int index = 0;
        char filler = 'a';

        for (int r = 0; r < row; r++)
        {
            for (int c = 0; c < col; c++)
            {
                if (index < message.length())
                {
                    matrix[r][c] = message.charAt(index++);
                } else
                {
                    matrix[r][c] = filler;
                    filler++;
                    if (filler > 'z')
                    {
                        filler = 'a';
                    }
                }
            }
        }

        Integer[] order = getOrder(key);
        StringBuilder result = new StringBuilder();

        for (int pos : order)
        {
            for (int r = 0; r < row; r++)
            {
                result.append(matrix[r][pos]);
            }
        }
        return result.toString();
    }

    public static String decrypt(String message, String key)
    {
        int col = key.length();
        int row = (int) Math.ceil((double) message.length() / col);
        char[][] matrix = new char[row][col];

        Integer[] order = getOrder(key);
        int index = 0;

        for (int pos : order)
        {
            for (int r = 0; r < row; r++)
            {
                if (index < message.length())
                {
                    matrix[r][pos] = message.charAt(index++);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int r = 0; r < row; r++)
        {
            for (int c = 0; c < col; c++)
            {
                result.append(matrix[r][c]);
            }
        }
        return result.toString().replaceAll("a+$", ""); // eliminăm caracterele adăugate pentru padding
    }

    private static Integer[] getOrder(String key)
    {
        Character[] chars = new Character[key.length()];
        for (int i = 0; i < key.length(); i++)
        {
            chars[i] = key.charAt(i);
        }

        Integer[] order = new Integer[key.length()];
        for (int i = 0; i < key.length(); i++)
        {
            order[i] = i;
        }

        Arrays.sort(order, Comparator.comparingInt(i -> chars[i]));
        return order;
    }
}