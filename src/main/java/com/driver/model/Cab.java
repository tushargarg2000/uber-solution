package com.driver.model;

import javax.persistence.*;

@Entity
public class Cab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private Integer perKmRate;
	private Boolean available;

	@OneToOne
	@JoinColumn
	Driver driver;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getPerKmRate() {
		return perKmRate;
	}

	public void setPerKmRate(Integer perKmRate) {
		this.perKmRate = perKmRate;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
