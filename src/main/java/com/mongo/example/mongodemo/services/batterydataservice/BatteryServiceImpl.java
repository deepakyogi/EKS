package com.mongo.example.mongodemo.services.batterydataservice;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.example.mongodemo.models.batterydataapimodel.BatteryData;
import com.mongo.example.mongodemo.repository.batterydataapirepository.BatteryDao;

@Service
public class BatteryServiceImpl implements BatteryService {
	
	@Autowired
	private BatteryDao batteryDao;
	
	public BatteryServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<BatteryData> getBattery() {
		
		return this.batteryDao.getBattery();
	}

	@Override
	public BatteryData getBattery(int id) {
		// TODO Auto-generated method stub
		return this.batteryDao.getBattery(id);
	}

	@Override
	public BatteryData addBattery(BatteryData battey) {
		// TODO Auto-generated method stub
		return this.batteryDao.addBattery(battey);	}

	@Override
	public BatteryData UpdateBattery(BatteryData battery) {
		// TODO Auto-generated method stub
		return this.batteryDao.UpdateBattery(battery);
	}

	@Override
	public void deleBattery(int vin) {
		// TODO Auto-generated method stub
		this.batteryDao.deleBattery(vin);
	}

}
