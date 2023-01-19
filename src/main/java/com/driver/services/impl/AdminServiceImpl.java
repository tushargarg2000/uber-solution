package com.driver.services.impl;

import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	DriverRepository driverRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Admin adminRegister(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		Admin admin = adminRepository.findById(adminId).get();
		admin.setPassword(password);
		Admin updated = adminRepository.save(admin);
		return updated;
	}

	@Override
	public void deleteAdmin(int adminId){
		Admin admin = adminRepository.findById(adminId).get();
		adminRepository.delete(admin);
	}

	@Override
	public List<Driver> getListOfDrivers() {
		List<Driver> listOfDrivers = driverRepository.findAll();
		return listOfDrivers;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		List<Customer> listOfCustomers = customerRepository.findAll();
		return listOfCustomers;
	}

}
