package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer driverId; 
	
	private String mobile;
	private String password;
	
	@OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
	private Cab cab;

	@OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
	List<TripBooking> tripBookingList = new ArrayList<>();

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Cab getCab() {
		return cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	public List<TripBooking> getTripBookingList() {
		return tripBookingList;
	}

	public void setTripBookingList(List<TripBooking> tripBookingList) {
		this.tripBookingList = tripBookingList;
	}
}
