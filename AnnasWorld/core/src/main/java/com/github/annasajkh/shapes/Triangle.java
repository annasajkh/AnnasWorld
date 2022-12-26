package com.github.annasajkh.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Triangle
{

    private float[] vertices;
    private float normalX, normalY, normalZ;

    public Triangle(float x1, float y1, float z1,
                    float x2, float y2, float z2,
                    float x3, float y3, float z3, 
                    Color color1, 
                    Color color2,
                    Color color3)
    {
        set(x1, y1, z1,
            x2, y2, z2,
            x3, y3, z3,
            color1,
            color2,
            color3);
        
    }
    
    private void setNormal()
    {
        // x, y, z, colorBits, normalX, normalY, normalZ
        //[0, 1, 2, 3,         4,       5,       6]
        //[7, 8, 9, 10         11,      12,      13
        //[14,15,16,17,        18,      19,      20]
        
        float ax = vertices[7] - vertices[0];
        float ay = vertices[8] - vertices[1];
        float az = vertices[9] - vertices[2];
        
        float bx = vertices[14] - vertices[0];
        float by = vertices[15] - vertices[1];
        float bz = vertices[16] - vertices[2];
        
        
        normalX = ay * bz - az * by;
        normalY = az * bx - ax * bz;
        normalZ = ax * by - ay * bx;
        
        float length = Vector3.len(normalX, normalY, normalZ);
        
        normalX = normalX / length;
        normalY = normalY / length;
        normalZ = normalZ / length;
        
        vertices[4] = normalX;
        vertices[5] = normalY;
        vertices[6] = normalZ;
        
        vertices[11] = normalX;
        vertices[12] = normalY;
        vertices[13] = normalZ;
        
        vertices[18] = normalX;
        vertices[19] = normalY;
        vertices[20] = normalZ;
    }
    
    public void set(float x1, float y1, float z1,
                    float x2, float y2, float z2,
                    float x3, float y3, float z3, 
                    Color color1, 
                    Color color2,
                    Color color3)
    {
        //position (vec3), color (floatBits), normal (vec3)
        vertices = new float[] {
          x1, y1, z1, color1.toFloatBits(), 0f, 0f, 0f,
          x2, y2, z2, color2.toFloatBits(), 0f, 0f, 0f,
          x3, y3, z3, color3.toFloatBits(), 0f, 0f, 0f,
        };
        
        setNormal();
    }
    
    public void setVertex1(float x, float y, float z, Color color)
    {
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = z;
        vertices[3] = color.toFloatBits();
        
        setNormal();
    }
    
    public void setVertex2(float x, float y, float z, Color color)
    {
        vertices[7] = x;
        vertices[8] = y;
        vertices[9] = z;
        vertices[10] = color.toFloatBits();
        
        setNormal();
    }
    
    public void setVertex3(float x, float y, float z, Color color)
    {
        vertices[14] = x;
        vertices[15] = y;
        vertices[16] = z;
        vertices[17] = color.toFloatBits();
        
        setNormal();
    }
    
    public float[] getVertices()
    {
        return vertices;
    }
}
