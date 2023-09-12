package com.mongo.example.mongodemo.aws;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.repository.CarRepository;

@Service
public class MyClass {

	@Autowired
	public static CarRepository carRepo ;
	
	public int myMethod(int i) {
		System.out.println(carRepo);
		Optional<Car> myVar = carRepo.findById(i);
		System.out.println(myVar.get());
		return i;
	}
}
