package com.mongo.example.mongodemo.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

public class InfluxConnection {
	
private static  InfluxConnection mConn;
	
	public static InfluxConnection getInstance() {
		if(mConn== null) {
			mConn= new InfluxConnection();
			return mConn;
		}else {
			return mConn;
		}
	}
  
	public InfluxDB forConnection () 
	{	
		final String serverURL = "http://localhost:8086", username = "root", password = "root";
      final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);
      System.out.println("connection With influxDb Establish");
      return influxDB;
	}
}


