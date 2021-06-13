package VectorNoise;
import java.util.Random;

public class PerlinNoise 
{
    private int width = 256;
    private int height = width;
    private int MASK = 255;
    private int[] permutationTable = new int[width * 2];
    private Vector3[] numberArray = new Vector3[width];
    public PerlinNoise(long seed)
    {
        Random randomNum = new Random();
        randomNum.setSeed(seed);
        for(int i = 0; i < width; i++)
        {
            float theta = (float)Math.acos(2 * randomNum.nextDouble() - 1);
            float phi = (float)(2 * randomNum.nextDouble() * Math.PI);

            float x = (float)(Math.cos(phi) * Math.sin(theta));
            float y = (float)(Math.sin(phi) * Math.sin(theta));
            float z = (float)(Math.cos(theta));

            numberArray[i] = new Vector3(x, y, z);
            permutationTable[i] = i;
        }

        for(int i = 0; i < width; i++)
        {
            int k = randomNum.nextInt() & MASK;
            int temp = permutationTable[i];
            permutationTable[i] = permutationTable[k];
            permutationTable[k] = temp;
        }

        for(int i = 0; i < width; i++)
        {
            permutationTable[width + i] = permutationTable[i];
        }
    }

    public float Evaluate2002(Vector3 p, Vector3 derivs)
    {
        int xi0 = (int)Math.floor(p.x) & MASK;
        int yi0 = (int)Math.floor(p.y) & MASK;
        int zi0 = (int)Math.floor(p.z) & MASK;

        int xi1 = (xi0 + 1) & MASK;
        int yi1 = (yi0 + 1) & MASK;
        int zi1 = (zi0 + 1) & MASK;

        float tx = p.x - (int)Math.floor(p.x);
        float ty = p.y - (int)Math.floor(p.y);
        float tz = p.z - (int)Math.floor(p.z);

        float u = QuinticSmoothStep(tx);
        float v = QuinticSmoothStep(ty);
        float w = QuinticSmoothStep(tz);

        float x0 = tx;
        float x1 = tx - 1;
        float y0 = ty;
        float y1 = ty - 1;
        float z0 = tz;
        float z1 = tz - 1;

        float a = gradientDotV(hash(xi0, yi0, zi0), x0, y0, z0);
        float b = gradientDotV(hash(xi1, yi0, zi0), x1, y0, z0);
        float c = gradientDotV(hash(xi0, yi1, zi0), x0, y1, z0);
        float d = gradientDotV(hash(xi1, yi1, zi0), x1, y1, z0);
        float e = gradientDotV(hash(xi0, yi0, zi1), x0, y0, z1);
        float f = gradientDotV(hash(xi1, yi0, zi1), x1, y0, z1);
        float g = gradientDotV(hash(xi0, yi1, zi1), x0, y1, z1);
        float h = gradientDotV(hash(xi1, yi1, zi1), x1, y1, z1);

        float du = QuinticSmoothStepDeriv(tx);
        float dv = QuinticSmoothStepDeriv(ty);
        float dw = QuinticSmoothStepDeriv(tz);
        
        float k0 = a; 
        float k1 = (b - a); 
        float k2 = (c - a); 
        float k3 = (e - a); 
        float k4 = (a + d - b - c); 
        float k5 = (a + f - b - e); 
        float k6 = (a + g - c - e); 
        float k7 = (b + c + e + h - a - d - f - g);
        
        derivs.x = du *(k1 + k4 * v + k5 * w + k7 * v * w); 
        derivs.y = dv *(k2 + k4 * u + k6 * w + k7 * v * w); 
        derivs.z = dw *(k3 + k5 * u + k6 * v + k7 * v * w); 
 
        return k0 + k1 * u + k2 * v + k3 * w + k4 * u * v + k5 * u * w + k6 * v * w + k7 * u * v * w; 
    }

