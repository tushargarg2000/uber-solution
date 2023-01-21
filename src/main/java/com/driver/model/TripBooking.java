package com.driver.model;

import javax.persistence.*;

@Entity
public class TripBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tripBookingId;

	private String fromLocation; //The start location of the trip
	private String toLocation; //The end location of the trip
	private int distanceInKm;

	@Enumerated(value = EnumType.STRING)
	private TripStatus status;
	private int bill;

	@ManyToOne
	@JoinColumn
	private Customer customer;

	@ManyToOne
	@JoinColumn
	private Driver driver;

	public Integer getTripBookingId() {
		return tripBookingId;
	}

	public void setTripBookingId(Integer tripBookingId) {
		this.tripBookingId = tripBookingId;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public int getDistanceInKm() {
		return distanceInKm;
	}

	public void setDistanceInKm(int distanceInKm) {
		this.distanceInKm = distanceInKm;
	}

	public TripStatus getStatus() {
		return status;
	}

	public void setStatus(TripStatus status) {
		this.status = status;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bill) {
		this.bill = bill;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
