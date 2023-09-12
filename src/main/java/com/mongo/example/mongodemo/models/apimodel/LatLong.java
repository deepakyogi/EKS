package com.mongo.example.mongodemo.models.apimodel;

public class LatLong {
	private double lang;
	private double lat;

	public LatLong() {
		super();
	}

	public LatLong(double lang, double lat) {
		super();
		this.lang = lang;
		this.lat = lat;
	}

	public double getLang() {
		return lang;
	}

	public void setLang(double lang) {
		this.lang = lang;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "LatLong [lang=" + lang + ", lat=" + lat + "]";
	}

	

}
