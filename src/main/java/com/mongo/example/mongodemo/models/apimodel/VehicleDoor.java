package com.mongo.example.mongodemo.models.apimodel;

import javax.persistence.Column;

import org.springframework.data.mongodb.core.mapping.Field;

public class VehicleDoor {
	@Field(name = "child_lock")
	private int childLock;
	private Door door;
	private Window window;

	public VehicleDoor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VehicleDoor(int childLock, Door door, Window window) {
		super();
		this.childLock = childLock;
		this.door = door;
		this.window = window;
	}
	
	public int getChildLock() {
		return childLock;
	}

	public void setChildLock(int childLock) {
		this.childLock = childLock;
	}

	public Door getDoor() {
		return door;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	@Override
	public String toString() {
		return "VehicleDoor [child_lock=" + childLock + ", door=" + door + ", window=" + window + "]";
	}

}
