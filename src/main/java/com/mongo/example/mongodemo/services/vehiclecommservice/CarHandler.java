package com.mongo.example.mongodemo.services.vehiclecommservice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongo.example.mongodemo.SpringBootSmartVehicleApplication;
import com.mongo.example.mongodemo.aws.AwsMqttHelper;
import com.mongo.example.mongodemo.aws.myConstants;
import com.mongo.example.mongodemo.exception.BusinessException;
import com.mongo.example.mongodemo.models.apimodel.*;
import com.mongo.example.mongodemo.repository.CarRepository;
import com.mongodb.client.result.UpdateResult;

@Service
public class CarHandler implements CarInterface {

	MqttMessage message = new MqttMessage();

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	CarRepository carRepo;

//	@Autowired
//	CarRepositoryInterface carRepositoryinterface;

	private static final Logger logger = LoggerFactory.getLogger(SpringBootSmartVehicleApplication.class);

	Query query = null;
	List<Car> restCar = null;
	Car v;
	Update update;

	String payload;

	public String commonCode(int id) {
		query = new Query(Criteria.where("_id").is(id));
		restCar = mongoTemplate.find(query, Car.class);
//		Optional<Car> byidobj = carRepo.findById(id);
		v = restCar.get(0);
		return "common method";
	}

	public CarHandler(MongoTemplate mongoTemplate, CarRepository carRepo) {
		super();
		this.mongoTemplate = mongoTemplate;
		this.carRepo = carRepo;
//		this.carRepositoryinterface = carRepositoryinterface;
	}

	public List<Car> getData() {
		return carRepo.findAll();
	}

