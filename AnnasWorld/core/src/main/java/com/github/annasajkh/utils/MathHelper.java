package com.github.annasajkh.utils;

public class MathHelper
{
    
    public static float snap(float value, float snapSize)
    {
        return Math.round(value / snapSize) * snapSize;
    }
}
