//package com.mongo.example.mongodemo.mqtt;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//
//
//  public class RabbitConnection
//{
//	   
//	   public static RabbitConnection single_instance= null;
//
//	public MqttClient client ;
//	
//	
//	
//	public RabbitConnection() {
//		super();
//		try {
//			client= new MqttClient("tcp://172.22.236.133:1883", MqttClient.generateClientId());
//		} catch (MqttException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	 public static  RabbitConnection getInstance() 
//	    { 
//	        if (single_instance == null) 
//	            single_instance = new  RabbitConnection();
//	        return single_instance; 
//	    } 
//
//
//
//
//	
//}