package terrains;

import java.util.ArrayList;
import java.util.List;

import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class TerrainCluster {

	private List<Terrain> terrains = new ArrayList<Terrain>();
	private int width;
	private int height;
	
	public TerrainCluster(int x, int z, int terrainX, int terrainZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap) {	
		this.width = x;
		this.height = z;
		
		for(int i = x; i < terrainX; i++) {
			for(int j = z; j < terrainZ; j++) {
				HeightsGenerator generator = new HeightsGenerator(i,j,128,42);
				terrains.add(new Terrain(i,j,loader,texturePack,blendMap,generator));
			}
		}
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}
	
	public float getHeightOfTerrain(float x, float z) {
		float gridSquareSize = Terrain.getSize() / (terrains.size() - 1);
		float terrainX = (x % gridSquareSize) / gridSquareSize;
		float terrainZ = (z % gridSquareSize) / gridSquareSize;
		return terrains.get(0).getHeightOfTerrain(terrainX, terrainZ);	
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
	
