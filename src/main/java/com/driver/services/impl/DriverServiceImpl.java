package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository;

	@Autowired
	CabRepository cabRepository;

	//Shyd yeh register driver waala code aapne shi krdia tha....so not rectifying it.
	@Override
	public Driver register(Driver driver){
		return driverRepository.save(driver);
	}


	//ismein bhi deleteById waale case testing k tym
	@Override
	public void removeDriver(int driverId){
		Driver driver = driverRepository.findById(driverId).get();
		driverRepository.delete(driver);
	}



	@Override
	public void updateStatus(int driverId){
//		Driver driver = driverRepository.findById(driverId).get();
//
//		String currentStatus = driver.getCab().getAvailable();
//		String newStatus = "YES";
//		if(currentStatus == "YES"){
//			newStatus = "NO";
//		}
//		driver.getCab().setAvailable(newStatus);
//		driverRepository.save(driver);

		//students might be going through the normal route of making changes directly in the cabRepository iteself.


		//getter setter ka check krlena....probably allArgs and noArgs nhi chlenge...so will have to write them manually
		//And also
		Driver driver = driverRepository.findById(driverId).get();
		Cab cab = driver.getCab();

		cab.setAvailable("NO");
		cabRepository.save(cab);
	}
}
