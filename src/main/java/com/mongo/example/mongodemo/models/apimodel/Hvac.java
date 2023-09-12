package com.mongo.example.mongodemo.models.apimodel;

import org.springframework.data.mongodb.core.mapping.Field;

public class Hvac {
	@Field(name = "defrost_front")
	private int defrostFront;
	
	@Field(name = "defrost_rear")
	private int defrostRear;
	
	@Field(name = "air_circulation")
	private int airCirculation;
	
	private ACPosition front;
	private ACPosition rear;

	public Hvac() {
		super();
	}

	public Hvac(int defrostFront, int defrostRear, int airCirculation, ACPosition front, ACPosition rear) {
		super();
		this.defrostFront = defrostFront;
		this.defrostRear = defrostRear;
		this.airCirculation = airCirculation;
		this.front = front;
		this.rear = rear;
	}

	public int getDefrostFront() {
		return defrostFront;
	}

	public void setDefrostFront(int defrostFront) {
		this.defrostFront = defrostFront;
	}

	public int getDefrostRear() {
		return defrostRear;
	}

	public void setDefrostRear(int defrostRear) {
		this.defrostRear = defrostRear;
	}

	public int getAirCirculation() {
		return airCirculation;
	}

	public void setAirCirculation(int airCirculation) {
		this.airCirculation = airCirculation;
	}

	public ACPosition getFront() {
		return front;
	}

	public void setFront(ACPosition front) {
		this.front = front;
	}

	public ACPosition getRear() {
		return rear;
	}

	public void setRear(ACPosition rear) {
		this.rear = rear;
	}

	@Override
	public String toString() {
		return "Hvac [defrostFront=" + defrostFront + ", defrostRear=" + defrostRear + ", airCirculation="
				+ airCirculation + ", front=" + front + ", rear=" + rear + "]";
	}
}
