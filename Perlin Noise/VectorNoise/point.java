package VectorNoise;
import java.awt.Color;
import java.io.Console;

public class point 
{
    public int x;
    public int y;
    public float colorOffset;
    public point(int s, int e, float colorType)
    {
        x = s;
        y = e;
        colorOffset = colorType;
    }
    
    public Color getColor()
    {
        int colorVal =  Math.round(colorOffset*255);
        if (colorVal > 255)
        {
            colorVal = 255;
        }
        if(colorVal < 0)
        {
            colorVal = 0;
        }
        return new Color(colorVal, colorVal, colorVal);
    }
    public Color dadsColor()
    {
        if(colorOffset < 0)
        {
            return new Color(0,0, Math.round(Math.abs(colorOffset) * 255));
        }
        else
        {
            int colorVal = Math.round(colorOffset*255);
            return new Color(colorVal, colorVal, colorVal);
        }
    }

    public Color differColors()
    {
        if(colorOffset < 0)
        {
            return new Color(0,0, Math.round(Math.abs(colorOffset) * 255));
        }
        else
        {
            int colorVal = Math.round(colorOffset*255);
            return new Color(0, colorVal,colorVal);
        }
    }
}
