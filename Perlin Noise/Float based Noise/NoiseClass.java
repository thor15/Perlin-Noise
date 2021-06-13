import java.util.Random;

public class NoiseClass 
{
    private static int width = 256;
    private static int MASK = 255;
    private static int[] permutationTable = new int[width * 2];
    private static float[] numberArray = new float[width];

    public NoiseClass()
    {
        long seed = 2000;
        Random randomNum = new Random();
        randomNum.setSeed(seed);
        

        for(int i = 0; i < width; i++)
        {
            numberArray[i] = randomNum.nextFloat();
            permutationTable[i] = i;
        }

        for(int i = 0; i < width; i++)
        {
            int k = randomNum.nextInt() & MASK;
            int temp = permutationTable[i];
            permutationTable[i] = permutationTable[k];
            permutationTable[k] = temp;
            permutationTable[k + width] = permutationTable[k];
        }
    }

    public float Evaluate(vector p)
    {
        int xi = (int)Math.floor(p.x);
        int yi = (int)Math.floor(p.y);

        float tx = p.x - xi;
        float ty = p.y - yi;

        int rx0 = xi & MASK;
        int rx1 = (rx0 + 1) & MASK;
        int ry0 = yi & MASK;
        int ry1 = (ry0 + 1) & MASK;


        float c00 = numberArray[permutationTable[permutationTable[rx0] + ry0]];
        float c10 = numberArray[permutationTable[permutationTable[rx1] + ry0]];
        float c01 = numberArray[permutationTable[permutationTable[rx0] + ry1]];
        float c11 = numberArray[permutationTable[permutationTable[rx1] + ry1]];


        float sx = SmoothStep(tx);
        float sy = SmoothStep(ty);


        float lerpx0 = Lerp(c00, c10, sx);
        float lerpx1 = Lerp(c01, c11, sx);


        return Lerp(lerpx0, lerpx1, sy);

    }

    private static float SmoothStep( float t)
    {
        return t * t * (3 - 2 * t);
    }

    private static float Lerp(float low, float hi, float t)
    {
        return (low * (1-t) + hi*t);
    }
}
