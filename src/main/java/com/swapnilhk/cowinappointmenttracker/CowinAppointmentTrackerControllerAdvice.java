package com.swapnilhk.cowinappointmenttracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.swapnilhk.cowinappointmenttracker.exception.CowinAppointTrackerException;

@ControllerAdvice
public class CowinAppointmentTrackerControllerAdvice {
	
    Logger logger = LoggerFactory.getLogger(CowinAppointmentTrackerControllerAdvice.class);

	public void handle(CowinAppointTrackerException e) {
        logger.error(e.getMessage(), e);
    }
	
	public void handle(Exception e) {
        logger.error("Unhandeled Exception", e);
    }
}

