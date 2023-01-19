package com.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.driver.model.Driver;
import com.driver.services.DriverService;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {
	
	@Autowired
	DriverService driverService;
	
	@PostMapping(value = "/register")
	public Driver registerDriver(@RequestBody Driver driver){
		return driverService.register(driver);
	}
	
	@DeleteMapping(value = "/delete")
	public void deleteDriver(@PathVariable Integer driverId){
		driverService.removeDriver(driverId);
	}

	@PutMapping("/status")
	public void updateStatus(@RequestParam Integer driverId){
		driverService.updateStatus(driverId);
	}
}
