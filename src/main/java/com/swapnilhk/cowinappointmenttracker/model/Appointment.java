package com.swapnilhk.cowinappointmenttracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Appointment {
	@JsonProperty("center_id")
	Integer centerId;
	@JsonProperty("name")
	String name;
	@JsonProperty("address")
	String address;
	@JsonProperty("state_name")
	String stateName;
	@JsonProperty("district_name")
	String districtName;
	@JsonProperty("block_name")
	String blockName;
	@JsonProperty("pincode")
	Integer pincode;
	@JsonProperty("from")
	String from;
	@JsonProperty("to")
	String to;
	@JsonProperty("lat")
	Integer lat;
	@JsonProperty("long")
	Integer longitude;
	@JsonProperty("fee_type")
	String feeType;
	@JsonProperty("session_id")
	String sessionId;
	@JsonProperty("date")
	String date;
	@JsonProperty("available_capacity_dose1")
	Double availableCapacityDose1;
	@JsonProperty("available_capacity_dose2")
	Double availableCapacityDose2;
	@JsonProperty("available_capacity")
	Double availableCapacity;
	@JsonProperty("fee")
	String fee;
	@JsonProperty("min_age_limit")
	Integer minAgeLimit;
	@JsonProperty("vaccine")
	String vaccine;
	@JsonProperty("slots")
	List<String> slots;
}

