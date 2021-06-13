import java.util.Random;

public class OneDNoise
{
    
    public static void main(String[] args)
    {
        long seed = 100;
        int length = 256;
        float[] noise = new float[length];
        Random randomNum = new Random();
        randomNum.setSeed(seed);
        for(int i = 0; i < noise.length; i++)
        {
            noise[i] = randomNum.nextFloat();
        }

        point[] colorPoints = new point[length*11];

        // for (float f : noise) 
        // {
        //     System.out.println(f);
        // }

        // System.out.println();
        // System.out.println();
        // System.out.println();
        // System.out.println();

        float t = 0;
        int xMin = 0;
        while(t < length - 1)
        {
            xMin = (int)(t%256);
            //System.out.println(xMin);
            if(xMin == 255)
            {
                float yValue = Lerp(noise[xMin], noise[0], SmoothStep(t-xMin));
                point color = new point((int)(t * 10), (int)(yValue * 10), 255);
                colorPoints[(int)(t*10)] = color;
            }
            else
            {
                float yValue = Lerp(noise[xMin], noise[xMin + 1], SmoothStep(t-xMin));
                point color = new point((int)(t * 10), (int)(yValue * 30), 255);
                colorPoints[(int)(t*10)] = color;
            }
            
            t+=0.1;
        }
        if(xMin == 255)
        {
            float yValue = Lerp(noise[xMin], noise[0], SmoothStep(t-xMin));
                point color = new point((int)(t * 10), (int)(yValue * 10), 255);
                colorPoints[(int)(t*10)] = color;
        }
        else
        {
            float yValue = Lerp(noise[xMin], noise[xMin + 1], SmoothStep(t-xMin));
                point color = new point((int)(t * 10), (int)(yValue * 10), 255);
                colorPoints[(int)(t*10)] = color;
        }
        ImageGenerator.DrawImage(256*11, colorPoints, "name2");
        
        
    }

    
    private static float Lerp(float low, float hi, float t)
    {
        return (low * (1-t) + hi*t);
    }

    private static float SmoothStep( float t)
    {
        return t * t * (3 - 2 * t);
    }
   
}

