package com.mongo.example.mongodemo.aws;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.services.vehiclecommservice.CarInterface;

public class MyTopicListener implements MessageListener	{

	@Autowired
	private CarInterface carInterface;
	
	public MyTopicListener( String topic, AWSIotQos qos ) {
	}
	
	@Override
	public void onMessage(Message message) {
	System.out.println("Message => " + message);
		 
		AWSIotMessage a = (AWSIotMessage) message;
		
		

		System.out.println(System.currentTimeMillis() + ": <<< " + "{\"type\" : \"vehicle_state\","
				+ "\"subType\" : \"info\"," + "\"data\" : \"" + ((AWSIotMessage) message).getStringPayload() + "}");

//         new MyListener(message).start();

		ObjectMapper mapper = new ObjectMapper();
		
		
		JsonNode json = null;
		try {
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
			json = mapper.readTree(((AWSIotMessage) message).getStringPayload());

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		byte[] bytes = new byte[0];
		Car car = new Car();

//        JSONObject json1 = (JSONObject) JSONSerializer.toJSON("data");        
		JsonNode carString = json.findValue("data");
		System.out.println(carString);
		String v = carString.toString();
		
		JsonNode vinNo = json.findValue("vin");
		JsonNode current_temp = json.findValue("current_temp");
		JsonNode bonet = json.findValue("bonet");
		
		
		int stringToInt = Integer.parseInt(vinNo.asText());
		int Icurrent_temp = Integer.parseInt(current_temp.asText());
		int Ibonet = Integer.parseInt(bonet.asText());
		
		System.out.println(stringToInt +" "+ Icurrent_temp+ " "+  Ibonet);
		bytes = v.getBytes();
		try {
			
			car = mapper.readValue(bytes, Car.class);
			car.setVin(vinNo.asInt());

//			car.setCurrentTemp(Icurrent_temp);
//			car.setBonet(Ibonet);
			
			
//			m.myMethod(5);
//			Thread.getcurrentThread();
			
			carInterface.save(car);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("car =" + car);
	}
}
