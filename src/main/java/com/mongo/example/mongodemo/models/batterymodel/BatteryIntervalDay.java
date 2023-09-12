package com.mongo.example.mongodemo.models.batterymodel;

import org.springframework.data.mongodb.core.mapping.Field;

public class BatteryIntervalDay {
	
	@Field(name = "totalduration")
	private DayTotalDuration totalduration;
	
	private DayCycle cycle;
	
	
	public DayTotalDuration getTotalduration() {
		return totalduration;
	}
	public void setTotalduration(DayTotalDuration totalduration) {
		this.totalduration = totalduration;
	}
	public DayCycle getCycle() {
		return cycle;
	}
	public void setCycle(DayCycle cycle) {
		this.cycle = cycle;
	}
	
}
