package examen;

public class DiffieHellman
{
    public static int findPrimitiveRoot(int q)
    {
        for (int r = 2; r < q; r++)
        {
            boolean isPrimitive = true;
            for (int i = 1; i < q - 1; i++)
            {
                if (modExp(r, i, q) == 1)
                {
                    isPrimitive = false;
                    break;
                }
            }
            if (isPrimitive)
            {
                return r; // Rădăcina primitivă
            }
        }
        return -1;
    }

    public static int modExp(int base, int exponent, int mod)
    {
        int result = 1;
        base = base % mod;
        while (exponent > 0) {
            if (exponent % 2 == 1)
            {
                result = (result * base) % mod;
            }
            exponent = exponent >> 1;
            base = (base * base) % mod;
        }
        return result;
    }

    public static int generateSharedKey(int YA, int XB, int q)
    {
        return modExp(YA, XB, q);
    }
}