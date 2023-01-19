package com.driver.services;

import com.driver.model.Driver;

public interface DriverService {

		public Driver register(Driver driver);
		public void removeDriver(int driverId);
		public void updateStatus(int driverId);
}
