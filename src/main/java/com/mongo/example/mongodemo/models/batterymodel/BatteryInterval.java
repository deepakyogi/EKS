package com.mongo.example.mongodemo.models.batterymodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "battery_interval")
public class BatteryInterval {

	@Id
	private String batteryIntervalId;
	private String id;
	private BatteryIntervalDay day;
//abhijiit
	private List<HashMap<Integer, ChargeDischargeTime>> hour = new ArrayList<>();

	public BatteryInterval() {
		super();
	}

	public BatteryInterval(String batteryIntervalId, String id, BatteryIntervalDay day,
			List<HashMap<Integer, ChargeDischargeTime>> hour) {
		super();
		this.batteryIntervalId = batteryIntervalId;
		this.id = id;
		this.day = day;
		this.hour = hour;
	}

	public String getBatteryIntervalId() {
		return batteryIntervalId;
	}

	public void setBatteryIntervalId(String batteryIntervalId) {
		this.batteryIntervalId = batteryIntervalId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BatteryIntervalDay getDay() {
		return day;
	}

	public void setDay(BatteryIntervalDay day) {
		this.day = day;
	}

	public List<HashMap<Integer, ChargeDischargeTime>> getHour() {
		return hour;
	}

	public void setHour(List<HashMap<Integer, ChargeDischargeTime>> hour) {
		this.hour = hour;
	}

}
