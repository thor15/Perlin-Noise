import java.awt.Color;

import javax.swing.text.AttributeSet.ColorAttribute;
;public class point {
    public int x;
    public int y;
    public int r;
    public int g;
    public int b;
    public float rRatio = 0.223350253f;
    public float gRatio = 0.147208121f;
    public float bRatio = 1;   
    public float colorOffset;
    public point(int s,int e,float colorOffset)
    {
        x=s;
        y=e;
        this.colorOffset=colorOffset;
    }
    public point(int s,int e,int red,int green,int blue)
    {
        x=s;
        y=e;
        r=red;
        g=green;
        b=blue;
    }
    public Color getColor()
    {
        int colorVal =  Math.round(colorOffset*255);
        return new Color(colorVal, colorVal, colorVal);
    }

    public Color get1DColor()
    {
        return new Color((int)colorOffset, (int)colorOffset, (int)colorOffset);
    }

    public Color getBlueColor()
    {
        int colorVal = Math.round(colorOffset * 255);
        return new Color(0, 0, colorVal);
    }

    public Color getRedColor()
    {
        int colorVal = Math.round(colorOffset * 255);
        return new Color(colorVal, 0, 0);
    }

    public Color getGreenColor()
    {
        int colorVal = Math.round(colorOffset * 255);
        return new Color(0, colorVal, 0);
    }

    public Color getGreenAndRedColor()
    {
        int colorVal = Math.round(colorOffset * 255);
        return new Color(colorVal, colorVal, 0);
    }

    public Color getRedAndBlueColor()
    {
        int colorVal = Math.round(colorOffset * 255);
        return new Color(colorVal, 0, colorVal);
    }

    public Color getCertainColor()
    {
        return new Color(Math.round(rRatio * colorOffset * 255),Math.round(gRatio * colorOffset  * 255),Math.round(bRatio * colorOffset  * 255));
    }
}
