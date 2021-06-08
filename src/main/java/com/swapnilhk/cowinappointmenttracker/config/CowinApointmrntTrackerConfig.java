package com.swapnilhk.cowinappointmenttracker.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.swapnilhk.cowinappointmenttracker.client.CowinClient;
import com.swapnilhk.cowinappointmenttracker.model.CalendarQuery;
import com.swapnilhk.cowinappointmenttracker.model.Constants;
import com.swapnilhk.cowinappointmenttracker.model.District;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class CowinApointmrntTrackerConfig {
	
	@Bean
	public CowinClient getCowinClient() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://cdn-api.co-vin.in/api/")
				.addConverterFactory(JacksonConverterFactory.create()).build();
		return retrofit.create(CowinClient.class);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setUsername("swapnilhk@gmail.com");
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    return mailSender;
	}
	
	@Bean
	public List<CalendarQuery> calendarQueryConfig() {
		List<CalendarQuery> ret = new ArrayList<>();
		ret.add(new CalendarQuery(
				"Pranali Bhosle",
				District.PUNE,
				session -> session.getMinAgeLimit() == 18 && session.getVaccine().equalsIgnoreCase(Constants.COVISHIELD) && session.getAvailableCapacityDose1() > 0,
				appointmentCalendar -> Arrays.asList(412208, 411006, 411014, 412207, 412216, 412209, 412220).contains(appointmentCalendar.getPincode()), 
				"swapnilhk@gmail.com", "leenakhade15@gmail.com"));
		ret.add(new CalendarQuery(
				"Monika Jadhav",
				District.NASHIK,
				session -> session.getMinAgeLimit() == 18 && session.getVaccine().equalsIgnoreCase(Constants.COVISHIELD) && session.getAvailableCapacityDose1() > 0,
				appointmentCalendar -> Arrays.asList(420003).contains(appointmentCalendar.getPincode()) || appointmentCalendar.getPincode().toString().startsWith("422"), 
				"swapnilhk@gmail.com", "leenakhade15@gmail.com"));
		return ret;
	}
}

