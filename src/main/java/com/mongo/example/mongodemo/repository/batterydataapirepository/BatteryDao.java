package com.mongo.example.mongodemo.repository.batterydataapirepository;

import java.util.List;
import com.mongo.example.mongodemo.models.batterydataapimodel.BatteryData;

public interface BatteryDao {
	
	public List <BatteryData> getBattery();
	public BatteryData getBattery(int id);
	public BatteryData addBattery(BatteryData battey);
	public BatteryData UpdateBattery(BatteryData battery);
	public void deleBattery(int vin);
	
	
	// set of operation to be perfomed 
	}


