package com.github.annasajkh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.annasajkh.entities.Player;
import com.github.annasajkh.generation.Chunk;
import com.github.annasajkh.generation.WorldGenerator;
import com.github.annasajkh.managers.InputManager;
import com.github.annasajkh.utils.FastNoiseLite;
import com.github.annasajkh.utils.FastNoiseLite.NoiseType;
import com.github.annasajkh.utils.Global;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Game extends ApplicationAdapter
{   
    public static Player player;
    public static FastNoiseLite worldNoise;
    public static ShaderProgram shaderProgram;
    
    public static Chunk[][] chunks;
    
    public static int seed = MathUtils.random(Integer.MAX_VALUE - 1);
    public static float worldCenterX, worldCenterZ;
    
    @Override
    public void create()
    {
        player = new Player(new Vector3(Global.playerSpawnPosition.x, 
                                        Global.playerSpawnPosition.y,
                                        Global.playerSpawnPosition.z));
        
        worldNoise = new FastNoiseLite();   
        worldNoise.SetNoiseType(NoiseType.Perlin);
        worldNoise.SetSeed(seed);
        
        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/vertexShader.vert"), 
                                          Gdx.files.internal("shaders/fragmentShader.frag"));
        
        chunks = new Chunk[Global.renderDistance][Global.renderDistance];        
        
        
        WorldGenerator.generateAroundPlayer(player.position.x, player.position.z);
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputManager());
        
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCursorCatched(true);
    }
    
    public void getInput(float delta)
    {
        player.getInput(delta);
    }
    
    public void update(float delta)
    {
        player.getInput(delta);
        player.update(delta);
        
        if(Vector2.dst2(player.position.x, player.position.z, worldCenterX, worldCenterZ) > Global.regenerateTriggerDistance2)
        {
            WorldGenerator.updateAroundPlayer(player.position.x, player.position.z);
            
            System.out.println("generate triggered");
        }
    }

    @Override
    public void render()
    {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        for(int i = 0; i < chunks.length; i++)
        {
            for(int j = 0; j < chunks[0].length; j++)
            {                
                chunks[i][j].setProjection(player.camera.combined);
                chunks[i][j].draw();
            }
        }
        
    }

    @Override
    public void dispose()
    {
        for(int i = 0; i < chunks.length; i++)
        {
            for(int j = 0; j < chunks[0].length; j++)
            {   
                chunks[i][j].dispose();
            }
        }
    }
}