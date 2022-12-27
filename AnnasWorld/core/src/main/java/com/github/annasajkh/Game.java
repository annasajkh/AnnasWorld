package com.github.annasajkh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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

    public static Array<Chunk> chunks;
    public static Model model;
    public static ModelInstance modelInstance;
    public static ModelBatch modelBatch;
    public static ModelBuilder modelBuilder;
    public static MeshBuilder meshBuilder;

    
    public static Environment environment;
    public static int seed = MathUtils.random(Integer.MAX_VALUE - 1);
    public static float worldCenterX, worldCenterZ;
    public static DirectionalLight sunLight;

    @Override
    public void create()
    {
        player = new Player(new Vector3(Global.playerSpawnPosition.x, Global.playerSpawnPosition.y, Global.playerSpawnPosition.z));
        
        worldNoise = new FastNoiseLite();
        worldNoise.SetNoiseType(NoiseType.Perlin);
        worldNoise.SetSeed(seed);
        
        chunks = new Array<>(Global.renderDistance * Global.renderDistance);


        model = new Model();
        modelBatch = new ModelBatch();
        modelBuilder = new ModelBuilder();
        meshBuilder = new MeshBuilder();
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f));
        environment.add(sunLight = new DirectionalLight().set(Color.WHITE, 0.0f, -0.9f, 0.1f));
        
        //generate world
        WorldGenerator.generateAroundPlayer(player.position.x, player.position.z);
        
        
        //set inputs
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
        }
    }

    @Override
    public void render()
    {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(player.camera);
        modelBatch.render(modelInstance, environment);
        modelBatch.end();
    }

    @Override
    public void dispose()
    {
        modelBatch.dispose();
        model.dispose();
    }
}