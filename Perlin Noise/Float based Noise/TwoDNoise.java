import java.io.Console;
import java.util.Random;

public class TwoDNoise 
{
    private static int imageWidth = 256;
    private static point[] colorsPoints = new point[imageWidth * imageWidth];
    private static point[] secondPoints = new point[imageWidth * imageWidth];
    private static point[] pointsToPrint = new point[imageWidth * imageWidth];

    public static void main(String[] args)
    {
        // long seed = 2000;
        // Random randomNum = new Random();
        // randomNum.setSeed(seed);
        // float[] numberArray = new float[width * width];

        // point[] colorsPoints = new point[2000];

        // for(int i = 0; i < numberArray.length; i++)
        // {
        //         numberArray[i] = randomNum.nextFloat();
        // }
        // float change = 1/199;
        // float point = 0;
        // int yValue = 0;
        // for(int i = 0; i < colorsPoints.length; i++)
        // {
        //     int positionInArray = (int)point;
        //     if(point % 99 == 0)
        //     {
        //         float xValue1 = Lerp(numberArray[positionInArray], numberArray[0], SmoothStep(point));
        //         float xValue2 = Lerp(numberArray[10], numberArray[0], SmoothStep(point) );
        //         float colorValue = Lerp(xValue1, xValue2, yValue);
        //         colorsPoints[i] = new point((int)(point * 199), yValue, (int)colorValue);
        //     }
        //     else
        //     {
        //         float xValue1 = Lerp(numberArray[positionInArray], numberArray[positionInArray + 1], SmoothStep(point));
        //         float xValue2 = Lerp(numberArray[positionInArray + 10], numberArray[positionInArray + 11], SmoothStep(point));
        //         float colorValue = Lerp(xValue1, xValue2, yValue);
        //         colorsPoints[i] = new point((int)(point * 199), yValue, (int)colorValue);
        //     }
        //     point += change;
        //     if(point % 9 == 0)
        //     {
        //         yValue++;
        //     }
        // }
        //generateWhiteNoise();
        //generateValueNoise(.05f);
        generateFractalSum(.08f, 3.5f, 2, 0.5f, 5);
        //generateTurbulence(0.02f,2f,0.5f,5);
        //generateFractalSum2(.02f, 2f, 5, 0.5f, 5);
        //generateWood(.05f, 5f);
        // float frequency = .01f;
        // float amplitude = 5;
        // for(int frequencyMultiplier = 1; frequencyMultiplier < 5; frequencyMultiplier *= 2)
        // {
        //     amplitude = 5;
        //     for(int amplitudeMultipier = 5; amplitudeMultipier < 21; amplitudeMultipier *= 2)
        //     {
        //         generateFractalSum(frequency, 1.8f, amplitude, 0.35f, 5);
        //         String name = "fractualSum_frequency" + frequency + "_amplitude" + amplitude;
        //         ImageGenerator.DrawImage(imageWidth, colorsPoints, name); 
        //         amplitude *= 2;
        //     }
        //     frequency *= 2;
        // }
        
        // for(int i = 0; i < secondPoints.length; i++)
        // {
        //     pointsToPrint[i] = new point(secondPoints[i].x, secondPoints[i].y, secondPoints[i].colorOffset * colorsPoints[i].colorOffset);
        // }
        //generateWhiteNoise();
        ImageGenerator.DrawImage(imageWidth, colorsPoints, "2D_Noise");
        
    }
    
    private static float Lerp(float low, float hi, float t)
    {
        return (low * (1-t) + hi*t);
    }

    
    private static float SmoothStep( float t)
    {
        return t * t * (3 - 2 * t);
    }
    
    private static void generateWhiteNoise()
    {
        long seed = 2003;
        Random randomNum = new Random();
        randomNum.setSeed(seed);

        for(int height = 0; height < imageWidth ; height++)
        {
            for(int length = 0; length < imageWidth; length++)
            {
                point color = new point(length, height, (float)randomNum.nextDouble());
                colorsPoints[height* 256 + length] = color;
            }
        }
    }

    private static void generateValueNoise(float frequency)
    {
        NoiseClass noise = new NoiseClass();
        for(int height = 0; height < imageWidth ; height++)
        {
            for(int length = 0; length < imageWidth; length++)
            {
                vector pointVector = new vector((float)length, (float)height);
                point color = new point(length, height, noise.Evaluate(pointVector.multiply(frequency)));
                colorsPoints[height* 256 + length] = color;
            }
        }
    }

