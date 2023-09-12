package com.mongo.example.mongodemo.models.apimodel;

import org.springframework.data.mongodb.core.mapping.Field;
//import org.springframework.data.neo4j.core.schema.*;

public class Battery {
	
//	@Field(name = "sum_volt")
//	@Property("sum_volt")
	private Double sumVolt;
	
	private Double current;
	private Double temperature;
	private Double voltage;
	private String mode;
	private String soc;
	private String soh;

	public Battery() {
		super();
	}


	public Battery(Double sumVolt, Double current, Double temperature, Double voltage, String mode, String soc,
			String soh) {
		super();
		this.sumVolt = sumVolt;
		this.current = current;
		this.temperature = temperature;
		this.voltage = voltage;
		this.mode = mode;
		this.soc = soc;
		this.soh = soh;
	}


	public Double getSumVolt() {
		return sumVolt;
	}

	public void setSumVolt(Double sumVolt) {
		this.sumVolt = sumVolt;
	}

	public Double getCurrent() {
		return current;
	}

	public void setCurrent(Double current) {
		this.current = current;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getSoc() {
		return soc;
	}


	public void setSoc(String soc) {
		this.soc = soc;
	}


	public String getSoh() {
		return soh;
	}


	public void setSoh(String soh) {
		this.soh = soh;
	}


	@Override
	public String toString() {
		return "Battery [sumVolt=" + sumVolt + ", current=" + current + ", temperature=" + temperature + ", voltage="
				+ voltage + ", mode=" + mode + ", soc=" + soc + ", soh=" + soh + "]";
	}



}
