package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

/**
 * Basis Entity Klasse
 * @author Lars Bücker
 */
public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private int textureIndex = 0;

	/**
	 * Constructor für Entities mit Normalen Texturen
	 * @param model Model der Entity
	 * @param position Position der Entity
	 * @param rotX Rotation der Entity auf der X-Achse
	 * @param rotY Rotation der Entity auf der Y-Achse
	 * @param rotZ Rotation der Entity auf der Z-Achse
	 * @param scale Größe der Entity
	 */
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	/**
	 * Constructor für Entites mit mehreren Texturen in einem Textureatals
	 * @param model Model der Entity
	 * @param index Index der Texture auf dem Textureatlas
	 * @param position Position der Entity
	 * @param rotX Rotation der Entity auf der X-Achse
	 * @param rotY Rotation der Entity auf der Y-Achse
	 * @param rotZ Rotation der Entity auf der Z-Achse
	 * @param scale Größe der Entity
	 */
	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	/**
	 * Für die verwendung von Textureatlanten
	 * @return Gibt die Spalte auf dem Texture Atlas zurück
	 */
	public float getTextureXOffset(){
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column / (float)model.getTexture().getNumberOfRows();
	}
	
	/**
	 * Für die verwendung von Textureatlanten
	 * @return Gibt die Reihe auf dem Texture Atlas zurück
	 */
	public float getTextureYOffset(){
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float)row / (float)model.getTexture().getNumberOfRows();
	}

	/**
	 * Für veränderung der Position
	 * @param dx Zu addierender X-Wert
	 * @param dy Zu addierender Y-Wert
	 * @param dz Zu addierender Z-Wert
	 */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	/**
	 * Für veränderung der Rotation
	 * @param dx Zu addierender X-Wer
	 * @param dy Zu addierender Y-Wert
	 * @param dz Zu addierender Z-Wert
	 */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