    public float EvaluateSmooth(Vector3 p, Vector3 derivs)
    {
        int xi0 = (int)Math.floor(p.x) & MASK;
        int yi0 = (int)Math.floor(p.y) & MASK;
        int zi0 = (int)Math.floor(p.z) & MASK;

        int xi1 = (xi0 + 1) & MASK;
        int yi1 = (yi0 + 1) & MASK;
        int zi1 = (zi0 + 1) & MASK;

        float tx = p.x - (int)Math.floor(p.x);
        float ty = p.y - (int)Math.floor(p.y);
        float tz = p.z - (int)Math.floor(p.z);

        float u = SmoothStep(tx);
        float v = SmoothStep(ty);
        float w = SmoothStep(tz);

        float x0 = tx;
        float x1 = tx - 1;
        float y0 = ty;
        float y1 = ty - 1;
        float z0 = tz;
        float z1 = tz - 1;

        float a = gradientDotV(hash(xi0, yi0, zi0), x0, y0, z0);
        float b = gradientDotV(hash(xi1, yi0, zi0), x1, y0, z0);
        float c = gradientDotV(hash(xi0, yi1, zi0), x0, y1, z0);
        float d = gradientDotV(hash(xi1, yi1, zi0), x1, y1, z0);
        float e = gradientDotV(hash(xi0, yi0, zi1), x0, y0, z1);
        float f = gradientDotV(hash(xi1, yi0, zi1), x1, y0, z1);
        float g = gradientDotV(hash(xi0, yi1, zi1), x0, y1, z1);
        float h = gradientDotV(hash(xi1, yi1, zi1), x1, y1, z1);

        float du = SmoothStepDeriv(tx);
        float dv = SmoothStepDeriv(ty);
        float dw = SmoothStepDeriv(tz);
        
        float k0 = a; 
        float k1 = (b - a); 
        float k2 = (c - a); 
        float k3 = (e - a); 
        float k4 = (a + d - b - c); 
        float k5 = (a + f - b - e); 
        float k6 = (a + g - c - e); 
        float k7 = (b + c + e + h - a - d - f - g);
        
        derivs.x = du *(k1 + k4 * v + k5 * w + k7 * v * w); 
        derivs.y = dv *(k2 + k4 * u + k6 * w + k7 * v * w); 
        derivs.z = dw *(k3 + k5 * u + k6 * v + k7 * v * w); 
 
        return k0 + k1 * u + k2 * v + k3 * w + k4 * u * v + k5 * u * w + k6 * v * w + k7 * u * v * w; 
    }

    private float gradientDotV( 
        int perm, // a value between 0 and 255 
        float x, float y, float z) 
    { 
        switch (perm & 15) 
        { 
        case  0: return  x + y; // (1,1,0) 
        case  1: return -x + y; // (-1,1,0) 
        case  2: return  x - y; // (1,-1,0) 
        case  3: return -x - y; // (-1,-1,0) 
        case  4: return  x + z; // (1,0,1) 
        case  5: return -x + z; // (-1,0,1) 
        case  6: return  x - z; // (1,0,-1) 
        case  7: return -x - z; // (-1,0,-1) 
        case  8: return  y + z; // (0,1,1), 
        case  9: return -y + z; // (0,-1,1), 
        case 10: return  y - z; // (0,1,-1), 
        case 11: return -y - z; // (0,-1,-1) 
        case 12: return  y + x; // (1,1,0) 
        case 13: return -x + y; // (-1,1,0) 
        case 14: return -y + z; // (0,-1,1) 
        case 15: return -y - z; // (0,-1,-1)
        } 
        return 0;
    } 

    private int hash( int x, int y, int z) 
    { 
        return permutationTable[permutationTable[permutationTable[x] + y] + z]; 
    }

    private float Lerp(float low, float hi, float t)
    {
        return (low * (1-t) + hi*t);
    }

    private float SmoothStep( float t)
    {
        return t * t * (3 - 2 * t);
    }

    private float QuinticSmoothStep(float t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private float SmoothStepDeriv(float t)
    {
        return t * (6 - 6 * t);
    }

    private float QuinticSmoothStepDeriv(float t)
    {
        return 30 * t * t * (t * (t - 2) + 1);
    }
}
