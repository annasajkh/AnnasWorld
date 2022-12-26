package com.github.annasajkh.components;

import com.badlogic.gdx.math.Vector3;

public abstract class Entity extends GameObject
{
    public Vector3 velocity;
    
    public Entity(Vector3 position, Vector3 velocity)
    {
        super(position);
        
        this.velocity = velocity;
    }
    
    @Override
    public void update(float delta)
    {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        position.z += velocity.z * delta;
    }

}
