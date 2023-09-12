package com.mongo.example.mongodemo.models.apimodel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class RoutingData {
	@Field(name = "dest_location")
	private LatLong destLocation;
	
	@Field(name = "start_location")
	private LatLong startLocation;
	
	private List<LatLong> stops = new ArrayList<LatLong>();

	public RoutingData() {
		super();
	}

	public RoutingData(LatLong destLocation, LatLong startLocation, List<LatLong> stops) {
		super();
		this.destLocation = destLocation;
		this.startLocation = startLocation;
		this.stops = stops;
	}

	public LatLong getDestLocation() {
		return destLocation;
	}

	public void setDestLocation(LatLong destLocation) {
		this.destLocation = destLocation;
	}

	public LatLong getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(LatLong startLocation) {
		this.startLocation = startLocation;
	}

	public List<LatLong> getStops() {
		return stops;
	}

	public void setStops(List<LatLong> stops) {
		this.stops = stops;
	}

	@Override
	public String toString() {
		return "RoutingData [destLocation=" + destLocation + ", startLocation=" + startLocation + ", stops=" + stops
				+ "]";
	}

}
