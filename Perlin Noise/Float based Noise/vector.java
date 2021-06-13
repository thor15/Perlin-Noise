public class vector 
{
    public float x;
    public float y;
    
    public vector(Float s, Float e)
    {
        x = s;
        y = e;
    }

    public vector multiply(Float r)
    {
        return new vector(x * r, y * r);
    }
}
