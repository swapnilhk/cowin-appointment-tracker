package com.swapnilhk.cowinappointmenttracker.model;

import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CalendarQuery {
	private String name;
	private District district;
	private Predicate<Session> query;
	private List<Integer> pincodes;
	private String[] email;

	public CalendarQuery(String name, District district, Predicate<Session> query, int[] pincodes, String...email) {
		this.name = name;
		this.district = district;
		this.query = query;
		this.pincodes = new ArrayList<>();
		for(int poncode: pincodes) {
			this.pincodes.add(poncode);
		}
		this.email = email;
	}
}


