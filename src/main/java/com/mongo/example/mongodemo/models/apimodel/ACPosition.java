package com.mongo.example.mongodemo.models.apimodel;

import org.springframework.data.mongodb.core.mapping.Field;

public class ACPosition {
	@Field(name = "ac_state")
	private int acState;
	
	@Field(name = "ac_vent_flow")
	private String acVentFlow;
	
	private int temperature;

	public ACPosition() {
		super();
	}

	public ACPosition(int acState, String acVentFlow, int temperature) {
		super();
		this.acState = acState;
		this.acVentFlow = acVentFlow;
		this.temperature = temperature;
	}

	public int getAcState() {
		return acState;
	}

	public void setAcState(int acState) {
		this.acState = acState;
	}

	public String getAcVentFlow() {
		return acVentFlow;
	}

	public void setAcVentFlow(String acVentFlow) {
		this.acVentFlow = acVentFlow;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "ACPosition [acState=" + acState + ", acVentFlow=" + acVentFlow + ", temperature=" + temperature + "]";
	}
}
