package com.swapnilhk.cowinappointmenttracker.config;

import java.util.ArrayList;
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
		ret.add(new CalendarQuery("Pranali Bhosle", District.PUNE,
				session -> session.getMinAgeLimit() == 45 && session.getVaccine().equalsIgnoreCase(Constants.COVAXIN)
						&& session.getAvailableCapacityDose2() > 0,
				new int[] { 411027 }, "swapnilhk@gmail.com"));
		return ret;
	}
}


