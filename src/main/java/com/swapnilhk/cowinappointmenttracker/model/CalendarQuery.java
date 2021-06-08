package com.swapnilhk.cowinappointmenttracker.model;

import java.util.function.Predicate;
import java.util.Arrays;

import lombok.Getter;

@Getter
public class CalendarQuery {
	private String name;
	private District district;
	private Predicate<AppointmentNext7Days> query;
	private List pincodes;
	private String[] email;

	public QueryConfig(String name, District district, Predicate<AppointmentNext7Days> query, int[] pincodes, String...email) {
		this.name = name;
		this.district = district;
		this.query = query;
		this.pincodes = Arrays.asList(pincodes);
		this.email = email;
	}
}

