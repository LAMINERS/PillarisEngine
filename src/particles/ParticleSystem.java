package particles;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class ParticleSystem {

	private float spawnRate;
	private Vector3f minVelocity;
	private Vector3f maxVelocity;
	private Vector2f gravityComplient;
	private Vector2f lifetime;
	private Vector2f scale;
	private Vector2f rotation;
	
	private ParticleTexture texture;
	
	private Random random = new Random();

	public ParticleSystem(float spawnRate, Vector3f minVelocity, Vector3f maxVelocity, Vector2f gravityComplient,
			Vector2f lifetime, Vector2f scale, Vector2f rotation, ParticleTexture texture) {
		this.spawnRate = spawnRate;
		this.minVelocity = minVelocity;
		this.maxVelocity = maxVelocity;
		this.gravityComplient = gravityComplient;
		this.lifetime = lifetime;
		this.scale = scale;
		this.rotation = rotation;
		this.texture = texture;
	}
	
	public void generateParticles(Vector3f systemCenter) {
        float delta = DisplayManager.getFrameTimeSeconds();
        float particlesToCreate = spawnRate * delta;
        int count = (int) Math.floor(particlesToCreate);
        float partialParticle = particlesToCreate % 1;
        for (int i = 0; i < count; i++) {
            emitParticle(systemCenter);
        }
        if (Math.random() < partialParticle) {
            emitParticle(systemCenter);
        }
    }
 
    private void emitParticle(Vector3f center) {
    	float lifetime = getLifeitme(this.lifetime.x, this.lifetime.y);
        Vector3f velocity = getVelocity(minVelocity, maxVelocity);
        float gravityComplient = getGravity(this.gravityComplient.x, this.gravityComplient.y);
        float scale = getScale(this.scale.x, this.scale.y);
        float rotation = getRotation(this.rotation.x, this.rotation.y);
        new Particle(new Vector3f(center), velocity, gravityComplient, lifetime, rotation, scale, texture);
    }
	
	private float getRotation(float min, float max) {
		return min + random.nextFloat() * (max - min);
	}

	private Vector3f getVelocity(Vector3f min, Vector3f max) {
		return new Vector3f(min.x + random.nextFloat() * (max.x - min.x),
							min.y + random.nextFloat() * (max.y - min.y),
							min.z + random.nextFloat() * (max.z - min.z));
		
	}
	
	private float getLifeitme(float min, float max) {
		return min + random.nextFloat() * (max - min);
							
	}
	
	private float getGravity(float min, float max) {
		return min + random.nextFloat() * (max - min);
	}
	
	private float getScale(float min, float max) {
		return min + random.nextFloat() * (max - min);
	}

	public float getSpawnRate() {
		return spawnRate;
	}

	public void setSpawnRate(float spawnRate) {
		this.spawnRate = spawnRate;
	}

	public Vector3f getMinVelocity() {
		return minVelocity;
	}

	public void setMinVelocity(Vector3f minVelocity) {
		this.minVelocity = minVelocity;
	}

	public Vector3f getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(Vector3f maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public Vector2f getGravityComplient() {
		return gravityComplient;
	}

	public void setGravityComplient(Vector2f gravityComplient) {
		this.gravityComplient = gravityComplient;
	}

	public Vector2f getLifetime() {
		return lifetime;
	}

	public void setLifetime(Vector2f lifetime) {
		this.lifetime = lifetime;
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public void setTexture(ParticleTexture texture) {
		this.texture = texture;
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public void setRotation(Vector2f rotation) {
		this.rotation = rotation;
	}
	
	
	
	
}
