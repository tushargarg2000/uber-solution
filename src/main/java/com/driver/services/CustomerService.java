package com.driver.services;


import java.util.List;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.model.TripBooking;


public interface CustomerService {

	public Customer register(Customer customer);
	
	public List<Customer> getAllCustomers();
    
	public Customer updatePassword(Integer customerId, String password) ;
	
	public void deleteCustomer(Integer customerId);

	public List<Driver> getAvailableDrivers();
	
	public List<Driver> getAllDrivers();
	
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception;
	
	public void cancelTrip(Integer tripId);

	public void completeTrip(Integer tripId);
	
}
