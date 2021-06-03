package com.swapnilhk.cowinappointmenttracker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

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
import com.swapnilhk.cowinappointmenttracker.model.QueryConfig;
import com.swapnilhk.cowinappointmenttracker.model.Appointment;

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
    
	private static final int DELAY_IN_MILLI_SECONDS = 12000;
	
    private Logger logger = LoggerFactory.getLogger(CowinAppointmentTracker.class);
    
    private final String dateToCheck;
    
    public CowinAppointmentTracker(){
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.add(Calendar.DATE, 1);
    	this.dateToCheck = new SimpleDateFormat("dd-MM-yyyy").format(gc.getTime());
    }
    
	@Scheduled(fixedDelay = DELAY_IN_MILLI_SECONDS)
	public void checkAppointmentavAilibility() {
		for (QueryConfig filter : filters) {
			logger.info("Checking for distrint {} on {}", filter.getDistrict(), dateToCheck);
			Call<CowinResponse> res = cowinClient.findByDistrict(filter.getDistrict().getId(), dateToCheck);
			try {
				Response<CowinResponse> r = res.execute();
				List<Appointment> appointments = r.body().getSessions().stream().filter(filter.getQuery()).collect(Collectors.toList());
				if (!appointments.isEmpty()) {
					logger.info("{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n{}\n",
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(), appointments.get(0).getPincode(),
							appointments.get(0).getPincode(),
							appointments.get(0).getPincode());
					logger.info("Appointments available for distrint {} on {}. Sending mail...", filter.getDistrict(), dateToCheck);
					sendMail(filter.getDistrict().name(), dateToCheck, appointments, filter.getEmail());
				} else {
					logger.info("No appointments available");
				}
			} catch (IOException e) {
				throw new CowinAppointTrackerException("Exception occured while calling cowin API", e);
			}
		}
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

