package com.github.annasajkh.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.github.annasajkh.Game;
import com.github.annasajkh.utils.Global;

public class InputManager implements InputProcessor
{
    
    private Vector3 temp1 = new Vector3();
    private Vector3 temp2 = new Vector3();
    private Vector3 temp3 = new Vector3();

    @Override
    public boolean keyDown(int keycode)
    {

        if(keycode == Keys.ESCAPE)
        {
            Gdx.app.exit();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {    
        float deltaX = -Gdx.input.getDeltaX() * Global.mouseSensitivity;
        float deltaY = -Gdx.input.getDeltaY() * Global.mouseSensitivity;
        
        Game.player.camera.direction.rotate(Game.player.camera.up, deltaX);
        
        Vector3 oldPitchAxis = temp1.set(Game.player.camera.direction).crs(Game.player.camera.up).nor().rotate(1, 0, 1, 0);
        Vector3 newDirection = temp2.set(Game.player.camera.direction).rotate(temp1, deltaY);
        Vector3 newPitchAxis = temp3.set(temp2).crs(Game.player.camera.up);
        
        if(!newPitchAxis.hasOppositeDirection(oldPitchAxis))
        {
            Game.player.camera.direction.set(newDirection);            
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {

        return true;
    }

}
