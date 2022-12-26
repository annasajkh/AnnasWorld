package com.github.annasajkh.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject
{
    public Vector3 position;
    
    public GameObject(Vector3 position)
    {
        this.position = position;
    }
    
    
    public abstract void update(float delta);
    
    public abstract void draw(ShapeRenderer shapeRenderer);
}
