package com.mongo.example.mongodemo.models.apimodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Document(collection = "car")
public class Car {
	@Id
	private int vin;
	private int level;
	private int speed;
	private int rpm;

	private int mileage;
	private int trip;
	
	@Field(name = "drive_mode")
	private String driveMode ;	
	
	@Field(name = "gear_mode")
	private String gearMode ;
	
	private int trunk;

	@Length(min = 16, max = 30)
	@Field(name = "current_temp")
	private int currentTemp;
	private int ac;

	@NotNull
	@Size(min = 0, max = 5)
	@Field(name = "sun_roof_state")
	private int sunRoofState;

	private int engine;
	private int hazard;
	
	@Field(name = "min_temp")
	private int minTemp = 16;
	
	@Field(name = "max_temp")
	private int maxTemp = 30;
	
	private int bonet;
	private int horn;
	
	@Field(name = "master_lock")
    private int masterLock;

	@Field(name = "vehicle_doors")
	private VehicleDoor vehicleDoors;
	
	
	private Light lights;
	
	@Field(name = "side_mirror")
	private Side sideMirror;
	
	private Battery battery;
	private Hvac hvac;

	private Location location;

	public Car() {
		super();
	}

	public Car(int vin, int level, int speed, int rpm, int mileage, int trip, String driveMode, String gearMode,
			int trunk, @Length(min = 16, max = 30) int currentTemp, int ac,
			@NotNull @Size(min = 0, max = 5) int sunRoofState, int engine, int hazard, int minTemp, int maxTemp,
			int bonet, int horn, int masterLock, VehicleDoor vehicleDoors, Light lights, Side sideMirror,
			Battery battery, Hvac hvac, Location location) {
		super();
		this.vin = vin;
		this.level = level;
		this.speed = speed;
		this.rpm = rpm;
		this.mileage = mileage;
		this.trip = trip;
		this.driveMode = driveMode;
		this.gearMode = gearMode;
		this.trunk = trunk;
		this.currentTemp = currentTemp;
		this.ac = ac;
		this.sunRoofState = sunRoofState;
		this.engine = engine;
		this.hazard = hazard;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.bonet = bonet;
		this.horn = horn;
		this.masterLock = masterLock;
		this.vehicleDoors = vehicleDoors;
		this.lights = lights;
		this.sideMirror = sideMirror;
		this.battery = battery;
		this.hvac = hvac;
		this.location = location;
	}

	public Car(int vin, @Length(min = 16, max = 30) int currentTemp, int bonet) {
		super();
		this.vin = vin;
		this.currentTemp = currentTemp;
		this.bonet = bonet;
	}

	public int getVin() {
		return vin;
	}

	public void setVin(int vin) {
		this.vin = vin;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTrunk() {
		return trunk;
	}

	public void setTrunk(int trunk) {
		this.trunk = trunk;
	}

	public int getAc() {
		return ac;
	}

	public void setAc(int ac) {
		this.ac = ac;
	}

	public int getEngine() {
		return engine;
	}

	public void setEngine(int engine) {
		this.engine = engine;
	}

	public int getHazard() {
		return hazard;
	}

	public void setHazard(int hazard) {
		this.hazard = hazard;
	}

	
	public int getBonet() {
		return bonet;
	}

	public void setBonet(int bonet) {
		this.bonet = bonet;
	}

	public int getHorn() {
		return horn;
	}

	public void setHorn(int horn) {
		this.horn = horn;
	}
	
	public Light getLights() {
		return lights;
	}

	public void setLights(Light lights) {
		this.lights = lights;
	}

	public Battery getBattery() {
		return battery;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	public Hvac getHvac() {
		return hvac;
	}

	public void setHvac(Hvac hvac) {
		this.hvac = hvac;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(int currentTemp) {
		this.currentTemp = currentTemp;
	}

	public int getSunRoofState() {
		return sunRoofState;
	}

	public void setSunRoofState(int sunRoofState) {
		this.sunRoofState = sunRoofState;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getMasterLock() {
		return masterLock;
	}

	public void setMasterLock(int masterLock) {
		this.masterLock = masterLock;
	}

	public VehicleDoor getVehicleDoors() {
		return vehicleDoors;
	}

	public void setVehicleDoors(VehicleDoor vehicleDoors) {
		this.vehicleDoors = vehicleDoors;
	}

	public Side getSideMirror() {
		return sideMirror;
	}

	public void setSideMirror(Side sideMirror) {
		this.sideMirror = sideMirror;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getTrip() {
		return trip;
	}

	public void setTrip(int trip) {
		this.trip = trip;
	}

	public String getDriveMode() {
		return driveMode;
	}

	public void setDriveMode(String driveMode) {
		this.driveMode = driveMode;
	}

	public int getRpm() {
		return rpm;
	}

	public void setRpm(int rpm) {
		this.rpm = rpm;
	}

	public String getGearMode() {
		return gearMode;
	}

	public void setGearMode(String gearMode) {
		this.gearMode = gearMode;
	}

	@Override
	public String toString() {
		return "Car [vin=" + vin + ", level=" + level + ", speed=" + speed + ", rpm=" + rpm + ", mileage=" + mileage
				+ ", trip=" + trip + ", driveMode=" + driveMode + ", gearMode=" + gearMode + ", trunk=" + trunk
				+ ", currentTemp=" + currentTemp + ", ac=" + ac + ", sunRoofState=" + sunRoofState + ", engine="
				+ engine + ", hazard=" + hazard + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp + ", bonet=" + bonet
				+ ", horn=" + horn + ", masterLock=" + masterLock + ", vehicleDoors=" + vehicleDoors + ", lights="
				+ lights + ", sideMirror=" + sideMirror + ", battery=" + battery + ", hvac=" + hvac + ", location="
				+ location + "]";
	}
	
}