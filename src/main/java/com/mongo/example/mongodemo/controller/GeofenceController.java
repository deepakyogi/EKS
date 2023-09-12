//package com.mongo.example.mongodemo.controller;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.amazonaws.services.iot.client.AWSIotMessage;
//import com.amazonaws.services.iot.client.AWSIotQos;
//import com.amazonaws.services.iot.client.AWSIotTopic;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mongo.example.mongodemo.aws.AwsMqttHelper;
//import com.mongo.example.mongodemo.models.apimodel.Car;
//import com.mongo.example.mongodemo.models.apimodel.Geofence;
//import com.mongo.example.mongodemo.models.apimodel.LatLong;
//import com.mongo.example.mongodemo.repository.CarRepository;
//import com.mongo.example.mongodemo.repository.CustomCarRepositoryImpl;
//
//@CrossOrigin(origins = "http://localhost:8080")
//@RestController
//@RequestMapping("/geofence")
//public class GeofenceController {
//	Geofence geofenceAttr;
//	LatLong vehicleLatLang = new LatLong(0, 0);
//	AwsMqttHelper awsMqttHelper = new AwsMqttHelper();
//
//	@Autowired
//	CarRepository carRepo;
//
//	@Autowired
//	CustomCarRepositoryImpl ca;
//	
//	String payload;
//	
//	@PutMapping("/setG/{id}")
//	public ResponseEntity<?> setGeofence(@PathVariable("id") int id, @RequestBody Geofence obj) {
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
//
//		System.out.println("setg");
//		geofenceAttr = obj;
//		System.out.println("abhi= " + obj);
//		v.getLocation().setGeofence(obj);
//
//		carRepo.save(v);
//payload= "pay "+v;
//		awsMqttHelper.connect();
//		AWSIotQos qos = AWSIotQos.QOS0;
//		AWSIotTopic topic = new TestTopicListener("location", qos);
////		awsMqttHelper.subscribe(topic,payload);
//
//		System.out.println("GEOFENCE_STATUS: geofence setted");
//		return new ResponseEntity<String>("success", HttpStatus.OK);
//	}
//
//	class TestTopicListener extends AWSIotTopic {
//		public TestTopicListener(String topic, AWSIotQos qos) {
//			super(topic, qos);
//		}
//
//		@Override
//		public void onMessage(AWSIotMessage message) {
//			System.out.println(System.currentTimeMillis() + ": <<< " + message.getStringPayload());
//			ObjectMapper objectMapper = new ObjectMapper();
//			byte[] bytes = message.getPayload();
//			LatLong latLang = new LatLong();
//			try {
//				latLang = objectMapper.readValue(bytes, LatLong.class);
//				System.out.println(latLang.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			String topic = "geofence_status";
//			String geofence_status = calculateVehicleStatus(latLang);
////			try {
////				awsMqttHelper.publish(topic, geofence_status);
////				System.out.println("geofence published");
////			} catch (JsonProcessingException | AWSIotException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		}
//	}
//
////	@GetMapping("/activateG/{id}")
////	public ResponseEntity<?> activateGeofence(@PathVariable("id") int id) {
////		return new ResponseEntity<String>("success", HttpStatus.OK);
////	}
////
////	@PutMapping("/setVehicleLocation/{id}")
////	public ResponseEntity<?> setVehicleLocation(@PathVariable("id") int id,
////			@RequestBody LatLong vehicleCurrentLatLang) {
//////      
//////      vehicleLatLang =vehicleCurrentLatLang;
//////      System.err.println(calculateVehicleStatus());
////		return new ResponseEntity<String>(HttpStatus.OK);
////	}
//
//	public String calculateVehicleStatus(LatLong latLang) {
//		double distance = calculateDistance(latLang);
//		if (distance < geofenceAttr.getRadius()) {
//			return "INSIDE";
//		} else
//			return "OUTSIDE";
//	}
//
//	public double calculateDistance(LatLong latLang) {
//		LatLong point1 = geofenceAttr.getPoint();
//		LatLong point2 = latLang;
//		double lat1 = point1.getLat();
//		double lon1 = point1.getLang();
//		double lat2 = point2.getLat();
//		double lon2 = point2.getLang();
//		if ((lat1 == lat2) && (lon1 == lon2)) {
//			return 0;
//		} else {
//			double theta = lon1 - lon2;
//			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
//					+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
//			dist = Math.acos(dist);
//			dist = dist * 6371 * 1000;
//			return (dist);
//		}
//	}
//}
