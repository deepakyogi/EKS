//package com.mongo.example.mongodemo.aws;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.services.iot.client.AWSIotException;
//import com.amazonaws.services.iot.client.AWSIotQos;
//import com.amazonaws.services.iot.client.AWSIotTopic;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.mongo.example.mongodemo.models.apimodel.Car;
//
//
//@Service
//public class MqttPubsubService {
//	
//	@Autowired
//	Publisher conn;
//	
//	 public void publishmessage(String payload) throws AWSIotException, JsonProcessingException 
//	 {
//		 conn.publish(payload);	
//		 System.out.println("connection done" );		 
//	}
//	 
//	 public void subscribe(AWSIotTopic topic) {
//			conn.subscribe( topic);
//		}
//
//}
