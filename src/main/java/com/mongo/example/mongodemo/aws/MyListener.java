//package com.mongo.example.mongodemo.aws;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import com.amazonaws.services.iot.client.AWSIotMessage;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.json.JsonMapper;
//import com.mongo.example.mongodemo.models.apimodel.Car;
//import com.mongo.example.mongodemo.repository.CarRepository;
//
//public class MyListener extends Thread{
//
//	CarRepository c ;
//
//	AWSIotMessage message;
//	
//	 public MyListener(	AWSIotMessage message) {
//	       this.message = message;
//	      }
//
//	 public void run() {
//	
//		 ObjectMapper mapper = new JsonMapper();
//         JsonNode json = null;
//            try {
//                 json = mapper.readTree(message.getStringPayload());
//                 System.out.println("json"+json);
//            } catch (JsonMappingException e) {
//                e.printStackTrace();
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//             
//                    ObjectMapper objectMapper = new ObjectMapper();
//         byte[] bytes = new byte[0];
//         Car car = new Car();
////         JSONObject json1 = (JSONObject) JSONSerializer.toJSON("data");        
//         
//          JsonNode carString = json.findValue("data");
//          System.out.println("data = "+json.findValue("data"));
//
//		String v = carString.toString();
//		System.out.println("v "+v);
////		json.findValue("data").asText()
//		String v1 = json.findValue("vin").toString();
//System.out.println("v1"+v1);
// 
//		bytes = v.getBytes() ;
//		System.out.println("byte "+bytes);
//		
//		try {	
//			car = objectMapper.readValue(bytes, Car.class);
//			car.setVin(Integer.parseInt(v1));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("car "+car);
//		
//		System.out.println("carrsepo=>"+c);
//     
//     
//	 }
//    
//    
//    
//}
