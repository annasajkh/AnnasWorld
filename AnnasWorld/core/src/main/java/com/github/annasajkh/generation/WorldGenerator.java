package com.github.annasajkh.generation;

import com.github.annasajkh.Game;
import com.github.annasajkh.utils.Global;
import com.github.annasajkh.utils.MathHelper;

public class WorldGenerator
{    
    public static void generateAroundPlayer(float x, float z)
    {
        float centerX = MathHelper.snap(x - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        float centerZ = MathHelper.snap(z - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        
        for(int i = 0; i < Game.chunks.length; i++)
        {
            for(int j = 0; j < Game.chunks[0].length; j++)
            {
                
                Game.chunks[i][j] = new Chunk(centerX + j * Global.chunkFullSize, 
                                              centerZ + i * Global.chunkFullSize,
                                              Global.chunkPerPlaneSize,
                                              Global.chunkResolution,
                                              Game.shaderProgram);
            }
            
        }
        
        Game.worldCenterX = x;
        Game.worldCenterZ = z;
    }
    
    public static void updateAroundPlayer(float x, float z)
    {        
        float centerX = MathHelper.snap(x - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        float centerZ = MathHelper.snap(z - Global.chunkFullSize * Global.renderDistance / 2, Global.chunkFullSize);
        
        for(int i = 0; i < Game.chunks.length; i++)
        {
            for(int j = 0; j < Game.chunks[0].length; j++)
            {                   
                Game.chunks[i][j].updateChunk(centerX + j * Global.chunkFullSize, 
                                              centerZ + i * Global.chunkFullSize,
                                              Global.chunkResolution);
            }
            
        }
        

        Game.worldCenterX = x;
        Game.worldCenterZ = z;
    }
}
