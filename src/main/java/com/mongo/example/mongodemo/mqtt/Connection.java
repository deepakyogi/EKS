//package com.mongo.example.mongodemo.mqtt;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.MqttSecurityException;
//
//public class Connection {
//
//	public static void Publisher() throws MqttException {
//		RabbitConnection conn = RabbitConnection.getInstance();
//		conn.client.connect();
	
//		String messageString = "Hello World from Java!";
//
//
//	    System.out.println("== START PUBLISHER ==");
//
//		  conn.client.connect();
//		    MqttMessage message = new MqttMessage();
//	    message.setPayload(messageString.getBytes());
//		  conn.client.publish("test", message);

//		}

//	public static void Subscriber() throws MqttException {
//		MqttClient client = new MqttClient("tcp://172.22.236.133:1883", MqttClient.generateClientId());
//		client.connect();
//		client.setCallback(new SimpleMqttCallBack());
//		client.subscribe("test");
//	}
//}

// Qos = 0 => at most once
//Qos = 1 => at least once
//Qos = 2 => exact once
