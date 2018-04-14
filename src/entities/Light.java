package entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Lichtquelle
 * @author Lars Bücker
 *
 */
public class Light {
	
	private Vector3f position;
	private Vector3f colour;
	private Vector3f attenuation = new Vector3f(1, 0, 0);
	
	/**
	 * Constructor für Globale Lichter
	 * @param position Position des Lichts
	 * @param colour Farbe des Lichts
	 */
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
	}
	
	/**
	 * Constructor für Lichter mit begrenzter Reichweite
	 * @param position Position des Lichts
	 * @param colour Farbe des Lichts
	 * @param attenuation Radius des Lichts
	 */
	public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}
	
	public Vector3f getAttenuation(){
		return attenuation;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	

}
