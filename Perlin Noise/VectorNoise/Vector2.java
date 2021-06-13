package VectorNoise;

public class Vector2 
{
    public float x;
    public float y;
    
    public Vector2(Float s, Float e)
    {
        x = s;
        y = e;
    }

    public Vector2 multiply(Float r)
    {
        return new Vector2(x * r, y * r);
    }
}
