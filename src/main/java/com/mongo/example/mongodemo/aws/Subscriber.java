//package com.mongo.example.mongodemo.aws;
//
//import com.amazonaws.services.iot.client.AWSIotException;
//import com.amazonaws.services.iot.client.AWSIotMqttClient;
//import com.amazonaws.services.iot.client.AWSIotTopic;
//
//public class Subscriber {
//
//	AWSIotMqttClient client = null;
//
//	public void subscribe(AWSIotTopic topic) {
//		try {
//			client.subscribe(topic);
//		} catch (AWSIotException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
