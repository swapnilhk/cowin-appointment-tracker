package com.swapnilhk.cowinappointmenttracker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.swapnilhk.cowinappointmenttracker.client.CowinClient;
import com.swapnilhk.cowinappointmenttracker.exception.CowinAppointTrackerException;
import com.swapnilhk.cowinappointmenttracker.model.CowinResponse;
import com.swapnilhk.cowinappointmenttracker.model.CowinResponseNext7Days;
import com.swapnilhk.cowinappointmenttracker.model.QueryConfig;
import com.swapnilhk.cowinappointmenttracker.model.Appointment;
import com.swapnilhk.cowinappointmenttracker.model.AppointmentNext7Days;

import retrofit2.Call;
import retrofit2.Response;

@Configuration
@EnableScheduling
public class CowinAppointmentTracker {
	
	@Autowired
	private CowinClient cowinClient;
	
	@Autowired
    	private JavaMailSender emailSender;
	
    	@Autowired
    	private List<QueryConfig> filters;
    
	private static final int TIME_BETWEEN_CALLS = 3000;
	
    	private Logger logger = LoggerFactory.getLogger(CowinAppointmentTracker.class);
    
    	private String dateToCheck;
   
        private int index = 0;

    	public CowinAppointmentTracker(){
    		this.dateToCheck = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	}
    
	@Scheduled(cron = "0 0 0 * * *")
	public void changeDate(){
		this.dateToCheck = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Changing date. new date: {}", this.dateToCheck);
	}

	//@Scheduled(fixedDelay = TIME_BETWEEN_CALLS)
	public void checkDailyAppointmentavAilibility() {
		QueryConfig filter = filters.get(index);
		logger.info("Checking for distrint {} on {}", filter.getDistrict(), dateToCheck);
		Call<CowinResponse> res = cowinClient.findByDistrict(filter.getDistrict().getId(), dateToCheck);
		try {
			Response<CowinResponse> r = res.execute();
			List<Appointment> appointments = r.body().getSessions().stream().filter(filter.getQuery()).collect(Collectors.toList());
			if (!appointments.isEmpty()) {
				logger.info("Appointments available for distrint {} on {}. Sending mail...", filter.getDistrict(), dateToCheck);
				sendMail(filter.getDistrict().name(), dateToCheck, appointments, filter.getEmail());
			} else {
				logger.info("No appointments available");
			}
		} catch (IOException e) {
			throw new CowinAppointTrackerException("Exception occured while calling cowin API", e);
		}
		index = (index + 1) % filters.size(); 
	}

	@Scheduled(fixedDelay = TIME_BETWEEN_CALLS)
        public void checkWeeklyAppointmentavAilibility() {
                QueryConfig filter = filters.get(index);
		Call<CowinResponseNext7Days> res = null;
		if(filter.getPincode() != null && !filter.getPincode().equals("")){
			logger.info("Checking for pincode {} for week starting with {}", filter.getPincode(), dateToCheck);
                        res = cowinClient.findCalendarPin(filter.getPincode(), dateToCheck);
		}
		else {
                	logger.info("Checking for distrint {} for week starting with {}", filter.getDistrict(), dateToCheck);
                	res = cowinClient.findCalendarDistrict(filter.getDistrict().getId(), dateToCheck);
		}
		try {
                        Response<CowinResponse> r = res.execute();
                        List<Appointment> appointments = r.body().getSessions().stream().filter(filter.getQuery()).collect(Collectors.toList());
                        if (!appointments.isEmpty()) {
                                logger.info("Appointments available for distrint {} on {}. Sending mail...", filter.getDistrict(), dateToCheck);
                                sendMail(filter.getDistrict().name(), dateToCheck, appointments, filter.getEmail());
                        } else {
                                logger.info("No appointments available");
                        }
                } catch (IOException e) {
                        throw new CowinAppointTrackerException("Exception occured while calling cowin API", e);
                }
                index = (index + 1) % filters.size();
        }

	private void sendMail(String city, String date, List<Appointment> appointments, String...email) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("swapnilhk@localhost.com");
        message.setTo(email); 
		message.setSubject(String.format("Appointment Available at %s", city));
		StringBuilder b = new StringBuilder();
		for (Appointment appointment : appointments) {
			b.append(String.format("Name: %s%nAddress: %s%nPin Code: %d%nAvailable Capacity: %f%nVaccineType: %s%nDate: %s%nMinimum Age: %d%n%n",
					appointment.getName(), appointment.getAddress(), appointment.getPincode(),
					appointment.getAvailableCapacity(), appointment.getVaccine(), date,
					appointment.getMinAgeLimit()));
		}
		message.setText(b.toString());
        emailSender.send(message);
	}
}

