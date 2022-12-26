package com.github.annasajkh.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Plane
{
    private float[] vertices;
    private short[] indices;
    
    private float normalX1, normalY1, normalZ1;
    private float normalX2, normalY2, normalZ2;
    
    public float centerX, centerZ;
    
    public Plane(float x1, float y1, float z1,
                 float x2, float y2, float z2,
                 float x3, float y3, float z3,
                 float x4, float y4, float z4,
                 float centerX, float centerZ,
                 Color color)
    {
        this.centerX = centerX;
        this.centerZ = centerZ;
        
        vertices = new float[] {
            x1, y1, z1, Color.RED.toFloatBits(), 0f, 0f, 0f,
            x2, y2, z2, Color.GREEN.toFloatBits(), 0f, 0f, 0f,
            x3, y3, z3, Color.BLUE.toFloatBits(), 0f, 0f, 0f,
            x4, y4, z4, Color.YELLOW.toFloatBits(), 0f, 0f, 0f
        };
        
        indices = new short[] {
            0, 1, 2,
            1, 2, 3
        };
        
        setNormal();
    }
    
    public void updatePlane(float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            float x3, float y3, float z3,
                            float x4, float y4, float z4,
                            float centerX, float centerZ,
                            Color color)
    {
        this.centerX = centerX;
        this.centerZ = centerZ;
        
        vertices = new float[] {
            x1, y1, z1, Color.RED.toFloatBits(), 0f, 0f, 0f,
            x2, y2, z2, Color.GREEN.toFloatBits(), 0f, 0f, 0f,
            x3, y3, z3, Color.BLUE.toFloatBits(), 0f, 0f, 0f,
            x4, y4, z4, Color.YELLOW.toFloatBits(), 0f, 0f, 0f
        };
        
        indices = new short[] {
            0, 1, 2,
            1, 2, 3
        };
        
        setNormal();
    }
    
    
    private void setNormal()
    {
        // x, y, z, colorBits, normalX, normalY, normalZ
        //[0, 1, 2, 3,         4,       5,       6] /
        //[7, 8, 9, 10         11,      12,      13] /
        //[14,15,16,17,        18,      19,      20] /
        //[21,22,23,24,        25,      26,      27] 
        
        int[] t1Point1 = {0, 1, 2};
        int[] t1Point2 = {7, 8, 9};
        int[] t1Point3 = {14, 15, 16};
        
        //triangle bottom
        float ax = vertices[t1Point2[0]] - vertices[t1Point1[0]];
        float ay = vertices[t1Point2[1]] - vertices[t1Point1[1]];
        float az = vertices[t1Point2[2]] - vertices[t1Point1[2]];
        
        float bx = vertices[t1Point3[0]] - vertices[t1Point1[0]];
        float by = vertices[t1Point3[1]] - vertices[t1Point1[1]];
        float bz = vertices[t1Point3[2]] - vertices[t1Point1[2]];
        
        
        // x, y, z, colorBits, normalX, normalY, normalZ
        //[0, 1, 2, 3,         4,       5,       6]
        //[7, 8, 9, 10         11,      12,      13] /
        //[14,15,16,17,        18,      19,      20] /
        //[21,22,23,24,        25,      26,      27] /
        
        int[] t2Point1 = {7, 8, 9};
        int[] t2Point3 = {14, 15, 16};
        int[] t2Point2 = {21, 22, 23};
        
        //triangle top
        float ax2 = vertices[t2Point2[0]] - vertices[t2Point1[0]];
        float ay2 = vertices[t2Point2[1]] - vertices[t2Point1[1]];
        float az2 = vertices[t2Point2[2]] - vertices[t2Point1[2]];
        
        float bx2 = vertices[t2Point3[0]] - vertices[t2Point1[0]];
        float by2 = vertices[t2Point3[1]] - vertices[t2Point1[1]];
        float bz2 = vertices[t2Point3[2]] - vertices[t2Point1[2]];
        
        
        
        //calculate normal
        normalX1 = ay * bz - az * by;
        normalY1 = az * bx - ax * bz;
        normalZ1 = ax * by - ay * bx;
        
        float length = Vector3.len(normalX1, normalY1, normalZ1);
        
        normalX1 = normalX1 / length;
        normalY1 = normalY1 / length;
        normalZ1 = normalZ1 / length;
        
        normalX2 = ay2 * bz2 - az2 * by2;
        normalY2 = az2 * bx2 - ax2 * bz2;
        normalZ2 = ax2 * by2 - ay2 * bx2;
        
        length = Vector3.len(normalX2, normalY2, normalZ2);
        
        normalX2 = normalX2 / length;
        normalY2 = normalY2 / length;
        normalZ2 = normalZ2 / length;
        
        // x, y, z, colorBits, normalX, normalY, normalZ
        //[0, 1, 2, 3,         4,       5,       6] /
        //[7, 8, 9, 10         11,      12,      13 /
        //[14,15,16,17,        18,      19,      20] /
        //[21,22,23,24,        25,      26,      27]
        
        //set normal triangle bottom
        vertices[4] = normalX1;
        vertices[5] = normalY1;
        vertices[6] = normalZ1;
        
        vertices[11] = normalX1;
        vertices[12] = normalY1;
        vertices[13] = normalZ1;
        
        vertices[18] = normalX1;
        vertices[19] = normalY1;
        vertices[20] = normalZ1;
        
        // x, y, z, colorBits, normalX, normalY, normalZ
        //[0, 1, 2, 3,         4,       5,       6]
        //[7, 8, 9, 10         11,      12,      13] /
        //[14,15,16,17,        18,      19,      20] /
        //[21,22,23,24,        25,      26,      27] /
        
        //set normal triangle top
        vertices[11] = normalX2;
        vertices[12] = normalY2;
        vertices[13] = normalZ2;
        
        vertices[18] = normalX2;
        vertices[19] = normalY2;
        vertices[20] = normalZ2;
        
        vertices[25] = normalX2;
        vertices[26] = normalY2;
        vertices[27] = normalZ2;
    }
    
    public float[] getVertices()
    {
        return vertices;
    }
    
    public short[] getIndices(int offset)
    {
        indices = new short[] {
            0, 1, 2,
            1, 2, 3
        };
        
        indices[0] = (short) (indices[0] + offset);
        indices[1] = (short) (indices[1] + offset);
        indices[2] = (short) (indices[2] + offset);
        indices[3] = (short) (indices[3] + offset);
        indices[4] = (short) (indices[4] + offset);
        indices[5] = (short) (indices[5] + offset);
        
        return indices;
    }
}
