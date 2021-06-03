package com.swapnilhk.cowinappointmenttracker.model;

import java.util.function.Predicate;

import lombok.Getter;

@Getter
public class QueryConfig {
	private String name;
	private District district;
	private Predicate<Appointment> query;
	private String[] email;

	public QueryConfig(String name, District district, Predicate<Appointment> query, String...email) {
		this.name = name;
		this.district = district;
		this.query = query;
		this.email = email;
	}
}

