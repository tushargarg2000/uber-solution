package com.driver.model;

import javax.persistence.*;

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
	@JoinColumn
	private Customer customer;

	@ManyToOne
	@JoinColumn
	private Driver driver;
}