    private static void generateFractalSum(float frequency, float frequencyMultiplier,  float amplitude, float amplitudeMultipier,int layers)
    {
        NoiseClass noise = new NoiseClass();
        float maxNoise = 0;
        for(int height = 0; height < imageWidth ; height++)
        {
            for(int length = 0; length < imageWidth; length++)
            {
                vector pointVector = new vector((float)length, (float)height);
                pointVector = pointVector.multiply(frequency);
                float amplitudeNow = amplitude;
                colorsPoints[height * imageWidth + length] = new point(length, height, 0);
                for(int layer = 0; layer < layers; layer++)
                {
                // noiseMap[j * imageWidth + i] += noise.eval(pNoise) * amplitude; 
                // pNoise *= frequencyMult; 
                // amplitude *= amplitudeMult;
                    
                    colorsPoints[height * imageWidth + length].colorOffset += noise.Evaluate(pointVector) * amplitudeNow;
                    pointVector = pointVector.multiply(frequencyMultiplier);
                    amplitudeNow *= amplitudeMultipier;    
                }
                if(colorsPoints[height * imageWidth + length].colorOffset > maxNoise)
                {
                    maxNoise = colorsPoints[height * imageWidth + length].colorOffset;
                }
            }
        }
        for (point p : colorsPoints) 
        {
            p.colorOffset /= maxNoise;    
        }

        // point color = new point(length, height, (int)(noise.Evaluate(pointVector.multiply(frequency))*255));
        // colorsPoints[height* 256 + length] = color;
    }

    private static void generateTurbulence(float frequency, float frequencyMultiplier, float amplitudeMultipier,int layers)
    {
        NoiseClass noise = new NoiseClass();
        float maxNoise = 0;
        for(int height = 0; height < imageWidth ; height++)
        {
            for(int length = 0; length < imageWidth; length++)
            {
                vector pointVector = new vector((float)length, (float)height);
                pointVector = pointVector.multiply(frequency);
                float amplitude = 1;
                colorsPoints[height * imageWidth + length] = new point(length, height, 0);
                for(int layer = 0; layer < layers; layer++)
                {
                // noiseMap[j * imageWidth + i] += noise.eval(pNoise) * amplitude; 
                // pNoise *= frequencyMult; 
                // amplitude *= amplitudeMult;
                    
                    colorsPoints[height * imageWidth + length].colorOffset += Math.abs(2 * noise.Evaluate(pointVector)-1) * amplitude;
                    pointVector = pointVector.multiply(frequencyMultiplier);
                    amplitude *= amplitudeMultipier;    
                }
                if(colorsPoints[height * imageWidth + length].colorOffset > maxNoise)
                {
                    maxNoise = colorsPoints[height * imageWidth + length].colorOffset;
                }
            }
        }
        for (point p : colorsPoints) 
        {
            p.colorOffset /= maxNoise;    
        }
    }

    private static void generateWood(float frequency, float amplitude)
    {
        NoiseClass noise = new NoiseClass();

        for(int height = 0; height < imageWidth ; height++)
        {
            for(int length = 0; length < imageWidth; length++)
            {
                point color = new point(length, height, 0);
                colorsPoints[height* 256 + length] = color;
                float g = noise.Evaluate(new vector((float)length, (float)height).multiply(frequency));
                g *= amplitude;
                colorsPoints[height* 256 + length].colorOffset = g - (int)g;
            }
        }
    }


    private static void generateFractalSum2(float frequency, float frequencyMultiplier,  float amplitude, float amplitudeMultipier,int layers)
    {
        NoiseClass noise = new NoiseClass();
        float maxNoise = 0;
        for(int height = 0; height < imageWidth ; height++)
        {
            for(int length = 0; length < imageWidth; length++)
            {
                vector pointVector = new vector((float)length, (float)height);
                pointVector = pointVector.multiply(frequency);
                float amplitudeNow = amplitude;
                secondPoints[height * imageWidth + length] = new point(length, height, 0);
                for(int layer = 0; layer < layers; layer++)
                {
                // noiseMap[j * imageWidth + i] += noise.eval(pNoise) * amplitude; 
                // pNoise *= frequencyMult; 
                // amplitude *= amplitudeMult;
                    
                    secondPoints[height * imageWidth + length].colorOffset += noise.Evaluate(pointVector) * amplitudeNow;
                    pointVector = pointVector.multiply(frequencyMultiplier);
                    amplitudeNow *= amplitudeMultipier;    
                }
                if(secondPoints[height * imageWidth + length].colorOffset > maxNoise)
                {
                    maxNoise = secondPoints[height * imageWidth + length].colorOffset;
                }
            }
        }
        for (point p : secondPoints) 
        {
            p.colorOffset /= maxNoise;    
        }

        // point color = new point(length, height, (int)(noise.Evaluate(pointVector.multiply(frequency))*255));
        // colorsPoints[height* 256 + length] = color;
    }

    
}
