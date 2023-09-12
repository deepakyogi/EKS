//package com.mongo.example.mongodemo.aws;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.context.annotation.Configuration;
//
//import com.amazonaws.services.iot.client.AWSIotDevice;
//
//import com.amazonaws.services.iot.client.AWSIotException;
//import com.amazonaws.services.iot.client.AWSIotMqttClient;
//import com.amazonaws.services.iot.client.AWSIotQos;
//import com.amazonaws.services.iot.client.AWSIotTopic;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mongo.example.mongodemo.models.apimodel.Car;
//
//@Configuration
//public class Publisher {
//
//
//	Connection conn =new Connection();
//	AWSIotMqttClient client = null;
//	
//	
//	
//	public void publish(String payload) throws AWSIotException, JsonProcessingException {
//		
//		//String topic = "$aws/things/data/shadow/update";
//		String topic = "vehicle/vin/control/action";
//		AWSIotQos qos = AWSIotQos.QOS0;
//
//		long timeout = 3000;    
//		               // milliseconds
//		ObjectMapper mapper = new ObjectMapper();
//		AWSIotDevice device = new AWSIotDevice(conn.clientId);
//		//String state = "{\"state\":{\"reported\":{\"sensor\":3.0}}}";
//		client = new AWSIotMqttClient(conn.clientEndpoint, conn.clientId, conn.awsAccessKeyId,conn.awsSecretAccessKey,
//				null);
//		client.attach(device);
//		client.connect();
//		MyMessage message = new MyMessage(topic, qos, mapper.writeValueAsString(payload));
//		//MyMessage message = new MyMessage(topic, qos, state);
//		client.publish(message, timeout);
//
//			}
//	
//	public void subscribe(AWSIotTopic topic) {
//		try {
//			client.connect();
//			client.subscribe(topic);
//		} catch (AWSIotException e) {
//			e.printStackTrace();
//		}
//	}
//	 
//}
