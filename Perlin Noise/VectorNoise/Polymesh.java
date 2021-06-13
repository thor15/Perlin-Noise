package VectorNoise;



public class Polymesh 
{
    public Vector3[] verticies = null;
    private Vector2[] st = null;
    public Vector3[] normals = null;

    private int[] faceArray;
    public int[] verticiesArray;
    private int numFaces;
    public int numVerticies;
    
    public Polymesh()
    {

    }

    public static Polymesh CreatePolymesh(int width, int height, int subdivitionWidth,  int subdivitionHeight)
    {
        Polymesh poly = new Polymesh();
        poly.numVerticies = (subdivitionWidth + 1) * (subdivitionHeight + 1);
        poly.verticies = new Vector3[poly.numVerticies];
        poly.normals = new Vector3[poly.numVerticies];
        poly.st = new Vector2[poly.numVerticies];

        float invSubdivitionWidth = 1f / subdivitionWidth;
        float invSubdivitionHeight = 1f / subdivitionHeight;

        for(int i = 0; i <= subdivitionHeight; i++)
        {
            for(int j = 0; j <= subdivitionWidth; j++)
            {
                poly.verticies[i * (subdivitionWidth + 1) + j] = new Vector3((float)(width * (j * invSubdivitionWidth - .5)), 0f, (float)(height * (i * invSubdivitionWidth - .5)));
                poly.st[i * (subdivitionWidth + 1) + j] = new Vector2((float)(j * invSubdivitionWidth),(float)( i * invSubdivitionHeight));
            }
        }

        poly.numFaces = subdivitionWidth * subdivitionHeight;
        poly.faceArray = new int[(poly.numFaces)];
        for(int i = 0; i < poly.numFaces; i++)
        {
            poly.faceArray[i] = 4;
        }

        poly.verticiesArray = new int[poly.numFaces * 4];

        int k = 0;

        for(int j = 0; j < subdivitionHeight; j++)
        {
            for(int i = 0; i < subdivitionWidth; i++)
            {
                poly.verticiesArray[k] = j * (subdivitionWidth + 1) + i;
                poly.verticiesArray[k] = j * (subdivitionWidth + 1) + i + 1;
                poly.verticiesArray[k] = (j + 1) * (subdivitionWidth + 1) + i + 1;
                poly.verticiesArray[k] = (j + 1) * (subdivitionWidth + 1) + i;
                k+= 4;
            }
        }



        return poly;
    }


}
