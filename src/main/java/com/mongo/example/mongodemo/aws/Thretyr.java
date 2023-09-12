package com.mongo.example.mongodemo.aws;

import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.repository.CarRepository;

public class Thretyr extends Thread{
	private CarRepository carRepo;
	Car car;

	public Thretyr(Car car) {
		this.car =car;
	}

	@Override
	public void run() {
		try {
//			service.publishmessage(payload);
//			AwsMqttHelper.instance.publish(myConstants.TOPIC_ACTIONS_REQUEST, payload);
			Car saved = carRepo.save(car);
		     System.out.println(saved+"saved");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
