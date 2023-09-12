package com.mongo.example.mongodemo.models.apimodel;

import org.springframework.data.mongodb.core.mapping.Field;

public class Door {
	@Field(name = "door_front_left")
	private int doorFrontLeft ;
	
	@Field(name = "door_front_right")
	private int doorFrontRight;
	
	@Field(name = "door_rear_right")
	private int doorRearRight;
	
	@Field(name = "door_rear_left")
	private int doorRearLeft;

	public Door() {
		super();
	}

	public Door(int doorFrontLeft, int doorFrontRight, int doorRearRight, int doorRearLeft) {
		super();
		this.doorFrontLeft = doorFrontLeft;
		this.doorFrontRight = doorFrontRight;
		this.doorRearRight = doorRearRight;
		this.doorRearLeft = doorRearLeft;
	}

	public int getDoorFrontLeft() {
		return doorFrontLeft;
	}

	public void setDoorFrontLeft(int doorFrontLeft) {
		this.doorFrontLeft = doorFrontLeft;
	}

	public int getDoorFrontRight() {
		return doorFrontRight;
	}

	public void setDoorFrontRight(int doorFrontRight) {
		this.doorFrontRight = doorFrontRight;
	}

	public int getDoorRearRight() {
		return doorRearRight;
	}

	public void setDoorRearRight(int doorRearRight) {
		this.doorRearRight = doorRearRight;
	}

	public int getDoorRearLeft() {
		return doorRearLeft;
	}

	public void setDoorRearLeft(int doorRearLeft) {
		this.doorRearLeft = doorRearLeft;
	}

	@Override
	public String toString() {
		return "Door [doorFrontLeft=" + doorFrontLeft + ", doorFrontRight=" + doorFrontRight + ", doorRearRight="
				+ doorRearRight + ", doorRearLeft=" + doorRearLeft + "]";
	}
}
