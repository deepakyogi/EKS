package com.mongo.example.mongodemo.models.apimodel;

import org.springframework.data.mongodb.core.mapping.Field;

public class Location {
	@Field(name = "current_location")
	private LatLong currentLocation;
	
	private RoutingData routes;
    private Geofence geofence;

    public Location() {
		super();
	}
    
	public Location(LatLong current_location, RoutingData routes, Geofence geofence) {
		super();
		this.currentLocation = current_location;
		this.routes = routes;
		this.geofence = geofence;
	}
	
	public LatLong getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LatLong currentLocation) {
		this.currentLocation = currentLocation;
	}

	public RoutingData getRoutes() {
		return routes;
	}

	public void setRoutes(RoutingData routes) {
		this.routes = routes;
	}

	public Geofence getGeofence() {
		return geofence;
	}

	public void setGeofence(Geofence geofence) {
		this.geofence = geofence;
	}

	@Override
	public String toString() {
		return "Location [currentLocation=" + currentLocation + ", routes=" + routes + ", geofence=" + geofence + "]";
	}
    
    
}
