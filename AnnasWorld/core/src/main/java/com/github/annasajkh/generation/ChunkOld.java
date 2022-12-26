package com.github.annasajkh.generation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.github.annasajkh.Game;

public class ChunkOld implements Disposable
{
    private ShaderProgram shaderProgram;
    private Mesh mesh;
    private Matrix4 projection;
    
    float[] vertices;
    short[] indices;
    
    int postionAttributeSize = 3; 
    int colorAttributeSize = 1; 
    int normalAttributeSize = 3; 

    int allAttributeSize = postionAttributeSize + colorAttributeSize + normalAttributeSize;
    
    public ChunkOld(float x, float z,  int size, int distanceBetweenVertex, ShaderProgram shaderProgram)
    {        
        vertices = new float[size * size * allAttributeSize];
        indices = new short[size * size * 6];
        
        int verticesLength = vertices.length / (size * allAttributeSize);
        int indicesLength = indices.length / (size * 6);
        
        for(int i = 0; i < verticesLength; i++)
        {
            for(int j = 0; j < verticesLength; j++)
            {
                //generate height map
                float noiseY = MathUtils.map(-1, 1, 0, 50, Game.worldNoise.GetNoise(j * distanceBetweenVertex * 0.5F, i * distanceBetweenVertex * 0.5f));

                //build the vertices
                vertices[(j + verticesLength * i) * allAttributeSize] = j * distanceBetweenVertex + x;
                vertices[(j + verticesLength * i) * allAttributeSize + 1] = noiseY;
                vertices[(j + verticesLength * i) * allAttributeSize + 2] = i * distanceBetweenVertex + z;
                vertices[(j + verticesLength * i) * allAttributeSize + 3] = Color.WHITE.toFloatBits();
            }
        }
        
        for(short i = 0; i < indicesLength; i++)
        {
            for(short j = 0; j < indicesLength; j++)
            {          
                int iOffset = MathUtils.clamp(i + 1, 1, indicesLength - 1);
                int jOffset = MathUtils.clamp(j + 1, 1, indicesLength - 1);
                
                int step = (j + indicesLength * i) * 6;
                
                //triangulate the vertices (bottom triangle)
                indices[step] = (short) (indicesLength * (iOffset - 1) + (jOffset - 1));
                indices[step + 1] = (short) (indicesLength * (iOffset - 1) + jOffset);;
                indices[step + 2] = (short) (indicesLength * iOffset + (jOffset - 1));
                
                //triangulate the vertices (top triangle)
                indices[step + 3] = (short) (indicesLength * (iOffset - 1) + jOffset);
                indices[step + 4] = (short) (indicesLength * iOffset + (jOffset - 1));
                indices[step + 5] = (short) (indicesLength * iOffset + jOffset);
                                
                //calculate normal (bottom triangle)
                float ax1 = vertices[indices[step] * allAttributeSize];
                float ay1 = vertices[indices[step] * allAttributeSize + 1];
                float az1 = vertices[indices[step] * allAttributeSize + 2];
                
                float bx1 = vertices[indices[step + 1] * allAttributeSize];
                float by1 = vertices[(indices[step + 1] * allAttributeSize) + 1];
                float bz1 = vertices[indices[step + 1] * allAttributeSize + 2];
                
                float cx1 = vertices[indices[step + 2] * allAttributeSize];
                float cy1 = vertices[indices[step + 2] * allAttributeSize + 1];
                float cz1 = vertices[indices[step + 2] * allAttributeSize + 2];
                
                
                float ax = bx1 - ax1;
                float ay = by1 - ay1;
                float az = bz1 - az1;
                
                float bx = cx1 - ax1;
                float by = cy1 - ay1;
                float bz = cz1 - az1;
                
                
                float normalX1 = ay * bz - az * by;
                float normalY1 = az * bx - ax * bz;
                float normalZ1 = ax * by - ay * bx;
                
                Vector3 normalVector = new Vector3(normalX1, normalY1, normalZ1).nor();
                
                
                //set the normal to the vertex data
                vertices[indices[step] * allAttributeSize + 4] = normalVector.x;
                vertices[indices[step] * allAttributeSize + 5] = normalVector.y;
                vertices[indices[step] * allAttributeSize + 6] = normalVector.z;
                
                vertices[indices[step + 1] * allAttributeSize + 4] = normalVector.x;
                vertices[indices[step + 1] * allAttributeSize + 5] = normalVector.y;
                vertices[indices[step + 1] * allAttributeSize + 6] = normalVector.z;
                
                vertices[indices[step + 2] * allAttributeSize + 4] = normalVector.x;
                vertices[indices[step + 2] * allAttributeSize + 5] = normalVector.y;
                vertices[indices[step + 2] * allAttributeSize + 6] = normalVector.z;
                            
                
                
                
                //calculate normal (top triangle)
                float ax2 = vertices[indices[step + 3] * allAttributeSize];
                float ay2 = vertices[indices[step + 3] * allAttributeSize + 1];
                float az2 = vertices[indices[step + 3] * allAttributeSize + 2];
                
                float bx2 = vertices[indices[step + 4] * allAttributeSize];
                float by2 = vertices[indices[step + 4] * allAttributeSize + 1];
                float bz2 = vertices[indices[step + 4] * allAttributeSize + 2];
                
                float cx2 = vertices[indices[step + 5] * allAttributeSize];
                float cy2 = vertices[indices[step + 5] * allAttributeSize + 1];
                float cz2 = vertices[indices[step + 5] * allAttributeSize + 2];
                
                ax = bx2 - ax2;
                ay = by2 - ay2;
                az = bz2 - az2;
                
                bx = cx2 - ax2;
                by = cy2 - ay2;
                bz = cz2 - az2;
                
                
                float normalX2 = ay * bz - az * by;
                float normalY2 = az * bx - ax * bz;
                float normalZ2 = ax * by - ay * bx;
                
                normalVector = new Vector3(normalX2, normalY2, normalZ2).nor();
                
                //set the normal to the vertex data
                vertices[indices[step + 3] * allAttributeSize + 4] = normalVector.x;
                vertices[indices[step + 3] * allAttributeSize + 5] = normalVector.y;
                vertices[indices[step + 3] * allAttributeSize + 6] = normalVector.z;
                
                vertices[indices[step + 4] * allAttributeSize + 4] = normalVector.x;
                vertices[indices[step + 4] * allAttributeSize + 5] = normalVector.y;
                vertices[indices[step + 4] * allAttributeSize + 6] = normalVector.z;
                
                vertices[indices[step + 5] * allAttributeSize + 4] = normalVector.x;
                vertices[indices[step + 5] * allAttributeSize + 5] = normalVector.y;
                vertices[indices[step + 5] * allAttributeSize + 6] = normalVector.z;
                
            }
        }
        
        this.mesh = new Mesh(true, vertices.length, indices.length, new VertexAttribute(Usage.Position, 3, "a_position"),
                                                                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                                                                    new VertexAttribute(Usage.Position, 3, "a_normal"));
        this.shaderProgram = shaderProgram;
        this.projection = new Matrix4();
                
        mesh.setVertices(vertices);
        mesh.setIndices(indices);
        
    }
    
    public void setProjection(Matrix4 projection)
    {
        this.projection = projection;
    }
    
    public void draw() 
    {
        Gdx.gl.glDepthMask(false);
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        shaderProgram.bind();        
        shaderProgram.setUniformMatrix("u_projection", projection);
        
        mesh.render(shaderProgram, GL20.GL_TRIANGLES);
        
        Gdx.gl.glDepthMask(true);
    }
    
    
    @Override
    public void dispose()
    {
        mesh.dispose();
        shaderProgram.dispose();
    }
    
}