	@Override
	public String save(Car car) {
		carRepo.save(car);
		return " saved ";
	}
	
//	Add new 
	@Override
	public Car addNew(Car restCar) {
		if (restCar.getSpeed() < 0 || restCar.getSpeed() > 100) {
			System.out.println("carInterface");
			throw new BusinessException("631", "speed out of range");
		} else if (restCar.getSpeed() >= 0 && restCar.getSpeed() <= 20) {
			restCar.setLevel(1);
		} else if (restCar.getSpeed() > 20 && restCar.getSpeed() <= 40) {
			restCar.setLevel(2);
		} else if (restCar.getSpeed() > 40 && restCar.getSpeed() <= 60) {
			restCar.setLevel(3);
		} else if (restCar.getSpeed() > 60 && restCar.getSpeed() <= 80) {
			restCar.setLevel(4);
		} else if (restCar.getSpeed() > 80 && restCar.getSpeed() <= 100) {
			restCar.setLevel(5);
		}
		try {
			return this.carRepo.insert(restCar);
		} catch (Exception e) {
			throw new BusinessException("631",
					"Something went wrong in service layer while saving new user" + e.getMessage());
		}
	}

//	TRUNK ON - OFF
	@Override
	public String handlingTrunk(int id) {
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar.get(0).getTrunk() != 0) {
			update = new Update().set("trunk", 0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.TRUNK_CLOSE + "\"," + "\"Message\" : \" 0 \"" + "}";
//  MQTT
//			message.setPayload(payload.getBytes());
			logger.info("Trunk is closed");

		} else {
			update = new Update().set("trunk", 1);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.TRUNK_OPEN + "\"," + "\"Message\" : \" 1 \"" + "}";
// MQTT			
//			message.setPayload(payload.getBytes());
			logger.info("Trunk is OPEN ");
		}
		try {
//MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();
//AWS
//			AwsMqttHelper help = new AwsMqttHelper();
//			help.connect();
//			AWSIotQos qos = AWSIotQos.QOS0;
//			AWSIotTopic topic = new TestTopicListener("vehicle/vin/control/action/response", qos);
//			help.subscribe(topic);

//			AwsMqttHelper.instance.connect();
//			AWSIotQos qos = AWSIotQos.QOS0;
//			AWSIotTopic topic = new TestTopicListener(myConstants.TOPIC_ACTIONS_RESPONSE, qos);
//			AwsMqttHelper.instance.subscribe(topic);

			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (JsonProcessingException | AWSIotException e) {
			e.printStackTrace();
			throw new BusinessException("632A",
					"Something went wrong in service layer while handling trunk JsonProcessingException/AWSIotException"
							+ e.getMessage());
		} catch (BusinessException e) {
			throw new BusinessException("632B",
					"Something went wrong in service layer while handling trunk BusinessException" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("632C", "Something went wrong in service layer  " + e.getMessage());
		}
		UpdateResult result = mongoTemplate.upsert(query, update, Car.class);
		return "Trunk OPEN-CLOSE state changed: " + result.wasAcknowledged();

	}

//	AC temp change
	@Override
	public String AcTempChange(int id, String slide) {
		Optional<Car> byId = carRepo.findById(id);

		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (slide.equals("increase")) {

			update = new Update().inc("current_temp", 1);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.ACTEMP_INCREASE + "\"," + "\"Message\" : \"" + slide
					+ "}";

//			MQTT
//					message.setPayload(payload.getBytes());
			logger.info("current Temperature is increasing ");
		} else {
			update = new Update().inc("current_temp", -1);
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.ACTEMP_DECREASE + "\"," + "\"Message\" : \"" + slide
					+ "}";

//			MQTT
//					message.setPayload(payload.getBytes());
			logger.info("current Temperature is decreasing");
		}

		try {
//					Connection.Publisher();
//					conn.client.publish("test", message);
//					conn.client.disconnect();

//			AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (Exception e) {
			throw new BusinessException("633", "Something went wrong in repository  " + e.getMessage());
		}
		UpdateResult result = mongoTemplate.updateFirst(query, update, Car.class);
		return "Temperature changing: " + result.wasAcknowledged();
	}

//	AC ON - OFF
	@Override
	public String handlingAirConditioner(int id) {
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (restCar.get(0).getAc() == 0) {
			update = new Update().set("ac", 1);
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_ON + "\"," + "\"Message\" : \" 1 \"" + "}";
//	MQTT
			// message.setPayload(payload.getBytes());
			logger.info("AC is ON");
		} else {
			update = new Update().set("ac", 0);
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_OFF + "\"," + "\"Message\" : \" 0 \"" + "}";
//	MQTT
//			message.setPayload(payload.getBytes());
			logger.info("AC is OFF");
		}
		try {
// MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//	AWS		
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("634",
					"Something went wrong in service layer while handling AC " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("634", "Something went wrong in repository  " + e.getMessage());
		}
		UpdateResult result = mongoTemplate.upsert(query, update, Car.class);
		return "AC ON-OFF state change: " + result.wasAcknowledged();

	}

	@Override
	public String handlingSlider(int id, int value) {
		commonCode(id);
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = restCar.get(0);

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		v.setSunRoofState(value);

		if (value < 0 || value > 100) {
			throw new BusinessException("635 A", "value out of range");
		}

		payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
				+ "\"subType\" : \"" + VehicleControl.Actions.SLIDER_SET + "\"," + "\"Message\" : \"" + value + "\" }";
		// MQTT
		// message.setPayload(payload.getBytes());
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("635 B",
					"Something went wrong in service layer while handling sunroofslider " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("635", "Something went wrong in service  " + e.getMessage());
		}
		carRepo.save(v);
		return "slider state changing: " + v.getSunRoofState();

	}

	@Override
	public String handlingEngine(int id, int engineVal) {
		commonCode(id);
		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);
		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (restCar.get(0).getEngine() != 0) {
			v.setEngine(engineVal);
			// update = new Update().set("engine", 0);
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.ENGINE_OFF + "\"," + "\"Message\" : \"" + engineVal
					+ "\" }";
//      MQTT    
//          message.setPayload(payload.getBytes());
			logger.info("Engine is OFF ");
		} else {
//          update = new Update().set("engine", 1);
			v.setEngine(engineVal);
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.ENGINE_ON + "\"," + "\"Message\" : \"" + engineVal
					+ "\"}";
//   MQTT
//          message.setPayload(payload.getBytes());
			logger.info("Engine is ON");
		}
		try {
// MQTT
//          Connection.Publisher();
//          conn.client.publish("test", message);
//          conn.client.disconnect();
// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);
		} catch (BusinessException e) {
			throw new BusinessException("636",
					"Something went wrong in service layer while handling engine " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("636", "Something went wrong in repository  " + e.getMessage());
		}
//      UpdateResult result = mongoTemplate.updateFirst(query, update, Car.class);
		carRepo.save(v);
		return "Engine ON-OFF state changed: " + v.getEngine();
	}

	@Override
	public String handlingHazardLight(int id) {
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (restCar.get(0).getHazard() != 0) {
			update = new Update().set("hazard", 0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.HAZARD_LIGHT_OFF + "\"," + "\"Message\" : \" 0 \""
					+ "}";

//		MQTT	
//			message.setPayload(payload.getBytes());
			logger.info("Hazard light is OFF");
		} else {
			update = new Update().set("hazard", 1);
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.HAZARD_LIGHT_ON + "\"," + "\"Message\" : \" 1 \""
					+ "}";

			logger.info("Hazard light is ON ");
		}
		try {
// MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//	AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("637",
					"Something went wrong in service layer while handling hazard light" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("637", "Something went wrong in repository  " + e.getMessage());
		}

		UpdateResult result = mongoTemplate.updateFirst(query, update, Car.class);
		return "Hazard light ON-OFF state changed: " + result.wasAcknowledged();

	}

	@Override
	public String handlingBonet(int id) {
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (restCar.get(0).getBonet() != 0) {
			update = new Update().set("bonet", 0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.BONET_CLOSE + "\"," + "\"Message\" : \"0 \"" + "}";

//			MQTT	
//			message.setPayload(payload.getBytes());
			logger.info("Bonet is CLOSE");
		} else {
			update = new Update().set("bonet", 1);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.BONET_OPEN + "\"," + "\"Message\" : \" 1 \"" + "}";
//			MQTT	
//			message.setPayload(payload.getBytes());
			logger.info("Bonet is OPEN");
		}
		try {
// MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//	AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("638",
					"Something went wrong in service layer, handling bonet " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("638", "Something went wrong in repository  " + e.getMessage());
		}
		UpdateResult result = mongoTemplate.updateFirst(query, update, Car.class);
		return "BONET OPEN-CLOSE state changed: " + result.wasAcknowledged();

	}

	@Override
	public String handlingHornOn(int id) {
		commonCode(id);
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else {
			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.HORN + "\"," + "\" value =\" \"" + v.getHorn() + "}";

//			MQTT		
//			message.setPayload(payload.getBytes());	
		}
		try {
// MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("639",
					"Something went wrong in service layer while handling horn " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("639", "Something went wrong in repository  " + e.getMessage());
		}
		return "HORN  state : ";
	}

	@Override
	public String handlingChildLock(int id) {
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar.get(0).getVehicleDoors().getChildLock() != 0) {
			restCar.get(0).getVehicleDoors().setChildLock(0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.CHILD_LOCK_CLOSE + "\","
					+ "\"Message\" : \"Child lock set to 0 \"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
			logger.info("Child lock is closed");

		} else {
			restCar.get(0).getVehicleDoors().setChildLock(1);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.CHILD_LOCK_OPEN + "\","
					+ "\"Message\" : \"Child lock set to 1 \"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
			logger.info("child_lock is OPEN ");
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();
			// AWS
			// AwsMqttHelper help = new AwsMqttHelper();
			// help.connect();
			// AWSIotQos qos = AWSIotQos.QOS0;
			// AWSIotTopic topic = new
			// TestTopicListener("vehicle/vin/control/action/response", qos);
			// help.subscribe(topic);

			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("640",
					" Something went wrong in service layer while handling child lock " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("640", "Something went wrong in service  " + e.getMessage());
		}
		carRepo.save(restCar.get(0));
		return "child lock OPEN-CLOSE state changed: ";

	}

	@Override
	public String handlingMasterLock(int id) {
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		if (restCar.get(0).getMasterLock() != 0) {
			restCar.get(0).setMasterLock(1);

			restCar.get(0).setTrunk(0);
			restCar.get(0).setBonet(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorFrontLeft(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorFrontRight(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorRearLeft(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorRearRight(0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.MASTER_LOCK_CLOSE + "\","
					+ "\"Message\" : \"Master lock close\"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
			logger.info("master_lock is closed");

		} else {
			restCar.get(0).setMasterLock(0);
			restCar.get(0).setTrunk(0);
			restCar.get(0).setBonet(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorFrontLeft(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorFrontRight(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorRearLeft(0);
			restCar.get(0).getVehicleDoors().getDoor().setDoorRearRight(0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.MASTER_LOCK_OPEN + "\","
					+ "\"Message\" : \"Master lock open\"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
			logger.info("master_lock is OPEN ");
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();
			// AWS
			// AwsMqttHelper help = new AwsMqttHelper();
			// help.connect();
			// AWSIotQos qos = AWSIotQos.QOS0;
			// AWSIotTopic topic = new
			// TestTopicListener("vehicle/vin/control/action/response", qos);
			// help.subscribe(topic);

			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("641",
					" Something went wrong in service layer while handling master lock " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("641", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(restCar.get(0));
		return "Master lock OPEN-CLOSE state changed: ";

	}

	@Override
	public String handlingDoorLockWithEnum(int id, CarEnum carenum) {
		Optional<Car> byidobj = carRepo.findById(id);
		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		switch (carenum) {
		case DOORLF:
			if (v.getVehicleDoors().getDoor().getDoorFrontLeft() != 0) {
				v.getVehicleDoors().getDoor().setDoorFrontLeft(0);

				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORFL_OFF + "\","
						+ "\"Message\" : \"door front left set to 0\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door front left is CLOSE ");
			} else {
				v.getVehicleDoors().getDoor().setDoorFrontLeft(1);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORFL_ON + "\","
						+ "\"Message\" : \"door front left set to 1\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door front left is OPEN ");
			}
			break;
		case DOORLR:
			if (v.getVehicleDoors().getDoor().getDoorRearLeft() != 0) {
				v.getVehicleDoors().getDoor().setDoorRearLeft(0);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORRL_OFF + "\","
						+ "\"Message\" : \"door left rear set to 0\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door rear left is CLOSE ");
			} else {
				v.getVehicleDoors().getDoor().setDoorRearLeft(1);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORRL_ON + "\","
						+ "\"Message\" : \"door left rear set to 1\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door rear left is OPEN ");
			}
			break;
		case DOORRF:
			if (v.getVehicleDoors().getDoor().getDoorFrontRight() != 0) {
				v.getVehicleDoors().getDoor().setDoorFrontRight(0);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORFR_OFF + "\","
						+ "\"Message\" : \"door front right set to 0\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door front right is CLOSE ");
			} else {
				v.getVehicleDoors().getDoor().setDoorFrontRight(1);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORFR_ON + "\","
						+ "\"Message\" : \"door front right set to 1\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door front right is OPEN ");
			}
			break;
		case DOORRR:
			if (v.getVehicleDoors().getDoor().getDoorRearRight() != 0) {
				v.getVehicleDoors().getDoor().setDoorRearRight(id);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORRR_OFF + "\","
						+ "\"Message\" : \"door rear right set to 0\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door rear right is CLOSE ");
			} else {
				v.getVehicleDoors().getDoor().setDoorRearRight(1);
				payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
						+ "\"subType\" : \"" + VehicleControl.Actions.DOORRR_ON + "\","
						+ "\"Message\" : \"door rear right set to 1\"" + "}";
				// MQTT => message.setPayload(payload.getBytes());
				logger.info("Door rear right is OPEN ");
			}
			break;
		default:
			System.out.println("Invalid case");
			break;
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// MQTT => service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("642",
					" Something went wrong in service layer while handling door lock" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("642", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "switch case return";
	}

	@Override
	public String handlingWindowMirror(String pos, String side, int value, int id) {
		if (value < 0 || value > 100) {
			throw new BusinessException("643A", "value out of range");
		}
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (side.equals("left") && pos.equals("front")) {
			v.getVehicleDoors().getWindow().getFront().setLeft(value);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.WINDOWMIRROR_FRONT_LEFT + "\","
					+ "\"Message\" : \" WINDOWMIRROR_FRONT_LEFT \"" + "}";

			// MQTT => message.setPayload(payload.getBytes());
		} else if (side.equals("left") && pos.equals("rear")) {
			v.getVehicleDoors().getWindow().getRear().setLeft(value);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.WINDOWMIRROR_REAR_LEFT + "\","
					+ "\"Message\" : \" WINDOWMIRROR_REAR_LEFT \"" + "}";

			// MQTT=> message.setPayload(payload.getBytes());
		} else if (side.equals("right") && pos.equals("rear")) {
			v.getVehicleDoors().getWindow().getRear().setRight(value);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.WINDOWMIRROR_REAR_RIGHT + "\","
					+ "\"Message\" : \" WINDOWMIRROR_REAR_RIGHT \"" + "}";

			// MQTT => message.setPayload(payload.getBytes());
		} else if (side.equals("right") && pos.equals("front")) {
			v.getVehicleDoors().getWindow().getFront().setRight(value);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.WINDOWMIRROR_FRONT_RIGHT + "\","
					+ "\"Message\" : \" WINDOWMIRROR_FRONT_RIGHT \"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
		} else {
			v.getVehicleDoors().getWindow().getFront().setLeft(0);
			v.getVehicleDoors().getWindow().getFront().setRight(0);
			v.getVehicleDoors().getWindow().getRear().setRight(0);
			v.getVehicleDoors().getWindow().getRear().setLeft(0);

		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// MQTT => service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("643B",
					"Something went wrong in service layer while handling window mirror" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("643", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "Window mirror state changed: ";
	}

	public String handlingHeadLight(int id, String headLight) {

		commonCode(id);
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (headLight.equals("lower")) {
			v.getLights().setHead("lower");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.HEADLIGHT_LOWER + "\","
					+ "\"Message\" : \"HEADLIGHT_LOWER\"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
			logger.info("HEADLIGHT_LOWER");
		} else if (headLight.equals("upper")) {
			v.getLights().setHead("upper");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.HEADLIGHT_UPPER + "\","
					+ "\"Message\" : \"HEADLIGHT_UPPER\"" + "}";

			// MQTT => message.setPayload(payload.getBytes());
			logger.info("HEADLIGHT_UPPER");
		} else {
			v.getLights().setHead("off");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.HEADLIGHT_OFF + "\","
					+ "\"Message\" : \"HEADLIGHT_OFF\"" + "}";
			// MQTT => message.setPayload(payload.getBytes());
			logger.info("HEADLIGHT off");
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// MQTT => service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("645B",
					"Something went wrong in service layer while handling side mirror" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("644", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "Head light state changed: ";
	}

	@Override
	public String handlingSideMirror(String side, int value, int id) {
		if (value < 0 || value > 100) {
			throw new BusinessException("645A", "value out of range");
		}
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (side.equals("left")) {
			v.getSideMirror().setLeft(value);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.SIDEMIRROR_LEFT + "\","
					+ "\"Message\" : \" SIDEMIRROR_LEFT \"" + "}";

			// MQTT => message.setPayload(payload.getBytes());
		} else if (side.equals("right")) {
			v.getSideMirror().setRight(value);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.SIDEMIRROR_RIGHT + "\","
					+ "\"Message\" : \" SIDEMIRROR_RIGHT \"" + "}";

			// MQTT => message.setPayload(payload.getBytes());
		} else {
			v.getSideMirror().setRight(0);
			v.getSideMirror().setLeft(0);
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// MQTT => service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (Exception e) {
			throw new BusinessException("645", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "Side mirror state changed: ";
	}

	@Override
	public String defrostFront(int id, int value) {
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);
		v.getHvac().setDefrostFront(value);

		if (v.getHvac().getDefrostFront() != 0) {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.DEFROSTFRONT_ON + "\"," + "\"Message\" : \" 1 \""
					+ "}";

			// MQTT => message.setPayload(payload.getBytes());
		} else {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.DEFROSTFRONT_OFF + "\"," + "\"Message\" : \" 0 \""
					+ "}";

			// MQTT => message.setPayload(payload.getBytes());
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("646",
					"Something went wrong in service layer while handling defrost front" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("646", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		logger.info("modified count = " + v);
		return "DEFROST FRONT ON-OFF state changed ";

	}

	@Override
	public String defrostRear(int id, int value) {
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
		commonCode(id);
		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		v.getHvac().setDefrostRear(value);

		if (v.getHvac().getDefrostRear() != 0) {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.DEFROSTREAR_ON + "\"," + "\"Message\" : \"" + value
					+ "\"}";

			// MQTT => message.setPayload(payload.getBytes());
		} else {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.DEFROSTREAR_OFF + "\"," + "\"Message\" : \"" + value
					+ "\" }";

			// MQTT => message.setPayload(payload.getBytes());
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("647",
					"Something went wrong in service layer while handling defrost rear" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("647", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		logger.info("modified count = " + v);
		return "DEFROST REAR ON-OFF state changed ";
	}

//    "air_circulation": "0-1 where 0 is inner & 1 is outer",
	@Override
	public String handlingAirCirculation(int id, String inout) {
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (inout.equals("outer")) {
			v.getHvac().setAirCirculation(1);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AIRCIRCULATION_OUTER + "\"," + "\"Message\" : \" 1 \""
					+ "}";

			// MQTT=> message.setPayload(payload.getBytes());
			logger.info("AIRCIRCULATION_INNER ");
		} else {
			v.getHvac().setAirCirculation(0);

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AIRCIRCULATION_INNER + "\"," + "\"Message\" : \" 0 \""
					+ "}";

			// MQTT=> message.setPayload(payload.getBytes());
			logger.info("AIRCIRCULATION_OUTER ");
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("648",
					"Something went wrong in service layer while handling AC " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("648", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "AirCirculation state changed: ";
	}

	@Override
	public String frontACState(int id, int state) {
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		v.getHvac().getFront().setAcState(state);

		if (v.getHvac().getFront().getAcState() != 0) {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_STATE_FRONT_ON + "\"," + "\"Message\" : \"" + state
					+ "\"}";

			// MQTT => message.setPayload(payload.getBytes());
		} else {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_STATE_FRONT_OFF + "\"," + "\"Message\" :\"" + state
					+ "\"}";

			// MQTT => message.setPayload(payload.getBytes());
		}
		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("649",
					"Something went wrong in service layer while handling front AC state" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("649", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		logger.info("modified count = " + v);
		return "AC_STATE front ON-OFF state changed ";
	}

	@Override
	public String frontACVentFlow(int id, String ac_vent_flow) {
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (ac_vent_flow.equals("up")) {
			v.getHvac().getFront().setAcVentFlow("up");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_FRONT_UP + "\","
					+ "\"Message\" : \" 0 \"}";

			// MQTT
//			message.setPayload(payload.getBytes());
			logger.info("ac_vent_flow up");
		} else if (ac_vent_flow.equals("down")) {
			v.getHvac().getFront().setAcVentFlow("down");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_FRONT_DOWN + "\","
					+ "\"Message\" : \" 1 \"}";

// MQTT => message.setPayload(payload.getBytes());
			logger.info("ac_vent_flow down");
		} else if (ac_vent_flow.equals("both")) {
			v.getHvac().getFront().setAcVentFlow("both");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_FRONT_BOTH + "\","
					+ "\"Message\" : \" 2   \"}";

//	MQTT=>		message.setPayload(payload.getBytes());
			logger.info("ac vent both");
		} else {
			v.getHvac().getFront().setAcVentFlow("off");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_FRONT_OFF + "\","
					+ "\"Message\" : \"  3  \"}";
		}
		try {
//			MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//			AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);
		} catch (BusinessException e) {
			throw new BusinessException("650",
					"Something went wrong in service layer while handling front AC vent flow" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("650", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "ac_vent_flow state changed: ";
	}

	@Override
	public String frontAcTempChange(int id, int temp) {
		if (temp < 0 || temp > 50) {
			throw new BusinessException("651A", "temp out of range");
		}
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		v.getHvac().getFront().setTemperature(temp);
		System.out.println(v.getHvac().getFront().getTemperature());

		payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
				+ "\"subType\" : \"" + VehicleControl.Actions.AC_TEMPERATURE_FRONT + "\"," + "\"Message\" : \"" + temp
				+ "\" }";

		try {
			// MQTT
			// Connection.Publisher();
			// conn.client.publish("test", message);
			// conn.client.disconnect();

			// MQTT => service.publishmessage(payload);
			// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("651B",
					"Something went wrong in service layer while handling front AC temperature " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("651", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "Temp changing: ";
	}

	@Override
	public String rearACState(int id, int state) {
//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();
		commonCode(id);

		UUID uId = UUID.randomUUID();
		System.out.println("uId" + uId);

		v.getHvac().getRear().setAcState(state);

		if (v.getHvac().getRear().getAcState() != 0) {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control/rear AC\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_STATE_REAR_ON + "\"," + "\"Message\" : \"" + state
					+ "\"}";

//	MQTT =>		message.setPayload(payload.getBytes());
		} else {

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_STATE_REAR_OFF + "\"," + "\"Message\" :  \""
					+ state + "\"}";

			// MQTT => message.setPayload(payload.getBytes());
		}
		try {
//		MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//			service.publishmessage(payload);
//		AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("652",
					"Something went wrong in service layer while handling rear AC state" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("652", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		logger.info("modified count = " + v);
		return "ACState REAR ON-OFF state changed ";
	}

	@Override
	public String rearACVentFlow(String ac_vent_flow, int id) {
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);

		if (restCar == null) {
			logger.error("null ", restCar);
		} else if (ac_vent_flow.equals("up")) {
			v.getHvac().getRear().setAcVentFlow("up");

//			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
//					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_REAR_UP + "\","
//					+ "\"Message\" : \" rear ac_vent_flow  up \"," + "\"  Rear ac_vent_flow is \" \"" + ac_vent_flow
//					+ "}";

			payload = "{\"uId\" : \" " + uId + " \" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_REAR_UP + "\","
					+ "\"Message\" : \" 0 \" }";

//	MQTT =>		message.setPayload(payload.getBytes());
			logger.info("ac_vent_flow up");
		} else if (ac_vent_flow.equals("down")) {
			v.getHvac().getRear().setAcVentFlow("down");

			payload = "{\"uId\" : \" " + uId + " \" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_REAR_DOWN + "\","
					+ "\"Message\" : \" 1 \" }";

//	MQTT =>		message.setPayload(payload.getBytes());
			logger.info("ac_vent_flow down");
		} else if (ac_vent_flow.equals("both")) {
			v.getHvac().getRear().setAcVentFlow("both");

			payload = "{\"uId\" : \" " + uId + " \" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_REAR_BOTH + "\","
					+ "\"Message\" : \"  2 \" }";

			// MQTT => message.setPayload(payload.getBytes());
			logger.info("ac_vent_flow both");
		} else {
			v.getHvac().getRear().setAcVentFlow("off");

			payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
					+ "\"subType\" : \"" + VehicleControl.Actions.AC_VENT_FLOW_REAR_OFF + "\","
					+ "\"Message\" : \" 3 \"," + "\"  front ac_vent_flow is \" \"" + ac_vent_flow + "}";
		}
		try {
//		MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//			service.publishmessage(payload);

// AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (BusinessException e) {
			throw new BusinessException("653",
					"Something went wrong in service layer handling rear AC vent flow" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("653", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "ac_vent_flow state changed: ";
	}

	@Override
	public String rearAcTempChange(int temp, int id) {
		if (temp < 0 || temp > 50) {
			throw new BusinessException("654A", "temp out of range");
		}
		commonCode(id);

//		Optional<Car> byidobj = carRepo.findById(id);
//		Car v = byidobj.get();

		UUID uId = UUID.randomUUID();
		System.out.println("id" + uId);
		v.getHvac().getRear().setTemperature(temp);

		payload = "{\"uId\" : \"" + uId + "\" , " + "\"vin\" : \"" + id + "\"," + "\"type\" : \"control\","
				+ "\"subType\" : \"" + VehicleControl.Actions.AC_TEMPERATURE_REAR + "\"," + "\"Message\" : \"" + temp
				+ "\" }";
		try {
//	 MQTT
//			Connection.Publisher();
//			conn.client.publish("test", message);
//			conn.client.disconnect();

//	MQTT =>		service.publishmessage(payload);
//	AWS
			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);

		} catch (Exception e) {
			throw new BusinessException("654", "Something went wrong in repository  " + e.getMessage());
		}
		carRepo.save(v);
		return "Temp changing: ";

	}

	

}

//@Override
//public Car addNew(@RequestBody Car restCar) {
//	System.out.println("carInterface1");
//
//	if (restCar.getSpeed() < 0 || restCar.getSpeed() > 100) {
//		System.out.println("carInterface");
//		throw new BusinessException("631", "speed out of range");
//	}
//	try {
//		return this.carRepo.insert(restCar);
//	} catch (Exception e) {
//		throw new BusinessException("631",
//				"Something went wrong in service layer while saving new user" + e.getMessage());
//	}
//}
