package water;

import java.util.ArrayList;
import java.util.List;

public class WaterCluster {

	private List<WaterTile> waterTiles = new ArrayList<WaterTile>();
	
	public void addWaterTile(WaterTile waterTile) {
		waterTiles.add(waterTile);
	}
	
	public WaterCluster(float x, float z, int tileX, int tileZ, float height) {
		for(int i = 1; i < tileX; i++) {
			for(int j = 1; j < tileZ; j++) {
				waterTiles.add(new WaterTile(x * i, z * j, height));
			}
		}
	}

	public List<WaterTile> getWaterTiles() {
		return waterTiles;
	}
}
