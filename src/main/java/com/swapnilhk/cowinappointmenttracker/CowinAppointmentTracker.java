package com.swapnilhk.cowinappointmenttracker;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.swapnilhk.cowinappointmenttracker.model.Session;
import com.swapnilhk.cowinappointmenttracker.model.AppointmentCalendar;
import com.swapnilhk.cowinappointmenttracker.model.CalendarQuery;
import com.swapnilhk.cowinappointmenttracker.model.CowinCalendarResponse;

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
	private List<CalendarQuery> calendarQuery;

	private static final int TIME_BETWEEN_CALLS = 3000;

	private Logger logger = LoggerFactory.getLogger(CowinAppointmentTracker.class);

	private String dateToCheck;

	private int index = 0;

	public CowinAppointmentTracker() {
		this.dateToCheck = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void changeDate() {
		this.dateToCheck = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Changing date. new date: {}", this.dateToCheck);
	}

	@Scheduled(fixedDelay = TIME_BETWEEN_CALLS)
	public void checkWeeklyAppointmentavAilibility() {
		CalendarQuery query = calendarQuery.get(index);
		logger.info("Checking for distrint {} for week starting with {}", query.getDistrict(), dateToCheck);
		Call<CowinCalendarResponse> res = cowinClient.calendarByDistrict(query.getDistrict().getId(), dateToCheck);
		try {
			Response<CowinCalendarResponse> r = res.execute();
			// Filter centers by pincode
			List<AppointmentCalendar> filteredCenters = r.body().getCenters().stream()
					.filter(query.getCalendarQuery()).collect(Collectors.toList());
			// Filter sessions according to calendar query criteria
			filteredCenters.stream().forEach(center -> {
				center.setSessions(center.getSessions().stream().filter(query.getQuery()).collect(Collectors.toList()));
			});
			// Filters centers without any available sessions with given filter criteria
			filteredCenters = filteredCenters.stream().filter(center -> !center.getSessions().isEmpty()).collect(Collectors.toList());
			if (!filteredCenters.isEmpty()) {
				logger.info("Appointments available. Details are as follows: {}. Sending mail...", filteredCenters);
				sendMail(query.getDistrict().name(), filteredCenters, query.getEmail());
			} else {
				logger.info("No appointments available");
			}
		} catch (IOException e) {
			throw new CowinAppointTrackerException("Exception occured while calling cowin API", e);
		}
		index = (index + 1) % calendarQuery.size();
	}
	

	private void sendMail(String city, List<AppointmentCalendar> appointments, String... email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("swapnilhk@localhost.com");
		message.setTo(email);
		message.setSubject(String.format("Appointment Available at %s", city));
		StringBuilder b = new StringBuilder();
		for (AppointmentCalendar appointment : appointments) {
			for (Session s : appointment.getSessions()) {
				b.append(String.format(
						"Name: %s%nDate: %s%nAddress: %s%nPin Code: %d%nVaccineType: %s%nAvailable Capacity Dose One: %.2f%nAvailable Capacity Dose Two: %.2f%nMinimum Age: %d%n%n",
						appointment.getName(), new SimpleDateFormat("dd-MM-yyyy").format(s.getDate()), appointment.getAddress(), appointment.getPincode(),
						s.getVaccine(), s.getAvailableCapacityDose1(), s.getAvailableCapacityDose2(),
						s.getMinAgeLimit()));
			}

		}
		message.setText(b.toString());
		emailSender.send(message);}

}

