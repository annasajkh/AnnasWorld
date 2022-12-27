package com.github.annasajkh.generation;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.github.annasajkh.Game;
import com.github.annasajkh.utils.Global;
import com.github.annasajkh.utils.MathHelper;

public class WorldGenerator
{
    private static Array<String> chunkIds = new Array<>(Global.renderDistance * Global.renderDistance);
    
    public static void generateAroundPlayer(float x, float z)
    {
        float centerX = MathHelper.snap(x - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        float centerZ = MathHelper.snap(z - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        
        for(int i = 0; i < Global.renderDistance; i++)
        {
            for(int j = 0; j < Global.renderDistance; j++)
            {
                
                Game.chunks.add(new Chunk(centerX + j * Global.chunkFullSize, 
                                          centerZ + i * Global.chunkFullSize,
                                          Global.chunkPerPlaneSize,
                                          Global.chunkResolution));
                chunkIds.add(Game.chunks.get(i * Global.renderDistance + j).getId());
            }
            
        }
        
        Game.worldCenterX = x;
        Game.worldCenterZ = z;
        
        build();
    }
    
    public static void updateAroundPlayer(float x, float z)
    {        
        float centerX = MathHelper.snap(x - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        float centerZ = MathHelper.snap(z - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        
        for(int i = 0; i < Global.renderDistance; i++)
        {
            for(int j = 0; j < Global.renderDistance; j++)
            {                   
                Game.chunks.get(i * Global.renderDistance + j).updateChunk(centerX + j * Global.chunkFullSize, 
                                                                           centerZ + i * Global.chunkFullSize,
                                                                           Global.chunkResolution);
            }
            
        }
        

        Game.worldCenterX = x;
        Game.worldCenterZ = z;
        
        build();
    }
    
    public static void build()
    {
        if(Game.modelInstance == null)
        {
            Game.modelBuilder.begin();
            for(Chunk chunk : Game.chunks)
            {
                Game.modelBuilder.part(null, chunk.mesh, GL20.GL_TRIANGLES, new Material());
                
            }
            Game.model = Game.modelBuilder.end();
            Game.modelInstance = new ModelInstance(Game.model);
        }
        else
        {
            for(int i = 0; i < Game.model.meshParts.size; i++)
            {
                Chunk chunk = Game.chunks.get(i);
                
                if(!chunkIds.contains(chunk.getId(), false))
                {                    
                    Game.model.meshes.set(i, chunk.mesh);
                    chunkIds.set(i, chunk.getId());
                }
            }
        }
        
    }
}
