package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import renderEngine.DisplayManager;

/**
 * Kamera mit Basic bewegung
 * @author Lars Bücker
 *
 */

public class FreeCamera extends Camera {

	private static final float MOVE_SPPED = 20;
	
	private float forwardSpeed;
	private float rightSpeed;
	private float upSpeed;
	
	public void move() {
		checkInput();
		calculateYaw();
		calculatePitch();
		float forwardDistance = forwardSpeed * DisplayManager.getFrameTimeSeconds();
		float rightDistance = rightSpeed * DisplayManager.getFrameTimeSeconds();
		float upDistance = upSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (forwardDistance * Math.sin(Math.toRadians(yaw)));
		float dz = (float) (forwardDistance * Math.cos(Math.toRadians(yaw)));
		dx += (float) rightDistance * Math.cos(Math.toRadians(yaw));
		dz += (float) rightDistance * Math.sin(Math.toRadians(yaw));
		position.x += dx;
		position.z += dz;
		position.y += upDistance;
		yaw %= 360;
	}
	
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			forwardSpeed = MOVE_SPPED;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			forwardSpeed = -MOVE_SPPED;
		} else {
			forwardSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			rightSpeed = MOVE_SPPED;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			rightSpeed = -MOVE_SPPED;
		} else {
			rightSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			upSpeed = MOVE_SPPED;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			upSpeed = -MOVE_SPPED;
		} else {
			upSpeed = 0;
		}
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.2f;
			pitch -= pitchChange;
			if(pitch < 0) {
				pitch = 0;
			} else if(pitch > 90) {
				pitch = 90;
			}
		}
	}
	
	private void calculateYaw() {
		if(Mouse.isButtonDown(1)) {
			float yawChange = Mouse.getDX() * 0.2f;
			yaw += yawChange;
		}
	}
	
}
