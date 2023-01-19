package com.driver.model;

import javax.persistence.*;

import lombok.Data;
@Data
@Entity
public class Cab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private Integer perKmRate;
	private String available;

	@OneToOne
	@JoinColumn
	Driver driver;
}
