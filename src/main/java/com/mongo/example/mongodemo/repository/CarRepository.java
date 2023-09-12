package com.mongo.example.mongodemo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.mongo.example.mongodemo.models.apimodel.*;

@Repository
public interface CarRepository extends MongoRepository<Car, Integer> {

	
}
