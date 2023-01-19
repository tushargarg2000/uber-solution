package com.driver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/register")
	public Customer registerCustomer(@RequestBody Customer customer){
		Customer newUser = customerService.register(customer);
		return newUser;
	}

	@GetMapping("/customerList")
	public List<Customer> getAllCustomers() {
		List<Customer> list = customerService.getAllCustomers();
		return list;
	}

	@PutMapping("/updatePassword")
	public Customer updateCustomerPassword(@PathVariable Integer customerId, @RequestParam String password){
		return customerService.updatePassword(customerId, password);
	}

	@DeleteMapping("/delete")
	public void deleteCustomer(@PathVariable Integer customerId){
		customerService.deleteCustomer(customerId);
	}

	@GetMapping("/availableCabs")
	public List<Driver> availableDrivers(){
		return customerService.getAvailableDrivers();
	}

	@GetMapping("/allCabs")
	public List<Driver> getListForAllDrivers(){
		return customerService.getAllDrivers();
	}

	@PostMapping("/bookTrip")
	public ResponseEntity<TripBooking> bookTrip(@PathVariable Integer customerId, @RequestParam String fromLocation, @RequestParam String toLocation, @RequestParam Integer distanceInKm) throws Exception {
		TripBooking bookedTrip = customerService.bookTrip(customerId, fromLocation, toLocation, distanceInKm);
		return new ResponseEntity<TripBooking>(bookedTrip, HttpStatus.CREATED);
	}

	@DeleteMapping("/complete/{tripId}")
	public void completeTrip(@PathVariable("tripId") Integer tripId){
		customerService.completeTrip(tripId);
	}

	@DeleteMapping("/cancelTrip")
	public void cancelTrip(@RequestParam Integer tripId){
		customerService.cancelTrip(tripId);
	}
}
