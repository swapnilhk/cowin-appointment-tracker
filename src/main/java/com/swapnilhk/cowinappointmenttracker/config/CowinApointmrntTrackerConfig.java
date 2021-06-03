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
import com.swapnilhk.cowinappointmenttracker.model.Constants;
import com.swapnilhk.cowinappointmenttracker.model.District;
import com.swapnilhk.cowinappointmenttracker.model.QueryConfig;

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
	    mailSender.setPassword("xggdxjbvjymwutbm");
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    return mailSender;
	}
	
	@Bean
	public List<QueryConfig> queryConfig(){
		List<QueryConfig> ret = new ArrayList<>();
		ret.add(new QueryConfig(
				"Pranali Bhosle",
				District.PUNE, 
				a -> a.getMinAgeLimit() == 18 
				&& (a.getPincode().toString().startsWith("412208")) 
				&& a.getVaccine().equalsIgnoreCase(Constants.COVISHIELD)
				&& a.getAvailableCapacityDose1() > 0,
				"swapnilhk@gmail.com", "leenakhade15@gmail.com"));
		ret.add(new QueryConfig(
				"Monika Jadhav",
				District.NASHIK, 
				a -> a.getMinAgeLimit() == 18 
				&& (a.getPincode().toString().startsWith("422") || a.getPincode().toString().startsWith("420003"))
				&& a.getVaccine().equalsIgnoreCase(Constants.COVISHIELD)
				&& a.getAvailableCapacityDose1() > 0,
				"swapnilhk@gmail.com", "leenakhade15@gmail.com"));
		ret.add(new QueryConfig(
				"Saurabh Ambadkar",
				District.NAGPUR, 
				a -> a.getMinAgeLimit() == 18 
				&& a.getPincode().toString().startsWith("440")
				&& a.getVaccine().equalsIgnoreCase(Constants.COVAXIN)
				&& a.getAvailableCapacityDose2() > 0,
				"swapnilhk@gmail.com", "saurabh.ambadkar2804@gmail.com"));
		/*ret.add(new QueryConfig(
                                "Saurabh Ambadkar",
                                District.NAGPUR,
                                a -> a.getMinAgeLimit() == 18
                                && a.getPincode().toString().startsWith("482001")
                                && a.getVaccine().equalsIgnoreCase(Constants.COVISHIELD)
                                && a.getAvailableCapacityDose1() > 0,
                                "swapnilhk@gmail.com", "atulsingh.yadav@hotmail.com"))i;*/
		return ret;
	}
}

