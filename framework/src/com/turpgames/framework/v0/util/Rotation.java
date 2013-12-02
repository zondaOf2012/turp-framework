package com.turpgames.framework.v0.util;

public class Rotation {
	public Vector angle;
	public Vector origin;

	public Rotation() {
		angle = new Vector();
		origin = new Vector();
	}

	public Rotation(Rotation rotation) {
		this.angle = new Vector(rotation.angle);
		this.origin = new Vector(rotation.origin);
	}

	public Rotation(float rx, float ry, float rz, float ox, float oy, float oz) {
		this.angle = new Vector(rx, ry, rz);
		this.origin = new Vector(ox, oy, oz);
	}

	public void setRotationZ(float z) {
		angle.z = z;
	}

	public void addRotationZ(float z) {
		angle.z += z;
	}
}
