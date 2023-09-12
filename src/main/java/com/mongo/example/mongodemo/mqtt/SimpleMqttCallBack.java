//package com.mongo.example.mongodemo.mqtt;
//
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//public class SimpleMqttCallBack implements MqttCallback {
//
//		  public void connectionLost(Throwable throwable) {
//		    System.out.println("Connection to MQTT broker lost!");
//		  }
//
//		  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
//
//
//		System.out.println("Message received:\t"+ new String(mqttMessage.getPayload()) );
////		System.out.println(
////                "  Topic:\t" + s +
////                "  Message:\t" + new String(mqttMessage.getPayload()) +
////                "  QoS:\t" + mqttMessage.getQos());
////		
//		  }
//		  
//
//		  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//
//			  
//		  }
//}
