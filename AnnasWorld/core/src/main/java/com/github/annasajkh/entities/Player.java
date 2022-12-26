package com.github.annasajkh.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.github.annasajkh.components.Entity;
import com.github.annasajkh.utils.Global;

public class Player extends Entity
{
    public PerspectiveCamera camera;
    public Vector3 direction;
    private Vector3 cameraDirection;
    
    
    public Player(Vector3 position)
    {
        super(position, new Vector3());
        
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.lookAt(0, 0, 0);
        camera.position.set(position);
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.update();
        
        direction = new Vector3();
        cameraDirection = camera.direction.cpy();
    }
    
    public void getInput(float delta)
    {           
        if(Gdx.input.isKeyPressed(Keys.W))
        {
            cameraDirection.set(camera.direction);
            
            direction.x += cameraDirection.x;
            direction.z += cameraDirection.z;
            direction.y = 0;
        }
        else if(Gdx.input.isKeyPressed(Keys.S))
        {
            cameraDirection.set(camera.direction);
            
            direction.x += -cameraDirection.x;
            direction.z += -cameraDirection.z;
            direction.y = 0;
        }
        
        
        if(Gdx.input.isKeyPressed(Keys.A))
        {
            cameraDirection.set(camera.direction);
            cameraDirection.rotate(Vector3.Y, 90);
            
            direction.x += cameraDirection.x;
            direction.z += cameraDirection.z;
            direction.y = 0;

        }
        else if(Gdx.input.isKeyPressed(Keys.D))
        {
            cameraDirection.set(camera.direction);
            cameraDirection.rotate(Vector3.Y, -90);
            
            direction.x += cameraDirection.x;
            direction.z += cameraDirection.z;
            direction.y = 0;        
        }
        
        if(Gdx.input.isKeyPressed(Keys.SPACE))
        {
            direction.y += 1;
        }
        else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
        {
            direction.y += -1;
        }
        
        if(!(Gdx.input.isKeyPressed(Keys.W) ||
             Gdx.input.isKeyPressed(Keys.A) ||
             Gdx.input.isKeyPressed(Keys.S) ||
             Gdx.input.isKeyPressed(Keys.D) ||
             Gdx.input.isKeyPressed(Keys.SPACE) ||
             Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)))
        {
            direction.setZero();
        }
        
        direction.nor();
        
        velocity.x = direction.x * Global.playerSpeed;
        velocity.y = direction.y * Global.playerSpeed;
        velocity.z = direction.z * Global.playerSpeed;
    }
    
    @Override
    public void update(float delta)
    {
        super.update(delta);
        
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer)
    {
        // TODO Auto-generated method stub
        
    }
    
}
