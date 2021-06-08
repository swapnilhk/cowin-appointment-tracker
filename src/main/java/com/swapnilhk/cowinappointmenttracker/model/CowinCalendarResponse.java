package com.swapnilhk.cowinappointmenttracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CowinCalendarResponse {
	@JsonProperty("centers")
	List<AppointmentCalendar> centers;
}


