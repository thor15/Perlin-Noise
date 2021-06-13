package VectorNoise;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import java.util.Arrays;

public class ImageGenerator 
{
   public static void DrawImage(int imageSize, point[] pointList, String imageName)
   {
    BufferedImage off_Image =
    new BufferedImage(imageSize,imageSize,
                      BufferedImage.TYPE_INT_RGB);

    // point[] pointList = new point[imageSize*imageSize];
    // int index =0;
    // Random r = new Random();
  // for(int x=0;x < imageSize;x++)
  // {
  //   for(int y=0;y < imageSize;y++)
  //   {
  //       pointList[index++] = new point(x,y,r.nextInt(255));
  //       //pointList[index++] = new point(x,y,r.nextInt(255),r.nextInt(255),r.nextInt(255));
  //   }
  // }
    
    for(int i = 0; i < pointList.length; i++)
    {
        try
        {
        
          off_Image.setRGB(pointList[i].x, pointList[i].y, pointList[i].getColor().getRGB());
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          System.out.println("ArrayIndexOutOfBounds: " + i);
        }
        catch(NullPointerException exception)
        {
          System.out.println("Null refrence: " + i);
        }
    }
    try {
            if (ImageIO.write(off_Image, "png", new File("./" + imageName + ".png")))
            {
                System.out.println("-- saved");
            }
    } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
   } 
}
