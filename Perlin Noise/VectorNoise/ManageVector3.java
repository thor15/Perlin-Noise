package VectorNoise;

public class ManageVector3 
{
    
    
    public static Vector3 AddVector3(Vector3 firstVector3, Vector3 secondVector3)
    {
        float xCorr = firstVector3.x + secondVector3.x;
        float yCorr = firstVector3.y + secondVector3.y;
        float zCorr = firstVector3.z + secondVector3.z;

        Vector3 vector3 = new Vector3(xCorr, yCorr, zCorr);

        return vector3;
    }

    public static Vector3 multiplyVector3(Vector3 vectorToMultipy,float numberToMultiplyBy)
    {
        return new Vector3(vectorToMultipy.x * numberToMultiplyBy, vectorToMultipy.y * numberToMultiplyBy, vectorToMultipy.z * numberToMultiplyBy);
    }

    public static Vector3 NormalizeVector3(Vector3 vector3ToNormalize)
    {
        float length = (float)Math.pow(vector3ToNormalize.x, 2) + (float)Math.pow(vector3ToNormalize.y, 2) + (float)Math.pow(vector3ToNormalize.z, 2);
        length = (float)Math.sqrt(length);
        length = 1/length;

        Vector3 vector3 = new Vector3(vector3ToNormalize.x * length, vector3ToNormalize.y * length, vector3ToNormalize.z * length);
        return vector3;
    }

    public static float DotProduct(Vector3 fVector3, Vector3 sVector3)
    {
        float product = 0;

        product += fVector3.x * sVector3.x;
        product += fVector3.y * sVector3.y;
        product += fVector3.z * sVector3.z;

        return product;
    }

    public static Vector3 CrossProduct(Vector3 fVector3, Vector3 sVector3)
    {
        return new Vector3(fVector3.y * sVector3.z - fVector3.z * sVector3.y, fVector3.z * sVector3.x - fVector3.x * sVector3.z, fVector3.x * sVector3.y - fVector3.y * sVector3.x);
    }
}
