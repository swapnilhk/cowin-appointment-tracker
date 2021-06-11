package com.swapnilhk.cowinappointmenttracker.model;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
        
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Session other = (Session) obj;
			if (sessionId == null) {
				if (other.sessionId != null)
					return false;
			} else if (!sessionId.equals(other.sessionId))
				return false;
			return true;
		}
        
}
