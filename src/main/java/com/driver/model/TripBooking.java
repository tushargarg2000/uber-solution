package com.driver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class TripBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tripBookingId;

	private String fromLocation;
	private String toLocation;
	private int distanceInKm;

	private TripStatus status;
	private int bill;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private Driver driver;
}
