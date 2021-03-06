package skybox;

import models.RawModel;
import renderEngine.Loader;
 
public class CubeMap {
     
    private static final float SIZE = 4096f;
     
    private static final float[] VERTICES = {        
        -SIZE,  SIZE, -SIZE,
        -SIZE, -SIZE, -SIZE,
        SIZE, -SIZE, -SIZE,
         SIZE, -SIZE, -SIZE,
         SIZE,  SIZE, -SIZE,
        -SIZE,  SIZE, -SIZE,
 
        -SIZE, -SIZE,  SIZE,
        -SIZE, -SIZE, -SIZE,
        -SIZE,  SIZE, -SIZE,
        -SIZE,  SIZE, -SIZE,
        -SIZE,  SIZE,  SIZE,
        -SIZE, -SIZE,  SIZE,
 
         SIZE, -SIZE, -SIZE,
         SIZE, -SIZE,  SIZE,
         SIZE,  SIZE,  SIZE,
         SIZE,  SIZE,  SIZE,
         SIZE,  SIZE, -SIZE,
         SIZE, -SIZE, -SIZE,
 
        -SIZE, -SIZE,  SIZE,
        -SIZE,  SIZE,  SIZE,
         SIZE,  SIZE,  SIZE,
         SIZE,  SIZE,  SIZE,
         SIZE, -SIZE,  SIZE,
        -SIZE, -SIZE,  SIZE,
 
        -SIZE,  SIZE, -SIZE,
         SIZE,  SIZE, -SIZE,
         SIZE,  SIZE,  SIZE,
         SIZE,  SIZE,  SIZE,
        -SIZE,  SIZE,  SIZE,
        -SIZE,  SIZE, -SIZE,
 
        -SIZE, -SIZE, -SIZE,
        -SIZE, -SIZE,  SIZE,
         SIZE, -SIZE, -SIZE,
         SIZE, -SIZE, -SIZE,
        -SIZE, -SIZE,  SIZE,
         SIZE, -SIZE,  SIZE
    };
     
    private RawModel cube;
    private int texture;
     
    public CubeMap(String[] textureFiles, Loader loader){
        cube = loader.loadToVAO(VERTICES, 3);
        texture = loader.loadCubeMap(textureFiles);
    }
     
    public RawModel getCube(){
        return cube;
    }
     
    public int getTexture(){
        return texture;
    }
 
}