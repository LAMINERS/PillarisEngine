package skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import renderEngine.Loader;
import entities.Camera;

public class SkyboxRenderer {
	
	private CubeMap cubeMap;
	private SkyboxShader shader;

	
	public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix, CubeMap cubeMap){
		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		this.cubeMap = cubeMap;
	}
	
	public void render(Camera camera, float r, float g, float b){
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColour(r, g, b);
		bindCubeVao();
		bindTexture();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cubeMap.getCube().getVertexCount());
		unbindCubeVao();
		shader.stop();
	}
	
	private void bindTexture(){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, cubeMap.getTexture());
	}
	
	private void bindCubeVao(){
		GL30.glBindVertexArray(cubeMap.getCube().getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void unbindCubeVao(){
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}
