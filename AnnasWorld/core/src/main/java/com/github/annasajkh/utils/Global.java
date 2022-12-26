package com.github.annasajkh.utils;

import com.badlogic.gdx.math.Vector3;

public class Global
{
    public static float mouseSensitivity = 1f;
    public static float playerSpeed = 100;
    public static short chunkPerPlaneSize = 25;
    public static short chunkResolution = 5;
    public static float chunkFullSize = chunkPerPlaneSize * chunkResolution;
    public static int renderDistance = 1;
    public static Vector3 playerSpawnPosition = new Vector3(chunkFullSize / 2,
                                                            100,
                                                            chunkFullSize / 2);
    public static float regenerateTriggerDistance2 = (chunkFullSize * renderDistance * 0.25f) * 
                                                     (chunkFullSize * renderDistance * 0.25f);
}
