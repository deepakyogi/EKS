package com.mongo.example.mongodemo.models.batterymodel;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Document(collection = "battery_cycles")
public class BatteryCycles {
	@Id
	private String batteryCyclesId;
	
	@Field(name = "start_time")
	private String startTime;
	
	@Field(name = "end_time")
	private String endTime;
	
	@Field(name = "difference")
	private String difference;
	
	@Field(name = "state")
	private String state;
	
	@Field(name = "max_charge")
	private String maxCharge;
	
	@Field(name = "soc_duration")
	private String socDuration;

	public BatteryCycles() {
		super();
	}

	public String getBatteryCyclesId() {
		return batteryCyclesId;
	}
	
	public void setBatteryCyclesId(String batteryCyclesId) {
		this.batteryCyclesId = batteryCyclesId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(String maxCharge) {
		this.maxCharge = maxCharge;
	}

	public String getSocDuration() {
		return socDuration;
	}

	public void setSocDuration(String socDuration) {
		this.socDuration = socDuration;
	}

}
