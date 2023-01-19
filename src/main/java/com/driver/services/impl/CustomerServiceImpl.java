package com.driver.services.impl;

import java.util.List;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	DriverRepository driverRepository;

	@Autowired
	TripBookingRepository tripBookingRepository;

	@Override
	public Customer register(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> list = customerRepository.findAll();
		return list;
	}

	@Override
	public Customer updatePassword(Integer customerId, String password){
		Customer customer = customerRepository.findById(customerId).get();
		customer.setPassword(password);
		return customerRepository.save(customer);

	}

	@Override
	public void deleteCustomer(Integer customerId) {
		Customer customer = customerRepository.findById(customerId).get();
		customerRepository.delete(customer);
	}

	@Override
	public List<Driver> getAvailableDrivers() {
		List<Driver> listOfAvailableDrivers = driverRepository.findByCabAvailable("YES");
		return listOfAvailableDrivers;
	}

	@Override
	public List<Driver> getAllDrivers() {
		List<Driver> listOfDrivers = driverRepository.findAll();
		return listOfDrivers;
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free
		TripBooking tripBooking = new TripBooking();
		Driver driver = driverRepository.findByCabAvailable("YES").get(0);
		if(driver == null){
			throw new Exception("No cab available!");
		}
		Customer customer = customerRepository.findById(customerId).get();
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(driver);
		driver.getCab().setAvailable("NO");
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		tripBookingRepository.save(tripBooking);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		TripBooking tripBooking = tripBookingRepository.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBookingRepository.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//set bill and status
		TripBooking tripBooking = tripBookingRepository.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		int bill = tripBooking.getDriver().getCab().getPerKmRate()*tripBooking.getDistanceInKm();
		tripBooking.setBill(bill);
		tripBooking.getDriver().getCab().setAvailable("YES");
	}
}
