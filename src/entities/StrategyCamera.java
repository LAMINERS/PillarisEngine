package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

/**
 * 
 * @author Lars Bücker
 *
 */

public class StrategyCamera extends Camera {
	
	private Vector3f pivotPoint = new Vector3f(300,25,300);
	private float angleAroundPivotPoint = 0;
	private float distanceToPivotPoint = 100;
	
	private float moveSpeed = 100 * distanceToPivotPoint / 50;
	private static final float MAX_ZOOM = 1000;
	
	private float forwardSpeed;
	private float rightSpeed;
	
	
	
	public void move() {
		checkInput();
		moveSpeed = 100 * distanceToPivotPoint / 50;
		float forwardDistance = forwardSpeed * DisplayManager.getFrameTimeSeconds();
		float rightDistance = rightSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (forwardDistance * Math.sin(Math.toRadians(angleAroundPivotPoint)));
		float dz = (float) (forwardDistance * Math.cos(Math.toRadians(angleAroundPivotPoint)));
		dx += (float) rightDistance * Math.cos(Math.toRadians(-angleAroundPivotPoint));
		dz += (float) rightDistance * Math.sin(Math.toRadians(-angleAroundPivotPoint));
		pivotPoint.x += dx;
		pivotPoint.z += dz;
		
		
		
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPivotPoint();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - angleAroundPivotPoint;
		yaw %= 360;
	}
	
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			forwardSpeed = moveSpeed;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			forwardSpeed = -moveSpeed;
		} else {
			forwardSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			rightSpeed = -moveSpeed;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			rightSpeed = moveSpeed;
		} else {
			rightSpeed = 0;
		}
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(angleAroundPivotPoint)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(angleAroundPivotPoint)));
		position.x = pivotPoint.x - offsetX;
		position.z = pivotPoint.z - offsetZ;
		position.y = pivotPoint.y + verticalDistance + 4;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceToPivotPoint * Math.cos(Math.toRadians(pitch + 4)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceToPivotPoint * Math.sin(Math.toRadians(pitch + 4)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.03f;
		distanceToPivotPoint -= zoomLevel;
		if(distanceToPivotPoint < 50) {
			distanceToPivotPoint = 50;
		} else if(distanceToPivotPoint > MAX_ZOOM) {
			distanceToPivotPoint = MAX_ZOOM;
		}
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.2f;
			pitch -= pitchChange;
			if(pitch < 10) {
				pitch = 10;
			} else if(pitch > 75) {
				pitch = 75;
			}
		}
	}
	
	private void calculateAngleAroundPivotPoint() {
		if(Mouse.isButtonDown(1)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPivotPoint -= angleChange;
		}
	}

	public Vector3f getPivotPoint() {
		return pivotPoint;
	}
	
	
}
