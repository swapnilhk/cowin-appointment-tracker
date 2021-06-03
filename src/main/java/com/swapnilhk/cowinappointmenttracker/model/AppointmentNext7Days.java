package com.swapnilhk.cowinappointmenttracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AppointmentNext7Days {
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
	@JsonProperty("sessions")
	List<Session> sessions;
}

