package com.mongo.example.mongodemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.example.mongodemo.models.batterydataapimodel.BatteryData;
import com.mongo.example.mongodemo.services.batterydataservice.BatteryService;

@RestController
@RequestMapping("/api/v1")
public class BatteryController {

	@Autowired
	private BatteryService batteryService;

	@GetMapping("/home")
	public String home() {
		return "Welcome to sourabh application";
	}

	@GetMapping("/battery")
	public List<BatteryData> geBatteries() {
		return this.batteryService.getBattery();

	}

	@GetMapping("/battery/{id}")
	public BatteryData getBattery(@PathVariable String id) {
		return this.batteryService.getBattery(Integer.parseInt(id));
	}

	@PostMapping("/battery")
	public BatteryData addBattery(@RequestBody BatteryData battery) {
		return this.batteryService.addBattery(battery);
	}

}