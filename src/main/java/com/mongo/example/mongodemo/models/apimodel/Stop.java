package com.mongo.example.mongodemo.models.apimodel;

public class Stop {
	private Double longitude;
	private Double lattitude;

	public Stop() {
		super();
	}

	public Stop(Double longitude, Double lattitude) {
		super();
		this.longitude = longitude;
		this.lattitude = lattitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}

	@Override
	public String toString() {
		return "Stop [longitude=" + longitude + ", lattitude=" + lattitude + "]";
	}
}
