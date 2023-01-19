package com.driver.services.impl;

import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository;

	@Override
	public Driver register(Driver driver){
		return driverRepository.save(driver);
	}

	@Override
	public void removeDriver(int driverId){
		Driver driver = driverRepository.findById(driverId).get();
		driverRepository.delete(driver);
	}

	@Override
	public void updateStatus(int driverId){
		Driver driver = driverRepository.findById(driverId).get();
		String currentStatus = driver.getCab().getAvailable();
		String newStatus = "YES";
		if(currentStatus == "YES"){
			newStatus = "NO";
		}
		driver.getCab().setAvailable(newStatus);
		driverRepository.save(driver);
	}
}
