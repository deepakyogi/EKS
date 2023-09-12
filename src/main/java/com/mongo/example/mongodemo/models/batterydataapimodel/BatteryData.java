package com.mongo.example.mongodemo.models.batterydataapimodel;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "battery")
public class BatteryData {
	@Column(name = "id")
	private int id;

	@Column(name = "vin")
	private int vin;

	@Column(name = "type")
	private String Type;

	@Column(name = "subtype")
	private String subtype;

	@Column(name = "sumvolt")
	private int sumvolt;

	@Column(name = "temperature")
	private int Temperature;

	@Column(name = "state")
	private String state;

	@Column(name = "current")
	private int current;

	@Column(name = "voltage")
	private int voltage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVin() {
		return vin;
	}

	public void setVin(int vin) {
		this.vin = vin;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public int getSumvolt() {
		return sumvolt;
	}

	public void setSumvolt(int sumvolt) {
		this.sumvolt = sumvolt;
	}

	public int getTemperature() {
		return Temperature;
	}

	public void setTemperature(int temperature) {
		Temperature = temperature;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public BatteryData(int id, int vin, String type, String subtype, int sumvolt, int temperature, String state,
			int current, int voltage) {
		super();
		this.id = id;
		this.vin = vin;
		Type = type;
		this.subtype = subtype;
		this.sumvolt = sumvolt;
		Temperature = temperature;
		this.state = state;
		this.current = current;
		this.voltage = voltage;
	}

	public BatteryData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Battery [id=" + id + ", vin=" + vin + ", Type=" + Type + ", subtype=" + subtype + ", sumvolt=" + sumvolt
				+ ", Temperature=" + Temperature + ", state=" + state + ", current=" + current + ", voltage=" + voltage
				+ "]";
	}

}
