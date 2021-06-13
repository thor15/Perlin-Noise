package VectorNoise;

public class CreatePerlinNoiseBAK
{
    private static int width = 512;
    private static int height = 512;
    private static Vector3 derivs = new Vector3(0, 0, 0);
    private static float[] noiseMap = new float[width * height];
    private static float[] noiseMap2 = new float[width * height];
    private static point[] pointList = new point[width * height];
    public static void main(String[] args)
    {
        PerlinNoise noise = new PerlinNoise(230);

        Polymesh poly = Polymesh.CreatePolymesh(3, 3, 30, 30);

        
        for(int i = 0; i < poly.numVerticies; i++)
        {
            Vector3 p = new Vector3((float)(poly.verticies[i].x +.5), 0, (float)(poly.verticies[i].z + 0.5));
            poly.verticies[i].y = noise.Evaluate2002(p, derivs);
            Vector3 tangent = new Vector3(1, derivs.x, 1);
            Vector3 bitangent = new Vector3(0, derivs.z, 1);
            poly.normals[i] = new Vector3(-derivs.x, 1, -derivs.z);
            poly.normals[i] = ManageVector3.NormalizeVector3(poly.normals[i]);
        }

        

        

        
        
        //generateWhiteNoise2002(noise);
        //generateWhiteNoiseSmooth(noise);
        long seed = 2389;
        float frequency = .00625f;
        for(int i = 0; i < 3; i++)
        {
            float amplitude = 2.5f;
            for(int j = 0; j < 3; j++)
            {
                String name = "perlin_wooddDifferColor_" + frequency + "_" + amplitude + "_"+seed;
                generateWoodNegative(frequency, amplitude, seed);
                ImageGenerator.DrawImage(width, pointList, name);
                amplitude +=2.5;
            }
            frequency *= 2;
        }
        

        // for(int i = 0; i < height; i ++)
        // {
        //     for(int j = 0; j < width; j++)
        //     {
        //         pointList[i * width + j] = new point(j, i, noiseMap[i * width + j]);
        //     }
        // }

        
        // float freq = 1f/128f;
        // float amplitude = 1;
        //generateFractalSum(noise, freq, amplitude);
        ///ImageGenerator.DrawImage(512, pointList, "perlin_noise_" + freq + "_" + amplitude);
        // for(int i = 0; i < 3; i ++)
        // {
        //     amplitude = 1;
        //     for(int j = 0; j < 3; j++)
        //     {
        //         generateFractalSum(noise, freq, amplitude);
        //         ImageGenerator.DrawImage(512, pointList, "perlin_noise_" + freq + "_" + amplitude);
        //         amplitude *= .5f;
        //     }
        //     freq *= 2;
        // }
        
        //ImageGenerator.DrawImage(512, pointList2, "perlin_noise_smooth");
    }
    
    public static void generateWhiteNoise2002(PerlinNoise noise)
    {
        float divisor = 1/64f;
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i < width; i++)
            {
                
                Vector3 cordVector3 = new Vector3(i, 0, j);
                noiseMap[j * width + i] = (noise.Evaluate2002(ManageVector3.multiplyVector3( cordVector3, divisor), derivs)
                 + 1) * .5f;
            }
        }
    }

    public static void generateWhiteNoiseSmooth(PerlinNoise noise)
    {
        float divisor = 1/64f;
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i < width; i++)
            {
                
                Vector3 cordVector3 = new Vector3(i, 0, j);
                noiseMap2[j * width + i] = (noise.EvaluateSmooth(ManageVector3.multiplyVector3( cordVector3, divisor), derivs)
                 + 1) * .5f;
            }
        }
    }

    public static void generateFractalSum(float frequency, float amplitude, long seed)
    {
        PerlinNoise noise = new PerlinNoise(seed);
        int numLayers = 5; 
        float maxVal = 0; 
        for (int j = 0; j < height; j++) 
        { 
            for (int i = 0; i < width; i++) 
            { 
                float fractal = 0;  
                Vector3 pt = new Vector3(i, 0, j);
                float divisor = (1f / 128.f);
                float amp = 1;
                pt = ManageVector3.multiplyVector3(pt, divisor); 
                for (int k = 0; k < numLayers; k++) 
                { 
                    fractal += (1 + noise.Evaluate2002(pt, derivs)) * 0.5 * amp; 
                    ManageVector3.multiplyVector3(pt, 2); 
                    amp *= 0.5; 
                } 
                if (fractal > maxVal)
                {
                    maxVal = fractal; 
                }
                pointList[j * width + i] = new point(i, j, fractal); 
            } 
        }

        for (int i = 0; i < width * height; i++)
        { 
            pointList[i].colorOffset /= maxVal;
        }
    }

    private static void generateWood(float frequency, float amplitude, long seed)
    {
        PerlinNoise noise = new PerlinNoise(seed);
        int counter = 0;
        for(int height = 0; height < CreatePerlinNoiseBAK.height ; height++)
        {
            for(int length = 0; length < CreatePerlinNoiseBAK.width; length++)
            {
                // point color = new point(length, height, 0);
                // noiseMap[height* 256 + length] = color;
                float g = noise.Evaluate2002(ManageVector3.multiplyVector3(new Vector3(length, 0, height), frequency), derivs); 
                g *= amplitude;
                float gFix = g - (int)g;
                gFix = (gFix+1)/2;
                if(gFix< 0)
                {
                    counter++;
                }
                
                pointList[height* CreatePerlinNoiseBAK.width + length] = new point(height, length, gFix);
            }
        }
        System.out.println(counter);
    }

    private static void generateWoodAbs(float frequency, float amplitude, long seed)
    {
        PerlinNoise noise = new PerlinNoise(seed);

        for(int height = 0; height < CreatePerlinNoiseBAK.height; height++)
        {
            for(int length = 0; length < CreatePerlinNoiseBAK.width; length++)
            {
                // point color = new point(length, height, 0);
                // noiseMap[height* 256 + length] = color;
                float g = noise.Evaluate2002(ManageVector3.multiplyVector3(new Vector3(length, 0, height), frequency), derivs);
                g *= amplitude;
                pointList[height* CreatePerlinNoiseBAK.width + length] = new point(length, height, Math.abs(g - (int)g));
            }
        }
    }

    private static void generateWoodNegative(float frequency, float amplitude, long seed)
    {
        PerlinNoise noise = new PerlinNoise(seed);
        for(int height = 0; height < CreatePerlinNoiseBAK.height ; height++)
        {
            for(int length = 0; length < CreatePerlinNoiseBAK.width; length++)
            {
                // point color = new point(length, height, 0);
                // noiseMap[height* 256 + length] = color;
                float g = noise.Evaluate2002(ManageVector3.multiplyVector3(new Vector3(length, 0, height), frequency), derivs); 
                g *= amplitude;
                pointList[height* CreatePerlinNoiseBAK.width + length] = new point(height, length, g - (int)g);
            }
        }
    }

        //Generating a second list 
        // point[] pointList2 = new point[512* 512];
        // for(int i = 0; i < height; i ++)
        // {
        //     for(int j = 0; j < width; j++)
        //     {
        //         pointList2[i * width + j] = new point(j, i, noiseMap2[i * width + j]);
        //     }
        // }
}
