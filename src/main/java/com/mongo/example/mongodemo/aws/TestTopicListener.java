package com.mongo.example.mongodemo.aws;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.Document;
import org.bson.json.JsonObject;
import org.hibernate.validator.constraints.Length;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mongo.example.mongodemo.models.apimodel.Battery;
import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.models.apimodel.Hvac;
import com.mongo.example.mongodemo.models.apimodel.Light;
import com.mongo.example.mongodemo.models.apimodel.Location;
import com.mongo.example.mongodemo.models.apimodel.Side;
import com.mongo.example.mongodemo.models.apimodel.VehicleDoor;
import com.mongo.example.mongodemo.repository.CarRepository;
import com.mongo.example.mongodemo.services.vehiclecommservice.CarInterface;
import com.mongodb.client.MongoCollection;

//@JsonIgnoreProperties(ignoreUnknown = true)
//@Service

public class TestTopicListener extends AWSIotTopic {

//	@Autowired
//	Publisher awsMqttHelper;

	@Autowired
	private CarRepository carRepo;

//	@Autowired
//	MongoTemplate mongoTemplate;
	
	@Autowired
	private CarInterface carInterface;

	
//		MyClass m = new MyClass();
//	@Autowired
//	public static CarRepository carRepos;

//	CarInterface carInterface;

//	static Car c;
//	Car car = new Car();

//	public TestTopicListener(String topic) {
//		super(topic);
//		// TODO Auto-generated constructor stub
//	}

//	    public TestTopicListener(@Value("${topicString}") String topic, AWSIotQos qos, CarInterface carInterface) {
//	        super(topic, qos);
//	        this.carInterface = carInterface;
//	    }

	
	
	public TestTopicListener(String topic, AWSIotQos qos) {
		super(topic, qos);
	}

//    Handler mainHandler = new Handler(context.getMainLooper());
//
//    Runnable myRunnable = new Runnable() {
//        @Override 
//        public void run() {....} // This is your code
//    };
//    mainHandler.post(myRunnable);

	@Override
	public void onMessage(AWSIotMessage message) {

//		System.out.println(System.currentTimeMillis() + ": <<< " + message.getStringPayload());

//		JSONObject obj = new JSONObject();
//		
//
//		
//		obj.putAll(message.getStringPayload().toString());
//		
//		
//		ObjectMapper mapper = new ObjectMapper();
//		
//		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
//
//		byte[] bytes = new byte[0];
//
//		bytes =obj.

		System.out.println(System.currentTimeMillis() + ": <<< " + "{\"type\" : \"vehicle_state\","
				+ "\"subType\" : \"info\"," + "\"data\" : \"" + message.getStringPayload() + "}");

//         new MyListener(message).start();

		ObjectMapper mapper = new ObjectMapper();

		JsonNode json = null;
		try {
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
			json = mapper.readTree(message.getStringPayload());

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

		System.out.println(stringToInt + " " + Icurrent_temp + " " + Ibonet);
		bytes = v.getBytes();
		System.out.println("hi"+carInterface);
		try {

			car = mapper.readValue(bytes, Car.class);
			car.setVin(vinNo.asInt());

//			car.setCurrentTemp(Icurrent_temp);
//			car.setBonet(Ibonet);
			Car c = new Car(stringToInt, Icurrent_temp, Ibonet);
System.out.println(" c "+c);
//			(int vin, int level, int speed, int rpm, int mileage, int trip, String driveMode, String gearMode,
//					int trunk, @Length(min = 16, max = 30) int currentTemp, int ac,
//					@NotNull @Size(min = 0, max = 5) int sunRoofState, int engine, int hazard, int minTemp, int maxTemp,
//					int bonet, int horn, int masterLock, VehicleDoor vehicleDoors, Light lights, Side sideMirror,
//					Battery battery, Hvac hvac, Location location) 

carInterface.save(c);
//			m.myMethod(5);
//			Thread.getcurrentThread();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("car =" + car);
//		Runnable obj = new Runnable() {
//			@Override
//			public void run() {
//				System.out.println(car);
//				System.out.println(carRepo);
//				Car obj2 = carRepo.save(car);
//				System.out.println(obj2);
//			}
//		};
//		Thread t1 = new Thread(obj);
//		t1.start();

//		try {
//			System.out.println("try testtopic");
//			System.out.println("currnt thrd"+Thread.currentThread().getName());
//			Thread.sleep(5000);
//			Thread thread = new Thread() {
//			@Override
//			public void run() {
//				try {
////					service.publishmessage(payload);
////					AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);
//					Car saved = carRepo.save(car);
////				     System.out.println(saved+"saved");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		};
//			new Thretyr(car).start();
//			MyClass cvar = new MyClass();
//			System.out.println("xefvfd");
//			int byidvar = cvar.myMethod(stringToInt);
//			System.out.println(byidvar +"byid var");
//			System.out.println("carRepo => " + carRepo);
//
//			Car saved = carRepo.save(car);
//			System.out.println(saved + "saved");
//			load();	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

//	    public void load(){
//	    	
//	    	System.out.println("carRepo => " + carRepo);
//
//			Car saved = carRepo.save(car);
//			System.out.println(saved + "saved");
//			
//		}
}







