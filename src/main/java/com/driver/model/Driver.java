package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer driverId; 
	
	private String mobile;
	private String password;
	
	@OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
	private Cab cab;

	@OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
	List<TripBooking> tripBookingList;

}
