package com.mongo.example.mongodemo.models.apimodel;

public class Geofence {
	LatLong point;
	double radius;
	
	public Geofence() {
		super();
	}

	public Geofence(LatLong point, double radius) {
		super();
		this.point = point;
		this.radius = radius;
	}

	public LatLong getPoint() {
		return point;
	}

	public void setPoint(LatLong point) {
		this.point = point;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "Geofence [point=" + point + ", radius=" + radius + "]";
	}


}
