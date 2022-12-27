package com.github.annasajkh.generation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.math.MathUtils;
import com.github.annasajkh.Game;
import com.github.annasajkh.shapes.Rectangle;

public class Chunk
{
    public Rectangle[][] rectangles;
    public Mesh mesh;
    
    VertexInfo vertex1;
    VertexInfo vertex2;
    VertexInfo vertex3;
    VertexInfo vertex4;
    VertexInfo vertex5;
    
    float x;
    float z;
    int size;
    
    public Chunk(float x, float z,  int size, int distanceBetweenVertex)
    {
        mesh = new Mesh(false, 2500, 3750, new VertexAttribute(Usage.Position, 3, "a_position"),
                                           new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                                           new VertexAttribute(Usage.Normal, 3, "a_normal"));
        rectangles = new Rectangle[size][size];
        
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
                float noiseY1 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX, planeZ));
                float noiseY2 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX, planeZ + height));
                float noiseY3 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX + width, planeZ + height));
                float noiseY4 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX + width, planeZ));
                
                //construct the rectangle
                vertex1 = new VertexInfo().setPos(planeX, noiseY1, planeZ).setCol(Color.WHITE);
                vertex2 = new VertexInfo().setPos(planeX, noiseY2, planeZ + height).setCol(Color.BLACK);
                vertex3 = new VertexInfo().setPos(planeX + width, noiseY3, planeZ + height).setCol(Color.WHITE);
                vertex4 = new VertexInfo().setPos(planeX + width, noiseY4, planeZ).setCol(Color.BLACK);
                
                rectangles[i][j] = new Rectangle(vertex1, vertex2, vertex3, vertex4);
            }
        }
        
        buildMesh();
    }
    
    public void updateChunk(float x, float z, int distanceBetweenVertex)
    {           
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                float planeX = x + j * distanceBetweenVertex;
                float planeZ = z + i * distanceBetweenVertex;
                float width = distanceBetweenVertex;
                float height = distanceBetweenVertex;
                
                //generate noise
                float noiseY1 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX, planeZ));
                float noiseY2 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX, planeZ + height));
                float noiseY3 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX + width, planeZ + height));
                float noiseY4 = MathUtils.map(-1, 1, 50, 150, Game.worldNoise.GetNoise(planeX + width, planeZ));
                
                vertex1.reset();
                vertex2.reset();
                vertex3.reset();
                vertex4.reset();
                
                //construct the rectangle
                vertex1.setPos(planeX, noiseY1, planeZ).setCol(Color.WHITE);
                vertex2.setPos(planeX, noiseY2, planeZ + height).setCol(Color.BLACK);
                vertex3.setPos(planeX + width, noiseY3, planeZ + height).setCol(Color.WHITE);
                vertex4.setPos(planeX + width, noiseY4, planeZ).setCol(Color.BLACK);
                
                rectangles[i][j].updateVertexInfo(vertex1, vertex2, vertex3, vertex4);
            }
        }
        
        buildMesh();
    }
    
    public void buildMesh()
    {
        Game.meshBuilder.begin(Usage.Position | Usage.ColorPacked | Usage.Normal, GL20.GL_TRIANGLES);
                
        for(int i = 0; i < rectangles.length; i++)
        {
            for(int j = 0; j < rectangles[0].length; j++)
            {
                Game.meshBuilder.rect(rectangles[i][j].vertex1,
                                      rectangles[i][j].vertex2,
                                      rectangles[i][j].vertex3,
                                      rectangles[i][j].vertex4);
            }
        }
        
        Game.meshBuilder.end(mesh);
    }
    
    public String getId()
    {
        return (int)(x) + "," + (int)(z);
    }
}
