package com.mongo.example.mongodemo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mongo.example.mongodemo.exception.BusinessException;
import com.mongo.example.mongodemo.exception.ControllerException;
import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.models.apimodel.CarEnum;
import com.mongo.example.mongodemo.models.apimodel.Geofence;
import com.mongo.example.mongodemo.models.apimodel.LatLong;
import com.mongo.example.mongodemo.models.apimodel.Location;
import com.mongo.example.mongodemo.models.batterymodel.BatteryCycles;
import com.mongo.example.mongodemo.models.batterymodel.BatteryInterval;
import com.mongo.example.mongodemo.repository.*;
import com.mongo.example.mongodemo.repository.batteryrepository.BatteryCyclesRepository;
import com.mongo.example.mongodemo.repository.batteryrepository.BatteryIntervalRepository;
import com.mongo.example.mongodemo.services.vehiclecommservice.CarInterface;

import com.mongodb.client.MongoClient;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CarController {
//	RabbitConnection conn = RabbitConnection.getInstance();
	MqttMessage message = new MqttMessage();

	Geofence geofenceAttr;
	LatLong vehicleLatLang = new LatLong();

	@Autowired
	private CarRepository carRepo;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	public CarInterface carInterface;

	MongoClient mongoClient;

	@Autowired
	BatteryIntervalRepository batteryIntervalRepository;

	@Autowired
	BatteryCyclesRepository batteryCyclesRepository;

	Query query = null;
	Car restCar = null;

//	@Autowired
//	CarRepositoryInterface carRepositoryInterface;

//	@Autowired
//	MqttPubsubService service;

//	 Subscriber awsMqttHelper =new Subscriber();

	@Value("${aws.abc}")
	private String abc;

	@GetMapping("/hi")
	public MqttMessage getMsg() {
		try {
//			String messageString = "Hello World from Java!";
//				  service.publishmessage(messageString);
//				  awsMqttHelper.subscribe(messageString);
			return message;
		} catch (Exception e) {
			throw new BusinessException("627", "not found");
		}
	}

	@GetMapping("/get")
	public void getMessage() {
		System.out.println("abc " + abc);
	}

	@GetMapping("/getAllBatteryIntervals")
	public List<BatteryInterval> getAllBatteryIntervals() {
		return batteryIntervalRepository.findAll();

	}

	@GetMapping("/getAllBatteryCycles")
	public List<BatteryCycles> getAllBatteryCycles() {
		return batteryCyclesRepository.findAll();

	}

//	Get list
	@GetMapping("/")
	public List<Car> getSpeed() {
		try {
			List<Car> list = carInterface.getData();
			return list;
		} catch (Exception e) {
			throw new BusinessException("628", "empty");
		}
	}

//	Get an object by id
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id) throws UnsupportedEncodingException {
		try {
			Optional<Car> byId = carRepo.findById(id);
//			Car byid = byId.get();
			return ResponseEntity.ok(byId);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("629", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//Map current location
	@GetMapping("/currentlocation/{id}")
	public ResponseEntity<?> getByIdLocation(@PathVariable("id") int id) {
		try {
			Optional<Car> v = carRepo.findById(id);
			System.out.println(v);
			LatLong loc = v.get().getLocation().getCurrentLocation();
			return ResponseEntity.ok(loc);
		} catch (Exception e) {
			throw new BusinessException("630", "not found");
		}
	}

//	Add new object
	@PostMapping("/add")
	public ResponseEntity<?> addNew(@RequestBody Car restCar) {
		try {
			Car saved = carInterface.addNew(restCar);
			return new ResponseEntity<Car>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("631", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

// Trunk open - close
	@PutMapping("/trunk/{id}")
	public ResponseEntity<?> handlingTrunkOpen(@PathVariable("id") int id) {
		try {
			System.out.println("vin" + id);
			String saved = carInterface.handlingTrunk(id);

//			service.publishmessage(saved);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("632", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// AC temperature Increase - Decrease
	@PutMapping("/changeTemp/{actemp}/{id}")
	public ResponseEntity<?> AcTempInc(@PathVariable("id") int id, @PathVariable("actemp") String temp) {
		try {
			String acTemp = carInterface.AcTempChange(id, temp);
			return new ResponseEntity<String>(acTemp, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("633", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	AC on - off
	@PutMapping("/ac/{id}")
	public ResponseEntity<?> handlingACOn(@PathVariable("id") int id) {
		try {
			String saved = carInterface.handlingAirConditioner(id);
			System.out.println(saved);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("634", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}

//	Sunroofslider increment - decrement	
	@PutMapping("/sunroofslider/{slider}/{id}/")
	public ResponseEntity<?> IncreSunroofSlider(@PathVariable("id") int id, @PathVariable("slider") int slide) {
		try {
			String saved = carInterface.handlingSlider(id, slide);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("635", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	engine on - off
	@PutMapping("/engine/{id}/{value}")
	public ResponseEntity<?> handlingEngine(@PathVariable("id") int id, @PathVariable("value") int value) {
		try {
			String saved = carInterface.handlingEngine(id, value);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("636", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	Hazard light on - off
	@PutMapping("/hazardlight/{id}")
	public ResponseEntity<?> handlingHazardLight(@PathVariable("id") int id) {
		try {
			String saved = carInterface.handlingHazardLight(id);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("637", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

// Bonet open - close 	
	@PutMapping("/bonet/{id}")
	public ResponseEntity<?> handlingBonetClose(@PathVariable("id") int id) {
		try {
			String saved = carInterface.handlingBonet(id);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("638", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	Horn
	@PutMapping("/horn/{id}")
	public ResponseEntity<?> handlingHornOn(@PathVariable("id") int id) {
		try {
			String saved = carInterface.handlingHornOn(id);
			System.out.println(saved);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("639", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}

//	Child lock
	@PutMapping("/childLock/{id}")
	public ResponseEntity<?> handlingChildLock(@PathVariable("id") int id) {
		try {
			System.out.println("vin" + id);
			String saved = carInterface.handlingChildLock(id);

			// service.publishmessage(saved);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("640", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	Master lock
	@PutMapping("/masterLock/{id}")
	public ResponseEntity<?> handlingMasterLock(@PathVariable("id") int id) {
		try {
			System.out.println("vin =" + id);
			String saved = carInterface.handlingMasterLock(id);

//			service.publishmessage(saved);
			return new ResponseEntity<String>(saved, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("641", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	Door LF-Left Front, LR-Left Rear, RF-Right Front, RR-Right Rear
	@PutMapping("/doorlockenum/{id}/{CarEnum}") // lock = 0, unlock =1
	public ResponseEntity<?> handlingDoorLockWithEnum(@PathVariable("id") int id,
			@PathVariable("CarEnum") CarEnum carenum) {
		try {
			String content = carInterface.handlingDoorLockWithEnum(id, carenum);
			return new ResponseEntity<String>(content, HttpStatus.CREATED);

		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("642", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
		// return new ResponseEntity<String>(HttpStatus.OK);
	}

//	Window mirror -> pos/side/value/id
	@PutMapping("/windowmirror/{pos}/{side}/{value}/{id}")
	public ResponseEntity<?> handlingWindowMirror(@PathVariable("id") int id, @PathVariable("side") String side,
			@PathVariable("value") int value, @PathVariable("pos") String pos) {
		try {
			String saved = carInterface.handlingWindowMirror(pos, side, value, id);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("643", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	 Head light on - off
	@PutMapping("/headLight/{id}/{pos}/")
	public ResponseEntity<?> handlingHeadLight(@PathVariable("id") int id, @PathVariable("pos") String headLight) {
		try {
			String saved = carInterface.handlingHeadLight(id, headLight);
			System.out.println("saved=>" + saved);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("644", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	SideMirror( String side, int value,int id)
	@PutMapping("/sidemirror/{side}/{value}/{id}")
	public ResponseEntity<?> handlingSideMirror(@PathVariable("id") int id, @PathVariable("side") String side,
			@PathVariable("value") int value) {
		try {
			String saved = carInterface.handlingSideMirror(side, value, id);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("645", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	Defrost front 
	@PutMapping("/defrostFront/{value}/{id}")
	public ResponseEntity<?> defrostFrontOn(@PathVariable("id") int id, @PathVariable("value") int value) {
		try {
			String saved = carInterface.defrostFront(id, value);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("646", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

// Defrost rear
	@PutMapping("/defrostRear/{value}/{id}")
	public ResponseEntity<?> defrostRear(@PathVariable("id") int id, @PathVariable("value") int value) {
		try {
			String saved = carInterface.defrostRear(id, value);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("647", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

// AirCirculation  "0-1 where 0 is inner & 1 is outer"
	@PutMapping("/airCirculation/{inout}/{id}")
	public ResponseEntity<?> handlingAirCirculation(@PathVariable("id") int id, @PathVariable("inout") String inout) {
		try {
			String saved = carInterface.handlingAirCirculation(id, inout);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("648", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	HVAC - front ac state
	@PutMapping("/hvac/front_ac_state/{state}/{id}")
	public ResponseEntity<?> frontACState(@PathVariable("id") int id, @PathVariable("state") int state) {
		try {
			String saved = carInterface.frontACState(id, state);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("649", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	HVAC - front ac vent flow
	@PutMapping("/hvac/front_ac_vent_flow/{pos}/{id}")
	public ResponseEntity<?> frontACVentFlow(@PathVariable("pos") String pos, @PathVariable("id") int id) {
		try {
			String saved = carInterface.frontACVentFlow(id, pos);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("650", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	HVAC - front ac temperature change
	@PutMapping("/hvac/frontAcTempChange/{temp}/{id}")
	public ResponseEntity<?> frontAcTempChange(@PathVariable("id") int id, @PathVariable("temp") int temp) {
		try {
			String acTemp = carInterface.frontAcTempChange(id, temp);
			return new ResponseEntity<String>(acTemp, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("651", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	HVAC - rear ac state
	@PutMapping("/hvac/rear_ac_state/{id}/{state}")
	public ResponseEntity<?> rearACState(@PathVariable("id") int id, @PathVariable("state") int state) {
		try {
			String saved = carInterface.rearACState(id, state);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("652", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	HVAC - rear ac vent flow
	@PutMapping("/hvac/rear_ac_vent_flow/{pos}/{id}")
	public ResponseEntity<?> rearACVentFlow(@PathVariable("pos") String pos, @PathVariable("id") int id) {
		try {
			String saved = carInterface.rearACVentFlow(pos, id);
			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("653", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	HVAC - rear ac temperature change
	@PutMapping("/hvac/rearAcTempChange/{temp}/{id}")
	public ResponseEntity<?> rearAcTempChange(@PathVariable("id") int id, @PathVariable("temp") int temp) {
		try {
			String acTemp = carInterface.rearAcTempChange(temp, id);
			return new ResponseEntity<String>(acTemp, HttpStatus.CREATED);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("654", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

//	delete an object by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		carRepo.deleteById(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.GONE);
	}
}

//@GetMapping("/hi")
//public MqttMessage getMsg(@RequestBody String payload) {
//	try {
//		String messageString = "Hello World from Java!";
//		  conn.client.connect();
//		    MqttMessage message = new MqttMessage();
//	    message.setPayload(messageString.getBytes());
//		  conn.client.publish("iot_data", message);
//		  conn.client.disconnect();
//		  service.publishmessage(payload);
//		return message;
//	} catch (Exception e) {
//		throw new BusinessException("6", "not found");
//	}
//}

//@GetMapping("/getState/{id}")
//public String getState(@PathVariable("id") int id) {
//	try {
//		 String list = carRepositoryInterface.vehicleState(id);
//		return list;
//	} catch (Exception e) {
//		throw new BusinessException("6", "empty");
//	}
//}
//	@PutMapping("/flowDown/{id}")
//	public ResponseEntity<?> flowDownOn(@PathVariable("id") int id) {
//		try {
//			String saved = carInterface.flowDown(id);
//			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
//		} catch (BusinessException e) {
//			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			ControllerException ce = new ControllerException("650", "Something went wrong in controller");
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@PutMapping("/flowUp/{id}")
//	public ResponseEntity<?> flowUpOn(@PathVariable("id") int id) {
//		try {
//			String saved = carInterface.flowUp(id);
//			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
//		} catch (BusinessException e) {
//			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			ControllerException ce = new ControllerException("653", "Something went wrong in controller");
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@PutMapping("/flowBoth/{id}")
//	public ResponseEntity<?> flowBothOff(@PathVariable("id") int id) {
//		try {
//			String saved = carInterface.flowBoth(id);
//			return new ResponseEntity<String>(saved, HttpStatus.CREATED);
//		} catch (BusinessException e) {
//			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			ControllerException ce = new ControllerException("655", "Something went wrong in controller");
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		}
//	}
