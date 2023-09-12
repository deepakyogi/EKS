package com.mongo.example.mongodemo.repository.batterydataapirepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Repository;

import com.mongo.example.mongodemo.models.batterydataapimodel.BatteryData;

@Repository
public class BatteryDaoImpl implements BatteryDao {
	final InfluxDB connection = InfluxDBFactory.connect("http://localhost:8086");
    String dbName = "car";

        Query queryObject = new Query("CREATE DATABASE "+dbName,dbName);
        QueryResult queryResult= connection.query(queryObject);

        String retentionPolicyName = "forever";
        Query queryObject1 = new Query("CREATE RETENTION POLICY "+ retentionPolicyName + " ON " + dbName + " DURATION 1d REPLICATION 1 DEFAULT",dbName);
        QueryResult queryResult1 = connection.query(queryObject1);
	
	public List<BatteryData> getPoints(String query) {
		Query queryObject = new Query(query,dbName);
        QueryResult queryResult = connection.query(queryObject);
//
//        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
//        System.out.println("connection establish");
//        return resultMapper.toPOJO(queryResult,Battery.class); 
         InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
         return resultMapper.toPOJO(queryResult,BatteryData.class);
//      
			    
	
	}


	@Override
	public BatteryData addBattery(BatteryData battery) {
		

				 
        		Point point = Point.measurement("battery")// measurement name same as table name
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
     				   .tag("battery", "data")
                       .addField("id",battery.getId())
                       .addField("state", battery.getState())
                       .addField("vin", battery.getVin())
                       .addField("subtype", battery.getSubtype())
                       .addField("sumvolt", battery.getSumvolt())
                       .addField("type",battery.getType())
                       .addField("temperature", battery.getTemperature())
                       .addField("current", battery.getCurrent())
                       .addField("voltage", battery.getVoltage())
                       
                       .build();
        		
        		connection.write(dbName,"forever",point);
                       return battery;
	}

	@Override
	public List<BatteryData> getBattery() {
	List<BatteryData> allBattery = getPoints("select * from battery");
	System.out.println(allBattery);
		return allBattery;
	}


	@Override
	public BatteryData getBattery(int id) {
	
		List<BatteryData>batteryPointList = getPoints("Select * from battery where id = " +id);
		if(batteryPointList.size()==0) {
			return null;
		}
		else {
			return batteryPointList.get(0);
		}
	}

	@Override
	public BatteryData UpdateBattery(BatteryData battery) {
		return null;
	}

	@Override
	public void deleBattery(int vin) {
		  String query = "DELETE FROM Course where vin="+"'"+vin+"'";
	        Query queryObject = new Query(query, dbName);
	        QueryResult queryResult = connection.query(queryObject);
	}


}
