package com.mongo.example.mongodemo.repository.batteryrepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.example.mongodemo.models.apimodel.Car;
import com.mongo.example.mongodemo.models.batterymodel.BatteryCycles;
import com.mongo.example.mongodemo.models.batterymodel.BatteryInterval;

@Repository
public interface BatteryCyclesRepository extends MongoRepository<BatteryCycles, String>  {

}
