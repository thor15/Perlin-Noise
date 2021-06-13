import java.util.Random;

public class RandomNumbers 
{
    public static void main(String[] args)
    {
        Random randomNum = new Random(2030);
        float[] numbers = new float[30];
        for(int i = 0; i < numbers.length; i++)
        {
            numbers[i] = (float)randomNum.nextDouble();
        }

        int yCorr = 0;
    
        for(int i = 0; i < numbers.length; i++)
        {
            System.out.println("(" + i % 10 + "," + yCorr + " ): " + numbers[i]);
            if(i % 9 == 0 && i != 0)
            {
                yCorr++;
            }
        }
    }
}
