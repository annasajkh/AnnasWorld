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
import com.badlogic.gdx.utils.Disposable;
import com.github.annasajkh.Game;
import com.github.annasajkh.shapes.Plane;

public class Chunk implements Disposable
{
    private ShaderProgram shaderProgram;
    private Mesh mesh;
    private Matrix4 projection;
    
    public float[] vertices;
    public short[] indices;

    Plane[][] planes;
    
    int postionAttributeSize = 3; 
    int colorAttributeSize = 1; 
    int normalAttributeSize = 3; 

    int allAttributeSize = postionAttributeSize + colorAttributeSize + normalAttributeSize;
    
    float x;
    float z;
    float size;
    
    public Chunk(float x, float z,  int size, int distanceBetweenVertex, ShaderProgram shaderProgram)
    {
        Color color = Color.GREEN;
        
        vertices = new float[size * size * allAttributeSize * 4];
        indices = new short[size * size * 6];
        
        planes = new Plane[size][size];
        mesh = new Mesh(false, vertices.length, indices.length, new VertexAttribute(Usage.Position, 3, "a_position"),
                                                                new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                                                                new VertexAttribute(Usage.Position, 3, "a_normal"));
        this.shaderProgram = shaderProgram;
        projection = new Matrix4();
        
        this.x = x;
        this.z = z;
        this.size = size;
                
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                float planeX = x + j * distanceBetweenVertex;
                float planeZ = z + i * distanceBetweenVertex;
                float width = distanceBetweenVertex;
                float height = distanceBetweenVertex;
                
                //generate noise
                float noiseY1 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX, planeZ));
                float noiseY2 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX + width, planeZ));
                float noiseY3 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX, planeZ + height));
                float noiseY4 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX + width, planeZ + height));
                
                planes[i][j] = new Plane(planeX, noiseY1, planeZ, //RED
                                         planeX + width, noiseY2, planeZ, //GREEN
                                         planeX, noiseY3, planeZ + height, //BLUE
                                         planeX + width, noiseY4, planeZ + height, //YELLOW
                                         planeX + width * 0.5f, planeZ + height * 0.5f,
                                         color);
            }
        }
        
        buildMesh();
    }
    
    public void updateChunk(float x, float z, int distanceBetweenVertex)
    {
        Color color = Color.GREEN;
                
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                float planeX = x + j * distanceBetweenVertex;
                float planeZ = z + i * distanceBetweenVertex;
                float width = distanceBetweenVertex;
                float height = distanceBetweenVertex;
                
                //generate noise
                float noiseY1 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX, planeZ));
                float noiseY2 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX + width, planeZ));
                float noiseY3 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX, planeZ + height));
                float noiseY4 = MathUtils.map(-1, 1, 0, 100, Game.worldNoise.GetNoise(planeX + width, planeZ + height));
                
                planes[i][j].updatePlane(planeX, noiseY1, planeZ,
                                         planeX + width, noiseY2, planeZ,
                                         planeX, noiseY3, planeZ + height,
                                         planeX + width, noiseY4, planeZ + height,
                                         planeX + width * 0.5f, planeZ + height * 0.5f,
                                         color);
            }
        }
        
        buildMesh();
    }
    
    public void buildMesh()
    {
        int index = 0;
        
        for(int i = 0; i < planes.length; i++)
        {
            for(int j = 0; j < planes[0].length; j++)
            {
                float[] verticesPlane = planes[i][j].getVertices();   
                short[] indicesPlane = planes[i][j].getIndices(index * 4);
                
                for(int k = 0; k < verticesPlane.length; k++)
                {
                    vertices[index * allAttributeSize * 4 + k] = verticesPlane[k];
                }
                
                for(int k = 0; k < indicesPlane.length; k++)
                {
                    indices[index * 6 + k] = indicesPlane[k];
                }
                
                index++;
            }
        }
        

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
    
    public String getUUID()
    {
        return (int)(x) + "," + (int)(z);
    }
    
    @Override
    public void dispose()
    {
        mesh.dispose();
        shaderProgram.dispose();
    }
}
