package com.mongo.example.mongodemo.models.batterymodel;

import org.springframework.data.mongodb.core.mapping.Field;

public class ChargeDischargeTime {
	
	@Field(name = "total_charging_time")
	private int totalChargingTime;
	
	@Field(name = "total_discharging_time")
	private int totalDischargingTime;

	@Field(name = "max_charge")
	private int maxCharge;
	
	public int getTotalChargingTime() {
		return totalChargingTime;
	}

	public void setTotalChargingTime(int totalChargingTime) {
		this.totalChargingTime = totalChargingTime;
	}

	public int getTotalDischargingTime() {
		return totalDischargingTime;
	}

	public void setTotalDischargingTime(int totalDischargingTime) {
		this.totalDischargingTime = totalDischargingTime;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(int maxCharge) {
		this.maxCharge = maxCharge;
	}
	
}
