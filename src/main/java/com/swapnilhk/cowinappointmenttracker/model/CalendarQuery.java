package com.swapnilhk.cowinappointmenttracker.model;

import java.util.function.Predicate;

import lombok.Getter;

@Getter
public class CalendarQuery {
	private String name;
	private District district;
	private Predicate<Session> query;
	private Predicate<AppointmentCalendar> calendarQuery;
	private String[] email;

	public CalendarQuery(String name, District district, Predicate<Session> query, Predicate<AppointmentCalendar> calendarQuery, String...email) {
		this.name = name;
		this.district = district;
		this.query = query;
		this.calendarQuery = calendarQuery;
		this.email = email;
	}
}

