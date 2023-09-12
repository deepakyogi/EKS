package com.mongo.example.mongodemo.repository.batteryrepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.example.mongodemo.models.batterymodel.BatteryInterval;


@Repository
public interface BatteryIntervalRepository extends MongoRepository<BatteryInterval, String> {

}
