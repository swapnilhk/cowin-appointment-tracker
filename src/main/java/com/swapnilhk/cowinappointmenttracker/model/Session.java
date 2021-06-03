package com.swapnilhk.cowinappointmenttracker.model;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Session {
	@JsonProperty("session_id")
	String sessionId;
        @JsonProperty("date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	Date date;
        @JsonProperty("available_capacity")
        Double availableCapacity;
        @JsonProperty("min_age_limit")
        Integer minAgeLimit;
        @JsonProperty("vaccine")
        String vaccine;
        @JsonProperty("slots")
        List<String> slots;
        @JsonProperty("available_capacity_dose1")
        Double availableCapacityDose1;
        @JsonProperty("available_capacity_dose2")
        Double availableCapacityDose2;
}
