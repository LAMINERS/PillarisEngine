package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import models.TexturedModel;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import utils.OpenGlUtils;
import entities.Entity;
import entities.Light;
import entities.StrategyCamera;
import guis.GuiRenderer;
import guis.GuiTexture;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import terrains.TerrainCluster;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterCluster;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		// *********TERRAIN TEXTURE STUFF**********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// *****************************************

		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);

		TexturedModel fern = new TexturedModel(OBJFileLoader.loadOBJ("fern", loader),
				fernTextureAtlas);

		TexturedModel bobble = new TexturedModel(OBJFileLoader.loadOBJ("pine", loader),
				new ModelTexture(loader.loadTexture("pine")));
		bobble.getTexture().setHasTransparency(true);

		fern.getTexture().setHasTransparency(true);

		TerrainCluster terrain = new TerrainCluster(0, 0, 4, 4, loader, texturePack, blendMap);

		TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),
				new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);

		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
			
		
		//************ENTITIES******************
		
		Random random = new Random(5666778);
		for (int i = 0; i < 500; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 1024;
				float z = random.nextFloat() * -1024;
				if ((x > 50 && x < 100) || (z < -50 && z > -100)) {
				} else {
					float y = terrain.getHeightOfTerrain(x, z);
					if(y > 0)
						entities.add(new Entity(fern, 3, new Vector3f(x, y, z), 0,random.nextFloat() * 360, 0, 0.9f));
				}
			}
			if (i % 2 == 0) {

				float x = random.nextFloat() * 1024;
				float z = random.nextFloat() * -1024;
				if ((x > 50 && x < 100) || (z < -50 && z > -100)) {

				} else {
					float y = terrain.getHeightOfTerrain(x, z);
					if(y > 0)
						entities.add(new Entity(bobble, 1, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.6f + 0.8f));
				}
			}
		}
		
		//*******************OTHER SETUP***************

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);
		
		StrategyCamera camera = new StrategyCamera();
		
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		//******** GUIs****************************************
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
				
		//MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		

		//**********Water Renderer Set-up************************
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		WaterCluster water = new WaterCluster(128f, 128f, 8 * 4 , 8 * 4, -10);
		
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("particleStar"), 1);
		//ParticleSystem system = new ParticleSystem(300, 50, 0.6f, 5, 1.6f, particleTexture);
		ParticleSystem system = new ParticleSystem(300, new Vector3f(3,0,3), new Vector3f(10, 10, 10), new Vector2f(0.01f, 0.2f), 
												new Vector2f(1,10), new Vector2f(0.5f,2.5f), new Vector2f(0,0), particleTexture);

		
		Fbo multisampledFbo = new Fbo(Display.getWidth(), Display.getHeight());
		Fbo outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		Fbo outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.init(loader);
	
		boolean wireframe = false;
		
		
		//****************Game Loop Below*********************

		while (!Display.isCloseRequested()) {
			camera.move();
			//picker.update();

			renderer.renderShadowMap(entities, sun);
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
	
			system.generateParticles(camera.getPivotPoint());
			
			//render reflection texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y/* - water.getHeight()*/);
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrain.getTerrains(), lights, camera, new Vector4f(0, 1, 0, /*-water.getHeight()+1*/0));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrain.getTerrains(), lights, camera, new Vector4f(0, -1, 0, /*water.getHeight()*/0));
			
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();				
			
			multisampledFbo.bindFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrain.getTerrains(), lights, camera, new Vector4f(0, -1, 0, 100000));	
			waterRenderer.render(water.getWaterTiles(), camera, sun);
			
			ParticleMaster.renderParticles(camera);
			ParticleMaster.update(camera);
			
			multisampledFbo.unbindFrameBuffer();
			multisampledFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
			multisampledFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
			PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());

			if(wireframe) {
				outputFbo.resolveToScreen();
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_1)) {
				wireframe = false;	
			} else if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
				wireframe = true;
			}
			OpenGlUtils.goWireframe(wireframe);
			
			
			guiRenderer.render(guiTextures);
			
			DisplayManager.updateDisplay();			
		}

		//*********Clean Up Below**************
		
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampledFbo.cleanUp();
		ParticleMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}


}
